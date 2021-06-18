package ru.pfr.repositories.SOPS;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pfr.model.SvedZar_do;
import ru.pfr.model.SvedZar_posle;

import java.util.List;
import java.util.Optional;

public interface SvedZar_posleRepository extends JpaRepository<SvedZar_posle, Long> {
    public Optional<SvedZar_posle> findById(Long l);
    public List<SvedZar_posle> findAll();
}
