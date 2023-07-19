package io.propwave.tree.auth.infrastructure;

import io.propwave.tree.auth.domain.SocialType;
import io.propwave.tree.auth.domain.SocialUser;
import io.propwave.tree.auth.domain.User;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface SocialUserRepository extends Repository<SocialUser, Long> {

    // CREATE
    void save(SocialUser socialUser);

    // READ
    Optional<SocialUser> findByUserId(Long userId);
    Optional<SocialUser> findByUserAndSocialType(User user, SocialType socialType);
    Optional<SocialUser> findByUserAndUsername(User user, String username);
    Optional<SocialUser> findBySocialIdAndSocialType(String socialId, SocialType socialType);
    boolean existsBySocialIdAndSocialType(String socialId, SocialType socialType);
    List<SocialUser> findAllByUser(User user);

    // UPDATE

    // DELETE
    void delete(SocialUser socialUser);
}
