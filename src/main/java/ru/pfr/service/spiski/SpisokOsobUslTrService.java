package ru.pfr.service.spiski;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.model.spiski.SpisokOsobUslTr;
import ru.pfr.repositories.spiski.SpisokOsobUslTrRepository;

import java.util.List;

@Service
public class SpisokOsobUslTrService {

    @Autowired
    SpisokOsobUslTrRepository repository;

    public SpisokOsobUslTr findById(Long id) {
        return repository.findById(id).get();
    }

    public List<SpisokOsobUslTr> findAll() {
        return repository.findAll();
    }

    @Transactional
    public void save(SpisokOsobUslTr s) {
        repository.save(s);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

}
