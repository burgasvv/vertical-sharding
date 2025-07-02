package org.burgas.replicashard.entity.first;

import jakarta.persistence.*;
import lombok.*;
import org.burgas.replicashard.entity.AbstractEntity;

import java.io.Serializable;
import java.util.List;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraph(
        name = "category-with-products",
        attributeNodes = @NamedAttributeNode(value = "products")
)
public class Category extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;
    private String description;

    @OneToMany(mappedBy = "category", cascade = {PERSIST, MERGE})
    private List<Product> products;
}
