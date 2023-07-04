package io.propwave.tree.auth.infrastructure;

import io.propwave.tree.auth.domain.ProfileDecorate;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface ProfileDecorateRepository extends Repository<ProfileDecorate, Long> {

    // CREATE
    void save(ProfileDecorate profileDecorate);

    // READ
    Optional<ProfileDecorate> findByUserUserId(String userId);

    // UPDATE

    // DELETE
}
