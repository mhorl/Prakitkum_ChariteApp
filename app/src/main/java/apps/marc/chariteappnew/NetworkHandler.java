package apps.marc.chariteappnew;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NetworkHandler {
    Context context;
    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;

    public NetworkHandler(Context context){
        this.context = context;
        this.connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public void sendMealToDatabase(MealEntry meal){
        DownloadWebpageTask dlwebtask = new DownloadWebpageTask();

        String mealName = meal.getMealName();

        Date mealDate = meal.getMealDate();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String mealDateString = dateFormat.format(mealDate);

        String serverQuery = "http://192.168.56.1/PROJECTS/ChariteApp/commitData.php?mealName="+mealName+"&mealDate="+mealDateString;
        dlwebtask.execute(serverQuery);


    }

    //Using AsyncTask to perform network-actions on differend thread
    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            //Checking if Connection is available
            networkInfo = connectivityManager.getActiveNetworkInfo();

            //Holding response-Code (200 - Ok, 401 - Unauthorized, -1 - Couldnt get code)
            //int responseCode;

            //Holding response-Message
            String responseMessage = "";

            if(networkInfo != null && networkInfo.isConnected()){
                try{
                    URL url = new URL(urls[0]);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    connection.setConnectTimeout(1000); //Milliseconds
                    connection.setRequestMethod("GET");
                    connection.setDoInput(false);

                    //Start query
                    connection.connect();
                    responseMessage = connection.getResponseMessage();

                    Log.i("Connection", "query was started");

                }
                catch(Exception e){
                    Log.i("Connection", "SOMETHING WENT WRONG!");
                    e.printStackTrace();
                }
            }
            else{
                Toast t = Toast.makeText(context, "No connection! Check your internet connetion and repeat your action!", Toast.LENGTH_LONG);
                t.show();
            }

            return responseMessage;
        }

    }
}
