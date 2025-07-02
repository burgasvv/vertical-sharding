package org.burgas.replicashard.dto.first;

import lombok.*;
import org.burgas.replicashard.dto.Request;

@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest extends Request {

    private Long id;
    private String name;
    private String description;
}
