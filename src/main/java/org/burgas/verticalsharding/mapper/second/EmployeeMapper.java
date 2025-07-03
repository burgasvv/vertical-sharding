package org.burgas.verticalsharding.mapper.second;

import lombok.RequiredArgsConstructor;
import org.burgas.verticalsharding.dto.second.EmployeeRequest;
import org.burgas.verticalsharding.dto.second.EmployeeResponse;
import org.burgas.verticalsharding.entity.second.Employee;
import org.burgas.verticalsharding.mapper.EntityMapper;
import org.burgas.verticalsharding.repository.second.DepartmentRepository;
import org.burgas.verticalsharding.repository.second.EmployeeRepository;
import org.springframework.stereotype.Component;

import static java.util.Optional.ofNullable;
import static org.burgas.verticalsharding.message.second.EmployeeMessages.*;

@Component
@RequiredArgsConstructor
public final class EmployeeMapper implements EntityMapper<EmployeeRequest, Employee, EmployeeResponse> {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    @Override
    public Employee toEntity(EmployeeRequest employeeRequest) {
        Long employeeId = this.handleData(employeeRequest.getId(), 0L);
        return this.employeeRepository.findById(employeeId)
                .map(
                        employee -> Employee.builder()
                                .id(employee.getId())
                                .name(this.handleData(employeeRequest.getName(), employee.getName()))
                                .surname(this.handleData(employeeRequest.getSurname(), employee.getSurname()))
                                .patronymic(this.handleData(employeeRequest.getPatronymic(), employee.getPatronymic()))
                                .department(
                                        this.handleData(
                                                this.departmentRepository
                                                        .findById(employeeRequest.getDepartmentId() == null ? 0L : employeeRequest.getDepartmentId())
                                                        .orElse(null),

                                                employee.getDepartment()
                                        )
                                )
                                .build()
                )
                .orElseGet(
                        () -> Employee.builder()
                                .name(this.handleDataException(employeeRequest.getName(), EMPLOYEE_FIELD_NAME_EMPTY.getMessage()))
                                .surname(this.handleDataException(employeeRequest.getSurname(), EMPLOYEE_FIELD_SURNAME_EMPTY.getMessage()))
                                .patronymic(this.handleDataException(employeeRequest.getPatronymic(), EMPLOYEE_FIELD_PATRONYMIC_EMPTY.getMessage()))
                                .department(
                                        this.handleDataException(
                                                this.departmentRepository
                                                        .findById(employeeRequest.getDepartmentId() == null ? 0L : employeeRequest.getDepartmentId())
                                                        .orElse(null),

                                                EMPLOYEE_FIELD_DEPARTMENT_EMPTY.getMessage()
                                        )
                                )
                                .build()
                );
    }

    @Override
    public EmployeeResponse toResponse(Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .name(employee.getName())
                .surname(employee.getSurname())
                .patronymic(employee.getPatronymic())
                .department(
                        ofNullable(employee.getDepartment())
                                .map(this.departmentMapper::toResponse)
                                .orElse(null)
                )
                .build();
    }
}
