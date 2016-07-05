package apps.marc.chariteappnew;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    //Database-Handler for saving and loading MealEntry-data
    DBHandler dbHandler;

    //Holds MealEntry-data temporary to disply it in History
    ArrayList<MealEntry> historyItems;

    //References to Interface-Objects
    EditText history_entry;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize Database-Handler
        dbHandler = new DBHandler(getApplicationContext(), null, null, 1);

        //Initialise History-List
        historyItems = new ArrayList<MealEntry>();

        //Create Reference
        history_entry = (EditText) findViewById(R.id.History_entry);
    }

    //Button to History
    public void toHistory(View v){

        //Set View to History
        setContentView(R.layout.history);

        //Load History from DB
        historyItems = dbHandler.loadDB();

        //Show eating-history
        ListAdapter listAdap = new HistoryItemAdapter(getApplicationContext(), historyItems);
        ListView myListView = (ListView) findViewById(R.id.Meals_list);
        myListView.setAdapter(listAdap);
    }

    //Button to Home
    public void toHome(View v){
        setContentView(R.layout.activity_main);
    }

    //Saving entered user-text into 'historyList'
    public void sendHistoryEntry(View v){
        //Get mealname
        EditText historyEntry = (EditText) findViewById(R.id.History_entry);
        String mealName = historyEntry.getText().toString();

        //Add mealEntry to Database
        dbHandler.addMeal(dbHandler.createMealEntry(mealName, false));


        //Clear entry-field after userinput
        history_entry.setText("");

        //Generate toast
        Toast t = Toast.makeText(this, "New meal added!", Toast.LENGTH_SHORT);
        t.show();
    }
}
