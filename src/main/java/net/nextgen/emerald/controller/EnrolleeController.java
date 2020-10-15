package net.nextgen.emerald.controller;

import java.util.List;
import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import net.nextgen.emerald.service.EnrolleeService;
import net.nextgen.emerald.vo.Enrollee;

@RestController
@RequiredArgsConstructor
public class EnrolleeController {

    private final EnrolleeService enrolleeService;

    /* Add a new enrollee */

    @PostMapping("/enrollees")
    @ResponseStatus(HttpStatus.CREATED)
    Enrollee newEnrollee(@Valid @RequestBody Enrollee newEnrollee) {
        return enrolleeService.create(newEnrollee);
    }

    /* fetch */

    @GetMapping("/enrollees")
    List<Enrollee> all() {
        return enrolleeService.read();
    }

    @GetMapping("/enrollees/{enrolleeId}")
    Enrollee one(@PathVariable Long enrolleeId) {
        return enrolleeService.read(enrolleeId);
    }

    /* Modify an existing enrollee */

    @PutMapping("/enrollees/{enrolleeId}")
    Enrollee replaceEnrollee(@PathVariable Long enrolleeId, @Valid @RequestBody Enrollee newEnrollee) {
        return enrolleeService.update(enrolleeId, newEnrollee);
    }

    /* Remove an enrollee entirely */

    /* The enrollee and all associated dependents will be deleted */
    @DeleteMapping("/enrollees/{enrolleeId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    void deleteEnrollee(@PathVariable Long enrolleeId) {
        enrolleeService.delete(enrolleeId);
    }
}
