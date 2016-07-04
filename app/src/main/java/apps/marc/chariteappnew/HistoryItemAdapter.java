package apps.marc.chariteappnew;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class HistoryItemAdapter extends ArrayAdapter<MealEntry>{

    public HistoryItemAdapter(Context context, ArrayList<MealEntry> historyItems) {
        super(context, R.layout.history_item_template, historyItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View itemView = layoutInflater.inflate(R.layout.history_item_template, parent, false);

        MealEntry historyItem = getItem(position);
        TextView historyItem_largeText = (TextView) itemView.findViewById(R.id.historyItem_largeText);
        TextView historyItem_smallText = (TextView) itemView.findViewById(R.id.historyItem_smallText);
        ImageView historyItem_image = (ImageView) itemView.findViewById(R.id.historyItem_image);

        historyItem_largeText.setText(historyItem.getMealName());
        historyItem_largeText.setTextColor(Color.BLACK);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        historyItem_smallText.setText(dateFormat.format(historyItem.getMealDate())); //"11:25 Uhr - 04.07.2016"
        historyItem_smallText.setTextColor(Color.GRAY);

        historyItem_image.setImageResource(R.drawable.meal_icon);

        return itemView;
    }
}
