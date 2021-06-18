package ru.pfr.service.SOPS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.model.SOPS_do;
import ru.pfr.model.SvedZarGod;
import ru.pfr.model.SvedZar_do;
import ru.pfr.repositories.SOPS.SOPS_doRepository;
import ru.pfr.repositories.SOPS.SvedZarGodRepository;

import java.util.List;

@Service
public class SOPS_doService {

    @Autowired
    SOPS_doRepository repository;

    @Autowired
    SvedZar_doService svedZar_doService;

    @Autowired
    SOPS_doDoljnostService sops_doDoljnostService;

    public SOPS_do findById(Long id) {
        return repository.findById(id).get();
    }

    public List<SOPS_do> findAll() {
        return repository.findAll();
    }

    @Transactional
    public void save(SOPS_do i) {
        repository.save(i);
    }

    @Transactional
    public void saveall(List<SOPS_do> list) {
        list.forEach( (t) -> {
            repository.save(t);
            svedZar_doService.saveall(t.getSvedZar_dos());
            sops_doDoljnostService.saveall(t.getSops_do_doljnosts());
        });
    }

    @Transactional
    public void delete(Long id) {
        svedZar_doService.deleteall(findById(id).getSvedZar_dos());
        sops_doDoljnostService.deleteall(findById(id).getSops_do_doljnosts());
        repository.deleteById(id);
    }

    @Transactional
    public void deleteall(List<SOPS_do> sops_dos) {
        sops_dos.forEach( (t) -> {
            delete(t.getId_sops_do());
        });
    }

}
