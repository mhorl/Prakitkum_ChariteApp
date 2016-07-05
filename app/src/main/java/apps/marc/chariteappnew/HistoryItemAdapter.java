package apps.marc.chariteappnew;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class HistoryItemAdapter extends ArrayAdapter<MealEntry>{

    private final ArrayList<MealEntry> historyItems;
    private final boolean[] checkboxState;
    private final Context context;

    public HistoryItemAdapter(Context context, ArrayList<MealEntry> historyItems) {
        super(context, R.layout.history_item_template, historyItems);

        this.context = context;
        this.historyItems = historyItems;
        this.checkboxState = new boolean[this.historyItems.size()];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        convertView = layoutInflater.inflate(R.layout.history_item_template, parent, false);

        //Checkbox-handling

        CheckBox checkBox;

        if(convertView == null){
            checkBox = (CheckBox) convertView.findViewById(R.id.historyItem_checkBox);

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int boxPosition = (Integer) buttonView.getTag();
                    historyItems.get(boxPosition).setChecked(buttonView.isChecked());
                }
            });
        }
        else{
            checkBox = (CheckBox) convertView.getTag();
        }

        checkBox.setChecked(true); //historyItems.get(position).isChecked()

        //*****************

        MealEntry historyItem = getItem(position);
        TextView historyItem_largeText = (TextView) convertView.findViewById(R.id.historyItem_largeText);
        TextView historyItem_smallText = (TextView) convertView.findViewById(R.id.historyItem_smallText);
        ImageView historyItem_image = (ImageView) convertView.findViewById(R.id.historyItem_image);

        historyItem_largeText.setText(historyItem.getMealName());
        historyItem_largeText.setTextColor(Color.BLACK);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        historyItem_smallText.setText(dateFormat.format(historyItem.getMealDate())); //"11:25 Uhr - 04.07.2016"
        historyItem_smallText.setTextColor(Color.GRAY);

        historyItem_image.setImageResource(R.drawable.meal_icon);

        return convertView;
    }
}
