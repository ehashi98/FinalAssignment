package algonquin.cst2335.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabase extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Playerlibrary.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "player_library";
    private static final String COLUMN_ID = "_id";
    private static final String PLAYER_NAME = "player_name";
    private static final String PLAYER_TEAM = "player_team";
    private static final String PLAYER_POSITION = "player_postion";

    MyDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        PLAYER_NAME + " TEXT, " +
                        PLAYER_TEAM + " TEXT, " +
                        PLAYER_POSITION + " TEXT);";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addPlayer(String name, String team, String position){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(PLAYER_NAME, name);
        cv.put(PLAYER_TEAM, team);
        cv.put(PLAYER_POSITION, position);
        long result = db.insert(TABLE_NAME,null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData(){
       String query = "SELECT * FROM " + TABLE_NAME;
       SQLiteDatabase db = this.getReadableDatabase();

       Cursor cursor = null;
       if(db != null){
           cursor = db.rawQuery(query, null);
       }
       return cursor;
    }

    void updateData(String row_id, String name, String team, String position){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(PLAYER_NAME, name);
        cv.put(PLAYER_TEAM, team);
        cv.put(PLAYER_POSITION, position);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Update", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
        }
    }

}
