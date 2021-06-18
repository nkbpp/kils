package ru.pfr.repositories.spiski;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pfr.model.spiski.SpisokRaiKoef;
import ru.pfr.model.spiski.SpisokTerUsl;

import java.util.List;
import java.util.Optional;

public interface SpisokTerUslRepository extends JpaRepository<SpisokTerUsl, Long> {
    public Optional<SpisokTerUsl> findById(Long l);
    public List<SpisokTerUsl> findAll();
}
