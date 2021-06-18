package ru.pfr.repositories.User;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pfr.model.Rayon;


import java.util.List;
import java.util.Optional;

public interface RayonRepository extends JpaRepository<Rayon, Long> {
    List<Rayon> findByKodNotAndKodNot(String p1, String p2);

    public Optional<Rayon> findByKod(String kod);
}
