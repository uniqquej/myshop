package me.yoon.myshop.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QItemImg is a Querydsl query type for ItemImg
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QItemImg extends EntityPathBase<ItemImg> {

    private static final long serialVersionUID = -946362962L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QItemImg itemImg = new QItemImg("itemImg");

    public final me.yoon.myshop.entity.QTimestamped _super = new me.yoon.myshop.entity.QTimestamped(this);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imgName = createString("imgName");

    public final StringPath imgUrl = createString("imgUrl");

    public final me.yoon.myshop.entity.QItem item;

    public final StringPath oriImgName = createString("oriImgName");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> registerTime = _super.registerTime;

    public final StringPath representationImgYn = createString("representationImgYn");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTime = _super.updateTime;

    public QItemImg(String variable) {
        this(ItemImg.class, forVariable(variable), INITS);
    }

    public QItemImg(Path<? extends ItemImg> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QItemImg(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QItemImg(PathMetadata metadata, PathInits inits) {
        this(ItemImg.class, metadata, inits);
    }

    public QItemImg(Class<? extends ItemImg> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.item = inits.isInitialized("item") ? new me.yoon.myshop.entity.QItem(forProperty("item")) : null;
    }

}

