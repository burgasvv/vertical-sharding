package org.burgas.replicashard.service.first;

import lombok.RequiredArgsConstructor;
import org.burgas.replicashard.dto.first.CategoryRequest;
import org.burgas.replicashard.dto.first.CategoryResponse;
import org.burgas.replicashard.exception.CategoryNotFoundException;
import org.burgas.replicashard.mapper.first.CategoryMapper;
import org.burgas.replicashard.repository.first.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.burgas.replicashard.message.first.CategoryMessages.CATEGORY_DELETED;
import static org.burgas.replicashard.message.first.CategoryMessages.CATEGORY_NOT_FOUND;
import static org.springframework.transaction.annotation.Isolation.REPEATABLE_READ;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;
import static org.springframework.transaction.annotation.Propagation.SUPPORTS;

@Service
@RequiredArgsConstructor
@Transactional(
        readOnly = true, propagation = SUPPORTS,
        transactionManager = "postgresFirstTransactionManager"
)
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<CategoryResponse> findAll() {
        return this.categoryRepository.findAll()
                .parallelStream()
                .map(this.categoryMapper::toResponse)
                .collect(Collectors.toList());
    }

    public CategoryResponse findById(final Long categoryId) {
        return this.categoryRepository.findById(categoryId == null ? 0L : categoryId)
                .map(this.categoryMapper::toResponse)
                .orElseThrow(
                        () -> new CategoryNotFoundException(CATEGORY_NOT_FOUND.getMessage())
                );
    }

    @Transactional(
            isolation = REPEATABLE_READ, propagation = REQUIRED,
            rollbackFor = Exception.class, transactionManager = "postgresFirstTransactionManager"
    )
    public CategoryResponse createOrUpdate(final CategoryRequest categoryRequest) {
        return this.categoryMapper.toResponse(
                this.categoryRepository.save(this.categoryMapper.toEntity(categoryRequest))
        );
    }

    @Transactional(
            isolation = REPEATABLE_READ, propagation = REQUIRED,
            rollbackFor = Exception.class, transactionManager = "postgresFirstTransactionManager"
    )
    public String delete(final Long categoryId) {
        return this.categoryRepository.findById(categoryId == null ? 0L : categoryId)
                .map(
                        category -> {
                            this.categoryRepository.delete(category);
                            return CATEGORY_DELETED.getMessage();
                        }
                )
                .orElseThrow(
                        () -> new CategoryNotFoundException(CATEGORY_NOT_FOUND.getMessage())
                );
    }
}
