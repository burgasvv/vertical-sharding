package org.burgas.replicashard.dto.second;

import lombok.*;
import org.burgas.replicashard.dto.Request;

@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentRequest extends Request {

    private Long id;
    private String name;
    private String description;
}
