package me.yoon.myshop.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTimestamped is a Querydsl query type for Timestamped
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QTimestamped extends EntityPathBase<Timestamped> {

    private static final long serialVersionUID = 1177376563L;

    public static final QTimestamped timestamped = new QTimestamped("timestamped");

    public final DateTimePath<java.time.LocalDateTime> registerTime = createDateTime("registerTime", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> updateTime = createDateTime("updateTime", java.time.LocalDateTime.class);

    public QTimestamped(String variable) {
        super(Timestamped.class, forVariable(variable));
    }

    public QTimestamped(Path<? extends Timestamped> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTimestamped(PathMetadata metadata) {
        super(Timestamped.class, metadata);
    }

}

