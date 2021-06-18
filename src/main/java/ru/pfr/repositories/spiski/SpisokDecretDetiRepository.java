package ru.pfr.repositories.spiski;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pfr.model.Inoe;
import ru.pfr.model.spiski.SpisokDecretDeti;

import java.util.List;
import java.util.Optional;

public interface SpisokDecretDetiRepository extends JpaRepository<SpisokDecretDeti, Long> {
    public Optional<SpisokDecretDeti> findById(Long l);
    public List<SpisokDecretDeti> findAll();
}
