package de.greenrobot.daogenerator.gentest;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * 首页公告信息
 */
public class ENotice {
    public static void addEntity(Schema schema) {
        Entity entity = schema.addEntity(ENotice.class.getSimpleName());
        entity.implementsSerializable();
        entity.addIdProperty().autoincrement();
        /**
         * 公告id
         */
        entity.addLongProperty("noticeId").columnName("advertisementId");
        /**
         * 关联文章id
         */
        entity.addLongProperty("articleId").columnName("articleId");
        /**
         * 公告内容
         */
        entity.addStringProperty("content").columnName("content");
        /**
         * 创建时间
         */
        entity.addDateProperty("created").columnName("created");
        /**
         * 修改时间
         */
        entity.addDateProperty("lastmod").columnName("lastmod");
        /**
         * 是否删除（I：删除;A:未删除）
         */
        entity.addStringProperty("status").columnName("status");

    }
}
