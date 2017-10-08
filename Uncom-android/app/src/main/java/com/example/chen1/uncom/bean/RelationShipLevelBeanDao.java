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
 * DAO for table "RELATION_SHIP_LEVEL_BEAN".
*/
public class RelationShipLevelBeanDao extends AbstractDao<RelationShipLevelBean, String> {

    public static final String TABLENAME = "RELATION_SHIP_LEVEL_BEAN";

    /**
     * Properties of entity RelationShipLevelBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, String.class, "id", true, "ID");
        public final static Property Level = new Property(1, Integer.class, "level", false, "LEVEL");
        public final static Property Minor_user = new Property(2, String.class, "minor_user", false, "MINOR_USER");
        public final static Property Header_pic = new Property(3, String.class, "header_pic", false, "HEADER_PIC");
        public final static Property Username = new Property(4, String.class, "username", false, "USERNAME");
        public final static Property Sex = new Property(5, Integer.class, "sex", false, "SEX");
        public final static Property Email = new Property(6, String.class, "email", false, "EMAIL");
        public final static Property Self_abstract = new Property(7, String.class, "self_abstract", false, "SELF_ABSTRACT");
        public final static Property Sprovince = new Property(8, String.class, "sprovince", false, "SPROVINCE");
        public final static Property Sarea = new Property(9, String.class, "sarea", false, "SAREA");
        public final static Property Stown = new Property(10, String.class, "stown", false, "STOWN");
        public final static Property Phone = new Property(11, String.class, "phone", false, "PHONE");
        public final static Property Age = new Property(12, Integer.class, "age", false, "AGE");
        public final static Property Last_message = new Property(13, String.class, "last_message", false, "LAST_MESSAGE");
        public final static Property Last_active_time = new Property(14, java.util.Date.class, "last_active_time", false, "LAST_ACTIVE_TIME");
        public final static Property Connect_time = new Property(15, java.util.Date.class, "connect_time", false, "CONNECT_TIME");
        public final static Property Active = new Property(16, boolean.class, "active", false, "ACTIVE");
    }


    public RelationShipLevelBeanDao(DaoConfig config) {
        super(config);
    }
    
    public RelationShipLevelBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"RELATION_SHIP_LEVEL_BEAN\" (" + //
                "\"ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: id
                "\"LEVEL\" INTEGER," + // 1: level
                "\"MINOR_USER\" TEXT," + // 2: minor_user
                "\"HEADER_PIC\" TEXT," + // 3: header_pic
                "\"USERNAME\" TEXT," + // 4: username
                "\"SEX\" INTEGER," + // 5: sex
                "\"EMAIL\" TEXT," + // 6: email
                "\"SELF_ABSTRACT\" TEXT," + // 7: self_abstract
                "\"SPROVINCE\" TEXT," + // 8: sprovince
                "\"SAREA\" TEXT," + // 9: sarea
                "\"STOWN\" TEXT," + // 10: stown
                "\"PHONE\" TEXT," + // 11: phone
                "\"AGE\" INTEGER," + // 12: age
                "\"LAST_MESSAGE\" TEXT," + // 13: last_message
                "\"LAST_ACTIVE_TIME\" INTEGER," + // 14: last_active_time
                "\"CONNECT_TIME\" INTEGER," + // 15: connect_time
                "\"ACTIVE\" INTEGER NOT NULL );"); // 16: active
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"RELATION_SHIP_LEVEL_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, RelationShipLevelBean entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        Integer level = entity.getLevel();
        if (level != null) {
            stmt.bindLong(2, level);
        }
 
        String minor_user = entity.getMinor_user();
        if (minor_user != null) {
            stmt.bindString(3, minor_user);
        }
 
        String header_pic = entity.getHeader_pic();
        if (header_pic != null) {
            stmt.bindString(4, header_pic);
        }
 
        String username = entity.getUsername();
        if (username != null) {
            stmt.bindString(5, username);
        }
 
        Integer sex = entity.getSex();
        if (sex != null) {
            stmt.bindLong(6, sex);
        }
 
        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(7, email);
        }
 
        String self_abstract = entity.getSelf_abstract();
        if (self_abstract != null) {
            stmt.bindString(8, self_abstract);
        }
 
        String sprovince = entity.getSprovince();
        if (sprovince != null) {
            stmt.bindString(9, sprovince);
        }
 
        String sarea = entity.getSarea();
        if (sarea != null) {
            stmt.bindString(10, sarea);
        }
 
        String stown = entity.getStown();
        if (stown != null) {
            stmt.bindString(11, stown);
        }
 
        String phone = entity.getPhone();
        if (phone != null) {
            stmt.bindString(12, phone);
        }
 
        Integer age = entity.getAge();
        if (age != null) {
            stmt.bindLong(13, age);
        }
 
        String last_message = entity.getLast_message();
        if (last_message != null) {
            stmt.bindString(14, last_message);
        }
 
        java.util.Date last_active_time = entity.getLast_active_time();
        if (last_active_time != null) {
            stmt.bindLong(15, last_active_time.getTime());
        }
 
        java.util.Date connect_time = entity.getConnect_time();
        if (connect_time != null) {
            stmt.bindLong(16, connect_time.getTime());
        }
        stmt.bindLong(17, entity.getActive() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, RelationShipLevelBean entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        Integer level = entity.getLevel();
        if (level != null) {
            stmt.bindLong(2, level);
        }
 
        String minor_user = entity.getMinor_user();
        if (minor_user != null) {
            stmt.bindString(3, minor_user);
        }
 
        String header_pic = entity.getHeader_pic();
        if (header_pic != null) {
            stmt.bindString(4, header_pic);
        }
 
        String username = entity.getUsername();
        if (username != null) {
            stmt.bindString(5, username);
        }
 
        Integer sex = entity.getSex();
        if (sex != null) {
            stmt.bindLong(6, sex);
        }
 
        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(7, email);
        }
 
        String self_abstract = entity.getSelf_abstract();
        if (self_abstract != null) {
            stmt.bindString(8, self_abstract);
        }
 
        String sprovince = entity.getSprovince();
        if (sprovince != null) {
            stmt.bindString(9, sprovince);
        }
 
        String sarea = entity.getSarea();
        if (sarea != null) {
            stmt.bindString(10, sarea);
        }
 
        String stown = entity.getStown();
        if (stown != null) {
            stmt.bindString(11, stown);
        }
 
        String phone = entity.getPhone();
        if (phone != null) {
            stmt.bindString(12, phone);
        }
 
        Integer age = entity.getAge();
        if (age != null) {
            stmt.bindLong(13, age);
        }
 
        String last_message = entity.getLast_message();
        if (last_message != null) {
            stmt.bindString(14, last_message);
        }
 
        java.util.Date last_active_time = entity.getLast_active_time();
        if (last_active_time != null) {
            stmt.bindLong(15, last_active_time.getTime());
        }
 
        java.util.Date connect_time = entity.getConnect_time();
        if (connect_time != null) {
            stmt.bindLong(16, connect_time.getTime());
        }
        stmt.bindLong(17, entity.getActive() ? 1L: 0L);
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public RelationShipLevelBean readEntity(Cursor cursor, int offset) {
        RelationShipLevelBean entity = new RelationShipLevelBean( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1), // level
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // minor_user
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // header_pic
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // username
            cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5), // sex
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // email
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // self_abstract
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // sprovince
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // sarea
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // stown
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // phone
            cursor.isNull(offset + 12) ? null : cursor.getInt(offset + 12), // age
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // last_message
            cursor.isNull(offset + 14) ? null : new java.util.Date(cursor.getLong(offset + 14)), // last_active_time
            cursor.isNull(offset + 15) ? null : new java.util.Date(cursor.getLong(offset + 15)), // connect_time
            cursor.getShort(offset + 16) != 0 // active
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, RelationShipLevelBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setLevel(cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1));
        entity.setMinor_user(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setHeader_pic(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setUsername(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setSex(cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5));
        entity.setEmail(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setSelf_abstract(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setSprovince(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setSarea(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setStown(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setPhone(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setAge(cursor.isNull(offset + 12) ? null : cursor.getInt(offset + 12));
        entity.setLast_message(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setLast_active_time(cursor.isNull(offset + 14) ? null : new java.util.Date(cursor.getLong(offset + 14)));
        entity.setConnect_time(cursor.isNull(offset + 15) ? null : new java.util.Date(cursor.getLong(offset + 15)));
        entity.setActive(cursor.getShort(offset + 16) != 0);
     }
    
    @Override
    protected final String updateKeyAfterInsert(RelationShipLevelBean entity, long rowId) {
        return entity.getId();
    }
    
    @Override
    public String getKey(RelationShipLevelBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(RelationShipLevelBean entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
