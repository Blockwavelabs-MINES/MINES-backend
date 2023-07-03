package io.propwave.tree.auth.infrastructure;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.propwave.tree.auth.domain.Link;
import io.propwave.tree.auth.domain.QLink;
import io.propwave.tree.auth.domain.QUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class LinkRepositoryCustomImpl implements LinkRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Link> findAllByUserId(Long userId) {
        QLink qLink = QLink.link;
        QUser qUser = QUser.user;

        return jpaQueryFactory
                .selectFrom(qLink)
                .join(qLink.user, qUser)
                .where(qUser.id.eq(qLink.user.id))
                .fetch();
    }
}
