package apps.marc.chariteappnew;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

    //Holds Views from history_item_template
    static class ViewHolder{
        TextView historyItem_largeText;
        TextView historyItem_smallText;
        ImageView historyItem_image;
        CheckBox checkBox;

    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());

        final ViewHolder viewHolder;

        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.history_item_template, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.historyItem_largeText = (TextView) convertView.findViewById(R.id.historyItem_largeText);
            viewHolder.historyItem_smallText = (TextView) convertView.findViewById(R.id.historyItem_smallText);
            viewHolder.historyItem_image = (ImageView) convertView.findViewById(R.id.historyItem_image);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.historyItem_checkBox);

            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(checkboxState[position]){
            viewHolder.checkBox.setChecked(true);
        }
        else{
            viewHolder.checkBox.setChecked(false);
        }

        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewHolder.checkBox.isChecked()){
                    checkboxState[position] = true;
                }
                else{
                    checkboxState[position] = false;
                }
            }
        });

        MealEntry historyItem = getItem(position);

        viewHolder.historyItem_largeText.setText(historyItem.getMealName());
        viewHolder.historyItem_largeText.setTextColor(Color.BLACK);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        viewHolder.historyItem_smallText.setText(dateFormat.format(historyItem.getMealDate())); //"11:25 Uhr - 04.07.2016"

        viewHolder.historyItem_smallText.setTextColor(Color.GRAY);
        viewHolder.historyItem_image.setImageResource(R.drawable.meal_icon);

        return convertView;
    }
}
