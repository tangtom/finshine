package com.finshine.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.finshine.dao.EProduct;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table EPRODUCT.
*/
public class EProductDao extends AbstractDao<EProduct, Long> {

    public static final String TABLENAME = "EPRODUCT";

    /**
     * Properties of entity EProduct.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property ProductId = new Property(1, Long.class, "productId", false, "productId");
        public final static Property Status = new Property(2, String.class, "status", false, "status");
        public final static Property Created = new Property(3, Long.class, "created", false, "created");
        public final static Property Lastmod = new Property(4, Long.class, "lastmod", false, "lastmod");
        public final static Property Version = new Property(5, Long.class, "version", false, "version");
        public final static Property ProdMyid = new Property(6, String.class, "prodMyid", false, "prodMyid");
        public final static Property ProdFirstType = new Property(7, Long.class, "prodFirstType", false, "prodFirstType");
        public final static Property ProdSecondtype = new Property(8, Long.class, "prodSecondtype", false, "prodSecondtype");
        public final static Property ProdStatus = new Property(9, Long.class, "prodStatus", false, "prodStatus");
        public final static Property ProdElementsSrc = new Property(10, String.class, "prodElementsSrc", false, "prodElementsSrc");
        public final static Property ProdPptSrc = new Property(11, String.class, "prodPptSrc", false, "prodPptSrc");
        public final static Property ProdInstructionsSrc = new Property(12, String.class, "prodInstructionsSrc", false, "prodInstructionsSrc");
        public final static Property ProdContractSrc = new Property(13, String.class, "prodContractSrc", false, "prodContractSrc");
        public final static Property ProdNetreportSrc = new Property(14, String.class, "prodNetreportSrc", false, "prodNetreportSrc");
        public final static Property ProdTrainingSrc = new Property(15, String.class, "prodTrainingSrc", false, "prodTrainingSrc");
        public final static Property ProdName = new Property(16, String.class, "prodName", false, "prodName");
        public final static Property ProdHighLights = new Property(17, String.class, "prodHighLights", false, "prodHighLights");
        public final static Property ProdAdmin = new Property(18, String.class, "prodAdmin", false, "prodAdmin");
        public final static Property ProdPreference = new Property(19, String.class, "prodPreference", false, "prodPreference");
        public final static Property ProdPublisher = new Property(20, String.class, "prodPublisher", false, "prodPublisher");
        public final static Property ProdPubEmail = new Property(21, String.class, "prodPubEmail", false, "prodPubEmail");
        public final static Property ProdOnDateTime = new Property(22, Long.class, "prodOnDateTime", false, "prodOnDateTime");
        public final static Property ProdEnDateTime = new Property(23, Long.class, "prodEnDateTime", false, "prodEnDateTime");
        public final static Property ProdSize = new Property(24, Float.class, "prodSize", false, "prodSize");
        public final static Property ProdTime = new Property(25, Float.class, "prodTime", false, "prodTime");
        public final static Property ProdYieldFloating = new Property(26, Float.class, "prodYieldFloating", false, "prodYieldFloating");
        public final static Property ProdYieldFixed = new Property(27, Float.class, "prodYieldFixed", false, "prodYieldFixed");
        public final static Property ProdPrice = new Property(28, Float.class, "prodPrice", false, "prodPrice");
        public final static Property ProdStart = new Property(29, Float.class, "prodStart", false, "prodStart");
        public final static Property ProdCounterParty = new Property(30, String.class, "prodCounterParty", false, "prodCounterParty");
        public final static Property ProdUse = new Property(31, String.class, "prodUse", false, "prodUse");
        public final static Property ProdRepayment = new Property(32, String.class, "prodRepayment", false, "prodRepayment");
        public final static Property ProdRisk = new Property(33, String.class, "prodRisk", false, "prodRisk");
        public final static Property ProdIncomeType = new Property(34, String.class, "prodIncomeType", false, "prodIncomeType");
        public final static Property ProdReceipts = new Property(35, String.class, "prodReceipts", false, "prodReceipts");
        public final static Property ProdIncomeDateTime = new Property(36, String.class, "prodIncomeDateTime", false, "prodIncomeDateTime");
        public final static Property ProdCstTel = new Property(37, String.class, "prodCstTel", false, "prodCstTel");
        public final static Property ProdEscrowacCount = new Property(38, String.class, "prodEscrowacCount", false, "prodEscrowacCount");
        public final static Property ProdCommissionBase = new Property(39, Float.class, "prodCommissionBase", false, "prodCommissionBase");
        public final static Property ProdComment = new Property(40, String.class, "prodComment", false, "prodComment");
        public final static Property UserId = new Property(41, Long.class, "userId", false, "userId");
        public final static Property ProdCode = new Property(42, String.class, "prodCode", false, "prodCode");
        public final static Property ProdTop = new Property(43, Long.class, "prodTop", false, "prodTop");
        public final static Property ProdOnDateTimeStr = new Property(44, String.class, "prodOnDateTimeStr", false, "prodOnDateTimeStr");
        public final static Property ProdEnDateTimeStr = new Property(45, String.class, "prodEnDateTimeStr", false, "prodEnDateTimeStr");
        public final static Property IsSave = new Property(46, Long.class, "isSave", false, "isSave");
        public final static Property HotPoint = new Property(47, Long.class, "hotPoint", false, "hotPoint");
    };


    public EProductDao(DaoConfig config) {
        super(config);
    }
    
    public EProductDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'EPRODUCT' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'productId' INTEGER," + // 1: productId
                "'status' TEXT," + // 2: status
                "'created' INTEGER," + // 3: created
                "'lastmod' INTEGER," + // 4: lastmod
                "'version' INTEGER," + // 5: version
                "'prodMyid' TEXT," + // 6: prodMyid
                "'prodFirstType' INTEGER," + // 7: prodFirstType
                "'prodSecondtype' INTEGER," + // 8: prodSecondtype
                "'prodStatus' INTEGER," + // 9: prodStatus
                "'prodElementsSrc' TEXT," + // 10: prodElementsSrc
                "'prodPptSrc' TEXT," + // 11: prodPptSrc
                "'prodInstructionsSrc' TEXT," + // 12: prodInstructionsSrc
                "'prodContractSrc' TEXT," + // 13: prodContractSrc
                "'prodNetreportSrc' TEXT," + // 14: prodNetreportSrc
                "'prodTrainingSrc' TEXT," + // 15: prodTrainingSrc
                "'prodName' TEXT," + // 16: prodName
                "'prodHighLights' TEXT," + // 17: prodHighLights
                "'prodAdmin' TEXT," + // 18: prodAdmin
                "'prodPreference' TEXT," + // 19: prodPreference
                "'prodPublisher' TEXT," + // 20: prodPublisher
                "'prodPubEmail' TEXT," + // 21: prodPubEmail
                "'prodOnDateTime' INTEGER," + // 22: prodOnDateTime
                "'prodEnDateTime' INTEGER," + // 23: prodEnDateTime
                "'prodSize' REAL," + // 24: prodSize
                "'prodTime' REAL," + // 25: prodTime
                "'prodYieldFloating' REAL," + // 26: prodYieldFloating
                "'prodYieldFixed' REAL," + // 27: prodYieldFixed
                "'prodPrice' REAL," + // 28: prodPrice
                "'prodStart' REAL," + // 29: prodStart
                "'prodCounterParty' TEXT," + // 30: prodCounterParty
                "'prodUse' TEXT," + // 31: prodUse
                "'prodRepayment' TEXT," + // 32: prodRepayment
                "'prodRisk' TEXT," + // 33: prodRisk
                "'prodIncomeType' TEXT," + // 34: prodIncomeType
                "'prodReceipts' TEXT," + // 35: prodReceipts
                "'prodIncomeDateTime' TEXT," + // 36: prodIncomeDateTime
                "'prodCstTel' TEXT," + // 37: prodCstTel
                "'prodEscrowacCount' TEXT," + // 38: prodEscrowacCount
                "'prodCommissionBase' REAL," + // 39: prodCommissionBase
                "'prodComment' TEXT," + // 40: prodComment
                "'userId' INTEGER," + // 41: userId
                "'prodCode' TEXT," + // 42: prodCode
                "'prodTop' INTEGER," + // 43: prodTop
                "'prodOnDateTimeStr' TEXT," + // 44: prodOnDateTimeStr
                "'prodEnDateTimeStr' TEXT," + // 45: prodEnDateTimeStr
                "'isSave' INTEGER," + // 46: isSave
                "'hotPoint' INTEGER);"); // 47: hotPoint
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'EPRODUCT'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, EProduct entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long productId = entity.getProductId();
        if (productId != null) {
            stmt.bindLong(2, productId);
        }
 
        String status = entity.getStatus();
        if (status != null) {
            stmt.bindString(3, status);
        }
 
        Long created = entity.getCreated();
        if (created != null) {
            stmt.bindLong(4, created);
        }
 
        Long lastmod = entity.getLastmod();
        if (lastmod != null) {
            stmt.bindLong(5, lastmod);
        }
 
        Long version = entity.getVersion();
        if (version != null) {
            stmt.bindLong(6, version);
        }
 
        String prodMyid = entity.getProdMyid();
        if (prodMyid != null) {
            stmt.bindString(7, prodMyid);
        }
 
        Long prodFirstType = entity.getProdFirstType();
        if (prodFirstType != null) {
            stmt.bindLong(8, prodFirstType);
        }
 
        Long prodSecondtype = entity.getProdSecondtype();
        if (prodSecondtype != null) {
            stmt.bindLong(9, prodSecondtype);
        }
 
        Long prodStatus = entity.getProdStatus();
        if (prodStatus != null) {
            stmt.bindLong(10, prodStatus);
        }
 
        String prodElementsSrc = entity.getProdElementsSrc();
        if (prodElementsSrc != null) {
            stmt.bindString(11, prodElementsSrc);
        }
 
        String prodPptSrc = entity.getProdPptSrc();
        if (prodPptSrc != null) {
            stmt.bindString(12, prodPptSrc);
        }
 
        String prodInstructionsSrc = entity.getProdInstructionsSrc();
        if (prodInstructionsSrc != null) {
            stmt.bindString(13, prodInstructionsSrc);
        }
 
        String prodContractSrc = entity.getProdContractSrc();
        if (prodContractSrc != null) {
            stmt.bindString(14, prodContractSrc);
        }
 
        String prodNetreportSrc = entity.getProdNetreportSrc();
        if (prodNetreportSrc != null) {
            stmt.bindString(15, prodNetreportSrc);
        }
 
        String prodTrainingSrc = entity.getProdTrainingSrc();
        if (prodTrainingSrc != null) {
            stmt.bindString(16, prodTrainingSrc);
        }
 
        String prodName = entity.getProdName();
        if (prodName != null) {
            stmt.bindString(17, prodName);
        }
 
        String prodHighLights = entity.getProdHighLights();
        if (prodHighLights != null) {
            stmt.bindString(18, prodHighLights);
        }
 
        String prodAdmin = entity.getProdAdmin();
        if (prodAdmin != null) {
            stmt.bindString(19, prodAdmin);
        }
 
        String prodPreference = entity.getProdPreference();
        if (prodPreference != null) {
            stmt.bindString(20, prodPreference);
        }
 
        String prodPublisher = entity.getProdPublisher();
        if (prodPublisher != null) {
            stmt.bindString(21, prodPublisher);
        }
 
        String prodPubEmail = entity.getProdPubEmail();
        if (prodPubEmail != null) {
            stmt.bindString(22, prodPubEmail);
        }
 
        Long prodOnDateTime = entity.getProdOnDateTime();
        if (prodOnDateTime != null) {
            stmt.bindLong(23, prodOnDateTime);
        }
 
        Long prodEnDateTime = entity.getProdEnDateTime();
        if (prodEnDateTime != null) {
            stmt.bindLong(24, prodEnDateTime);
        }
 
        Float prodSize = entity.getProdSize();
        if (prodSize != null) {
            stmt.bindDouble(25, prodSize);
        }
 
        Float prodTime = entity.getProdTime();
        if (prodTime != null) {
            stmt.bindDouble(26, prodTime);
        }
 
        Float prodYieldFloating = entity.getProdYieldFloating();
        if (prodYieldFloating != null) {
            stmt.bindDouble(27, prodYieldFloating);
        }
 
        Float prodYieldFixed = entity.getProdYieldFixed();
        if (prodYieldFixed != null) {
            stmt.bindDouble(28, prodYieldFixed);
        }
 
        Float prodPrice = entity.getProdPrice();
        if (prodPrice != null) {
            stmt.bindDouble(29, prodPrice);
        }
 
        Float prodStart = entity.getProdStart();
        if (prodStart != null) {
            stmt.bindDouble(30, prodStart);
        }
 
        String prodCounterParty = entity.getProdCounterParty();
        if (prodCounterParty != null) {
            stmt.bindString(31, prodCounterParty);
        }
 
        String prodUse = entity.getProdUse();
        if (prodUse != null) {
            stmt.bindString(32, prodUse);
        }
 
        String prodRepayment = entity.getProdRepayment();
        if (prodRepayment != null) {
            stmt.bindString(33, prodRepayment);
        }
 
        String prodRisk = entity.getProdRisk();
        if (prodRisk != null) {
            stmt.bindString(34, prodRisk);
        }
 
        String prodIncomeType = entity.getProdIncomeType();
        if (prodIncomeType != null) {
            stmt.bindString(35, prodIncomeType);
        }
 
        String prodReceipts = entity.getProdReceipts();
        if (prodReceipts != null) {
            stmt.bindString(36, prodReceipts);
        }
 
        String prodIncomeDateTime = entity.getProdIncomeDateTime();
        if (prodIncomeDateTime != null) {
            stmt.bindString(37, prodIncomeDateTime);
        }
 
        String prodCstTel = entity.getProdCstTel();
        if (prodCstTel != null) {
            stmt.bindString(38, prodCstTel);
        }
 
        String prodEscrowacCount = entity.getProdEscrowacCount();
        if (prodEscrowacCount != null) {
            stmt.bindString(39, prodEscrowacCount);
        }
 
        Float prodCommissionBase = entity.getProdCommissionBase();
        if (prodCommissionBase != null) {
            stmt.bindDouble(40, prodCommissionBase);
        }
 
        String prodComment = entity.getProdComment();
        if (prodComment != null) {
            stmt.bindString(41, prodComment);
        }
 
        Long userId = entity.getUserId();
        if (userId != null) {
            stmt.bindLong(42, userId);
        }
 
        String prodCode = entity.getProdCode();
        if (prodCode != null) {
            stmt.bindString(43, prodCode);
        }
 
        Long prodTop = entity.getProdTop();
        if (prodTop != null) {
            stmt.bindLong(44, prodTop);
        }
 
        String prodOnDateTimeStr = entity.getProdOnDateTimeStr();
        if (prodOnDateTimeStr != null) {
            stmt.bindString(45, prodOnDateTimeStr);
        }
 
        String prodEnDateTimeStr = entity.getProdEnDateTimeStr();
        if (prodEnDateTimeStr != null) {
            stmt.bindString(46, prodEnDateTimeStr);
        }
 
        Long isSave = entity.getIsSave();
        if (isSave != null) {
            stmt.bindLong(47, isSave);
        }
 
        Long hotPoint = entity.getHotPoint();
        if (hotPoint != null) {
            stmt.bindLong(48, hotPoint);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public EProduct readEntity(Cursor cursor, int offset) {
        EProduct entity = new EProduct( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // productId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // status
            cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3), // created
            cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4), // lastmod
            cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5), // version
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // prodMyid
            cursor.isNull(offset + 7) ? null : cursor.getLong(offset + 7), // prodFirstType
            cursor.isNull(offset + 8) ? null : cursor.getLong(offset + 8), // prodSecondtype
            cursor.isNull(offset + 9) ? null : cursor.getLong(offset + 9), // prodStatus
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // prodElementsSrc
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // prodPptSrc
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // prodInstructionsSrc
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // prodContractSrc
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // prodNetreportSrc
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // prodTrainingSrc
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // prodName
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // prodHighLights
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // prodAdmin
            cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19), // prodPreference
            cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20), // prodPublisher
            cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21), // prodPubEmail
            cursor.isNull(offset + 22) ? null : cursor.getLong(offset + 22), // prodOnDateTime
            cursor.isNull(offset + 23) ? null : cursor.getLong(offset + 23), // prodEnDateTime
            cursor.isNull(offset + 24) ? null : cursor.getFloat(offset + 24), // prodSize
            cursor.isNull(offset + 25) ? null : cursor.getFloat(offset + 25), // prodTime
            cursor.isNull(offset + 26) ? null : cursor.getFloat(offset + 26), // prodYieldFloating
            cursor.isNull(offset + 27) ? null : cursor.getFloat(offset + 27), // prodYieldFixed
            cursor.isNull(offset + 28) ? null : cursor.getFloat(offset + 28), // prodPrice
            cursor.isNull(offset + 29) ? null : cursor.getFloat(offset + 29), // prodStart
            cursor.isNull(offset + 30) ? null : cursor.getString(offset + 30), // prodCounterParty
            cursor.isNull(offset + 31) ? null : cursor.getString(offset + 31), // prodUse
            cursor.isNull(offset + 32) ? null : cursor.getString(offset + 32), // prodRepayment
            cursor.isNull(offset + 33) ? null : cursor.getString(offset + 33), // prodRisk
            cursor.isNull(offset + 34) ? null : cursor.getString(offset + 34), // prodIncomeType
            cursor.isNull(offset + 35) ? null : cursor.getString(offset + 35), // prodReceipts
            cursor.isNull(offset + 36) ? null : cursor.getString(offset + 36), // prodIncomeDateTime
            cursor.isNull(offset + 37) ? null : cursor.getString(offset + 37), // prodCstTel
            cursor.isNull(offset + 38) ? null : cursor.getString(offset + 38), // prodEscrowacCount
            cursor.isNull(offset + 39) ? null : cursor.getFloat(offset + 39), // prodCommissionBase
            cursor.isNull(offset + 40) ? null : cursor.getString(offset + 40), // prodComment
            cursor.isNull(offset + 41) ? null : cursor.getLong(offset + 41), // userId
            cursor.isNull(offset + 42) ? null : cursor.getString(offset + 42), // prodCode
            cursor.isNull(offset + 43) ? null : cursor.getLong(offset + 43), // prodTop
            cursor.isNull(offset + 44) ? null : cursor.getString(offset + 44), // prodOnDateTimeStr
            cursor.isNull(offset + 45) ? null : cursor.getString(offset + 45), // prodEnDateTimeStr
            cursor.isNull(offset + 46) ? null : cursor.getLong(offset + 46), // isSave
            cursor.isNull(offset + 47) ? null : cursor.getLong(offset + 47) // hotPoint
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, EProduct entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setProductId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setStatus(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setCreated(cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3));
        entity.setLastmod(cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4));
        entity.setVersion(cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5));
        entity.setProdMyid(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setProdFirstType(cursor.isNull(offset + 7) ? null : cursor.getLong(offset + 7));
        entity.setProdSecondtype(cursor.isNull(offset + 8) ? null : cursor.getLong(offset + 8));
        entity.setProdStatus(cursor.isNull(offset + 9) ? null : cursor.getLong(offset + 9));
        entity.setProdElementsSrc(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setProdPptSrc(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setProdInstructionsSrc(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setProdContractSrc(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setProdNetreportSrc(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setProdTrainingSrc(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setProdName(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setProdHighLights(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setProdAdmin(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setProdPreference(cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19));
        entity.setProdPublisher(cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20));
        entity.setProdPubEmail(cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21));
        entity.setProdOnDateTime(cursor.isNull(offset + 22) ? null : cursor.getLong(offset + 22));
        entity.setProdEnDateTime(cursor.isNull(offset + 23) ? null : cursor.getLong(offset + 23));
        entity.setProdSize(cursor.isNull(offset + 24) ? null : cursor.getFloat(offset + 24));
        entity.setProdTime(cursor.isNull(offset + 25) ? null : cursor.getFloat(offset + 25));
        entity.setProdYieldFloating(cursor.isNull(offset + 26) ? null : cursor.getFloat(offset + 26));
        entity.setProdYieldFixed(cursor.isNull(offset + 27) ? null : cursor.getFloat(offset + 27));
        entity.setProdPrice(cursor.isNull(offset + 28) ? null : cursor.getFloat(offset + 28));
        entity.setProdStart(cursor.isNull(offset + 29) ? null : cursor.getFloat(offset + 29));
        entity.setProdCounterParty(cursor.isNull(offset + 30) ? null : cursor.getString(offset + 30));
        entity.setProdUse(cursor.isNull(offset + 31) ? null : cursor.getString(offset + 31));
        entity.setProdRepayment(cursor.isNull(offset + 32) ? null : cursor.getString(offset + 32));
        entity.setProdRisk(cursor.isNull(offset + 33) ? null : cursor.getString(offset + 33));
        entity.setProdIncomeType(cursor.isNull(offset + 34) ? null : cursor.getString(offset + 34));
        entity.setProdReceipts(cursor.isNull(offset + 35) ? null : cursor.getString(offset + 35));
        entity.setProdIncomeDateTime(cursor.isNull(offset + 36) ? null : cursor.getString(offset + 36));
        entity.setProdCstTel(cursor.isNull(offset + 37) ? null : cursor.getString(offset + 37));
        entity.setProdEscrowacCount(cursor.isNull(offset + 38) ? null : cursor.getString(offset + 38));
        entity.setProdCommissionBase(cursor.isNull(offset + 39) ? null : cursor.getFloat(offset + 39));
        entity.setProdComment(cursor.isNull(offset + 40) ? null : cursor.getString(offset + 40));
        entity.setUserId(cursor.isNull(offset + 41) ? null : cursor.getLong(offset + 41));
        entity.setProdCode(cursor.isNull(offset + 42) ? null : cursor.getString(offset + 42));
        entity.setProdTop(cursor.isNull(offset + 43) ? null : cursor.getLong(offset + 43));
        entity.setProdOnDateTimeStr(cursor.isNull(offset + 44) ? null : cursor.getString(offset + 44));
        entity.setProdEnDateTimeStr(cursor.isNull(offset + 45) ? null : cursor.getString(offset + 45));
        entity.setIsSave(cursor.isNull(offset + 46) ? null : cursor.getLong(offset + 46));
        entity.setHotPoint(cursor.isNull(offset + 47) ? null : cursor.getLong(offset + 47));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(EProduct entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(EProduct entity) {
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
