package ru.pfr.service.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pfr.model.Rayon;
import ru.pfr.repositories.User.RayonRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RayonService {
    @Autowired
    private RayonRepository rayonRepository;

    public void save(Rayon rayon) {
        rayonRepository.save(rayon);
    }

    public List<Rayon> findAll() {
        return rayonRepository.findAll();
    }

    public List<Rayon> findAllMRU() {
        List<Rayon> r = new ArrayList<>();
        rayonRepository.findAll().forEach(rayon -> {
            if(rayon.getId().equals(1l) ||
                    rayon.getId().equals(3l) ||
                    rayon.getId().equals(4l) ||
                    rayon.getId().equals(5l) ||
                    rayon.getId().equals(6l) ||
                    rayon.getId().equals(7l) ||
                    rayon.getId().equals(8l) ||
                    rayon.getId().equals(10l) ||
                    rayon.getId().equals(2l) //НАДО БУДЕТ УБРАТЬ
            ) {
                r.add(rayon);
            }
        });
        return r;
    }

    public List<Rayon> findAllUpfr(String p1, String p2) {
        return rayonRepository.findByKodNotAndKodNot(p1, p2);
    }

    public Rayon findById(Long id) {
        return rayonRepository.findById(id).get();
    }

    public Optional<Rayon> findByKod(String kod) {
        return rayonRepository.findByKod(kod);
    }
}
