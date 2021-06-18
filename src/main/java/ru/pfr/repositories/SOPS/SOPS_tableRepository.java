package ru.pfr.repositories.SOPS;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pfr.model.SOPS_table;
import ru.pfr.model.SvedZarGod;

import java.util.List;
import java.util.Optional;

public interface SOPS_tableRepository extends JpaRepository<SOPS_table, Long> {
    public Optional<SOPS_table> findById(Long l);
    public List<SOPS_table> findAll();
}
