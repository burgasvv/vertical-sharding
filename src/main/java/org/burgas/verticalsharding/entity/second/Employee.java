package org.burgas.verticalsharding.entity.second;

import jakarta.persistence.*;
import lombok.*;
import org.burgas.verticalsharding.entity.AbstractEntity;

import java.io.Serializable;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Employee extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String patronymic;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
}
