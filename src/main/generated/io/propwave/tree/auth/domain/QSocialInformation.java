package io.propwave.tree.auth.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSocialInformation is a Querydsl query type for SocialInformation
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QSocialInformation extends BeanPath<SocialInformation> {

    private static final long serialVersionUID = -1130342027L;

    public static final QSocialInformation socialInformation = new QSocialInformation("socialInformation");

    public final StringPath email = createString("email");

    public final StringPath socialId = createString("socialId");

    public final EnumPath<SocialType> socialType = createEnum("socialType", SocialType.class);

    public QSocialInformation(String variable) {
        super(SocialInformation.class, forVariable(variable));
    }

    public QSocialInformation(Path<? extends SocialInformation> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSocialInformation(PathMetadata metadata) {
        super(SocialInformation.class, metadata);
    }

}

