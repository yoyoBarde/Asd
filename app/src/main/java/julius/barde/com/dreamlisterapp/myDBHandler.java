package julius.barde.com.dreamlisterapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.parseDouble;

/**
 * Created by JBluee on 10/9/2017.
 */

public class myDBHandler extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "dreamlist.db";
    public static final String TABLE_DREAMITEM = "dreamlist";
    // Drealist Table
    public static final String COLUMN_ID ="id";
    public static final String COLUMN_ITEM_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_IMAGE = "image";

    public myDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DREAMLIST_TABLE = "CREATE TABLE " + TABLE_DREAMITEM + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," +COLUMN_ITEM_NAME+ "TEXT," +COLUMN_DESCRIPTION+"TEXT,"
                + COLUMN_PRICE + "DOUBLE" +COLUMN_IMAGE+"BLOB )";
        db.execSQL(CREATE_DREAMLIST_TABLE);
    }
    // Upgrading    database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DREAMITEM);

        // Create tables again
        onCreate(db);
    }

    void addItem(DreamItem myDreamItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, myDreamItem.getID());
        values.put(COLUMN_ITEM_NAME, myDreamItem.getName());
        values.put(COLUMN_DESCRIPTION, myDreamItem.getDescription());
        values.put(COLUMN_PRICE, myDreamItem.getPrice());
        values.put(COLUMN_IMAGE,getBytes(myDreamItem.getImageID()));


        // Inserting Row
        db.insert(TABLE_DREAMITEM, null, values);
        db.close();
    }


    // Deleting single Dream Item
    public void deleteItem(DreamItem dreamItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DREAMITEM, COLUMN_ID + " = ?",
                new String[] { String.valueOf(dreamItem.getID()) });
        db.close();
    }

    // Updating single DreamItem
    public int updateDreamItem(DreamItem DreamItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM_NAME, DreamItem.getName());
        values.put(COLUMN_PRICE, DreamItem.getPrice());
        values.put(COLUMN_DESCRIPTION, DreamItem.getDescription());
        values.put(COLUMN_IMAGE,getBytes(DreamItem.getImageID()));

        // updating row
        return db.update(TABLE_DREAMITEM, values, COLUMN_ID + " = ?",
                new String[] { String.valueOf(DreamItem.getID()) });
    }


    // Getting All DreamItems
    public List<DreamItem> getAllDreamItems() {
        List<DreamItem> DreamItemList = new ArrayList<DreamItem>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_DREAMITEM;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DreamItem DreamItem = new DreamItem();
                DreamItem.setID(Integer.parseInt(cursor.getString(0)));
                DreamItem.setName(cursor.getString(1));
                DreamItem.setDescription(cursor.getString(2));
                DreamItem.setPrice(parseDouble(cursor.getString(3)));
                DreamItem.setImageID(getImage(cursor.getBlob(4)));
                // Adding DreamItem to list
                DreamItemList.add(DreamItem);
            } while (cursor.moveToNext());
        }

        // return DreamItem list
        return DreamItemList;
    }
    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}
