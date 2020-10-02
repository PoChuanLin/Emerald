package net.nextgen.emerald.controller;

import java.util.List;
import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import net.nextgen.emerald.service.EnrolleeService;
import net.nextgen.emerald.vo.Enrollee;

@RestController
@RequiredArgsConstructor
public class EnrolleeController {

    private final EnrolleeService enrolleeService;

    /* Add a new enrollee */

    @PostMapping("/enrollees")
    Enrollee newEnrollee(@Valid @RequestBody Enrollee newEnrollee) {
        return enrolleeService.create(newEnrollee);
    }

    /* fetch */

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
    Enrollee replaceEnrollee(@PathVariable Long id, @Valid @RequestBody Enrollee newEnrollee) {
        return enrolleeService.update(id, newEnrollee);
    }

    /* Remove an enrollee entirely */

    /* The enrollee and all associated dependents will be deleted */
    @DeleteMapping("/enrollees/{id}")
    void deleteEnrollee(@PathVariable Long id) {
        enrolleeService.delete(id);
    }
}
