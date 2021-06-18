package ru.pfr.service.spiski;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.model.spiski.SpisokDejatel;
import ru.pfr.repositories.spiski.SpisokDejatelRepository;

import java.util.List;

@Service
public class SpisokDejatelService {

    @Autowired
    SpisokDejatelRepository repository;

    public SpisokDejatel findById(Long id) {
        return repository.findById(id).get();
    }

    public List<SpisokDejatel> findAll() {
        return repository.findAll();
    }

    @Transactional
    public void save(SpisokDejatel s) {
        repository.save(s);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

}
