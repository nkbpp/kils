package ru.pfr.repositories.User;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.pfr.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    //User findByLogin(String username);
    public Optional<User> findByLogin(String login);
}

