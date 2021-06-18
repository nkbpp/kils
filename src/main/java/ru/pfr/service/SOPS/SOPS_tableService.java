package ru.pfr.service.SOPS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.model.Inoe;
import ru.pfr.model.SOPS_table;
import ru.pfr.model.SvedZarMounth;
import ru.pfr.repositories.SOPS.SOPS_tableRepository;
import ru.pfr.repositories.SOPS.SvedZarMounthRepository;

import java.util.Comparator;
import java.util.List;

@Service
public class SOPS_tableService {

    @Autowired
    SOPS_tableRepository sops_tableRepository;

    public SOPS_table findById(Long id) {
        return sops_tableRepository.findById(id).get();
    }

    public List<SOPS_table> findAll() {
        return sops_tableRepository.findAll();
    }

    @Transactional
    public void save(SOPS_table sops_table) {
        sops_tableRepository.save(sops_table);
    }

    @Transactional
    public void saveall(List<SOPS_table> sops_tables) {
        sops_tables.forEach( (t) -> {
            sops_tableRepository.save(t);
        });
    }

    @Transactional
    public void delete(Long id) {
        sops_tableRepository.deleteById(id);
    }

    @Transactional
    public boolean deleteall(List<SOPS_table> sops_tables) {
        sops_tables.forEach( (t) -> {
            delete(t.getId_sops_do_table());
        });
        return true;
    }



}
