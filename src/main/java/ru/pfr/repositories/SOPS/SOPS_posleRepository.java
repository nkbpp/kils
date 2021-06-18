package ru.pfr.repositories.SOPS;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pfr.model.SOPS_do;
import ru.pfr.model.SOPS_posle;

import java.util.List;
import java.util.Optional;

public interface SOPS_posleRepository extends JpaRepository<SOPS_posle, Long> {
    public Optional<SOPS_posle> findById(Long l);
    public List<SOPS_posle> findAll();
}
