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

//this is a new comment, checking if github works
//somemore comments

public class MainActivity extends AppCompatActivity {

    //Holding list-items for eating-history
    private ArrayList<MealEntry> historyItems;

    //References
    EditText history_entry;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        historyItems = new ArrayList<MealEntry>();

        history_entry = (EditText) findViewById(R.id.History_entry);
    }

    //Button to History
    public void toHistory(View v){

        //Set View to History
        setContentView(R.layout.history);

        //Show eating-history
        ListAdapter listAdap = new HistoryItemAdapter(getApplicationContext(), historyItems);
        //ListAdapter listAdap = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, historyList);
        ListView myListView = (ListView) findViewById(R.id.Meals_list);
        myListView.setAdapter(listAdap);
    }

    //Button to Home
    public void toHome(View v){
        setContentView(R.layout.activity_main);
    }

    //Saving entered user-text into 'historyList'
    public void sendHistoryEntry(View v){
        //Get current Date and Time
        Date mealDate = new Date();

        //Get meaname
        EditText historyEntry = (EditText) findViewById(R.id.History_entry);
        String mealName = historyEntry.getText().toString();

        //Add mealName and mealDate to List
        MealEntry historyItem = new MealEntry(0, mealName, mealDate, false);
        historyItems.add(historyItem);


        //Clear entry-field after userinput
        history_entry.setText("");

        //Generate toast
        /*Toast t = Toast.makeText(this, historyList.get(historyList.size()-1)+" added!", Toast.LENGTH_SHORT);
        t.show();*/
    }
}
