package net.nextgen.emerald.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

@Entity
@Data
@NoArgsConstructor
public class Enrollee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @lombok.NonNull
    @NotBlank (message = "Name can not be blank.")
    private String name;

    @lombok.NonNull
    @NotNull
    private Boolean activation;

    @lombok.NonNull
    @NotNull (message = "Birth date must not be null.")
    @PastOrPresent (message = "Birth date must be in the past.")
    private LocalDate dob;

    private String phone;

    public Enrollee (String name, Boolean activation, LocalDate dob) {
        this.name = name;
        this.activation = activation;
        this.dob = dob;
    }
}
