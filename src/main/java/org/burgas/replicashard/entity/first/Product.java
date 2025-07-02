package org.burgas.replicashard.entity.first;

import jakarta.persistence.*;
import lombok.*;
import org.burgas.replicashard.entity.AbstractEntity;

import java.io.Serializable;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Product extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String model;
    private String characteristics;
    private Double price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
