package io.propwave.tree.auth.infrastructure;

import io.propwave.tree.auth.domain.SocialType;
import io.propwave.tree.auth.domain.User;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface UserRepository extends Repository<User, Long> {

    // CREATE
    User save(User user);

    // READ
    Optional<User> findBySocialId(String socialId);

}
