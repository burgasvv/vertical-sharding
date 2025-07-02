package org.burgas.replicashard.service.second;

import lombok.RequiredArgsConstructor;
import org.burgas.replicashard.dto.second.EmployeeRequest;
import org.burgas.replicashard.dto.second.EmployeeResponse;
import org.burgas.replicashard.exception.EmployeeNotFoundException;
import org.burgas.replicashard.mapper.second.EmployeeMapper;
import org.burgas.replicashard.repository.second.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.burgas.replicashard.message.second.EmployeeMessages.EMPLOYEE_DELETED;
import static org.burgas.replicashard.message.second.EmployeeMessages.EMPLOYEE_NOT_FOUND;
import static org.springframework.transaction.annotation.Isolation.REPEATABLE_READ;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;
import static org.springframework.transaction.annotation.Propagation.SUPPORTS;

@Service
@RequiredArgsConstructor
@Transactional(
        readOnly = true, propagation = SUPPORTS,
        transactionManager = "postgresSecondTransactionManager"
)
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public List<EmployeeResponse> findAll() {
        return this.employeeRepository.findAll()
                .stream()
                .map(this.employeeMapper::toResponse)
                .collect(Collectors.toList());
    }

    public EmployeeResponse findById(final Long employeeId) {
        return this.employeeRepository.findById(employeeId == null ? 0L : employeeId)
                .map(this.employeeMapper::toResponse)
                .orElseThrow(
                        () -> new EmployeeNotFoundException(EMPLOYEE_NOT_FOUND.getMessage())
                );
    }

    @Transactional(
            isolation = REPEATABLE_READ, propagation = REQUIRED,
            rollbackFor = Exception.class, transactionManager = "postgresSecondTransactionManager"
    )
    public EmployeeResponse createOrUpdate(final EmployeeRequest employeeRequest) {
        return this.employeeMapper.toResponse(
                this.employeeRepository.save(this.employeeMapper.toEntity(employeeRequest))
        );
    }

    @Transactional(
            isolation = REPEATABLE_READ, propagation = REQUIRED,
            rollbackFor = Exception.class, transactionManager = "postgresSecondTransactionManager"
    )
    public String delete(final Long employeeId) {
        return this.employeeRepository.findById(employeeId == null ? 0L : employeeId)
                .map(
                        employee -> {
                            this.employeeRepository.delete(employee);
                            return EMPLOYEE_DELETED.getMessage();
                        }
                )
                .orElseThrow(
                        () -> new EmployeeNotFoundException(EMPLOYEE_NOT_FOUND.getMessage())
                );
    }
}
