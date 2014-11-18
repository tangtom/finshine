package de.greenrobot.daogenerator.gentest;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Schema;

public class FinshineDaoGenerator {
    private static final String packageName = "com.finshine.dao";
    private static final String genPath = "../finshine_client/src-gen";

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, packageName);

        EAdvertisement.addEntity(schema);
        EAnnualIncomExpend.addEntity(schema);
        EAppProd.addEntity(schema);
        EArticle.addEntity(schema);
        EArticleAd.addEntity(schema);
        EAssetInfo.addEntity(schema);
        EContactNote.addEntity(schema);
        ECustomer.addEntity(schema);
        ENotice.addEntity(schema);
        EPhoto.addEntity(schema);
        EProdAppointment.addEntity(schema);
        EProdCollection.addEntity(schema);
        EProdInvestMent.addEntity(schema);
        EProdLike.addEntity(schema);
        EProdReleasestrategy.addEntity(schema);
        EProduct.addEntity(schema);
        EProductSearch.addEntity(schema);
        ESalesAppProd.addEntity(schema);
        ETodoItem.addEntity(schema);
        new DaoGenerator().generateAll(schema, genPath);
    }

}
