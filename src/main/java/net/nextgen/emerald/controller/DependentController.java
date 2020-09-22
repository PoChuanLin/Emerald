package net.nextgen.emerald.controller;

import javax.inject.Inject;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.*;

import net.nextgen.emerald.service.DependentService;
import net.nextgen.emerald.vo.Dependent;
import net.nextgen.emerald.vo.Enrollee;

@RestController
public class DependentController {
    @Inject
    private DependentService dependentService;

    /* Add dependents to an enrollee */

    @PostMapping("/dependents")
    Dependent newDependent(@RequestBody Dependent newDependent) {
        return dependentService.create(newDependent);
    }

    /** Create a Dependent via related Enrollee's ID
     *
     * @param name
     * @param dob
     * @param id The ID of parent Enrollee.
     * @return
     */
    @PostMapping("/enrollees/{id}/dependents")
    Dependent newDependent(@RequestParam String name,
                           @RequestParam Date dob,
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
    Dependent replaceDependent(@PathVariable long id, @RequestBody Dependent newDependent) {
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
