package org.burgas.replicashard.dto.first;

import lombok.*;
import org.burgas.replicashard.dto.Request;

@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest extends Request {

    private Long id;
    private String model;
    private String characteristics;
    private Double price;
    private Long categoryId;
}
