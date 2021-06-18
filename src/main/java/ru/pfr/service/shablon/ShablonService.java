package ru.pfr.service.shablon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.model.shablon.Shablon;
import ru.pfr.repositories.shablon.ShablonRepository;

import java.util.List;


@Service
public class ShablonService {

    @Autowired
    ShablonRepository shablonRepository;

    @Transactional
    public void save(Shablon dokument) {
        shablonRepository.save(dokument);
    }

    public List<Shablon> findAll() {
        return shablonRepository.findAll();
    }

    public Shablon findById(Long id) {
        return shablonRepository.findById(id).get();
    }

}
