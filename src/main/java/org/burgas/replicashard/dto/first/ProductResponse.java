package org.burgas.replicashard.dto.first;

import lombok.*;
import org.burgas.replicashard.dto.Response;

@Getter
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse extends Response {

    private Long id;
    private String model;
    private String characteristics;
    private Double price;
    private CategoryResponse category;
}
