package net.pclin.emerald.controller;

import java.time.LocalDate;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import net.pclin.emerald.service.DependentService;
import net.pclin.emerald.vo.Dependent;

@RestController
@Validated
@RequiredArgsConstructor
public class DependentController {

    private final DependentService dependentService;

    /* Add dependents to an enrollee */

    @PostMapping("/dependents")
    @ResponseStatus(HttpStatus.CREATED)
    Dependent newDependent(@Valid @RequestBody Dependent newDependent) {
        return dependentService.create(newDependent);
    }

    /** Create a Dependent with related Enrollee's ID
     *
     * @param name Name of the Dependent to be created.
     * @param dob  Date of birth for Dependent to be created.
     * @param enrolleeId The ID of parent Enrollee.
     * @return   Newly created Dependent.
     */
    @PostMapping("/enrollees/{enrolleeId}/dependents")
    @ResponseStatus(HttpStatus.CREATED)
    Dependent newDependent(@RequestParam @NotBlank String name,
                           @RequestParam @PastOrPresent @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dob,
                           @PathVariable long enrolleeId) {
        return dependentService.create(name, dob, enrolleeId);
    }

    /* fetch */

    @GetMapping("/dependents")
    List<Dependent> all() {
        return dependentService.read();
    }

    @GetMapping("/dependents/{dependentId}")
    Dependent one(@PathVariable Long dependentId) {
        return dependentService.read(dependentId);
    }

    @GetMapping("/enrollees/{enrolleeId}/dependents")
    List<Dependent> findByEnrolleeId(@PathVariable long enrolleeId) {
        return dependentService.findByEnrolleeId(enrolleeId);
    }

    /* Modify existing dependent */

    @PutMapping("/dependents/{dependentId}")
    Dependent replaceDependent(@PathVariable long dependentId, @Valid @RequestBody Dependent newDependent) {
        return dependentService.update(dependentId, newDependent);
    }

    /* Remove dependent */
    /* Remove dependents from an enrollee */

    @DeleteMapping("/dependents/{dependentId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    void delete(@PathVariable long dependentId) {
        dependentService.delete(dependentId);
    }

    @DeleteMapping("/enrollees/{enrolleeId}/dependents")
    @ResponseStatus(HttpStatus.ACCEPTED)
    void deleteByEnrolleeId(@PathVariable long enrolleeId) {
        dependentService.deleteByEnrolleeId(enrolleeId);
    }
}
