package org.burgas.verticalsharding.dto.first;

import lombok.*;
import org.burgas.verticalsharding.dto.Request;

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
