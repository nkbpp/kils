package ru.pfr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.model.People;
import ru.pfr.model.User;
import ru.pfr.repositories.PeopleRepository;
import ru.pfr.role_enum.ROLE_ENUM;
import ru.pfr.service.Inoe.InoeService;
import ru.pfr.service.SOPS.SOPS_doService;
import ru.pfr.service.SOPS.SOPS_posleService;
import ru.pfr.service.Staj.StajService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PeopleService {

    @Autowired
    PeopleRepository peopleRepository;

    @Autowired
    InoeService inoeService;

    @Autowired
    StajService stajService;

    @Autowired
    SOPS_doService sops_doService;

    @Autowired
    SOPS_posleService sops_posleService;

    private final int mnoj = 30;

    public People findById(Long id) {
        return peopleRepository.findById(id).get();
    }

    private List<People> onlyKod(List<People> people, String kod){
        List<People> peopleList = new ArrayList<>();
        people.forEach(l -> {
            if(kod.equals(l.getUser().getRayon().getKod())){
                peopleList.add(l);
            }
        });
        Collections.reverse(peopleList);
        return peopleList;
    }

    public List<People> findByNameAndSnils(String kod) {
        return sliseToList(onlyKod(findByNameAndSnils("",""), kod),1);
    }

    public List<People> findByNameAndSnils() {
        return findByNameAndSnils("","", 1);
    }

    public List<People> findByNameAndSnils(String name, String snils, int list) {

        List<People> peopleAll = findByNameAndSnils(name, snils);
        List<People> people = new ArrayList<>();
        for (int i = mnoj*(list-1); i < mnoj*(list-1) + mnoj && i<peopleAll.size() ; i++) {
            people.add(peopleAll.get(i));
        }
        //Collections.reverse(people);
        return people;
    }

    private List<People> sliseToList(List<People> peopleAll, int list) {
        List<People> people = new ArrayList<>();
        for (int i = mnoj*(list-1); i < mnoj*(list-1) + mnoj && i<peopleAll.size() ; i++) {
            people.add(peopleAll.get(i));
        }
        //Collections.reverse(people);
        return people;
    }

    public List<People> findByNameAndSnils(String name, String snils, int list, String kod) {

        List<People> peopleAll = findByNameAndSnils(name, snils, kod);
        List<People> people = new ArrayList<>();
        for (int i = mnoj*(list-1); i < mnoj*(list-1) + mnoj && i<peopleAll.size() ; i++) {
            people.add(peopleAll.get(i));
        }
        //Collections.reverse(people);
        return people;
    }

    public List<People> findByNameAndSnils(String name, String snils, String kod) {
        return sliseToList(onlyKod(findByNameAndSnils(name, snils), kod),1);
    }

    public List<People> findByNameAndSnils(String name, String snils) { // всё
        return name.equals("")?
                snils.equals("")? findAll() :
                peopleRepository.findBySnilsOrderByStatusDesc(snils) :
                peopleRepository.findByFamContainingOrSnilsOrderByStatusDesc(name, snils);
    }

    public List<People> findAll() {
        return peopleRepository.findAllByOrderByStatusDesc();
    }

    @Transactional
    public void save(People people) {
        peopleRepository.save(people);
    }

    @Transactional
    public void saveall(People people) {

        peopleRepository.save(people);
        inoeService.saveall(people.getInoes());
        sops_doService.saveall(people.getSops_dos());
        sops_posleService.saveall(people.getSops_posles());
        stajService.saveall(people.getStajs());

    }

    @Transactional
    public void delete(Long id) {
        People people = findById(id);
        inoeService.deleteall(people.getInoes());
        stajService.deleteall(people.getStajs());
        sops_doService.deleteall(people.getSops_dos());
        sops_posleService.deleteall(people.getSops_posles());
        peopleRepository.deleteById(id);
    }


    public List<People> peopleRoleDownload(List<People> people, List<String> roleList) {

        if(roleList.indexOf(ROLE_ENUM.ROLE_DOWNLOAD.getString())!=-1){
            List<People> people2 = people;
            people = new ArrayList<>();
            for (People p:
                    people2) {
                if(p.getStatus()==1){
                    people.add(p);
                }
            }
        }
        return people;
    }

    public List<People> peopleUserKodEqualsRayonKod(List<People> people, User user) {
        List<People> people1 = new ArrayList<>();
        people.forEach(p -> {
            if(user.getRayon().getKod().equals(p.getRayon_kor().getKod())){
                people1.add(p);
            }
        });
        return people1;
    }

    public List<People> peopleUserKodNotEqualsRayonKod(List<People> people, User user) {
        List<People> people1 = new ArrayList<>();
        people.forEach(p -> {
/*            System.out.println("Код = " + user.getRayon().getKod());
            System.out.println("Ray = " + p.getRayon_kor().getKod());*/
            if(!user.getRayon().getKod().equals(p.getRayon_kor().getKod())){
                people1.add(p);
            }
        });
        return people1;
    }

}
