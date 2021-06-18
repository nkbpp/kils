package ru.pfr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.pfr.model.PeopleOld;
import ru.pfr.model.User;
import ru.pfr.repositories.PeopleOldRepository;
import ru.pfr.role_enum.ROLE_ENUM;
import ru.pfr.service.Inoe.InoeService;
import ru.pfr.service.SOPS.SOPS_doService;
import ru.pfr.service.SOPS.SOPS_posleService;
import ru.pfr.service.Staj.StajService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PeopleOldService {

    @Autowired
    PeopleOldRepository peopleRepository;

    @Autowired
    InoeService inoeService;

    @Autowired
    StajService stajService;

    @Autowired
    SOPS_doService sops_doService;

    @Autowired
    SOPS_posleService sops_posleService;

    private final int mnoj = 30;

    public PeopleOld findById(Long id) {
        return peopleRepository.findById(id).get();
    }

    private List<PeopleOld> onlyKod(List<PeopleOld> people, String kod){
        List<PeopleOld> peopleList = new ArrayList<>();
        people.forEach(l -> {
            if(kod.equals(l.getUser().getRayon().getKod())){
                peopleList.add(l);
            }
        });
        Collections.reverse(peopleList);
        return peopleList;
    }

    public List<PeopleOld> findByNameAndSnils(String kod) {
        return onlyKod(findByNameAndSnils("","", 1), kod);
    }

    public List<PeopleOld> findByNameAndSnils() {
        return findByNameAndSnils("","", 1);
    }

    public List<PeopleOld> findByNameAndSnils(String name, String snils, int list) {

        List<PeopleOld> peopleAll = findByNameAndSnils(name, snils);
        List<PeopleOld> people = new ArrayList<>();
        for (int i = mnoj*(list-1); i < mnoj*(list-1) + mnoj && i<peopleAll.size() ; i++) {
            people.add(peopleAll.get(i));
        }
        //Collections.reverse(people);
        return people;
    }

    public List<PeopleOld> findByNameAndSnils(String name, String snils, int list, String kod) {

        List<PeopleOld> peopleAll = findByNameAndSnils(name, snils, kod);
        List<PeopleOld> people = new ArrayList<>();
        for (int i = mnoj*(list-1); i < mnoj*(list-1) + mnoj && i<peopleAll.size() ; i++) {
            people.add(peopleAll.get(i));
        }
        //Collections.reverse(people);
        return people;
    }

    public List<PeopleOld> findByNameAndSnils(String name, String snils, String kod) {
        return onlyKod(findByNameAndSnils(name, snils), kod);
    }

    public List<PeopleOld> findByNameAndSnils(String name, String snils) {
        return name.equals("")?
                snils.equals("")? findAll() :
                peopleRepository.findBySnilsOrderByStatusDesc(snils) :
                peopleRepository.findByFamContainingOrSnilsOrderByStatusDesc(name, snils);
    }

    public List<PeopleOld> findAll() {
        return peopleRepository.findAllByOrderByStatusDesc();
    }

    @Transactional
    public void save(PeopleOld people) {
        peopleRepository.save(people);
    }

    @Transactional
    public void saveall(PeopleOld people) {

        if(people.getSops_dos()!=null)
            sops_doService.saveall(people.getSops_dos());

        if(people.getSops_posles()!=null)
            sops_posleService.saveall(people.getSops_posles());

        if(people.getStajs()!=null)
            stajService.saveall(people.getStajs());

        if(people.getInoes()!=null)
            inoeService.saveall(people.getInoes());

        peopleRepository.save(people);


    }

    @Transactional
    public void delete(Long id) {
        PeopleOld people = findById(id);
        inoeService.deleteall(people.getInoes());
        stajService.deleteall(people.getStajs());
        sops_doService.deleteall(people.getSops_dos());
        sops_posleService.deleteall(people.getSops_posles());
        peopleRepository.deleteById(id);
    }


    public List<PeopleOld> peopleRoleDownload(List<PeopleOld> people, List<String> roleList) {

        if(roleList.indexOf(ROLE_ENUM.ROLE_DOWNLOAD.getString())!=-1){
            List<PeopleOld> people2 = people;
            people = new ArrayList<>();
            for (PeopleOld p:
                    people2) {
                if(p.getStatus()==1){
                    people.add(p);
                }
            }
        }
        return people;
    }

    public List<PeopleOld> peopleUserKodEqualsRayonKod(List<PeopleOld> people, User user) {
        List<PeopleOld> people1 = new ArrayList<>();
        people.forEach(p -> {
            if(user.getRayon().getKod().equals(p.getRayon_kor().getKod())){
                people1.add(p);
            }
        });
        return people1;
    }

    public List<PeopleOld> peopleUserKodNotEqualsRayonKod(List<PeopleOld> people, User user) {
        List<PeopleOld> people1 = new ArrayList<>();
        people.forEach(p -> {
            if(!user.getRayon().getKod().equals(p.getRayon_kor().getKod())){
                people1.add(p);
            }
        });
        return people1;
    }

}
