package net.nextgen.emerald.controller;

import java.util.List;
import javax.inject.Inject;

import org.springframework.web.bind.annotation.*;

import net.nextgen.emerald.service.EnrolleeService;
import net.nextgen.emerald.vo.Enrollee;

@RestController
public class EnrolleeController {

    @Inject
    private EnrolleeService enrolleeService;

    /* Add a new enrollee */

    @PostMapping("/enrollees")
    Enrollee newEnrollee(@RequestBody Enrollee newEnrollee) {
        return enrolleeService.create(newEnrollee);
    }

    @GetMapping("/enrollees")
    List<Enrollee> all() {
        return enrolleeService.read();
    }

    @GetMapping("/enrollees/{id}")
    Enrollee one(@PathVariable Long id) {
        return enrolleeService.read(id);
    }

    /* Modify an existing enrollee */

    @PutMapping("/enrollees/{id}")
    Enrollee replaceEnrollee(@PathVariable Long id, @RequestBody Enrollee newEnrollee) {
        return enrolleeService.update(id, newEnrollee);
    }

    /* Remove an enrollee entirely */

    /* The enrollee and all associated dependents will be deleted */
    @DeleteMapping("/enrollees/{id}")
    void deleteEnrollee(@PathVariable Long id) {
        enrolleeService.delete(id);
    }
}
