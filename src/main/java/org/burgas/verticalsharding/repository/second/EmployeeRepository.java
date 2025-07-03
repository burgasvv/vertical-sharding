package org.burgas.verticalsharding.repository.second;

import org.burgas.verticalsharding.entity.second.Employee;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Override
    @Query(value = "select e from org.burgas.verticalsharding.entity.second.Employee e join fetch e.department")
    @NotNull List<Employee> findAll();

    @Override
    @Query(value = "select e from org.burgas.verticalsharding.entity.second.Employee e join fetch e.department where e.id = :aLong")
    @NotNull Optional<Employee> findById(@NotNull Long aLong);
}
