package org.burgas.replicashard.mapper.first;

import lombok.RequiredArgsConstructor;
import org.burgas.replicashard.dto.first.CategoryRequest;
import org.burgas.replicashard.dto.first.CategoryResponse;
import org.burgas.replicashard.entity.first.Category;
import org.burgas.replicashard.mapper.EntityMapper;
import org.burgas.replicashard.repository.first.CategoryRepository;
import org.springframework.stereotype.Component;

import static org.burgas.replicashard.message.first.CategoryMessages.CATEGORY_FIELD_DESCRIPTION_EMPTY;
import static org.burgas.replicashard.message.first.CategoryMessages.CATEGORY_FIELD_NAME_EMPTY;

@Component
@RequiredArgsConstructor
public final class CategoryMapper implements EntityMapper<CategoryRequest, Category, CategoryResponse> {

    private final CategoryRepository categoryRepository;

    @Override
    public Category toEntity(CategoryRequest categoryRequest) {
        Long categoryId = this.handleData(categoryRequest.getId(), 0L);
        return this.categoryRepository.findById(categoryId)
                .map(
                        category -> Category.builder()
                                .id(category.getId())
                                .name(this.handleData(categoryRequest.getName(), category.getName()))
                                .description(this.handleData(categoryRequest.getDescription(), category.getDescription()))
                                .build()
                )
                .orElseGet(
                        () -> Category.builder()
                                .name(this.handleDataException(categoryRequest.getName(), CATEGORY_FIELD_NAME_EMPTY.getMessage()))
                                .description(this.handleDataException(categoryRequest.getDescription(), CATEGORY_FIELD_DESCRIPTION_EMPTY.getMessage()))
                                .build()
                );
    }

    @Override
    public CategoryResponse toResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }
}
