package net.nextgen.emerald.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Enrollee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String name;

    @NotNull
    @Temporal(javax.persistence.TemporalType.DATE)
    @Past
    private LocalDate dob;

    private String phone;

    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Dependent> dependents = new ArrayList<>();
}
