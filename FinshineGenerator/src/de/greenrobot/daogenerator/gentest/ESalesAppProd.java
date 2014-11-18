package de.greenrobot.daogenerator.gentest;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class ESalesAppProd {
    public static void addEntity(Schema schema) {
        Entity entity = schema.addEntity(ESalesAppProd.class.getSimpleName());
        entity.implementsSerializable();
        entity.addIdProperty().autoincrement();

        entity.addLongProperty("salesAppProdId").columnName("salesAppProdId");
        // 理财师
        entity.addLongProperty("salesId").columnName("salesId");
        entity.addStringProperty("salesXy").columnName("salesXy");
        entity.addStringProperty("salesTopsale").columnName("salesTopsale");
        entity.addStringProperty("salesOrderamount").columnName(
                "salesOrderamount");
        entity.addStringProperty("salesPhone").columnName("salesPhone");
        entity.addFloatProperty("prodAmount").columnName("prodAmount");

    }
}
