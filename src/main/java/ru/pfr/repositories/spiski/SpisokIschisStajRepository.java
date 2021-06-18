package ru.pfr.repositories.spiski;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pfr.model.spiski.SpisokDejatel;
import ru.pfr.model.spiski.SpisokIschisStaj;

import java.util.List;
import java.util.Optional;

public interface SpisokIschisStajRepository extends JpaRepository<SpisokIschisStaj, Long> {
    public Optional<SpisokIschisStaj> findById(Long l);
    public List<SpisokIschisStaj> findAll();
}
