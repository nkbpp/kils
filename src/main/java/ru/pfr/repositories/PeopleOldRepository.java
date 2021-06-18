package ru.pfr.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pfr.model.PeopleOld;

import java.util.List;
import java.util.Optional;

public interface PeopleOldRepository extends JpaRepository<PeopleOld, Long> {
    public Optional<PeopleOld> findById(Long l);

    public List<PeopleOld> findAllByOrderByStatusDesc();

    public List<PeopleOld> findAll();

    public List<PeopleOld> findByFamContainingOrSnilsOrderByStatusDesc(String n, String s);

    public List<PeopleOld> findBySnilsOrderByStatusDesc(String s);


}
