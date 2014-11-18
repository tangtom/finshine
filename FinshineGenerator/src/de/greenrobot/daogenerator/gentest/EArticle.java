package de.greenrobot.daogenerator.gentest;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * 首页文章信息
 */
public class EArticle {
    public static void addEntity(Schema schema) {
        Entity entity = schema.addEntity(EArticle.class.getSimpleName());
        entity.addIdProperty().autoincrement();
        /**
         * 文章id
         */
        entity.addLongProperty("articleId").columnName("articleId");
        /**
         * 文章标题
         */
        entity.addStringProperty("title").columnName("title");
        /**
         * 发布时间
         */
        entity.addLongProperty("postTime").columnName("postTime");
        /**
         * 创建时间
         */
        entity.addLongProperty("created").columnName("created");
        /**
         * 修改时间
         */
        entity.addLongProperty("lastmod").columnName("lastmod");
        /**
         * 来源
         */
        entity.addStringProperty("resource").columnName("resource");
        /**
         * 作者
         */
        entity.addStringProperty("author").columnName("author");
        /**
         * 文章内容
         */
        entity.addStringProperty("content").columnName("content");
        /**
         * 是否删除（I：删除;A:未删除）
         */
        entity.addStringProperty("status").columnName("status");

    }
}
