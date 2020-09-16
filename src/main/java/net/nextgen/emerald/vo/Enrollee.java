package net.nextgen.emerald.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
public class Enrollee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @lombok.NonNull
    @NotNull
    private String name;

    @lombok.NonNull
    @NotNull
    private Boolean activation;

    @lombok.NonNull
    @NotNull
    private LocalDate dob;

    private String phone;
}
