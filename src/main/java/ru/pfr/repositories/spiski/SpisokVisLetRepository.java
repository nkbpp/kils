package ru.pfr.repositories.spiski;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pfr.model.spiski.SpisokTerUsl;
import ru.pfr.model.spiski.SpisokVisLet;

import java.util.List;
import java.util.Optional;

public interface SpisokVisLetRepository extends JpaRepository<SpisokVisLet, Long> {
    public Optional<SpisokVisLet> findById(Long l);
    public List<SpisokVisLet> findAll();
}
