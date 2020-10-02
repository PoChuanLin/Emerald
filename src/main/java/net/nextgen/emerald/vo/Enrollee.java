package net.nextgen.emerald.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

@Entity
@Data
@NoArgsConstructor
public class Enrollee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @lombok.NonNull
    @NotBlank
    private String name;

    @lombok.NonNull
    @NotNull
    private Boolean activation;

    @lombok.NonNull
    @NotNull
    @Past
    private LocalDate dob;

    private String phone;

    public Enrollee (String name, Boolean activation, LocalDate dob) {
        this.name = name;
        this.activation = activation;
        this.dob = dob;
    }
}
