package ru.pfr.repositories.spiski;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pfr.model.spiski.SpisokOsobUslTr;
import ru.pfr.model.spiski.SpisokRaiKoef;

import java.util.List;
import java.util.Optional;

public interface SpisokRaiKoefRepository extends JpaRepository<SpisokRaiKoef, Long> {
    public Optional<SpisokRaiKoef> findById(Long l);
    public List<SpisokRaiKoef> findAll();
}
