package de.greenrobot.daogenerator.gentest;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class EProductSearch {
    public static void addEntity(Schema schema) {
        Entity entity = schema.addEntity(EProductSearch.class.getSimpleName());
        entity.implementsSerializable();
        entity.addIdProperty().autoincrement();
        // 用户输入产品名称 模糊查询
        entity.addStringProperty("searchProdName").columnName("searchProdName");
        entity.addStringProperty("searchProdOnDateTime").columnName(
                "searchProdOnDateTime");// 用户输入或选择 发行期开始
        entity.addStringProperty("searchProdEnDateTime").columnName(
                "searchProdEnDateTime");// 用户输入或选择 发行期结束
        entity.addStringProperty("searchProdFirstType").columnName(
                "searchProdFirstType");// 用户选择 一级类型
        entity.addStringProperty("searchProdSecondtype").columnName(
                "searchProdSecondtype");// 用户选择 二级类型
        entity.addStringProperty("searchProdStatus").columnName(
                "searchProdStatus");// 用户选择 状态
        entity.addStringProperty("searchProdStartLow").columnName(
                "searchProdStartLow");// 用户输入最低起投金额
        entity.addStringProperty("searchProdStartTop").columnName(
                "searchProdStartTop");// 用户输入最高起投金额
        // 用户选择 参考收益的方式 1只查浮动 2只查询固定 3查固定+浮动 查浮动 searchFFType ==1 查浮动
        // searchFFType==2查固定 searchFFType==3浮动+固定 searchFFLow 最低 searchFFTop最高
        entity.addStringProperty("searchFFType").columnName("searchFFType");
        entity.addStringProperty("searchFFTop").columnName("searchFFTop");// 用户选择
                                                                          // 最高收益
        entity.addStringProperty("searchFFLow").columnName("searchFFLow");// 用户选择
                                                                          // 最低收益
        entity.addStringProperty("searchProdTimeStart").columnName(
                "searchProdTimeStart");// 用户选择 期限开始
        entity.addStringProperty("searchProdTimeEnd").columnName(
                "searchProdTimeEnd");// 用户选择 期限结束
        entity.addStringProperty("searchProdPreference").columnName(
                "searchProdPreference");// 用户选择 发行商偏好 1金元惠

        entity.addLongProperty("salesId").columnName("salesId");// 理财师id
        entity.addStringProperty("searchProdId").columnName("searchProdId");// 推荐表专用查询字段
        entity.addStringProperty("searchSalesLike").columnName(
                "searchSalesLike");// 用户选择 只看收藏
        entity.addStringProperty("searchProdInvest").columnName(
                "searchProdInvest");// 用户选择 只看推荐
        entity.addStringProperty("searchOverDue").columnName("searchOverDue");// 用户选择
                                                                              // 只看已过期
        entity.addStringProperty("searchProdTop").columnName("searchProdTop");// 只看置顶
        entity.addStringProperty("sortDesc").columnName("sortDesc");// 排序方向
        entity.addStringProperty("sortProdOnDateTime").columnName(
                "sortProdOnDateTime"); // 排序 发行时间
        entity.addStringProperty("sortProdYieldFixed").columnName(
                "sortProdYieldFixed");// 排序 预期固定收益
        entity.addStringProperty("sortProdCommissionBase").columnName(
                "sortProdCommissionBase");// 排序 佣金
        entity.addStringProperty("sortProdTime").columnName("sortProdTime");// 排序
                                                                            // 期限
        entity.addStringProperty("sortProdSize").columnName("sortProdSize"); // 排序
                                                                             // 规模
        // 最热 待确定 收藏最多
        entity.addStringProperty("sortHot").columnName("sortHot");// 排序 收藏!! 最热

        entity.addIntProperty("pageNow").columnName("pageNow");// 分页使用
        entity.addIntProperty("pageStart").columnName("pageStart");// pageSize *
                                                                   // pageNow =
                                                                   // pageStar
                                                                   // 用于MySql
                                                                   // LIMIT分页使用
        entity.addIntProperty("pageSize").columnName("pageSize");

    }
}
