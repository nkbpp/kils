package ru.pfr.service.SOPS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.model.SOPS_do_doljnost;
import ru.pfr.model.SvedZarGod;
import ru.pfr.repositories.SOPS.SOPS_doDoljnostRepository;
import ru.pfr.repositories.SOPS.SOPS_tableRepository;
import ru.pfr.repositories.SOPS.SvedZarGodRepository;

import java.util.List;

@Service
public class SOPS_doDoljnostService {

    @Autowired
    SOPS_doDoljnostRepository sops_doDoljnostRepository;

    @Autowired
    SOPS_tableService sops_tableService;

    public SOPS_do_doljnost findById(Long id) {
        return sops_doDoljnostRepository.findById(id).get();
    }

    public List<SOPS_do_doljnost> findAll() {
        return sops_doDoljnostRepository.findAll();
    }

    @Transactional
    public void save(SOPS_do_doljnost i) {
        sops_doDoljnostRepository.save(i);
    }

    @Transactional
    public void saveall(List<SOPS_do_doljnost> list) {
        list.forEach( (t) -> {
            sops_doDoljnostRepository.save(t);
            sops_tableService.saveall(t.getSops_tables());
        });
    }

    @Transactional
    public void delete(Long id) {
        sops_tableService.deleteall(findById(id).getSops_tables());
        sops_doDoljnostRepository.deleteById(id);
    }

    @Transactional
    public void deleteall(List<SOPS_do_doljnost> svedZarGods) {
        svedZarGods.forEach( (t) -> {
            delete(t.getId_sops_do_doljnost());
        });
    }

}
