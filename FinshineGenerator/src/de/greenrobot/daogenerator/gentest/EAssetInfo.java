//Source file: D:\\workspace\\finshine\\finshine_server\\src\\main\\java\\com\\incito\\finshine\\crms\\mci\\domain\\AssetInfo.java

package de.greenrobot.daogenerator.gentest;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class EAssetInfo {
    public static void addEntity(Schema schema) {
        Entity entity = schema.addEntity(EAssetInfo.class.getSimpleName());
        entity.implementsSerializable();
        entity.addIdProperty().autoincrement();

        entity.addLongProperty("assetInfoId").columnName("assetInfoId");
        entity.addLongProperty("customerId").columnName("customerId");
        /**
         * 按照理财目标类别标记
         */
        entity.addStringProperty("goal").columnName("goal");
        /**
         * 现金
         */
        entity.addLongProperty("cash").columnName("cash");
        entity.addFloatProperty("cashReturn").columnName("cashReturn");
        /**
         * 活期存款
         */
        entity.addLongProperty("demanDeposit").columnName("demanDeposit");
        entity.addFloatProperty("demanDepositReturn").columnName(
                "demanDepositReturn");
        /**
         * 活期存款
         */
        entity.addLongProperty("fixDeposit").columnName("fixDeposit");
        entity.addFloatProperty("fixDepositReturn").columnName(
                "fixDepositReturn");
        /**
         * 股票
         */
        entity.addLongProperty("stock").columnName("stock");
        entity.addFloatProperty("stockReturn").columnName("stockReturn");
        /**
         * 基金
         */
        entity.addLongProperty("fund").columnName("fund");
        entity.addFloatProperty("fundReturn").columnName("fundReturn");
        /**
         * 债券
         */
        entity.addLongProperty("bond").columnName("bond");
        entity.addFloatProperty("bondReturn").columnName("bondReturn");
        /**
         * 房产
         */
        entity.addLongProperty("estate").columnName("estate");
        entity.addFloatProperty("estateReturn").columnName("estateReturn");
        /**
         * 其他资产
         */
        entity.addLongProperty("otherAsset").columnName("otherAsset");
        entity.addFloatProperty("otherAssetReturn").columnName(
                "otherAssetReturn");
        /**
         * 资产总计
         */
        entity.addLongProperty("totalAsset").columnName("totalAsset");

        /**
         * 房屋公积金贷款
         */
        entity.addLongProperty("houseFundLoan").columnName("houseFundLoan");
        /**
         * 房屋商业贷款
         */
        entity.addLongProperty("houseBusinessLoan").columnName(
                "houseBusinessLoan");
        /**
         * 汽车贷款
         */
        entity.addLongProperty("carLoan").columnName("carLoan");
        /**
         * 消费贷款
         */
        entity.addLongProperty("consumerLoan").columnName("consumerLoan");
        /**
         * 装修贷款
         */
        entity.addLongProperty("renovationLoan").columnName("renovationLoan");
        /**
         * 民间贷款
         */
        entity.addLongProperty("privateLoan").columnName("privateLoan");
        /**
         * 信用卡未付款
         */
        entity.addLongProperty("unpaidCreditCard").columnName(
                "unpaidCreditCard");
        /**
         * 其他负债
         */
        entity.addLongProperty("otherLoan").columnName("otherLoan");
        /**
         * 负债总计
         */
        entity.addLongProperty("totalLoan").columnName("totalLoan");
        /**
         * 净资产
         */
        entity.addLongProperty("netAsset").columnName("netAsset");

    }
}
