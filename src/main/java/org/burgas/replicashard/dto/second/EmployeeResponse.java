package org.burgas.replicashard.dto.second;

import lombok.*;
import org.burgas.replicashard.dto.Response;

@Getter
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse extends Response {

    private Long id;
    private String name;
    private String surname;
    private String patronymic;
    private DepartmentResponse department;
}
