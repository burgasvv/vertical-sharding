package org.burgas.verticalsharding.repository.first;

import org.burgas.verticalsharding.entity.first.Category;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Override
    @EntityGraph(value = "category-with-products")
    @NotNull List<Category> findAll();

    @Override
    @EntityGraph(value = "category-with-products")
    @NotNull Optional<Category> findById(@NotNull Long aLong);
}
