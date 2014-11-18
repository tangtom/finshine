package de.greenrobot.daogenerator.gentest;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * 文章广告信息
 */
public class EArticleAd {
    public static void addEntity(Schema schema) {
        Entity entity = schema.addEntity(EArticleAd.class.getSimpleName());
        entity.implementsSerializable();
        entity.addIdProperty().autoincrement();

        /**
         * 轮播广告id
         */
        entity.addLongProperty("articleAdId").columnName("articleAdId");
        /**
         * 关联文章id
         */
        entity.addLongProperty("articleId").columnName("articleId");
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
        entity.addStringProperty("title").columnName("title");

    }
}
