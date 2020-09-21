package net.nextgen.emerald.controller;

import java.util.List;
import javax.inject.Inject;

import org.springframework.web.bind.annotation.*;

import net.nextgen.emerald.dao.EnrolleeRepository;
import net.nextgen.emerald.vo.Enrollee;

@RestController
public class EnrolleeController {

    @Inject
    private EnrolleeRepository repository;

    @GetMapping("/enrollees")
    List<Enrollee> all() {
        return repository.findAll();
    }

    @GetMapping("/enrollees/{id}")
    Enrollee one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EnrolleeNotFoundException(id));
    }

    /* Add a new enrollee */

    @PostMapping("/enrollees")
    Enrollee newEnrollee(@RequestBody Enrollee newEnrollee) {
        return repository.save(newEnrollee);
    }

    /* Modify an existing enrollee */

    @PutMapping("/enrollees/{id}")
    Enrollee replaceEnrollee(@RequestBody Enrollee newEnrollee, @PathVariable Long id) {
        return repository.findById(id)
                .map(enrollee -> {
                    enrollee.setName(newEnrollee.getName());
                    enrollee.setActivation(newEnrollee.getActivation());
                    enrollee.setDob(newEnrollee.getDob());
                    enrollee.setPhone(newEnrollee.getPhone());
                    return repository.save(enrollee);
                })
                .orElseGet(() -> {
                    newEnrollee.setId(id);
                    return repository.save(newEnrollee);
                });
    }

    /* Remove an enrollee entirely */

    @DeleteMapping("/enrollees/{id}")
    void deleteEnrollee(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
