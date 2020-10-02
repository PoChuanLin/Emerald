package net.nextgen.emerald.controller;

import java.time.LocalDate;
import java.util.List;
import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import net.nextgen.emerald.service.DependentService;
import net.nextgen.emerald.vo.Dependent;


@RestController
@RequiredArgsConstructor
public class DependentController {

    private final DependentService dependentService;

    /* Add dependents to an enrollee */

    @PostMapping("/dependents")
    Dependent newDependent(@Valid @RequestBody Dependent newDependent) {
        return dependentService.create(newDependent);
    }

    /** Create a Dependent with related Enrollee's ID
     *
     * @param name Name of the Dependent to be created.
     * @param dob  Date of birth for Dependent to be created.
     * @param id The ID of parent Enrollee.
     * @return   Newly created Dependent.
     */
    @PostMapping("/enrollees/{id}/dependents")
    Dependent newDependent(@RequestParam String name,
                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dob,
                           @PathVariable long id) {
        return dependentService.create(name, dob, id);
    }

    /* fetch */

    @GetMapping("/dependents")
    List<Dependent> all() {
        return dependentService.read();
    }

    @GetMapping("/dependents/{id}")
    Dependent one(@PathVariable Long id) {
        return dependentService.read(id);
    }

    @GetMapping("/enrollees/{id}/dependents")
    List<Dependent> findByEnrolleeId(@PathVariable long id) {
        return dependentService.findByEnrolleeId(id);
    }

    /* Modify existing dependent */

    @PutMapping("/dependents/{id}")
    Dependent replaceDependent(@PathVariable long id, @Valid @RequestBody Dependent newDependent) {
        return dependentService.update(id, newDependent);
    }

    /* Remove dependent */
    /* Remove dependents from an enrollee */

    @DeleteMapping("/dependents/{id}")
    void delete(@PathVariable long id) {
        dependentService.delete(id);
    }

    @DeleteMapping("/enrollees/{id}/dependents")
    void deleteByEnrolleeId(@PathVariable long id) {
        dependentService.deleteByEnrolleeId(id);
    }
}
