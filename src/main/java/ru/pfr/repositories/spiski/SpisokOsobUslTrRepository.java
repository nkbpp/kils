package ru.pfr.repositories.spiski;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pfr.model.spiski.SpisokIschisStaj;
import ru.pfr.model.spiski.SpisokOsobUslTr;

import java.util.List;
import java.util.Optional;

public interface SpisokOsobUslTrRepository extends JpaRepository<SpisokOsobUslTr, Long> {
    public Optional<SpisokOsobUslTr> findById(Long l);
    public List<SpisokOsobUslTr> findAll();
}
