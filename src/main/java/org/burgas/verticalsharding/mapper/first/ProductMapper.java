package org.burgas.verticalsharding.mapper.first;

import lombok.RequiredArgsConstructor;
import org.burgas.verticalsharding.dto.first.ProductRequest;
import org.burgas.verticalsharding.dto.first.ProductResponse;
import org.burgas.verticalsharding.entity.first.Product;
import org.burgas.verticalsharding.mapper.EntityMapper;
import org.burgas.verticalsharding.repository.first.CategoryRepository;
import org.burgas.verticalsharding.repository.first.ProductRepository;
import org.springframework.stereotype.Component;

import static java.util.Optional.ofNullable;
import static org.burgas.verticalsharding.message.first.ProductMessages.*;

@Component
@RequiredArgsConstructor
public final class ProductMapper implements EntityMapper<ProductRequest, Product, ProductResponse> {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public Product toEntity(ProductRequest productRequest) {
        Long productId = this.handleData(productRequest.getId(), 0L);
        return this.productRepository.findById(productId)
                .map(
                        product -> Product.builder()
                                .id(product.getId())
                                .model(this.handleData(productRequest.getModel(), product.getModel()))
                                .characteristics(this.handleData(productRequest.getCharacteristics(), product.getCharacteristics()))
                                .price(this.handleData(productRequest.getPrice(), product.getPrice()))
                                .category(
                                        this.handleData(
                                                this.categoryRepository
                                                        .findById(productRequest.getId() == null ? 0L : productRequest.getId())
                                                        .orElse(null),
                                                product.getCategory()
                                        )
                                )
                                .build()
                )
                .orElseGet(
                        () -> Product.builder()
                                .model(this.handleDataException(productRequest.getModel(), PRODUCT_FIELD_MODEL_EMPTY.getMessage()))
                                .characteristics(this.handleDataException(productRequest.getCharacteristics(), PRODUCT_FIELD_CHARACTERISTICS_EMPTY.getMessage()))
                                .price(this.handleDataException(productRequest.getPrice(), PRODUCT_FIELD_PRICE_EMPTY.getMessage()))
                                .category(
                                        this.handleDataException(
                                                this.categoryRepository
                                                        .findById(productRequest.getCategoryId() == null ? 0L : productRequest.getCategoryId())
                                                        .orElse(null),
                                                PRODUCT_FIELD_CATEGORY_EMPTY.getMessage()
                                        )
                                )
                                .build()
                );
    }

    @Override
    public ProductResponse toResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .model(product.getModel())
                .characteristics(product.getCharacteristics())
                .price(product.getPrice())
                .category(
                        ofNullable(product.getCategory()).map(this.categoryMapper::toResponse)
                                .orElse(null)
                )
                .build();
    }
}
