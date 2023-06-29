package io.propwave.tree.auth.infrastructure;

import io.propwave.tree.auth.domain.ProfileDecorate;
import org.springframework.data.repository.Repository;

public interface ProfileDecorateRepository extends Repository<ProfileDecorate, Long> {

    void save(ProfileDecorate profileDecorate);
}
