package org.burgas.verticalsharding.dto.second;

import lombok.*;
import org.burgas.verticalsharding.dto.Response;

@Getter
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentResponse extends Response {

    private Long id;
    private String name;
    private String description;
}
