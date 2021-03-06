package com.example.chen1.uncom.bean;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ROUTINE_BEAN".
*/
public class RoutineBeanDao extends AbstractDao<RoutineBean, Long> {

    public static final String TABLENAME = "ROUTINE_BEAN";

    /**
     * Properties of entity RoutineBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property ActiveId = new Property(1, String.class, "activeId", false, "ACTIVE_ID");
        public final static Property Count = new Property(2, Integer.class, "count", false, "COUNT");
        public final static Property NowCount = new Property(3, Integer.class, "nowCount", false, "NOW_COUNT");
        public final static Property Name = new Property(4, String.class, "name", false, "NAME");
        public final static Property NameBackColor = new Property(5, String.class, "nameBackColor", false, "NAME_BACK_COLOR");
        public final static Property AuthorId = new Property(6, String.class, "authorId", false, "AUTHOR_ID");
        public final static Property AuthorName = new Property(7, String.class, "authorName", false, "AUTHOR_NAME");
        public final static Property AuthorHeadImg = new Property(8, String.class, "authorHeadImg", false, "AUTHOR_HEAD_IMG");
        public final static Property ParticipantId = new Property(9, String.class, "participantId", false, "PARTICIPANT_ID");
        public final static Property ParticipantHeadImg = new Property(10, String.class, "participantHeadImg", false, "PARTICIPANT_HEAD_IMG");
        public final static Property CreateTime = new Property(11, java.util.Date.class, "createTime", false, "CREATE_TIME");
        public final static Property ShortReadme = new Property(12, String.class, "shortReadme", false, "SHORT_README");
        public final static Property Online = new Property(13, boolean.class, "online", false, "ONLINE");
        public final static Property Ofline = new Property(14, boolean.class, "ofline", false, "OFLINE");
        public final static Property AbstractReadme = new Property(15, String.class, "abstractReadme", false, "ABSTRACT_README");
        public final static Property Tag = new Property(16, String.class, "tag", false, "TAG");
        public final static Property TagId = new Property(17, String.class, "tagId", false, "TAG_ID");
        public final static Property TagColor = new Property(18, String.class, "tagColor", false, "TAG_COLOR");
        public final static Property TimeType = new Property(19, Integer.class, "timeType", false, "TIME_TYPE");
        public final static Property ActiveTime = new Property(20, String.class, "activeTime", false, "ACTIVE_TIME");
        public final static Property ActivePercent = new Property(21, float.class, "activePercent", false, "ACTIVE_PERCENT");
        public final static Property Quality = new Property(22, Integer.class, "quality", false, "QUALITY");
        public final static Property Address = new Property(23, String.class, "address", false, "ADDRESS");
    }


    public RoutineBeanDao(DaoConfig config) {
        super(config);
    }
    
    public RoutineBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ROUTINE_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"ACTIVE_ID\" TEXT," + // 1: activeId
                "\"COUNT\" INTEGER," + // 2: count
                "\"NOW_COUNT\" INTEGER," + // 3: nowCount
                "\"NAME\" TEXT," + // 4: name
                "\"NAME_BACK_COLOR\" TEXT," + // 5: nameBackColor
                "\"AUTHOR_ID\" TEXT," + // 6: authorId
                "\"AUTHOR_NAME\" TEXT," + // 7: authorName
                "\"AUTHOR_HEAD_IMG\" TEXT," + // 8: authorHeadImg
                "\"PARTICIPANT_ID\" TEXT," + // 9: participantId
                "\"PARTICIPANT_HEAD_IMG\" TEXT," + // 10: participantHeadImg
                "\"CREATE_TIME\" INTEGER," + // 11: createTime
                "\"SHORT_README\" TEXT," + // 12: shortReadme
                "\"ONLINE\" INTEGER NOT NULL ," + // 13: online
                "\"OFLINE\" INTEGER NOT NULL ," + // 14: ofline
                "\"ABSTRACT_README\" TEXT," + // 15: abstractReadme
                "\"TAG\" TEXT," + // 16: tag
                "\"TAG_ID\" TEXT," + // 17: tagId
                "\"TAG_COLOR\" TEXT," + // 18: tagColor
                "\"TIME_TYPE\" INTEGER," + // 19: timeType
                "\"ACTIVE_TIME\" TEXT," + // 20: activeTime
                "\"ACTIVE_PERCENT\" REAL NOT NULL ," + // 21: activePercent
                "\"QUALITY\" INTEGER," + // 22: quality
                "\"ADDRESS\" TEXT);"); // 23: address
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ROUTINE_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, RoutineBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String activeId = entity.getActiveId();
        if (activeId != null) {
            stmt.bindString(2, activeId);
        }
 
        Integer count = entity.getCount();
        if (count != null) {
            stmt.bindLong(3, count);
        }
 
        Integer nowCount = entity.getNowCount();
        if (nowCount != null) {
            stmt.bindLong(4, nowCount);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(5, name);
        }
 
        String nameBackColor = entity.getNameBackColor();
        if (nameBackColor != null) {
            stmt.bindString(6, nameBackColor);
        }
 
        String authorId = entity.getAuthorId();
        if (authorId != null) {
            stmt.bindString(7, authorId);
        }
 
        String authorName = entity.getAuthorName();
        if (authorName != null) {
            stmt.bindString(8, authorName);
        }
 
        String authorHeadImg = entity.getAuthorHeadImg();
        if (authorHeadImg != null) {
            stmt.bindString(9, authorHeadImg);
        }
 
        String participantId = entity.getParticipantId();
        if (participantId != null) {
            stmt.bindString(10, participantId);
        }
 
        String participantHeadImg = entity.getParticipantHeadImg();
        if (participantHeadImg != null) {
            stmt.bindString(11, participantHeadImg);
        }
 
        java.util.Date createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindLong(12, createTime.getTime());
        }
 
        String shortReadme = entity.getShortReadme();
        if (shortReadme != null) {
            stmt.bindString(13, shortReadme);
        }
        stmt.bindLong(14, entity.getOnline() ? 1L: 0L);
        stmt.bindLong(15, entity.getOfline() ? 1L: 0L);
 
        String abstractReadme = entity.getAbstractReadme();
        if (abstractReadme != null) {
            stmt.bindString(16, abstractReadme);
        }
 
        String tag = entity.getTag();
        if (tag != null) {
            stmt.bindString(17, tag);
        }
 
        String tagId = entity.getTagId();
        if (tagId != null) {
            stmt.bindString(18, tagId);
        }
 
        String tagColor = entity.getTagColor();
        if (tagColor != null) {
            stmt.bindString(19, tagColor);
        }
 
        Integer timeType = entity.getTimeType();
        if (timeType != null) {
            stmt.bindLong(20, timeType);
        }
 
        String activeTime = entity.getActiveTime();
        if (activeTime != null) {
            stmt.bindString(21, activeTime);
        }
        stmt.bindDouble(22, entity.getActivePercent());
 
        Integer quality = entity.getQuality();
        if (quality != null) {
            stmt.bindLong(23, quality);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(24, address);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, RoutineBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String activeId = entity.getActiveId();
        if (activeId != null) {
            stmt.bindString(2, activeId);
        }
 
        Integer count = entity.getCount();
        if (count != null) {
            stmt.bindLong(3, count);
        }
 
        Integer nowCount = entity.getNowCount();
        if (nowCount != null) {
            stmt.bindLong(4, nowCount);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(5, name);
        }
 
        String nameBackColor = entity.getNameBackColor();
        if (nameBackColor != null) {
            stmt.bindString(6, nameBackColor);
        }
 
        String authorId = entity.getAuthorId();
        if (authorId != null) {
            stmt.bindString(7, authorId);
        }
 
        String authorName = entity.getAuthorName();
        if (authorName != null) {
            stmt.bindString(8, authorName);
        }
 
        String authorHeadImg = entity.getAuthorHeadImg();
        if (authorHeadImg != null) {
            stmt.bindString(9, authorHeadImg);
        }
 
        String participantId = entity.getParticipantId();
        if (participantId != null) {
            stmt.bindString(10, participantId);
        }
 
        String participantHeadImg = entity.getParticipantHeadImg();
        if (participantHeadImg != null) {
            stmt.bindString(11, participantHeadImg);
        }
 
        java.util.Date createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindLong(12, createTime.getTime());
        }
 
        String shortReadme = entity.getShortReadme();
        if (shortReadme != null) {
            stmt.bindString(13, shortReadme);
        }
        stmt.bindLong(14, entity.getOnline() ? 1L: 0L);
        stmt.bindLong(15, entity.getOfline() ? 1L: 0L);
 
        String abstractReadme = entity.getAbstractReadme();
        if (abstractReadme != null) {
            stmt.bindString(16, abstractReadme);
        }
 
        String tag = entity.getTag();
        if (tag != null) {
            stmt.bindString(17, tag);
        }
 
        String tagId = entity.getTagId();
        if (tagId != null) {
            stmt.bindString(18, tagId);
        }
 
        String tagColor = entity.getTagColor();
        if (tagColor != null) {
            stmt.bindString(19, tagColor);
        }
 
        Integer timeType = entity.getTimeType();
        if (timeType != null) {
            stmt.bindLong(20, timeType);
        }
 
        String activeTime = entity.getActiveTime();
        if (activeTime != null) {
            stmt.bindString(21, activeTime);
        }
        stmt.bindDouble(22, entity.getActivePercent());
 
        Integer quality = entity.getQuality();
        if (quality != null) {
            stmt.bindLong(23, quality);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(24, address);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public RoutineBean readEntity(Cursor cursor, int offset) {
        RoutineBean entity = new RoutineBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // activeId
            cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2), // count
            cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3), // nowCount
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // name
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // nameBackColor
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // authorId
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // authorName
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // authorHeadImg
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // participantId
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // participantHeadImg
            cursor.isNull(offset + 11) ? null : new java.util.Date(cursor.getLong(offset + 11)), // createTime
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // shortReadme
            cursor.getShort(offset + 13) != 0, // online
            cursor.getShort(offset + 14) != 0, // ofline
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // abstractReadme
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // tag
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // tagId
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // tagColor
            cursor.isNull(offset + 19) ? null : cursor.getInt(offset + 19), // timeType
            cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20), // activeTime
            cursor.getFloat(offset + 21), // activePercent
            cursor.isNull(offset + 22) ? null : cursor.getInt(offset + 22), // quality
            cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23) // address
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, RoutineBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setActiveId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCount(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
        entity.setNowCount(cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3));
        entity.setName(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setNameBackColor(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setAuthorId(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setAuthorName(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setAuthorHeadImg(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setParticipantId(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setParticipantHeadImg(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setCreateTime(cursor.isNull(offset + 11) ? null : new java.util.Date(cursor.getLong(offset + 11)));
        entity.setShortReadme(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setOnline(cursor.getShort(offset + 13) != 0);
        entity.setOfline(cursor.getShort(offset + 14) != 0);
        entity.setAbstractReadme(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setTag(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setTagId(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setTagColor(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setTimeType(cursor.isNull(offset + 19) ? null : cursor.getInt(offset + 19));
        entity.setActiveTime(cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20));
        entity.setActivePercent(cursor.getFloat(offset + 21));
        entity.setQuality(cursor.isNull(offset + 22) ? null : cursor.getInt(offset + 22));
        entity.setAddress(cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(RoutineBean entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(RoutineBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(RoutineBean entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
