package ru.pfr.repositories.xlsx;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pfr.model.xlsx.DeregisteredEmployers;

import java.util.List;
import java.util.Optional;

public interface DeregisteredEmployersRepository extends JpaRepository<DeregisteredEmployers, Long> {
    public Optional<DeregisteredEmployers> findById(Long l);
    public Optional<DeregisteredEmployers> findByRegnum(String s);
    public List<DeregisteredEmployers> findAll();
}
