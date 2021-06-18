package ru.pfr.repositories.spiski;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pfr.model.spiski.SpisokDecretDeti;
import ru.pfr.model.spiski.SpisokDejatel;

import java.util.List;
import java.util.Optional;

public interface SpisokDejatelRepository extends JpaRepository<SpisokDejatel, Long> {
    public Optional<SpisokDejatel> findById(Long l);
    public List<SpisokDejatel> findAll();
}
