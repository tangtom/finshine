package de.greenrobot.daogenerator.gentest;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class EProdInvestMent {
    public static void addEntity(Schema schema) {
        Entity entity = schema.addEntity(EProdInvestMent.class.getSimpleName());
        entity.implementsSerializable();
        entity.addIdProperty().autoincrement();

        entity.addLongProperty("prodInvestMentId").columnName(
                "prodInvestMentId");
        /**
         * 是否删除（I：删除;A:未删除）
         */
        entity.addStringProperty("status").columnName("status");
        /**
         * 创建时间
         */
        entity.addDateProperty("created").columnName("created");
        /**
         * 修改时间
         */
        entity.addDateProperty("lastmod").columnName("lastmod");
        // 版本号
        entity.addLongProperty("version").columnName("version");
        // 产品id
        entity.addLongProperty("prodId").columnName("prodId");
        // 客户id
        entity.addStringProperty("customIds").columnName("customIds");
        // 理财师
        entity.addLongProperty("salesId").columnName("salesId");

    }
}
