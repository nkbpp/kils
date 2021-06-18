package ru.pfr.service.SOPS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.model.SOPS_do;
import ru.pfr.model.SOPS_posle;
import ru.pfr.repositories.SOPS.SOPS_doRepository;
import ru.pfr.repositories.SOPS.SOPS_posleRepository;

import java.util.List;

@Service
public class SOPS_posleService {

    @Autowired
    SOPS_posleRepository repository;

    @Autowired
    SvedZar_posleService svedZar_posleService;

    @Autowired
    SOPS_tableService sops_tableService;

    public SOPS_posle findById(Long id) {
        return repository.findById(id).get();
    }

    public List<SOPS_posle> findAll() {
        return repository.findAll();
    }

    @Transactional
    public void save(SOPS_posle i) {
        repository.save(i);
    }

    @Transactional
    public void saveall(List<SOPS_posle> list) {
        list.forEach( (t) -> {
            repository.save(t);
            svedZar_posleService.saveall(t.getSvedZar_posles());
            sops_tableService.saveall(t.getSops_tables());
        });
    }

    @Transactional
    public void delete(Long id) {
        svedZar_posleService.deleteall(findById(id).getSvedZar_posles());
        sops_tableService.deleteall(findById(id).getSops_tables());
        repository.deleteById(id);
    }

    @Transactional
    public void deleteall(List<SOPS_posle> sops_posles) {
        sops_posles.forEach( (t) -> {
            delete(t.getId_sops_posle());
        });
    }

}
