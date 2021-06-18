package ru.pfr.service.spiski;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.model.spiski.SpisokTerUsl;
import ru.pfr.repositories.spiski.SpisokTerUslRepository;


import java.util.List;

@Service
public class SpisokTerUslService {

    @Autowired
    SpisokTerUslRepository repository;

    public SpisokTerUsl findById(Long id) {
        return repository.findById(id).get();
    }

    public List<SpisokTerUsl> findAll() {
        return repository.findAll();
    }

    @Transactional
    public void save(SpisokTerUsl s) {
        repository.save(s);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

}
