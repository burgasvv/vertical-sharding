package org.burgas.replicashard.service.first;

import lombok.RequiredArgsConstructor;
import org.burgas.replicashard.dto.first.ProductRequest;
import org.burgas.replicashard.dto.first.ProductResponse;
import org.burgas.replicashard.entity.first.Product;
import org.burgas.replicashard.exception.ProductNotFoundException;
import org.burgas.replicashard.mapper.first.ProductMapper;
import org.burgas.replicashard.repository.first.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.burgas.replicashard.message.first.ProductMessages.PRODUCT_DELETED;
import static org.burgas.replicashard.message.first.ProductMessages.PRODUCT_NOT_FOUND_EXCEPTION;
import static org.springframework.transaction.annotation.Isolation.REPEATABLE_READ;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;
import static org.springframework.transaction.annotation.Propagation.SUPPORTS;

@Service
@RequiredArgsConstructor
@Transactional(
        readOnly = true, propagation = SUPPORTS,
        transactionManager = "postgresFirstTransactionManager"
)
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public List<ProductResponse> findAll() {
        return this.productRepository.findAll()
                .parallelStream()
                .map(this.productMapper::toResponse)
                .collect(Collectors.toList());
    }

    public ProductResponse findById(final Long productId) {
        return this.productRepository.findById(productId == null ? 0L : productId)
                .map(this.productMapper::toResponse)
                .orElseThrow(
                        () -> new ProductNotFoundException(PRODUCT_NOT_FOUND_EXCEPTION.getMessage())
                );
    }

    @Transactional(
            isolation = REPEATABLE_READ, propagation = REQUIRED,
            rollbackFor = Exception.class, transactionManager = "postgresFirstTransactionManager"
    )
    public ProductResponse createOrUpdate(final ProductRequest productRequest) {
        return this.productMapper.toResponse(
                this.productRepository.save(this.productMapper.toEntity(productRequest))
        );
    }

    @Transactional(
            isolation = REPEATABLE_READ, propagation = REQUIRED,
            rollbackFor = Exception.class, transactionManager = "postgresFirstTransactionManager"
    )
    public List<ProductResponse> createOrUpdateProducts(List<ProductRequest> productRequests) {
        List<Product> products = productRequests.stream().map(this.productMapper::toEntity)
                .toList();
        return this.productRepository.saveAll(products)
                .stream()
                .map(this.productMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(
            isolation = REPEATABLE_READ, propagation = REQUIRED,
            rollbackFor = Exception.class, transactionManager = "postgresFirstTransactionManager"
    )
    public String delete(final Long productId) {
        return this.productRepository.findById(productId == null ? 0L : productId)
                .map(
                        product -> {
                            this.productRepository.delete(product);
                            return PRODUCT_DELETED.getMessage();
                        }
                )
                .orElseThrow(
                        () -> new ProductNotFoundException(PRODUCT_NOT_FOUND_EXCEPTION.getMessage())
                );
    }
}
