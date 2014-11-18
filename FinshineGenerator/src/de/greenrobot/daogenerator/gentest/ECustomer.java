//Source file: D:\\workspace\\finshine\\finshine_server\\src\\main\\java\\com\\incito\\finshine\\crms\\mci\\domain\\Customer.java

package de.greenrobot.daogenerator.gentest;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * 投资人信息
 */
public class ECustomer {
    public static void addEntity(Schema schema) {
        Entity entity = schema.addEntity(ECustomer.class.getSimpleName());
        entity.implementsSerializable();
        entity.addIdProperty().autoincrement();
        /**
         * 年龄 投资人的最小年龄是多少
         */
        entity.addIntProperty("age").columnName("age");
        /**
         * 年收入
         */
        entity.addLongProperty("annualIncome").columnName("annualIncome");
        /**
         * 账单方式 0纸质 1电子
         */
        entity.addIntProperty("billType").columnName("billType");
        /**
         * 出生年月日
         */
        entity.addLongProperty("birthday").columnName("birthday");
        /**
         * 所在城市
         */
        entity.addStringProperty("city").columnName("city");
        /**
         * 联系地址
         */
        entity.addStringProperty("contactAddress").columnName("contactAddress");

        /**
         * 国家
         */
        entity.addStringProperty("country").columnName("country");
        /**
         * 当前已投金额
         */
        entity.addLongProperty("currentInvestValue").columnName(
                "currentInvestValue");
        /**
         * 学历 0未知 1未上学 2小学毕业 3初中毕业 4高中（中专、技校） 5大学（大专、本科） 6硕士 7博士
         */
        entity.addIntProperty("diploma").columnName("diploma");
        /**
         * 区县
         */
        entity.addStringProperty("district").columnName("district");
        /**
         * 常用电子邮件
         */
        entity.addStringProperty("email1").columnName("email1");
        /**
         * 备用电子邮件
         */
        entity.addStringProperty("email2").columnName("email2");

        /**
         * 雇主 工作单位
         */
        entity.addStringProperty("employer").columnName("employer");

        /**
         * 传真号码
         */
        entity.addStringProperty("fax").columnName("fax");
        /**
         * 性别 0男性 1女性 3其他
         */
        entity.addIntProperty("gender").columnName("gender");
        /**
         * 证件号码
         */
        entity.addStringProperty("IDNumber").columnName("IDNumber");
        /**
         * 身份证有效期
         */
        entity.addDateProperty("IDValid").columnName("IDValid");
        /**
         * 行业 诸如：教育、医疗卫生、政府、互联网
         */
        entity.addStringProperty("industry").columnName("industry");

        /**
         * 已投资次数
         */
        entity.addStringProperty("investNumber").columnName("investNumber");
        /**
         * 投资偏好 是不是列表？
         */
        entity.addStringProperty("investPreference").columnName(
                "investPreference");
        /**
         * 投资来源
         */
        entity.addStringProperty("investSource").columnName("investSource");
        /**
         * 关键字 描述投资人的一段文本文字
         */
        entity.addStringProperty("keyword").columnName("keyword");

        /**
         * 婚姻： 1未婚，2已婚，3离异，4丧偶，0未知
         */
        entity.addIntProperty("maritalStatus").columnName("maritalStatus");
        /**
         * 投资人姓名
         */
        entity.addStringProperty("name").columnName("name");
        /**
         * 国籍
         */
        entity.addStringProperty("nationality").columnName("nationality");
        /**
         * 昵称
         */
        entity.addStringProperty("nickName").columnName("nickName");
        /**
         * 护照最近一次入境时间
         */
        entity.addDateProperty("passportEntryTime").columnName(
                "passportEntryTime");

        /**
         * 峰值投资额
         */
        entity.addLongProperty("peakInvestValue").columnName("peakInvestValue");
        /**
         * 职务
         */
        entity.addStringProperty("position").columnName("position");
        /**
         * 职业
         */
        entity.addStringProperty("job").columnName("job");
        /**
         * 已收益
         */
        entity.addLongProperty("profit").columnName("profit");
        /**
         * 所在省
         */
        entity.addStringProperty("province").columnName("province");

        /**
         * 住址
         */
        entity.addStringProperty("residentialAddress").columnName(
                "residentialAddress");
        /**
         * 座机
         */
        entity.addStringProperty("telephone").columnName("telephone");
        /**
         * 邮政编码
         */
        entity.addStringProperty("zipcode").columnName("zipcode");
        /**
         * 证件类型 0-身份证 1-军官证 2-护照
         */
        entity.addIntProperty("idtype").columnName("idtype");
        /**
         * 客户id
         */
        entity.addLongProperty("customerId").columnName("customerId");
        entity.addStringProperty("salesId").columnName("salesId");

    }
}
