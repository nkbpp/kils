package ru.pfr.service.spiski;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.model.spiski.SpisokRaiKoef;
import ru.pfr.repositories.spiski.SpisokRaiKoefRepository;

import java.util.List;

@Service
public class SpisokRaiKoefService {

    @Autowired
    SpisokRaiKoefRepository repository;

    public SpisokRaiKoef findById(Long id) {
        return repository.findById(id).get();
    }

    public List<SpisokRaiKoef> findAll() {
        return repository.findAll();
    }

    @Transactional
    public void save(SpisokRaiKoef s) {
        repository.save(s);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

}
