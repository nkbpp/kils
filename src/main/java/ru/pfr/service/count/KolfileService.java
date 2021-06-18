package ru.pfr.service.count;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.everything.DateFormat;
import ru.pfr.model.Inoe;
import ru.pfr.model.count.Kolfile;
import ru.pfr.repositories.Inoe.InoeRepository;
import ru.pfr.repositories.count.KolfileRepository;

import java.util.List;

@Service
public class KolfileService {

    @Autowired
    KolfileRepository kolfileRepository;

    public Kolfile findById(Long id) {
        return kolfileRepository.findById(id).get();
    }

    public List<Kolfile> findAll() {
        return kolfileRepository.findAll();
    }

    @Transactional
    public void save(Kolfile kolfile) {
        kolfileRepository.save(kolfile);
    }

    @Transactional
    public void delete(Long id) {
        kolfileRepository.deleteById(id);
    }

    public String kolfile(Long id, int len) {
        int kol = findById(id).getKol();
        int god = findById(id).getGod();
        if(DateFormat.tekGod() == god){
            kol++;
        } else {
            kol = 1;
            god++;
        }
        kolfileRepository.save(new Kolfile(id,kol,god));
        String s = "";
        s+=kol;
        while(s.length()<len){
            s = "0" + s;
        }
        return s;
    }


}
