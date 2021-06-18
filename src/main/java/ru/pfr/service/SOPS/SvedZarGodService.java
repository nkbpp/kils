package ru.pfr.service.SOPS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.model.Staj;
import ru.pfr.model.SvedZarGod;
import ru.pfr.model.SvedZarMounth;
import ru.pfr.repositories.SOPS.SvedZarGodRepository;
import ru.pfr.repositories.Staj.StajRepository;
import ru.pfr.service.Staj.Staj_tableService;

import java.util.List;

@Service
public class SvedZarGodService {

    @Autowired
    SvedZarGodRepository svedZarGodRepository;

    @Autowired
    SvedZarMounthService svedZarMounthService;

    public SvedZarGod findById(Long id) {
        return svedZarGodRepository.findById(id).get();
    }

    public List<SvedZarGod> findAll() {
        return svedZarGodRepository.findAll();
    }

    @Transactional
    public void save(SvedZarGod i) {
        svedZarGodRepository.save(i);
    }

    @Transactional
    public void saveall(List<SvedZarGod> list) {
        list.forEach( (t) -> {
            svedZarGodRepository.save(t);
            svedZarMounthService.saveall(t.getSvedZarMounths());
        });
    }

    @Transactional
    public void delete(Long id) {
        svedZarMounthService.deleteall(findById(id).getSvedZarMounths());
        svedZarGodRepository.deleteById(id);
    }

    @Transactional
    public void deleteall(List<SvedZarGod> svedZarGods) {
        svedZarGods.forEach( (t) -> {
            delete(t.getId_svedzargod());
        });
    }

}
