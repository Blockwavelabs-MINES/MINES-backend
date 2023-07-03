package io.propwave.tree.auth.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProfileDecorate is a Querydsl query type for ProfileDecorate
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProfileDecorate extends EntityPathBase<ProfileDecorate> {

    private static final long serialVersionUID = 1602793292L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProfileDecorate profileDecorate = new QProfileDecorate("profileDecorate");

    public final StringPath backgroundColor = createString("backgroundColor");

    public final StringPath backgroundImg = createString("backgroundImg");

    public final EnumPath<BackgroundType> backgroundType = createEnum("backgroundType", BackgroundType.class);

    public final StringPath buttonColor = createString("buttonColor");

    public final StringPath buttonFontColor = createString("buttonFontColor");

    public final StringPath fontColor = createString("fontColor");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QUser user;

    public QProfileDecorate(String variable) {
        this(ProfileDecorate.class, forVariable(variable), INITS);
    }

    public QProfileDecorate(Path<? extends ProfileDecorate> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProfileDecorate(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProfileDecorate(PathMetadata metadata, PathInits inits) {
        this(ProfileDecorate.class, metadata, inits);
    }

    public QProfileDecorate(Class<? extends ProfileDecorate> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

