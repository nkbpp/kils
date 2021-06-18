package ru.pfr.service.Staj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.model.Inoe;
import ru.pfr.model.Staj_table;
import ru.pfr.repositories.Inoe.InoeRepository;
import ru.pfr.repositories.Staj.Staj_tableRepository;

import java.util.List;

@Service
public class Staj_tableService {

    @Autowired
    Staj_tableRepository staj_tableRepository;

    public Staj_table findById(Long id) {
        return staj_tableRepository.findById(id).get();
    }

    public List<Staj_table> findAll() {
        return staj_tableRepository.findAll();
    }

    @Transactional
    public void save(Staj_table staj_table) {
        staj_tableRepository.save(staj_table);
    }

    @Transactional
    public void saveall(List<Staj_table> staj_tables) {
        staj_tables.forEach( (t) -> {
            staj_tableRepository.save(t);
        });
    }

    @Transactional
    public void delete(Long id) {
        staj_tableRepository.deleteById(id);
    }

    @Transactional
    public boolean deleteall(List<Staj_table> staj_tables) {
        staj_tables.forEach( (t) -> {
            delete(t.getId_staj_table());
        });
        return true;
    }

}
