package org.burgas.replicashard.repository.first;

import org.burgas.replicashard.entity.first.Product;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Override
    @Query(value = "select p from org.burgas.replicashard.entity.first.Product p join fetch p.category")
    @NotNull List<Product> findAll();

    @Override
    @Query(value = "select p from org.burgas.replicashard.entity.first.Product p join fetch p.category where p.id = :aLong")
    @NotNull Optional<Product> findById(@NotNull Long aLong);
}
