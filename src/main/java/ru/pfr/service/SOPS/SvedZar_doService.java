package ru.pfr.service.SOPS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.model.SOPS_do;
import ru.pfr.model.SvedZarGod;
import ru.pfr.model.SvedZar_do;
import ru.pfr.repositories.SOPS.SOPS_doRepository;
import ru.pfr.repositories.SOPS.SvedZar_doRepository;

import java.util.List;

@Service
public class SvedZar_doService {

    @Autowired
    SvedZar_doRepository repository;

    @Autowired
    SvedZarGodService service;

    public SvedZar_do findById(Long id) {
        return repository.findById(id).get();
    }

    public List<SvedZar_do> findAll() {
        return repository.findAll();
    }

    @Transactional
    public void save(SvedZar_do i) {
        repository.save(i);
    }

    @Transactional
    public void saveall(List<SvedZar_do> list) {
        list.forEach( (t) -> {
            repository.save(t);
            service.saveall(t.getZarGods());
        });
    }

    @Transactional
    public void delete(Long id) {
        service.deleteall(findById(id).getZarGods());
        repository.deleteById(id);
    }

    @Transactional
    public void deleteall(List<SvedZar_do> svedZar_dos) {
        svedZar_dos.forEach( (t) -> {
            delete(t.getId_svedzar());
        });
    }

}
