package ru.pfr.service.xlsx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.model.xlsx.DeregisteredEmployers;
import ru.pfr.repositories.xlsx.DeregisteredEmployersRepository;

import java.util.List;

@Service
public class DeregisteredEmployersService {

    @Autowired
    DeregisteredEmployersRepository deregisteredEmployersRepository;

    public DeregisteredEmployers findById(Long id) {
        DeregisteredEmployers d = null;
        try{
            d = deregisteredEmployersRepository.findById(id).get();
        }catch (Exception e){}
        return d;
    }

    public DeregisteredEmployers findByRegNum(String s) {
        s = s.replace("-","");
        DeregisteredEmployers d = null;
        try{
            d = deregisteredEmployersRepository.findByRegnum(s).get();
        }catch (Exception e){}
        return d;
    }

    public List<DeregisteredEmployers> findAll() {
        return deregisteredEmployersRepository.findAll();
    }

    @Transactional
    public void save(DeregisteredEmployers deregisteredEmployers) {
        DeregisteredEmployers d = findByRegNum(deregisteredEmployers.getRegnum());
        if(d!=null){
            deregisteredEmployers.setId(d.getId());
        }
        deregisteredEmployersRepository.save(deregisteredEmployers);
    }

    @Transactional
    public List<DeregisteredEmployers> saveall(List<DeregisteredEmployers> deregisteredEmployers) {
        deregisteredEmployers.forEach( (t) -> {
            deregisteredEmployersRepository.save(t);
        });
        return deregisteredEmployers;
    }

    @Transactional
    public void delete(Long id) {
        deregisteredEmployersRepository.deleteById(id);
    }

    @Transactional
    public boolean deleteall(List<DeregisteredEmployers> deregisteredEmployers) {
        deregisteredEmployers.forEach( (t) -> {
            delete(t.getId());
        });
        return true;
    }

}
