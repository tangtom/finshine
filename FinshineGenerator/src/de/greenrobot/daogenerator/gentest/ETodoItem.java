package de.greenrobot.daogenerator.gentest;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * Created with IntelliJ IDEA. User: junjun long: 14-4-18 Time: 上午8:05
 */
public class ETodoItem {
    public static void addEntity(Schema schema) {
        Entity entity = schema.addEntity(ETodoItem.class.getSimpleName());
        entity.implementsSerializable();
        entity.addIdProperty().autoincrement();

        entity.addLongProperty("todoItemId").columnName("todoItemId");
        // 理财师id
        entity.addLongProperty("userId").columnName("userId");
        // 开始时间
        entity.addLongProperty("startTime").columnName("startTime");
        // 结束时间
        entity.addLongProperty("endTime").columnName("endTime");
        // 客户(可选)
        entity.addLongProperty("customerId").columnName("customerId");
        // 标题
        entity.addStringProperty("title").columnName("title");
        // 事项
        entity.addStringProperty("content").columnName("content");
        // 状态(已完成、未完成)
        entity.addIntProperty("status").columnName("status");

    }
}
