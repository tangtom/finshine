package de.greenrobot.daogenerator.gentest;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * 轮播广告信息
 * 
 * @author qiujiaheng
 * 
 */
public class EAdvertisement {
    public static void addEntity(Schema schema) {
        Entity entity = schema.addEntity(EAdvertisement.class.getSimpleName());
        entity.implementsSerializable();
        entity.addIdProperty().autoincrement();
        entity.addLongProperty("advertisementId").columnName("advertisementId");
        /**
         * 关联文章id
         */
        entity.addLongProperty("articleId").columnName("articleId");
        /**
         * 广告图片
         */
        entity.addStringProperty("imageAd").columnName("imageAd");
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
