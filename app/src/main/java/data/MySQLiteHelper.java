package data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Rohit on 8/28/2014.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_POSITION = "position";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_PLACE = "place";
    public static final String COLUMN_PINCODE = "pincode";

    private static final String DATABASE_NAME = "location.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_POSITION + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_LATITUDE
            + " TEXT NOT NULL"+COLUMN_LATITUDE+" REAL NOT NULL "+ COLUMN_LONGITUDE+" REAL NOT NULL "+COLUMN_PLACE+ " "+COLUMN_PINCODE+");";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data"
        );
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POSITION);
        onCreate(db);
    }
}
