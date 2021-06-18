package ru.pfr.repositories.Inoe;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pfr.model.Inoe;
import ru.pfr.model.Staj_table;

import java.util.List;
import java.util.Optional;

public interface InoeRepository extends JpaRepository<Inoe, Long> {
    public Optional<Inoe> findById(Long l);
    public List<Inoe> findAll();
}
