package ru.pfr.repositories.SOPS;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pfr.model.Staj_table;
import ru.pfr.model.SvedZarMounth;

import java.util.List;
import java.util.Optional;

public interface SvedZarMounthRepository extends JpaRepository<SvedZarMounth, Long> {
    public Optional<SvedZarMounth> findById(Long l);
    public List<SvedZarMounth> findAll();
}
