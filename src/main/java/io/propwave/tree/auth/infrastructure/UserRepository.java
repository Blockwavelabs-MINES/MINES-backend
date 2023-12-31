package io.propwave.tree.auth.infrastructure;

import io.propwave.tree.auth.domain.User;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface UserRepository extends Repository<User, Long> {

    // CREATE
    User save(User user);

    // READ
    Optional<User> findById(Long id);
    Optional<User> findByUserId(String userId);
    Optional<User> findBySocialInformationEmail(String email);
    Boolean existsByUserId(String userId);
    Boolean existsBySocialInformationEmail(String email);

}
