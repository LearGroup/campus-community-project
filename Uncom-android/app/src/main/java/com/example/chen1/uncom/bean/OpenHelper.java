package com.example.chen1.uncom.bean;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by chen1 on 2017/12/25.
 */

public class OpenHelper extends  DaoMaster.OpenHelper {
    public OpenHelper(Context context, String name) {
        super(context, name);
    }

    public OpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }
}
