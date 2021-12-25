package princeparmar.qrcode;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHealper extends SQLiteOpenHelper {
    public static final String DB_NAME = "Detail.db";
    public static final int DB_VERSION = 1;

    public DBHealper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String USER_TABLE = "CREATE TABLE " + Model.detail.TABLE_NAME + " ( " +
                Model.detail.COL_ID + " INTEGER PRIMARY KEY," +
                Model.detail.COL_TYPE + " TEXT," + Model.detail.COL_DATE + " TEXT," + Model.detail.COL_MAIN_DATA + " TEXT," +
                Model.detail.COL_IMAGE_PATH + " TEXT)";

        sqLiteDatabase.execSQL(USER_TABLE);

    }

    public boolean addDeatil(String type, String date, String imagePath, String mainData) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Model.detail.COL_TYPE, type);
        contentValues.put(Model.detail.COL_DATE, date);
        contentValues.put(Model.detail.COL_MAIN_DATA, mainData);
        ;
        contentValues.put(Model.detail.COL_IMAGE_PATH, imagePath);


        long mid = sqLiteDatabase.insert(Model.detail.TABLE_NAME, null, contentValues);
        if (mid > 0) {
            return true;
        } else {
            return false;
        }

    }

    public ArrayList<RetriveModel> getData(){
        ArrayList<RetriveModel> arrayList = new ArrayList<>();

        // select all query
        String select_query= "SELECT *FROM " + Model.detail.TABLE_NAME;

        SQLiteDatabase db = this .getWritableDatabase();
        Cursor cursor = db.rawQuery(select_query, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                RetriveModel noteModel = new RetriveModel();
                noteModel.setMainData(cursor.getString(3));
                noteModel.setType(cursor.getString(1));
                noteModel.setImagePath(cursor.getString(4));
                arrayList.add(noteModel);
            }while (cursor.moveToNext());
        }
        return arrayList;
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
