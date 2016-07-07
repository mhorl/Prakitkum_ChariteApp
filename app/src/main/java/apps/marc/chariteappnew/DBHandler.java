package apps.marc.chariteappnew;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "meal_entrys.db";
    public static final String TABLE_MEALS = "meals";

    public static final String COLUMN_MEAL_ID = "meal_id";
    public static final String COLUMN_MEAL_PRODUCTNAME = "meal_name";
    public static final String COLUMN_MEAL_DATE = "meal_date";
    public static final String COLUMN_MEAL_COMMITTED = "meal_committed";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableMeals = "CREATE TABLE "+TABLE_MEALS+"("
                +COLUMN_MEAL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +COLUMN_MEAL_PRODUCTNAME+" TEXT, "
                +COLUMN_MEAL_DATE+" TEXT, "
                +COLUMN_MEAL_COMMITTED+" INTEGER);";
        db.execSQL(createTableMeals);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_MEALS);
        onCreate(db);
    }

    //Add DB entry
    public void addMeal(MealEntry meal){
        ContentValues meal_values = new ContentValues();
        meal_values.put(COLUMN_MEAL_PRODUCTNAME, meal.getMealName());

        //Parse Date to String
        DateFormat iso8601FormatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        meal_values.put(COLUMN_MEAL_DATE, iso8601FormatDate.format(meal.getMealDate()));

        meal_values.put(COLUMN_MEAL_COMMITTED, meal.isMealCommitted());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_MEALS, null, meal_values);
        db.close();
    }

    //Delete DB entry
    public void deleteMeal(int meal_id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_MEALS+" WHERE ID="+meal_id+";");
    }

    //Change meal_committed
    public void updateMealCommitted(MealEntry meal, int isCommitted){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE "+TABLE_MEALS+" SET "+COLUMN_MEAL_COMMITTED+"="+isCommitted+" WHERE "+COLUMN_MEAL_ID+"="+meal.getMealId()+";");
    }

    //Get DB content
    public ArrayList<MealEntry> loadDB(){
        ArrayList<MealEntry> queryResults = new ArrayList<MealEntry>();

        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM "+TABLE_MEALS+" WHERE 1 ORDER BY meal_id DESC;";

        //Set cursor to query-result
        Cursor dbC = db.rawQuery(query, null);
        //Set cursor on to first object
        dbC.moveToFirst();

        //Iterate over result
        while(!dbC.isAfterLast()){
            //Getting Values
            int mealID = dbC.getInt(dbC.getColumnIndex("meal_id"));
            String mealName = dbC.getString(dbC.getColumnIndex("meal_name"));

            //Parse DB-Date-String to Date
            String mealDateString = dbC.getString(dbC.getColumnIndex("meal_date"));
            DateFormat iso8601FormatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date mealDate = new Date();
            try {
                mealDate = iso8601FormatDate.parse(mealDateString);
            }
            catch (ParseException e) {
                e.printStackTrace();
                System.out.println("Failed to parse meal_date (String) to Object 'Date");
            }

            //No native boolean-type in SQLite
            boolean mealCommitted = dbC.getInt(dbC.getColumnIndex("meal_committed")) != 0;

            //Create MealEntry object with values
            MealEntry mealEntry = new MealEntry(mealID, mealName, mealDate, mealCommitted);

            //Add to MealEntry-Stack
            queryResults.add(mealEntry);

            //Move cursor to next row
            dbC.moveToNext();
        }

        return queryResults;
    }

    //Prepare Values for Database-Insert, creating MealEntry Object
    public MealEntry createMealEntry(String mealName, Boolean mealCommitted){
        Date mealDate = new Date();

        return new MealEntry(0, mealName, mealDate, mealCommitted);
    }


}
