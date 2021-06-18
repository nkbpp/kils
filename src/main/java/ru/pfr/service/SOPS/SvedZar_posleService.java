package ru.pfr.service.SOPS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.model.SOPS_do;
import ru.pfr.model.SvedZar_do;
import ru.pfr.model.SvedZar_posle;
import ru.pfr.repositories.SOPS.SvedZar_doRepository;
import ru.pfr.repositories.SOPS.SvedZar_posleRepository;

import java.util.List;

@Service
public class SvedZar_posleService {

    @Autowired
    SvedZar_posleRepository repository;

    @Autowired
    SvedZarMounthService service;

    public SvedZar_posle findById(Long id) {
        return repository.findById(id).get();
    }

    public List<SvedZar_posle> findAll() {
        return repository.findAll();
    }

    @Transactional
    public void save(SvedZar_posle i) {
        repository.save(i);
    }

    @Transactional
    public void saveall(List<SvedZar_posle> list) {
        list.forEach( (t) -> {
            repository.save(t);
            service.saveall(t.getSvedZarMounths());
        });
    }

    @Transactional
    public void delete(Long id) {
        service.deleteall(findById(id).getSvedZarMounths());
        repository.deleteById(id);
    }

    @Transactional
    public void deleteall(List<SvedZar_posle> svedZar_posles) {
        svedZar_posles.forEach( (t) -> {
            delete(t.getId_svedzar());
        });
    }

}
