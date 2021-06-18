package ru.pfr.service.spiski;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.model.spiski.SpisokIschisStaj;
import ru.pfr.repositories.spiski.SpisokIschisStajRepository;

import java.util.List;

@Service
public class SpisokIschisStajService {

    @Autowired
    SpisokIschisStajRepository repository;

    public SpisokIschisStaj findById(Long id) {
        return repository.findById(id).get();
    }

    public List<SpisokIschisStaj> findAll() {
        return repository.findAll();
    }

    @Transactional
    public void save(SpisokIschisStaj s) {
        repository.save(s);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }


}
