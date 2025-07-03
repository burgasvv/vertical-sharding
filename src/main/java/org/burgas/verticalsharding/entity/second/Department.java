package org.burgas.verticalsharding.entity.second;

import jakarta.persistence.*;
import lombok.*;
import org.burgas.verticalsharding.entity.AbstractEntity;

import java.io.Serializable;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraph(
        name = "department-with-employees",
        attributeNodes = @NamedAttributeNode(value = "employees")
)
public class Department extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;
    private String description;

    @OneToMany(mappedBy = "department", cascade = {PERSIST, MERGE})
    private List<Employee> employees;
}
