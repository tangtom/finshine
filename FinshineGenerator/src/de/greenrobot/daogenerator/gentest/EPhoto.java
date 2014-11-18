package de.greenrobot.daogenerator.gentest;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class EPhoto  {
    public static void addEntity(Schema schema) {
        Entity entity = schema.addEntity(EPhoto.class.getSimpleName());
        entity.implementsSerializable();
        entity.addIdProperty().autoincrement();
        entity.addLongProperty("photoId").columnName("photoId");
        entity.addStringProperty("mimeType").columnName("mimeType");
        entity.addByteArrayProperty("photoData").columnName("photoData");
    }
}
