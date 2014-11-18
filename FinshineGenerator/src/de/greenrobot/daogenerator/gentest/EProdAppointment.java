package de.greenrobot.daogenerator.gentest;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class EProdAppointment {
    public static void addEntity(Schema schema) {
        Entity entity = schema.addEntity(EProdAppointment.class.getSimpleName());
        entity.addIdProperty().autoincrement();

        entity.addLongProperty("prodAppointmentId").columnName(
                "prodAppointmentId");
        entity.addDateProperty("created").columnName("created");
        entity.addDateProperty("lastmod").columnName("lastmod");
        entity.addLongProperty("version").columnName("version");
        entity.addLongProperty("salesId").columnName("salesId");
        entity.addLongProperty("prodId").columnName("prodId");
        entity.addIntProperty("prodQty").columnName("prodQty");
        entity.addFloatProperty("prodAmount").columnName("prodAmount");

    }
}