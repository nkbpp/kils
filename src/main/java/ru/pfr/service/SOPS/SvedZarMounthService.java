package ru.pfr.service.SOPS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.model.Staj_table;
import ru.pfr.model.SvedZarMounth;
import ru.pfr.repositories.SOPS.SvedZarMounthRepository;
import ru.pfr.repositories.Staj.Staj_tableRepository;

import java.util.List;

@Service
public class SvedZarMounthService {

    @Autowired
    SvedZarMounthRepository svedZarMounthRepository;

    public SvedZarMounth findById(Long id) {
        return svedZarMounthRepository.findById(id).get();
    }

    public List<SvedZarMounth> findAll() {
        return svedZarMounthRepository.findAll();
    }

    @Transactional
    public void save(SvedZarMounth svedZarMounth) {
        svedZarMounthRepository.save(svedZarMounth);
    }

    @Transactional
    public void saveall(List<SvedZarMounth> svedZarMounths) {
        svedZarMounths.forEach( (t) -> {
            svedZarMounthRepository.save(t);
        });
    }

    @Transactional
    public void deleteall(List<SvedZarMounth> svedZarMounths) {
        svedZarMounths.forEach( (t) -> {
            delete(t.getId_svedzarmounth());
        });
    }

    @Transactional
    public void delete(Long id) {
        svedZarMounthRepository.deleteById(id);
    }

}
