package io.propwave.tree.auth.infrastructure;

import io.propwave.tree.auth.domain.SocialUser;
import org.springframework.data.repository.Repository;

public interface SocialUserRepository extends Repository<SocialUser, Long> {

    // CREATE
    void save(SocialUser socialUser);

    // READ

    // UPDATE

    // DELETE
}
