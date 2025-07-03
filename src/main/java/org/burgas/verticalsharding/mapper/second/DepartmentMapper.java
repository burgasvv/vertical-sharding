package org.burgas.verticalsharding.mapper.second;

import lombok.RequiredArgsConstructor;
import org.burgas.verticalsharding.dto.second.DepartmentRequest;
import org.burgas.verticalsharding.dto.second.DepartmentResponse;
import org.burgas.verticalsharding.entity.second.Department;
import org.burgas.verticalsharding.mapper.EntityMapper;
import org.burgas.verticalsharding.repository.second.DepartmentRepository;
import org.springframework.stereotype.Component;

import static org.burgas.verticalsharding.message.second.DepartmentMessages.DEPARTMENT_FIELD_DESCRIPTION_EMPTY;
import static org.burgas.verticalsharding.message.second.DepartmentMessages.DEPARTMENT_FIELD_NAME_EMPTY;

@Component
@RequiredArgsConstructor
public final class DepartmentMapper implements EntityMapper<DepartmentRequest, Department, DepartmentResponse> {

    private final DepartmentRepository departmentRepository;

    @Override
    public Department toEntity(DepartmentRequest departmentRequest) {
        Long departmentId = this.handleData(departmentRequest.getId(), 0L);
        return this.departmentRepository.findById(departmentId)
                .map(
                        department -> Department.builder()
                                .id(department.getId())
                                .name(this.handleData(departmentRequest.getName(), department.getName()))
                                .description(this.handleData(departmentRequest.getDescription(), department.getDescription()))
                                .build()
                )
                .orElseGet(
                        () -> Department.builder()
                                .name(this.handleDataException(departmentRequest.getName(), DEPARTMENT_FIELD_NAME_EMPTY.getMessage()))
                                .description(this.handleDataException(departmentRequest.getDescription(), DEPARTMENT_FIELD_DESCRIPTION_EMPTY.name()))
                                .build()
                );
    }

    @Override
    public DepartmentResponse toResponse(Department department) {
        return DepartmentResponse.builder()
                .id(department.getId())
                .name(department.getName())
                .description(department.getDescription())
                .build();
    }
}
