package org.burgas.replicashard.dto.second;

import lombok.*;
import org.burgas.replicashard.dto.Request;

@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequest extends Request {

    private Long id;
    private String name;
    private String surname;
    private String patronymic;
    private Long departmentId;
}
