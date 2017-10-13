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

public class myDBHandler extends SQLiteOpenHelper {
    private static final String TAG ="DatabaseHelper";
    private static final String TABLE_NAME ="DREAMLIST" ;
    private static final String COL1 = "ID";
    private  static final String COL2 = "NAME";
    private  static final String COL3 = "DESCRIPTION";
    private  static final String COL4 = "PRICE";
    private  static final String COL5 = "IMAGE";
    public myDBHandler (Context context){
        super(context, TABLE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,"+ COL2 +" TEXT, " + COL3 +
                " TEXT, "+ COL4+ " NUMBER, " + COL5 + " TEXT " + ")" ;
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP IF TABLE EXISTS "+ TABLE_NAME);
        onCreate(db);
    }
    public void deleteWish(Integer id, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL1 + " = '" + id + "'" +
                " AND " + COL2 + " = '" + name + "'";
        db.execSQL(query);
    }

    public boolean addItem(String name, String desc, String price, Bitmap Image){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2,name);
        contentValues.put(COL3,desc);
        contentValues.put(COL4,price);
        contentValues.put(COL5,getBytes(Image));

        long result = db.insert(TABLE_NAME,null,contentValues);

        if(result == -1){
            return  false;
        }
        else {
            return true;
        }
    }
    public Cursor getData(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return  data;
    }
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public List<DreamItem> getAllDreamItems() {
        List<DreamItem> DreamItemList = new ArrayList<DreamItem>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DreamItem DreamItem = new DreamItem();
                DreamItem.setName(cursor.getString(1));
                DreamItem.setDescription(cursor.getString(2));
                DreamItem.setPrice((cursor.getString(3)));
                DreamItem.setImageID(getImage(cursor.getBlob(4)));
                // Adding DreamItem to list
                DreamItemList.add(DreamItem);
            } while (cursor.moveToNext());
        }

        // return DreamItem list
        return DreamItemList;
    }

}
