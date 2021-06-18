package ru.pfr.repositories.SOPS;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pfr.model.SvedZarGod;
import ru.pfr.model.SvedZarMounth;

import java.util.List;
import java.util.Optional;

public interface SvedZarGodRepository extends JpaRepository<SvedZarGod, Long> {
    public Optional<SvedZarGod> findById(Long l);
    public List<SvedZarGod> findAll();
}
