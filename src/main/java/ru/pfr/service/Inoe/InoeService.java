package ru.pfr.service.Inoe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.model.Inoe;
import ru.pfr.model.People;
import ru.pfr.repositories.Inoe.InoeRepository;
import ru.pfr.repositories.PeopleRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class InoeService {

    @Autowired
    InoeRepository inoeRepository;

    public Inoe findById(Long id) {
        return inoeRepository.findById(id).get();
    }

    public List<Inoe> findAll() {
        return inoeRepository.findAll();
    }

    @Transactional
    public void save(Inoe inoe) {
        inoeRepository.save(inoe);
    }

    @Transactional
    public List<Inoe> saveall(List<Inoe> inoes) {
        inoes.forEach( (t) -> {
            inoeRepository.save(t);
        });
        return inoes;
    }

    @Transactional
    public void delete(Long id) {
        inoeRepository.deleteById(id);
    }

    @Transactional
    public boolean deleteall(List<Inoe> inoes) {
        inoes.forEach( (t) -> {
            delete(t.getId_inoe());
        });
        return true;
    }

}
