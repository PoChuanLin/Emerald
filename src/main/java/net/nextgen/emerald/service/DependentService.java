package net.nextgen.emerald.service;

import javax.inject.Inject;
import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import net.nextgen.emerald.dao.DependentRepository;
import net.nextgen.emerald.dao.EnrolleeRepository;
import net.nextgen.emerald.vo.Dependent;
import net.nextgen.emerald.vo.Enrollee;

@Service
public class DependentService {
    @Inject
    private DependentRepository dependentRepository;
    @Inject
    private EnrolleeRepository enrolleeRepository;

    /* Create */

    public Dependent create(Dependent newDependent) {
        return dependentRepository.save(newDependent);
    }

    public Dependent create(String name, Date dob, long enrolleeID) {
        Enrollee enrollee = enrolleeRepository.findById(enrolleeID)
                .orElseThrow(() -> new EnrolleeNotFoundException(enrolleeID));

        Dependent dependent = new Dependent(name, dob.toLocalDate(), enrollee);
        return create(dependent);
    }

    /* Retrieve */

    public List<Dependent> read() {
        return dependentRepository.findAll();
    }

    public Dependent read(Long id) {
        return dependentRepository.findById(id)
                .orElseThrow(() -> new DependentNotFoundException(id));
    }

    public List<Dependent> findByEnrolleeId(Long id) {
        return dependentRepository.findByEnrolleeId(id);
    }

    /* Update */

    public Dependent update (Long id, Dependent newDependent) {
        return dependentRepository.findById(id)
                .map(dependent -> {
                    dependent.setName(newDependent.getName());
                    dependent.setDob(newDependent.getDob());
                    return dependentRepository.save(dependent);
                })
                .orElseGet(() -> {
                    newDependent.setId(id);
                    return dependentRepository.save(newDependent);
                });
    }

    /* Delete */

    public void delete(Long id) {
        dependentRepository.deleteById(id);
    }

    public void deleteByEnrolleeId(Long id) {
        dependentRepository.deleteByEnrolleeId(id);
    }
}
