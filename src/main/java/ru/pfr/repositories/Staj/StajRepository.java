package ru.pfr.repositories.Staj;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pfr.model.People;
import ru.pfr.model.Staj;

import java.util.List;
import java.util.Optional;

public interface StajRepository extends JpaRepository<Staj, Long> {
    public Optional<Staj> findById(Long l);
    public List<Staj> findAll();
}
