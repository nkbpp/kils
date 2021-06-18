package ru.pfr.repositories.Staj;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pfr.model.Staj;
import ru.pfr.model.Staj_table;

import java.util.List;
import java.util.Optional;

public interface Staj_tableRepository extends JpaRepository<Staj_table, Long> {
    public Optional<Staj_table> findById(Long l);
    public List<Staj_table> findAll();
}
