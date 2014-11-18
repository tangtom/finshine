package de.greenrobot.daogenerator.gentest;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class EProduct {
    public static void addEntity(Schema schema) {
        Entity entity = schema.addEntity(EProduct.class.getSimpleName());
        entity.implementsSerializable();
        entity.addIdProperty().autoincrement();

        entity.addLongProperty("productId").columnName("productId");
        entity.addStringProperty("status").columnName("status");
        /**
         * 创建时间
         */
        entity.addLongProperty("created").columnName("created");
        /**
         * 修改时间
         */
        entity.addLongProperty("lastmod").columnName("lastmod");
        // 版本号
        entity.addLongProperty("version").columnName("version");
        // 产品编号
        entity.addStringProperty("prodMyid").columnName("prodMyid");
        // 产品类型1
        entity.addLongProperty("prodFirstType").columnName("prodFirstType");
        // 产品类型2
        entity.addLongProperty("prodSecondtype").columnName("prodSecondtype");
        // 产品状态 1新建 2审核 3预约 4预售 5在售 6封账 7下架
        entity.addLongProperty("prodStatus").columnName("prodStatus");
        // 产品要素
        entity.addStringProperty("prodElementsSrc").columnName(
                "prodElementsSrc");
        // 产品ppt
        entity.addStringProperty("prodPptSrc").columnName("prodPptSrc");
        // 产品说明书
        entity.addStringProperty("prodInstructionsSrc").columnName(
                "prodInstructionsSrc");
        // 产品合同
        entity.addStringProperty("prodContractSrc").columnName(
                "prodContractSrc");
        // 净值报告
        entity.addStringProperty("prodNetreportSrc").columnName(
                "prodNetreportSrc");
        // 产品培训资料
        entity.addStringProperty("prodTrainingSrc").columnName(
                "prodTrainingSrc");
        // 产品名称
        entity.addStringProperty("prodName").columnName("prodName");
        // 产品亮点
        entity.addStringProperty("prodHighLights").columnName("prodHighLights");
        // 管理人
        entity.addStringProperty("prodAdmin").columnName("prodAdmin");
        // 发行商偏好
        entity.addStringProperty("prodPreference").columnName("prodPreference");
        // 发行商电话
        entity.addStringProperty("prodPublisher").columnName("prodPublisher");
        // 发行商EMAIL
        entity.addStringProperty("prodPubEmail").columnName("prodPubEmail");
        // 发行日期开始
        entity.addLongProperty("prodOnDateTime").columnName("prodOnDateTime");
        // 发行日期开始
        entity.addLongProperty("prodEnDateTime").columnName("prodEnDateTime");
        // 产品规模
        entity.addFloatProperty("prodSize").columnName("prodSize");
        // 产品期限
        entity.addFloatProperty("prodTime").columnName("prodTime");
        // 投资者参考收益浮动
        entity.addFloatProperty("prodYieldFloating").columnName(
                "prodYieldFloating");
        // 投资者参考收益率固定
        entity.addFloatProperty("prodYieldFixed").columnName("prodYieldFixed");
        // 每份价格
        entity.addFloatProperty("prodPrice").columnName("prodPrice");
        // 认购起点
        entity.addFloatProperty("prodStart").columnName("prodStart");

        entity.addStringProperty("prodCounterParty").columnName(
                "prodCounterParty");// 交易对手
        entity.addStringProperty("prodUse").columnName("prodUse");// 资金用途
        entity.addStringProperty("prodRepayment").columnName("prodRepayment");// 还款来源
        entity.addStringProperty("prodRisk").columnName("prodRisk");// 风险控制措施
        entity.addStringProperty("prodIncomeType").columnName("prodIncomeType");// 收益分配方式
        entity.addStringProperty("prodReceipts").columnName("prodReceipts");// 收益发放日对应收益
        entity.addStringProperty("prodIncomeDateTime").columnName(
                "prodIncomeDateTime");// 收益发放日
        entity.addStringProperty("prodCstTel").columnName("prodCstTel");// 客服电话
        entity.addStringProperty("prodEscrowacCount").columnName(
                "prodEscrowacCount");// 托管账号
        // 佣金基数
        entity.addFloatProperty("prodCommissionBase").columnName(
                "prodCommissionBase");
        entity.addStringProperty("prodComment").columnName("prodComment");// 备注

        entity.addLongProperty("userId").columnName("userId");// 提交人
        entity.addStringProperty("prodCode").columnName("prodCode");// 产品代码
        entity.addLongProperty("prodTop").columnName("prodTop");// 产品置顶
        entity.addStringProperty("prodOnDateTimeStr").columnName(
                "prodOnDateTimeStr"); // 发行日期开始
        entity.addStringProperty("prodEnDateTimeStr").columnName(
                "prodEnDateTimeStr");// 发行日期开始
        entity.addLongProperty("isSave").columnName("isSave");// 是否收藏 1.
                                                              // 已经收藏；其它未收藏
        entity.addLongProperty("hotPoint").columnName("hotPoint");// 收藏的次数
                                                                  // 最热排序按照来排序
    }
}
