package apps.marc.chariteappnew;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "meal_entrys.db";
    public static final String TABLE_MEALS = "meals";

    public static final String COLUMN_MEAL_ID = "meal_id";
    public static final String COLUMN_MEAL_PRODUCTNAME = "meal_productName";
    public static final String COLUMN_MEAL_DATE = "meal_date";
    public static final String COLUMN_MEAL_TIME = "meal_time";
    public static final String COLUMN_MEAL_COMMITTED = "meal_committed";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableMeals = "CREATE TABLE "+TABLE_MEALS+"("
                +COLUMN_MEAL_ID+" INTEGER PRIMATY KEY AUTOINCREMENT, "
                +COLUMN_MEAL_PRODUCTNAME+" TEXT, "
                +COLUMN_MEAL_DATE+" DATE, "
                +COLUMN_MEAL_TIME+" TIME);";
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

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_MEALS, null, meal_values);
        db.close();
    }

    //Delete DB entry
    public void deleteMeal(int meal_id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_MEALS+" WHERE ID="+meal_id+";");
    }


}
