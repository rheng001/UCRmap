package ucr.ucrmap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by jorge on 10/9/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {


    private static final String TAG = "DatabaseHelper";
    private static final String DATABASE_NAME = "MYDATABASE.db";
    private static final String Friend_Table = "Friends";
    private static final String COL1= "Entry";
    private static final String COL2 = "name";
    private static final String COL3 = "ID";




    private static final String Class_Table = "Class";
    private static final String C_COL1= "Entry";
    private static final String C_COL2 = "name";
    private static final String C_COL3 = "building";
    private static final String C_COL4= "room";
    private static final String C_COL5 = "start_Time";
    private static final String C_COL6 = "end_Time";

    private static final String Event_Table = "Event";
    private static final String E_COL1= "Title";
    //    private static final String E_COL2 = "Location";
    // private static final String E_COL3 = "Event Details";
    // private static final String E_COL4= "Hours";
    //  private static final String E_COL5 = "Mins";
    private static final String E_COL2 = "Day";
    private static final String E_COL3 = "Time"; //duration




    private final Context mContext;



    public DatabaseHelper(Context context)
    {
        super(context,DATABASE_NAME,null, 1);
        mContext = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // have to clear app data to create new tables in setting and ucrmap delete data

        String create_Friend = "CREATE TABLE " + Friend_Table + "("
                + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + COL2 + " TEXT NOT NULL , "
                + COL3 + " INTEGER NOT NULL)";

        String create_Class = "CREATE TABLE " + Class_Table + "("
                + C_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + C_COL2 + " TEXT NOT NULL , "
                + C_COL3 + " TEXT NOT NULL , "
                + C_COL4 + " TEXT NOT NULL , "
                + C_COL5 + " TEXT NOT NULL , "
                + C_COL6 + " TEXT NOT NULL)";

        String create_Event = "CREATE TABLE " + Event_Table + "("
                + E_COL1 + " TEXT NOT NULL ,"
                + E_COL2 + " TEXT NOT NULL , "
                + E_COL3 + " TEXT NOT NULL)";
        // + E_COL4 + " TEXT NOT NULL , "
        // + E_COL5 + " TEXT NOT NULL)";

        db.execSQL(create_Friend);
        db.execSQL(create_Class);
        db.execSQL(create_Event);
        toastMessage("Created database");
    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + Friend_Table);
        db.execSQL("DROP IF TABLE EXISTS " + Class_Table);
        db.execSQL("DROP IF TABLE EXISTS " + Event_Table);
        onCreate(db);
    }


    public long addData_Friend(String name, int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2,name);
        contentValues.put(COL3,id);
        toastMessage("ADDING TO FRIEND TABLE");
        Log.d(TAG, "addData: Adding " + name + " and " + id + " to " + Friend_Table);
        long result = db.insert(Friend_Table, null, contentValues);
        return result;
    }
    public boolean addData_Class(String name, String building, String room, String start_time, String end_Time)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(C_COL2,name);
        contentValues.put(C_COL3,building);
        contentValues.put(C_COL4,room);
        contentValues.put(C_COL5,start_time);
        contentValues.put(C_COL6,end_Time);

        toastMessage("ADDING TO CLASS TABLE");
        long result = db.insert(Class_Table, null, contentValues);
        Log.d(TAG, "addData: Adding " + name + " to " + Class_Table);
        if(result == -1) {
            toastMessage("FAILED");
            return false;
        }
        else
        {
            toastMessage("WORKED");
            return true;
        }
    }

    public long addData_Event(String title,String day, String time)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(E_COL1,title);
        contentValues.put(E_COL2,day);
        contentValues.put(E_COL3,time);
        //contentValues.put(E_COL4,day);
        //contentValues.put(E_COL5,time);
        toastMessage("ADDING TO EVENT TABLE");
        Log.d(TAG, "addData: Adding " + "to " + Event_Table);
        long result = db.insert(Event_Table, null, contentValues);
        return result;
    }

    public void DeleteData_Friends(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + Friend_Table+ " WHERE " + COL3 + " = '" + id + "'" ;
        db.execSQL(query);
        Log.d(TAG,"DELETE : " + id + " from database");
    }
    public void DeleteData_Events(String title){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + Event_Table+ " WHERE " + E_COL1 + " = '" + title + "'" ;
        db.execSQL(query);
        Log.d(TAG,"DELETE : " + title + " from database");
    }
    public void updateName(String newName,int id, String oldName)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE "+ Friend_Table + " SET " + COL2 + " = '" + newName
                + "' WHERE "+ COL1 +" = '" + id + "'" + " AND " + COL2 + " = '" +oldName + "'";

        Log.d(TAG,"update query: " + query);
        db.execSQL(query);
    }
    public Cursor getAllFriends()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * " + " FROM " + Friend_Table ;
        Cursor data = db.rawQuery(query,null);
        Log.d(TAG, "addData: Display" + Friend_Table);
        return data;
    }
    public Cursor getAllEvents()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * " + " FROM " + Event_Table ;
        Cursor data = db.rawQuery(query,null);
        Log.d(TAG, "addData: Display" + Event_Table);
        return data;
    }
    private void toastMessage(String message){
        Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show();
    }
}