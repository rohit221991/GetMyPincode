package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rohit on 8/28/2014.
 */
public class LocationDataSource {


    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_LATITUDE,MySQLiteHelper.COLUMN_LONGITUDE,
            MySQLiteHelper.COLUMN_LONGITUDE,MySQLiteHelper.COLUMN_PLACE,MySQLiteHelper.COLUMN_PINCODE };

    public LocationDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public location createLocation(String longitude,String latitude, String pincode, String plcae) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_PLACE,plcae );
        values.put(MySQLiteHelper.COLUMN_LONGITUDE,longitude );
        values.put(MySQLiteHelper.COLUMN_LATITUDE,latitude );
        values.put(MySQLiteHelper.COLUMN_PINCODE,pincode );
        long insertId = database.insert(MySQLiteHelper.TABLE_POSITION, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_POSITION,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        location newLocation = cursorToPosition(cursor);
        cursor.close();
        return newLocation;
    }

    public void deleteLocation(location loc) {
        long id = loc.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_POSITION, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<location> getAllLocation() {
        List<location> locations = new ArrayList<location>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_POSITION,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            location loc = cursorToPosition(cursor);
            locations.add(loc);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return locations;
    }

    private location cursorToPosition(Cursor cursor) {
        location loc = new location();
        loc.setId(cursor.getLong(0));
        loc.setPlace(cursor.getString(1));
        loc.setLongitude(cursor.getString(2));
        loc.setLatitude(cursor.getString(3));
        loc.setPincode(cursor.getString(4));

        return loc;
    }
}
