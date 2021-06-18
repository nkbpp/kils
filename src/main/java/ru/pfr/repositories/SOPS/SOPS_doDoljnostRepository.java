package ru.pfr.repositories.SOPS;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pfr.model.SOPS_do_doljnost;
import ru.pfr.model.SvedZarGod;

import java.util.List;
import java.util.Optional;

public interface SOPS_doDoljnostRepository extends JpaRepository<SOPS_do_doljnost, Long> {
    public Optional<SOPS_do_doljnost> findById(Long l);
    public List<SOPS_do_doljnost> findAll();
}
