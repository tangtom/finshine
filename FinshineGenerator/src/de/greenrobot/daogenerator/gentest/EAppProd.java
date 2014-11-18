package de.greenrobot.daogenerator.gentest;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class EAppProd {
    public static void addEntity(Schema schema) {
        Entity entity = schema.addEntity(EAppProd.class.getSimpleName());
        entity.implementsSerializable();
        entity.addIdProperty().autoincrement();

        entity.addLongProperty("appProdId").columnName("appProdId");
        entity.addStringProperty("prodName").columnName("prodName");
        entity.addStringProperty("prodMyid").columnName("prodMyid");
        entity.addStringProperty("prodAdmin").columnName("prodAdmin");
        entity.addLongProperty("userId").columnName("userId");
        entity.addDateProperty("created").columnName("created");
        entity.addFloatProperty("prodAmount").columnName("prodAmount");
        entity.addLongProperty("prodStatus").columnName("prodStatus");

    }
}
