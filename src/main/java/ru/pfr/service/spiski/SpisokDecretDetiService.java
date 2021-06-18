package ru.pfr.service.spiski;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.model.spiski.SpisokDecretDeti;
import ru.pfr.repositories.spiski.SpisokDecretDetiRepository;

import java.util.List;

@Service
public class SpisokDecretDetiService {

    @Autowired
    SpisokDecretDetiRepository repository;

    public SpisokDecretDeti findById(Long id) {
        return repository.findById(id).get();
    }

    public List<SpisokDecretDeti> findAll() {
        return repository.findAll();
    }

    @Transactional
    public void save(SpisokDecretDeti s) {
        repository.save(s);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

}
