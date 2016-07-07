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

    //ListAdapter for listView
    HistoryItemAdapter listAdap;

    //Holds MealEntry-data temporary to display it in History
    ArrayList<MealEntry> historyItems;

    //References to Interface-Objects
    EditText history_entry;

    //NetworkHandler to send data to server
    NetworkHandler networkHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize Database-Handler
        this.dbHandler = new DBHandler(getApplicationContext(), null, null, 1);

        //Initialise History-List
        this.historyItems = new ArrayList<MealEntry>();

        //Create Reference
        this.history_entry = (EditText) findViewById(R.id.History_entry);

        //Initialize NetworkHandler
        this.networkHandler = new NetworkHandler(getApplicationContext());
    }

    //Button to History
    public void toHistory(View v){

        //Set View to History
        setContentView(R.layout.history);

        //Load History from DB
        historyItems = dbHandler.loadDB();

        //Show eating-history
        listAdap = new HistoryItemAdapter(getApplicationContext(), historyItems);
        ListView myListView = (ListView) findViewById(R.id.Meals_list);
        myListView.setAdapter(listAdap);
    }

    //Performs 'commit'-action on selected items
    public int onClickCommit(View v){
        boolean[] checkedItems;
        checkedItems = listAdap.getCheckboxState();

        //Committed Item Counter
        int commitCounter = 0;

        //Submit succeded
        boolean submitSuccess = false;

        //Iterate over checkedItems
        for(int i = 0; i < checkedItems.length; i++) {
            if (checkedItems[i]) {
                String mealName = historyItems.get(i).getMealName();

                if(networkHandler.sendMealToDatabase(historyItems.get(i))){
                    /* submitt successfull */

                    //Increment
                    commitCounter++;

                    //Set commit-value to true
                    historyItems.get(i).setMealCommitted(true);
                    dbHandler.updateMealCommitted(historyItems.get(i), 1);
                }
                else{
                    Toast t = Toast.makeText(this, "While submitting: Could not establish or lost connection.", Toast.LENGTH_SHORT);
                    t.show();

                    return 0;
                }
            }
        }

        //Changes done.
        Toast t = Toast.makeText(this, commitCounter+" items committed!", Toast.LENGTH_SHORT);
        t.show();

        return 1;
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
