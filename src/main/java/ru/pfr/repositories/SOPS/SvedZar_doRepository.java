package ru.pfr.repositories.SOPS;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pfr.model.SOPS_table;
import ru.pfr.model.SvedZar_do;

import java.util.List;
import java.util.Optional;

public interface SvedZar_doRepository extends JpaRepository<SvedZar_do, Long> {
    public Optional<SvedZar_do> findById(Long l);
    public List<SvedZar_do> findAll();
}
