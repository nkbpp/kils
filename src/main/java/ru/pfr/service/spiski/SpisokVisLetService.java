package ru.pfr.service.spiski;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.model.spiski.SpisokVisLet;
import ru.pfr.repositories.spiski.SpisokVisLetRepository;

import java.util.List;

@Service
public class SpisokVisLetService {

    @Autowired
    SpisokVisLetRepository repository;

    public SpisokVisLet findById(Long id) {
        return repository.findById(id).get();
    }

    public List<SpisokVisLet> findAll() {
        return repository.findAll();
    }

    @Transactional
    public void save(SpisokVisLet s) {
        repository.save(s);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

}
