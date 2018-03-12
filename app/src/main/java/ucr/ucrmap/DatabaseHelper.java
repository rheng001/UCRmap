package ucr.ucrmap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * Created by jorge on 10/9/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {


    private static final String TAG = "DatabaseHelper";
    private static final String DATABASE_NAME = "UCRmapDatabase.db";
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
    private static final String C_COL7 = "day";
    private static final String Event_Table = "Events";
    private static final String E_COL1= "Title";
    //    private static final String E_COL2 = "Location";
    // private static final String E_COL3 = "Event Details";
    // private static final String E_COL4= "Hours";
    //  private static final String E_COL5 = "Mins";
    private static final String E_COL2 = "Day";
    private static final String E_COL3 = "Time"; //duration

    Student student = new Student("name","id","","entry");

    private final Context mContext;

    // read from csv file

    String line;
    String Location_table = "Loc_Coordinates";
    String Dining_table = "Dining";
    String Location_columns;
    String Dining_columns;
    String str1;
    String str2;



    public DatabaseHelper(Context context)
    {
        super(context,DATABASE_NAME,null, 1);
        mContext = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // It will be called whenever there is a first call to getReadableDatabase() or getWritableDatabase() function available in super SQLiteOpenHelper class

        InputStream read_text_location = mContext.getResources().openRawResource(R.raw.coordinatesdatabase);
        BufferedReader buffer_loc = new BufferedReader(new InputStreamReader(read_text_location, Charset.forName("UTF-8")));

        InputStream read_text_dining = mContext.getResources().openRawResource(R.raw.dining_foodtrucks);
        BufferedReader buffer_din = new BufferedReader(new InputStreamReader(read_text_dining, Charset.forName("UTF-8")));


        // have to clear app data to create new tables in setting and ucrmap delete data


        try {
            String create_Friend = "CREATE TABLE " + Friend_Table + "("
                    + student.num + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                    + student.name + " TEXT NOT NULL , "
                    + student.id + " INTEGER NOT NULL)";
            db.execSQL(create_Friend);

        }catch(Exception e)
        {
            Log.e(TAG,"ERROR: " + Log.getStackTraceString(e));
        }

        try {
        String create_Class = "CREATE TABLE " + Class_Table + "("
                + C_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + C_COL2 + " TEXT NOT NULL , "
                + C_COL3 + " TEXT NOT NULL , "
                + C_COL4 + " TEXT NOT NULL , "
                + C_COL5 + " TEXT NOT NULL , "
                + C_COL6 + " TEXT NOT NULL , "
                + C_COL7 + " TEXT NOT NULL)";
                db.execSQL(create_Class);
        }catch(Exception e)
        {
            Log.e(TAG,"ERROR: " + Log.getStackTraceString(e));
        }

        try {
            String create_Event = "CREATE TABLE " + Event_Table + "("
                    + "Event Title" + " TEXT NOT NULL ,"
                    + "Day" + " TEXT NOT NULL , "
                    + "Time" + " TEXT NOT NULL , "
                    + "Description" + " TEXT NOT NULL , "
                    + "Location" + " TEXT NOT NULL)";

            db.execSQL(create_Event);
        }catch(Exception e)
        {
            Log.e(TAG,"ERROR: " + Log.getStackTraceString(e));

        }

        try {
            String create_Location_Coordinates = "CREATE TABLE " + Location_table + "("
                    + "Building" + " TEXT NOT NULL ,"
                    + "Long" + " TEXT NOT NULL , "
                    + "Lat" + " TEXT NOT NULL)";
            db.execSQL(create_Location_Coordinates);
        }catch(Exception e)
        {
            Log.e(TAG,"ERROR: " + Log.getStackTraceString(e));
        }

        try{
            String create_Dining = "CREATE TABLE " + Dining_table + "("
                    + "Name" + " TEXT NOT NULL ,"
                    + "Day" + " TEXT NOT NULL , "
                    + "Time" + " TEXT NOT NULL , "
                    + "Location" + " TEXT NOT NULL)";
            db.execSQL(create_Dining);
        }catch(Exception e)
        {
            Log.e(TAG,"ERROR: " + Log.getStackTraceString(e));
        }




        Location_columns = "Building, Long, Lat";
        Dining_columns = "Name, Day, Time, Location";

        location_CSV(Location_table,Location_columns,buffer_loc,db);
        dining_CSV(Dining_table,Dining_columns,buffer_din,db);


        toastMessage("Created database");
        Log.e(TAG,"DATABASE: " + "hhh");

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
        contentValues.put(student.name,name);
        contentValues.put(student.id,id);
        toastMessage("ADDING TO FRIEND TABLE");
        Log.d(TAG, "addData: Adding " + name + " and " + id + " to " + Friend_Table);
        long result = db.insert(Friend_Table, null, contentValues);
        return result;
    }
    public long addData_Class(String name, String building, String room, String start_time, String end_Time,String day)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(C_COL2,name);
        contentValues.put(C_COL3,building);
        contentValues.put(C_COL4,room);
        contentValues.put(C_COL5,start_time);
        contentValues.put(C_COL6,end_Time);
        contentValues.put(C_COL7,day);

        toastMessage("ADDING TO CLASS TABLE");
        long result = db.insert(Class_Table, null, contentValues);
        Log.d(TAG, "addData: Adding " + name + " to " + Class_Table);
        if(result == -1) {
            toastMessage("FAILED");
        }
        else
        {
            toastMessage("WORKED");
        }
        return result;

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
        String query = "DELETE FROM " + Friend_Table+ " WHERE " + student.id + " = '" + id + "'" ;
        db.execSQL(query);
        Log.d(TAG,"DELETE : " + id + " from database");
    }
    public void DeleteData_Events(String title){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + Event_Table+ " WHERE " + E_COL1 + " = '" + title + "'" ;
        db.execSQL(query);
        Log.d(TAG,"DELETE : " + title + " from database");
    }
    public void DeleteData_Class(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + Class_Table+ " WHERE " + "name" + " = '" + name + "'" ;
        db.execSQL(query);
        Log.d(TAG,"DELETE : " + name + " from database");
    }
    public void updateName(String newName,int id, String oldName)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE "+ Friend_Table + " SET " + student.name+ " = '" + newName
                + "' WHERE "+ student.id +" = '" + id + "'" + " AND " + student.name + " = '" +oldName + "'";

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
    public Cursor getAllClasses(String day)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * " + " FROM " + Class_Table + " WHERE " + C_COL7 + " = '" + day + "'";
        Cursor data = db.rawQuery(query,null);
        Log.d(TAG, "addData: Display" + Class_Table);
        Log.d(TAG, "getallclasses" + day);

        return data;
    }
    public Cursor getLatLong(String building)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        building = building.trim();
        String query = "SELECT Long,Lat" + " FROM " + Location_table + " WHERE " + "Building" + " = '" + building + "'";
        //String query = "SELECT * " + " FROM " + Location_table;
        Cursor data = db.rawQuery(query,null);
        Log.d(TAG, "getLatLong" + data.toString());
        return data;
    }

    public void location_CSV(String table, String columns, BufferedReader buffer, SQLiteDatabase db)
    {
        String str1 = "INSERT INTO " + table + " (" + columns + ") values(";
        String str2 = ");";
        try {
            while ((line = buffer.readLine()) != null)
            {StringBuilder sb = new StringBuilder(str1);
                String[] str = line.split(",");
                sb.append("'" + str[0] + "',");
                sb.append("'" + str[1] + "',");
                sb.append("'" + str[2] + "'");
                sb.append(str2);
                db.execSQL(sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void dining_CSV(String table, String columns, BufferedReader buffer, SQLiteDatabase db)
    {
        String str1 = "INSERT INTO " + table + " (" + columns + ") values(";
        String str2 = ");";
        try {
            while ((line = buffer.readLine()) != null)
            {StringBuilder sb = new StringBuilder(str1);
                String[] str = line.split(",");
                sb.append("'" + str[0] + "',");
                sb.append("'" + str[1] + "',");
                sb.append("'" + str[2] + "',");
                sb.append("'" + str[3] + "'");
                sb.append(str2);
                db.execSQL(sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void toastMessage(String message){
        Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show();
    }
}
