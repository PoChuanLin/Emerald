package net.nextgen.emerald.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Dependent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @lombok.NonNull
    @NotNull
    @ManyToOne
    private Enrollee enrollee;

    @lombok.NonNull
    @NotNull
    private String name;

    @lombok.NonNull
    @NotNull
    @Temporal(javax.persistence.TemporalType.DATE)
    @Past
    private LocalDate dob;
}
