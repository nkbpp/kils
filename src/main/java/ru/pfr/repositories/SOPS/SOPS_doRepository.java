package ru.pfr.repositories.SOPS;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pfr.model.SOPS_do;
import ru.pfr.model.SOPS_table;

import java.util.List;
import java.util.Optional;

public interface SOPS_doRepository extends JpaRepository<SOPS_do, Long> {
    public Optional<SOPS_do> findById(Long l);
    public List<SOPS_do> findAll();
}
