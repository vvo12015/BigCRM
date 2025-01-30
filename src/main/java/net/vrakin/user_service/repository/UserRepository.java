package net.vrakin.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import net.vrakin.user_service.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByName(String name);
    Optional<User> findByPhone(String phone);
    List<User> findByNameLike(String namePattern);

    boolean existsByEmail(String email);
    boolean existsByName(String name);
}
