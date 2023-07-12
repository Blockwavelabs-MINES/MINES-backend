package io.propwave.tree.auth.infrastructure;

import io.propwave.tree.auth.domain.Link;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface LinkRepository extends Repository<Link, Long> {

    // CREATE
    void save(Link link);

    // READ
    Optional<Link> findById(Long id);
    List<Link> findAllByUserId(Long id);

    // UPDATE

    // DELETE
    void delete(Link link);
}
