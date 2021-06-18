package ru.pfr.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.model.Logi;
import ru.pfr.repositories.LogiRepository;


import java.util.Date;
import java.util.List;

@Service
public class LogiService {
    @Autowired
    LogiRepository logiRepository;

    @Transactional
    public void save(Logi logi) {
        logiRepository.save(logi);
    }

    @Transactional
    public void clear() {
        logiRepository.deleteAll();
    }

    public List<Logi> findAll() {
        return logiRepository.findAll();
    }

    public List<Logi> findByDateBetween(Date d1, Date d2, String user, Long l, String text) {
        //return logiRepository.findByDateBetweenOrDateNotNullAndUserOrUserNotNullAndTypeOrTypeNull(d1, d2, user, l);
        return logiRepository.findByDateParam(d1, d2, user, l, text);
    }

    public List<Logi> findByDateBetween(String user, Long l, String text) {
        //return logiRepository.findByUserOrUserNotNullAndTypeOrTypeNull(user, l);
        return logiRepository.findByUser(user, l, text);
    }

    public Logi findByUser(String login) {
        return logiRepository.findByUser(login).get();
    }
}
