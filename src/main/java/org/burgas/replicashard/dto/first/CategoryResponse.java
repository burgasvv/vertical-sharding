package org.burgas.replicashard.dto.first;

import lombok.*;
import org.burgas.replicashard.dto.Response;

@Getter
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse extends Response {

    private Long id;
    private String name;
    private String description;
}
