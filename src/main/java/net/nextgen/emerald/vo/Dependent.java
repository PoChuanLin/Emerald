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
public class Dependent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @lombok.NonNull
    @NotNull
    @ManyToOne
    private Enrollee enrollee;

    @lombok.NonNull
    @NotBlank(message = "Name can not be blank.")
    private String name;

    @lombok.NonNull
    @NotNull
    @Past(message = "Birth date must be in the past.")
    private LocalDate dob;

    public Dependent(String name, LocalDate dob, Enrollee enrollee) {
        this.name = name;
        this.dob  = dob;
        this.enrollee = enrollee;
    }
}
