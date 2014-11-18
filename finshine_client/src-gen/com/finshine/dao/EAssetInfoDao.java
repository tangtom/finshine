package com.finshine.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.finshine.dao.EAssetInfo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table EASSET_INFO.
*/
public class EAssetInfoDao extends AbstractDao<EAssetInfo, Long> {

    public static final String TABLENAME = "EASSET_INFO";

    /**
     * Properties of entity EAssetInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property AssetInfoId = new Property(1, Long.class, "assetInfoId", false, "assetInfoId");
        public final static Property CustomerId = new Property(2, Long.class, "customerId", false, "customerId");
        public final static Property Goal = new Property(3, String.class, "goal", false, "goal");
        public final static Property Cash = new Property(4, Long.class, "cash", false, "cash");
        public final static Property CashReturn = new Property(5, Float.class, "cashReturn", false, "cashReturn");
        public final static Property DemanDeposit = new Property(6, Long.class, "demanDeposit", false, "demanDeposit");
        public final static Property DemanDepositReturn = new Property(7, Float.class, "demanDepositReturn", false, "demanDepositReturn");
        public final static Property FixDeposit = new Property(8, Long.class, "fixDeposit", false, "fixDeposit");
        public final static Property FixDepositReturn = new Property(9, Float.class, "fixDepositReturn", false, "fixDepositReturn");
        public final static Property Stock = new Property(10, Long.class, "stock", false, "stock");
        public final static Property StockReturn = new Property(11, Float.class, "stockReturn", false, "stockReturn");
        public final static Property Fund = new Property(12, Long.class, "fund", false, "fund");
        public final static Property FundReturn = new Property(13, Float.class, "fundReturn", false, "fundReturn");
        public final static Property Bond = new Property(14, Long.class, "bond", false, "bond");
        public final static Property BondReturn = new Property(15, Float.class, "bondReturn", false, "bondReturn");
        public final static Property Estate = new Property(16, Long.class, "estate", false, "estate");
        public final static Property EstateReturn = new Property(17, Float.class, "estateReturn", false, "estateReturn");
        public final static Property OtherAsset = new Property(18, Long.class, "otherAsset", false, "otherAsset");
        public final static Property OtherAssetReturn = new Property(19, Float.class, "otherAssetReturn", false, "otherAssetReturn");
        public final static Property TotalAsset = new Property(20, Long.class, "totalAsset", false, "totalAsset");
        public final static Property HouseFundLoan = new Property(21, Long.class, "houseFundLoan", false, "houseFundLoan");
        public final static Property HouseBusinessLoan = new Property(22, Long.class, "houseBusinessLoan", false, "houseBusinessLoan");
        public final static Property CarLoan = new Property(23, Long.class, "carLoan", false, "carLoan");
        public final static Property ConsumerLoan = new Property(24, Long.class, "consumerLoan", false, "consumerLoan");
        public final static Property RenovationLoan = new Property(25, Long.class, "renovationLoan", false, "renovationLoan");
        public final static Property PrivateLoan = new Property(26, Long.class, "privateLoan", false, "privateLoan");
        public final static Property UnpaidCreditCard = new Property(27, Long.class, "unpaidCreditCard", false, "unpaidCreditCard");
        public final static Property OtherLoan = new Property(28, Long.class, "otherLoan", false, "otherLoan");
        public final static Property TotalLoan = new Property(29, Long.class, "totalLoan", false, "totalLoan");
        public final static Property NetAsset = new Property(30, Long.class, "netAsset", false, "netAsset");
    };


    public EAssetInfoDao(DaoConfig config) {
        super(config);
    }
    
    public EAssetInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'EASSET_INFO' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'assetInfoId' INTEGER," + // 1: assetInfoId
                "'customerId' INTEGER," + // 2: customerId
                "'goal' TEXT," + // 3: goal
                "'cash' INTEGER," + // 4: cash
                "'cashReturn' REAL," + // 5: cashReturn
                "'demanDeposit' INTEGER," + // 6: demanDeposit
                "'demanDepositReturn' REAL," + // 7: demanDepositReturn
                "'fixDeposit' INTEGER," + // 8: fixDeposit
                "'fixDepositReturn' REAL," + // 9: fixDepositReturn
                "'stock' INTEGER," + // 10: stock
                "'stockReturn' REAL," + // 11: stockReturn
                "'fund' INTEGER," + // 12: fund
                "'fundReturn' REAL," + // 13: fundReturn
                "'bond' INTEGER," + // 14: bond
                "'bondReturn' REAL," + // 15: bondReturn
                "'estate' INTEGER," + // 16: estate
                "'estateReturn' REAL," + // 17: estateReturn
                "'otherAsset' INTEGER," + // 18: otherAsset
                "'otherAssetReturn' REAL," + // 19: otherAssetReturn
                "'totalAsset' INTEGER," + // 20: totalAsset
                "'houseFundLoan' INTEGER," + // 21: houseFundLoan
                "'houseBusinessLoan' INTEGER," + // 22: houseBusinessLoan
                "'carLoan' INTEGER," + // 23: carLoan
                "'consumerLoan' INTEGER," + // 24: consumerLoan
                "'renovationLoan' INTEGER," + // 25: renovationLoan
                "'privateLoan' INTEGER," + // 26: privateLoan
                "'unpaidCreditCard' INTEGER," + // 27: unpaidCreditCard
                "'otherLoan' INTEGER," + // 28: otherLoan
                "'totalLoan' INTEGER," + // 29: totalLoan
                "'netAsset' INTEGER);"); // 30: netAsset
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'EASSET_INFO'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, EAssetInfo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long assetInfoId = entity.getAssetInfoId();
        if (assetInfoId != null) {
            stmt.bindLong(2, assetInfoId);
        }
 
        Long customerId = entity.getCustomerId();
        if (customerId != null) {
            stmt.bindLong(3, customerId);
        }
 
        String goal = entity.getGoal();
        if (goal != null) {
            stmt.bindString(4, goal);
        }
 
        Long cash = entity.getCash();
        if (cash != null) {
            stmt.bindLong(5, cash);
        }
 
        Float cashReturn = entity.getCashReturn();
        if (cashReturn != null) {
            stmt.bindDouble(6, cashReturn);
        }
 
        Long demanDeposit = entity.getDemanDeposit();
        if (demanDeposit != null) {
            stmt.bindLong(7, demanDeposit);
        }
 
        Float demanDepositReturn = entity.getDemanDepositReturn();
        if (demanDepositReturn != null) {
            stmt.bindDouble(8, demanDepositReturn);
        }
 
        Long fixDeposit = entity.getFixDeposit();
        if (fixDeposit != null) {
            stmt.bindLong(9, fixDeposit);
        }
 
        Float fixDepositReturn = entity.getFixDepositReturn();
        if (fixDepositReturn != null) {
            stmt.bindDouble(10, fixDepositReturn);
        }
 
        Long stock = entity.getStock();
        if (stock != null) {
            stmt.bindLong(11, stock);
        }
 
        Float stockReturn = entity.getStockReturn();
        if (stockReturn != null) {
            stmt.bindDouble(12, stockReturn);
        }
 
        Long fund = entity.getFund();
        if (fund != null) {
            stmt.bindLong(13, fund);
        }
 
        Float fundReturn = entity.getFundReturn();
        if (fundReturn != null) {
            stmt.bindDouble(14, fundReturn);
        }
 
        Long bond = entity.getBond();
        if (bond != null) {
            stmt.bindLong(15, bond);
        }
 
        Float bondReturn = entity.getBondReturn();
        if (bondReturn != null) {
            stmt.bindDouble(16, bondReturn);
        }
 
        Long estate = entity.getEstate();
        if (estate != null) {
            stmt.bindLong(17, estate);
        }
 
        Float estateReturn = entity.getEstateReturn();
        if (estateReturn != null) {
            stmt.bindDouble(18, estateReturn);
        }
 
        Long otherAsset = entity.getOtherAsset();
        if (otherAsset != null) {
            stmt.bindLong(19, otherAsset);
        }
 
        Float otherAssetReturn = entity.getOtherAssetReturn();
        if (otherAssetReturn != null) {
            stmt.bindDouble(20, otherAssetReturn);
        }
 
        Long totalAsset = entity.getTotalAsset();
        if (totalAsset != null) {
            stmt.bindLong(21, totalAsset);
        }
 
        Long houseFundLoan = entity.getHouseFundLoan();
        if (houseFundLoan != null) {
            stmt.bindLong(22, houseFundLoan);
        }
 
        Long houseBusinessLoan = entity.getHouseBusinessLoan();
        if (houseBusinessLoan != null) {
            stmt.bindLong(23, houseBusinessLoan);
        }
 
        Long carLoan = entity.getCarLoan();
        if (carLoan != null) {
            stmt.bindLong(24, carLoan);
        }
 
        Long consumerLoan = entity.getConsumerLoan();
        if (consumerLoan != null) {
            stmt.bindLong(25, consumerLoan);
        }
 
        Long renovationLoan = entity.getRenovationLoan();
        if (renovationLoan != null) {
            stmt.bindLong(26, renovationLoan);
        }
 
        Long privateLoan = entity.getPrivateLoan();
        if (privateLoan != null) {
            stmt.bindLong(27, privateLoan);
        }
 
        Long unpaidCreditCard = entity.getUnpaidCreditCard();
        if (unpaidCreditCard != null) {
            stmt.bindLong(28, unpaidCreditCard);
        }
 
        Long otherLoan = entity.getOtherLoan();
        if (otherLoan != null) {
            stmt.bindLong(29, otherLoan);
        }
 
        Long totalLoan = entity.getTotalLoan();
        if (totalLoan != null) {
            stmt.bindLong(30, totalLoan);
        }
 
        Long netAsset = entity.getNetAsset();
        if (netAsset != null) {
            stmt.bindLong(31, netAsset);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public EAssetInfo readEntity(Cursor cursor, int offset) {
        EAssetInfo entity = new EAssetInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // assetInfoId
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2), // customerId
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // goal
            cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4), // cash
            cursor.isNull(offset + 5) ? null : cursor.getFloat(offset + 5), // cashReturn
            cursor.isNull(offset + 6) ? null : cursor.getLong(offset + 6), // demanDeposit
            cursor.isNull(offset + 7) ? null : cursor.getFloat(offset + 7), // demanDepositReturn
            cursor.isNull(offset + 8) ? null : cursor.getLong(offset + 8), // fixDeposit
            cursor.isNull(offset + 9) ? null : cursor.getFloat(offset + 9), // fixDepositReturn
            cursor.isNull(offset + 10) ? null : cursor.getLong(offset + 10), // stock
            cursor.isNull(offset + 11) ? null : cursor.getFloat(offset + 11), // stockReturn
            cursor.isNull(offset + 12) ? null : cursor.getLong(offset + 12), // fund
            cursor.isNull(offset + 13) ? null : cursor.getFloat(offset + 13), // fundReturn
            cursor.isNull(offset + 14) ? null : cursor.getLong(offset + 14), // bond
            cursor.isNull(offset + 15) ? null : cursor.getFloat(offset + 15), // bondReturn
            cursor.isNull(offset + 16) ? null : cursor.getLong(offset + 16), // estate
            cursor.isNull(offset + 17) ? null : cursor.getFloat(offset + 17), // estateReturn
            cursor.isNull(offset + 18) ? null : cursor.getLong(offset + 18), // otherAsset
            cursor.isNull(offset + 19) ? null : cursor.getFloat(offset + 19), // otherAssetReturn
            cursor.isNull(offset + 20) ? null : cursor.getLong(offset + 20), // totalAsset
            cursor.isNull(offset + 21) ? null : cursor.getLong(offset + 21), // houseFundLoan
            cursor.isNull(offset + 22) ? null : cursor.getLong(offset + 22), // houseBusinessLoan
            cursor.isNull(offset + 23) ? null : cursor.getLong(offset + 23), // carLoan
            cursor.isNull(offset + 24) ? null : cursor.getLong(offset + 24), // consumerLoan
            cursor.isNull(offset + 25) ? null : cursor.getLong(offset + 25), // renovationLoan
            cursor.isNull(offset + 26) ? null : cursor.getLong(offset + 26), // privateLoan
            cursor.isNull(offset + 27) ? null : cursor.getLong(offset + 27), // unpaidCreditCard
            cursor.isNull(offset + 28) ? null : cursor.getLong(offset + 28), // otherLoan
            cursor.isNull(offset + 29) ? null : cursor.getLong(offset + 29), // totalLoan
            cursor.isNull(offset + 30) ? null : cursor.getLong(offset + 30) // netAsset
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, EAssetInfo entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setAssetInfoId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setCustomerId(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
        entity.setGoal(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setCash(cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4));
        entity.setCashReturn(cursor.isNull(offset + 5) ? null : cursor.getFloat(offset + 5));
        entity.setDemanDeposit(cursor.isNull(offset + 6) ? null : cursor.getLong(offset + 6));
        entity.setDemanDepositReturn(cursor.isNull(offset + 7) ? null : cursor.getFloat(offset + 7));
        entity.setFixDeposit(cursor.isNull(offset + 8) ? null : cursor.getLong(offset + 8));
        entity.setFixDepositReturn(cursor.isNull(offset + 9) ? null : cursor.getFloat(offset + 9));
        entity.setStock(cursor.isNull(offset + 10) ? null : cursor.getLong(offset + 10));
        entity.setStockReturn(cursor.isNull(offset + 11) ? null : cursor.getFloat(offset + 11));
        entity.setFund(cursor.isNull(offset + 12) ? null : cursor.getLong(offset + 12));
        entity.setFundReturn(cursor.isNull(offset + 13) ? null : cursor.getFloat(offset + 13));
        entity.setBond(cursor.isNull(offset + 14) ? null : cursor.getLong(offset + 14));
        entity.setBondReturn(cursor.isNull(offset + 15) ? null : cursor.getFloat(offset + 15));
        entity.setEstate(cursor.isNull(offset + 16) ? null : cursor.getLong(offset + 16));
        entity.setEstateReturn(cursor.isNull(offset + 17) ? null : cursor.getFloat(offset + 17));
        entity.setOtherAsset(cursor.isNull(offset + 18) ? null : cursor.getLong(offset + 18));
        entity.setOtherAssetReturn(cursor.isNull(offset + 19) ? null : cursor.getFloat(offset + 19));
        entity.setTotalAsset(cursor.isNull(offset + 20) ? null : cursor.getLong(offset + 20));
        entity.setHouseFundLoan(cursor.isNull(offset + 21) ? null : cursor.getLong(offset + 21));
        entity.setHouseBusinessLoan(cursor.isNull(offset + 22) ? null : cursor.getLong(offset + 22));
        entity.setCarLoan(cursor.isNull(offset + 23) ? null : cursor.getLong(offset + 23));
        entity.setConsumerLoan(cursor.isNull(offset + 24) ? null : cursor.getLong(offset + 24));
        entity.setRenovationLoan(cursor.isNull(offset + 25) ? null : cursor.getLong(offset + 25));
        entity.setPrivateLoan(cursor.isNull(offset + 26) ? null : cursor.getLong(offset + 26));
        entity.setUnpaidCreditCard(cursor.isNull(offset + 27) ? null : cursor.getLong(offset + 27));
        entity.setOtherLoan(cursor.isNull(offset + 28) ? null : cursor.getLong(offset + 28));
        entity.setTotalLoan(cursor.isNull(offset + 29) ? null : cursor.getLong(offset + 29));
        entity.setNetAsset(cursor.isNull(offset + 30) ? null : cursor.getLong(offset + 30));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(EAssetInfo entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(EAssetInfo entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
