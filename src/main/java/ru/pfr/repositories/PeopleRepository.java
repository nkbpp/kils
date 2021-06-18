package ru.pfr.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pfr.model.People;

import java.util.List;
import java.util.Optional;

public interface PeopleRepository extends JpaRepository<People, Long> {
    public Optional<People> findById(Long l);

    public List<People> findAllByOrderByStatusDesc();

    public List<People> findAll();

    public List<People> findByFamContainingOrSnilsOrderByStatusDesc(String n,String s);

    public List<People> findBySnilsOrderByStatusDesc(String s);


}
