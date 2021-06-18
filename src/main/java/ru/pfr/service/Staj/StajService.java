package ru.pfr.service.Staj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.model.Inoe;
import ru.pfr.model.Staj;
import ru.pfr.model.Staj_table;
import ru.pfr.repositories.Inoe.InoeRepository;
import ru.pfr.repositories.Staj.StajRepository;

import java.util.List;

@Service
public class StajService {

    @Autowired
    StajRepository stajRepository;

    @Autowired
    Staj_tableService staj_tableService;

    public Staj findById(Long id) {
        return stajRepository.findById(id).get();
    }

    public List<Staj> findAll() {
        return stajRepository.findAll();
    }

    @Transactional
    public void save(Staj staj) {
        stajRepository.save(staj);
    }

    @Transactional
    public void saveall(List<Staj> stajs) {
        stajs.forEach( (t) -> {
            stajRepository.save(t);
            staj_tableService.saveall(t.getStaj_tables());
        });
    }

    @Transactional
    public void delete(Long id) {
        staj_tableService.deleteall(findById(id).getStaj_tables());
        stajRepository.deleteById(id);
    }

    @Transactional
    public boolean deleteall(List<Staj> stajs) {
        stajs.forEach( (t) -> {
            delete(t.getId_staj());
        });
        return true;
    }

}
