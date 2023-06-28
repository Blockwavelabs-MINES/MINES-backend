package io.propwave.tree.auth.infrastructure;

import io.propwave.tree.auth.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}
