package de.greenrobot.daogenerator.gentest;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class EProdReleasestrategy {
    public static void addEntity(Schema schema) {
        Entity entity = schema.addEntity(EProdReleasestrategy.class.getSimpleName());
        entity.addIdProperty().autoincrement();

        entity.addLongProperty("prodReleasestrategyId").columnName(
                "prodReleasestrategyId");
        entity.addStringProperty("status").columnName("status");
        /**
         * 创建时间
         */
        entity.addDateProperty("created").columnName("created");
        /**
         * 修改时间
         */
        entity.addDateProperty("lastmod").columnName("lastmod");
        entity.addLongProperty("version").columnName("version");
        entity.addLongProperty("salesIds").columnName("salesIds");
        entity.addLongProperty("prodId").columnName("prodId");

    }
}