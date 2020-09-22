package net.nextgen.emerald.service;

import javax.inject.Inject;
import java.util.List;

import org.springframework.stereotype.Service;

import net.nextgen.emerald.dao.DependentRepository;
import net.nextgen.emerald.dao.EnrolleeRepository;
import net.nextgen.emerald.vo.Enrollee;

/**  Services for Enrollee persistence, CRUD processes.
 */
@Service
public class EnrolleeService {
    @Inject
    private EnrolleeRepository enrolleeRepository;
    @Inject
    private DependentRepository dependentRepository;

    /* Create */

    public Enrollee create(Enrollee newEnrollee) {
        return enrolleeRepository.save(newEnrollee);
    }

    /* Retrieve */

    public List<Enrollee> read() {
        return enrolleeRepository.findAll();
    }

    public Enrollee read(Long id) {
        return enrolleeRepository.findById(id)
                .orElseThrow(() -> new EnrolleeNotFoundException(id));
    }

    /* Update */

    public Enrollee update (Long id, Enrollee newEnrollee) {
        return enrolleeRepository.findById(id)
                .map(enrollee -> {
                    enrollee.setName(newEnrollee.getName());
                    enrollee.setActivation(newEnrollee.getActivation());
                    enrollee.setDob(newEnrollee.getDob());
                    enrollee.setPhone(newEnrollee.getPhone());
                    return enrolleeRepository.save(enrollee);
                })
                .orElseGet(() -> {
                    newEnrollee.setId(id);
                    return enrolleeRepository.save(newEnrollee);
                });
    }

    /* Delete */

    /** Delete an Enrollee by its ID, along with all its dependents.
     * @param id ID of the Enrollee, along with all its dependents, to be deleted.
     */
    public void delete(Long id) {
        // delete associated dependents
        dependentRepository.deleteByEnrolleeId(id);
        // delete the enrollee
        enrolleeRepository.deleteById(id);
    }
}
