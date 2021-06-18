package ru.pfr.repositories.count;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pfr.model.Inoe;
import ru.pfr.model.count.Kolfile;

import java.util.List;
import java.util.Optional;

public interface KolfileRepository extends JpaRepository<Kolfile, Long> {
    public Optional<Kolfile> findById(Long l);
    public List<Kolfile> findAll();
}
