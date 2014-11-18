//Source file: D:\\workspace\\finshine\\finshine_server\\src\\main\\java\\com\\incito\\finshine\\crms\\mci\\domain\\ContactNote.java

package de.greenrobot.daogenerator.gentest;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * 投资人联系纪要 作为理财师，我能够记录和客户每次沟通的沟通纪要，包括投资意向
 */
public class EContactNote {
    public static void addEntity(Schema schema) {
        Entity entity = schema.addEntity(EContactNote.class.getSimpleName());
        entity.implementsSerializable();
        entity.addIdProperty().autoincrement();

        entity.addLongProperty("contactNoteId").columnName("contactNoteId");
        /**
         * 投资人编号
         */
        entity.addLongProperty("customerId").columnName("customerId");
        /**
         * 理财师id
         */
        entity.addLongProperty("salesId").columnName("salesId");
        /**
         * 沟通日期
         */
        entity.addLongProperty("contactDate").columnName("contactDate");
        /**
         * 沟通标题
         */
        entity.addStringProperty("title").columnName("title");
        /**
         * 沟通记录内容
         */
        entity.addLongProperty("content").columnName("content");

    }
}
