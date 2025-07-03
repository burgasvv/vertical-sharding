package org.burgas.verticalsharding.service.second;

import lombok.RequiredArgsConstructor;
import org.burgas.verticalsharding.dto.second.DepartmentRequest;
import org.burgas.verticalsharding.dto.second.DepartmentResponse;
import org.burgas.verticalsharding.exception.DepartmentNotFoundException;
import org.burgas.verticalsharding.mapper.second.DepartmentMapper;
import org.burgas.verticalsharding.repository.second.DepartmentRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.burgas.verticalsharding.message.second.DepartmentMessages.DEPARTMENT_DELETED;
import static org.burgas.verticalsharding.message.second.DepartmentMessages.DEPARTMENT_NOT_FOUND;
import static org.springframework.transaction.annotation.Isolation.REPEATABLE_READ;
import static org.springframework.transaction.annotation.Propagation.SUPPORTS;

@Component
@RequiredArgsConstructor
@Transactional(
        readOnly = true, propagation = SUPPORTS,
        transactionManager = "postgresSecondTransactionManager"
)
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    public List<DepartmentResponse> findAll() {
        return this.departmentRepository.findAll()
                .stream()
                .map(this.departmentMapper::toResponse)
                .collect(Collectors.toList());
    }

    public DepartmentResponse findById(final Long departmentId) {
        return this.departmentRepository.findById(departmentId == null ? 0L : departmentId)
                .map(this.departmentMapper::toResponse)
                .orElseThrow(
                        () -> new DepartmentNotFoundException(DEPARTMENT_NOT_FOUND.getMessage())
                );
    }

    @Transactional(
            isolation = REPEATABLE_READ, propagation = SUPPORTS,
            rollbackFor = Exception.class, transactionManager = "postgresSecondTransactionManager"
    )
    public DepartmentResponse createOrUpdate(final DepartmentRequest departmentRequest) {
        return this.departmentMapper.toResponse(
                this.departmentRepository.save(this.departmentMapper.toEntity(departmentRequest))
        );
    }

    @Transactional(
            isolation = REPEATABLE_READ, propagation = SUPPORTS,
            rollbackFor = Exception.class, transactionManager = "postgresSecondTransactionManager"
    )
    public String delete(final Long departmentId) {
        return this.departmentRepository.findById(departmentId == null ? 0L : departmentId)
                .map(
                        department -> {
                            this.departmentRepository.delete(department);
                            return DEPARTMENT_DELETED.getMessage();
                        }
                )
                .orElseThrow(
                        () -> new DepartmentNotFoundException(DEPARTMENT_NOT_FOUND.getMessage())
                );
    }
}
