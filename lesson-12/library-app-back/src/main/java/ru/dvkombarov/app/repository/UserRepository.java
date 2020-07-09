package ru.dvkombarov.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dvkombarov.app.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> getByName(String name);
}
