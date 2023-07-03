package io.propwave.tree.auth.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProfile is a Querydsl query type for Profile
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QProfile extends BeanPath<Profile> {

    private static final long serialVersionUID = -461918433L;

    public static final QProfile profile = new QProfile("profile");

    public final StringPath profileBio = createString("profileBio");

    public final StringPath profileImg = createString("profileImg");

    public final StringPath profileName = createString("profileName");

    public QProfile(String variable) {
        super(Profile.class, forVariable(variable));
    }

    public QProfile(Path<? extends Profile> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProfile(PathMetadata metadata) {
        super(Profile.class, metadata);
    }

}

