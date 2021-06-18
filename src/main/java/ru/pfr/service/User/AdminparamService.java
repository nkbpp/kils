package ru.pfr.service.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.model.Adminparam;
import ru.pfr.repositories.User.AdminparamRepository;


@Service
public class AdminparamService {

    @Autowired
    AdminparamRepository adminparamRepository;

    @Transactional
    public void save(Adminparam adminparam) {
        adminparamRepository.save(adminparam);
    }

    public Adminparam findByAdminparam() {
        return adminparamRepository.findById(1l).get();
    }

}
