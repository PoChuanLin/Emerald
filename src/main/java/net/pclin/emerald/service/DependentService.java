package net.pclin.emerald.service;

import java.time.LocalDate;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import net.pclin.emerald.dao.DependentRepository;
import net.pclin.emerald.dao.EnrolleeRepository;
import net.pclin.emerald.vo.Dependent;
import net.pclin.emerald.vo.Enrollee;

@Service
@RequiredArgsConstructor
public class DependentService {

    private final DependentRepository dependentRepository;
    private final EnrolleeRepository enrolleeRepository;

    /* Create */

    public Dependent create (Dependent newDependent) {
        // validate Enrollee
        Enrollee newEnrollee = newDependent.getEnrollee();
        assert (newEnrollee != null) : "Missing Enrollee!";
        // validate enrollee
        fetchEnrollee (newEnrollee.getId());

        // create Dependent - save to repository
        return dependentRepository.save(newDependent);
    }

    public Dependent create (String name, LocalDate dob, long enrolleeID) {
        Enrollee enrollee = fetchEnrollee (enrolleeID);
        Dependent dependent = new Dependent(name, dob, enrollee);
        return create(dependent);
    }

    /* fetch & validate
         read Enrollee from repository, throws exception if not found.
     */
    private Enrollee fetchEnrollee (long enrolleeID) {
        Enrollee enrollee = enrolleeRepository.findById(enrolleeID)
                .orElseThrow(() -> new EnrolleeNotFoundException(enrolleeID));
        return enrollee;
    }

    /* Retrieve */

    public List<Dependent> read() {
        return dependentRepository.findAll();
    }

    public Dependent read(Long id) {
        return dependentRepository.findById(id)
                .orElseThrow(() -> new DependentNotFoundException(id));
    }

    public List<Dependent> findByEnrolleeId (Long enrolleeId) {
        return dependentRepository.findByEnrolleeId(enrolleeId);
    }

    /* Update */

    public Dependent update (Long id, Dependent newDependent) {
        return dependentRepository.findById(id)
                .map(dependent -> {
                    dependent.setName(newDependent.getName());
                    dependent.setDob(newDependent.getDob());
                    return dependentRepository.save(dependent);
                })
                .orElseThrow(() -> new DependentNotFoundException(id));
    }

    /* Delete */

    public void delete (Long id) {
        dependentRepository.deleteById(id);
    }

    public void deleteByEnrolleeId (Long enrolleeId) {
        dependentRepository.deleteByEnrolleeId(enrolleeId);
    }

    public Dependent findById(Long id) {
        return dependentRepository.findById(id).orElse(null);
    }

    public long count() {
        return dependentRepository.count();
    }
}
