package io.propwave.tree.auth.infrastructure;

import io.propwave.tree.auth.domain.SocialType;
import io.propwave.tree.auth.domain.SocialUser;
import io.propwave.tree.auth.domain.User;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface SocialUserRepository extends Repository<SocialUser, Long> {

    // CREATE
    void save(SocialUser socialUser);

    // READ
    Optional<SocialUser> findByUserAndSocialTypeAndIsConnected(User user, SocialType socialType, Boolean isConnected);
    boolean existsBySocialIdAndSocialTypeAndIsConnected(String socialId, SocialType socialType, Boolean isConnected);

    // UPDATE

    // DELETE
    void delete(SocialUser socialUser);
}
