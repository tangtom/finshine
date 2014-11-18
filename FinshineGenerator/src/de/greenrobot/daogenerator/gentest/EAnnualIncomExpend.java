//Source file: D:\\workspace\\finshine\\finshine_server\\src\\main\\java\\com\\incito\\finshine\\crms\\mci\\domain\\AnnualIncomExpend.java

package de.greenrobot.daogenerator.gentest;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * 投资人年度收支
 */
public class EAnnualIncomExpend {
    public static void addEntity(Schema schema) {
        Entity entity = schema.addEntity(EAnnualIncomExpend.class
                .getSimpleName());
        entity.implementsSerializable();
        entity.addIdProperty().autoincrement();

        entity.addLongProperty("annualincomexpendId").columnName(
                "annualincomexpendId");
        /**
         * 对应的客户id
         */
        entity.addLongProperty("customerId").columnName("customerId");
        /**
         * 本人收入
         */
        entity.addLongProperty("income").columnName("customerId");
        /**
         * 配偶收入
         */
        entity.addLongProperty("spouseIncome").columnName("spouseIncome");
        /**
         * 年终奖金
         */
        entity.addLongProperty("endBonus").columnName("endBonus");
        /**
         * 房租收入
         */
        entity.addLongProperty("rental").columnName("rental");
        /**
         * 存款债券利息
         */
        entity.addLongProperty("interest").columnName("interest");
        /**
         * 股利股息
         */
        entity.addLongProperty("dividends").columnName("dividends");
        /**
         * 各类分红
         */
        entity.addLongProperty("otherDividends").columnName("otherDividends");
        /**
         * 其他固定收入
         */
        entity.addLongProperty("otherFixIncome").columnName("otherFixIncome");
        /**
         * 其他不固定收入
         */
        entity.addLongProperty("otherUnfixIncome").columnName(
                "otherUnfixIncome");
        /**
         * 家庭生活开销
         */
        entity.addLongProperty("totalIncome").columnName("totalIncome");
        /**
         * 家庭生活开销
         */
        entity.addLongProperty("livingCost").columnName("livingCost");
        /**
         * 房屋支出
         */
        entity.addLongProperty("housExpense").columnName("housExpense");
        /**
         * 医疗费
         */
        entity.addLongProperty("medicExpense").columnName("medicExpense");
        /**
         * 子女教育费
         */
        entity.addLongProperty("eduExpense").columnName("eduExpense");
        /**
         * 保险费
         */
        entity.addLongProperty("insurance").columnName("insurance");
        /**
         * 旅游费
         */
        entity.addLongProperty("travel").columnName("travel");
        /**
         * 赡养费
         */
        entity.addLongProperty("alimony").columnName("alimony");
        /**
         * 其他固定支出
         */
        entity.addLongProperty("otherFixExpense").columnName("otherFixExpense");
        /**
         * 其他不固定支出
         */
        entity.addLongProperty("otherUnfixExpense").columnName(
                "otherUnfixExpense");
        /**
         * 支出合计
         */
        entity.addLongProperty("totalExpenditure").columnName(
                "totalExpenditure");
        /**
         * 每年结余
         */
        entity.addLongProperty("annualBalance").columnName("annualBalance");
        /**
         * 历史投资经验 txt文本描述
         */
        entity.addStringProperty("historyExperience").columnName(
                "historyExperience");
    }
}
