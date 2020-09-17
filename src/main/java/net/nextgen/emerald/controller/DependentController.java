package net.nextgen.emerald.controller;

import javax.inject.Inject;
import java.util.List;

import net.nextgen.emerald.dao.DependentRepository;
import net.nextgen.emerald.vo.Dependent;
import net.nextgen.emerald.vo.Enrollee;
import org.springframework.web.bind.annotation.*;

@RestController
public class DependentController {

    @Inject
    private DependentRepository repository;

    /* fetch */

    @GetMapping("/dependents")
    List<Dependent> all() {
        return repository.findAll();
    }

    @GetMapping("/dependents/{id}")
    Dependent one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new DependentNotFoundException(id));
    }

    @GetMapping("/enrollees/{id}/dependents")
    List<Dependent> findByEnrolleeId(@PathVariable long id) {
        return repository.findByEnrolleeId(id);
    }

    /* Add dependents to an enrollee */

    @PostMapping("/dependents")
    Dependent newDependent(@RequestBody Dependent newDependent) {
        return repository.save(newDependent);
    }

    /* Modify existing dependent */

    @PutMapping("/dependents/{id}")
    Dependent replaceDependent(@RequestBody Dependent newDependent, @PathVariable Long id) {
        return repository.findById(id)
                .map(dependent -> {
                    dependent.setName(newDependent.getName());
                    dependent.setDob(newDependent.getDob());
                    return repository.save(dependent);
                })
                .orElseGet(() -> {
                    newDependent.setId(id);
                    return repository.save(newDependent);
                });
    }

    /* Remove dependent */

    @DeleteMapping("/dependents/{id}")
    void deleteDependent(@PathVariable Long id) {
        repository.deleteById(id);
    }

    /* Remove dependents from an enrollee */

    @DeleteMapping("/dependents")
    void deleteByEnrollee(@RequestBody Enrollee enrollee) {
        repository.deleteByEnrollee(enrollee);
    }

    @DeleteMapping("/enrollees/{id}/dependents")
    void deleteByEnrolleeId(@PathVariable Long id) {
        repository.deleteByEnrolleeId(id);
    }
}
