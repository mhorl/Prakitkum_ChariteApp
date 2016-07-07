package apps.marc.chariteappnew;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
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

    public boolean sendMealToDatabase(MealEntry meal){

        //Submit succeded
        boolean submitSuccess = false;

        DownloadWebpageTask dlwebtask = new DownloadWebpageTask();

        String mealName = meal.getMealName();

        Date mealDate = meal.getMealDate();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String mealDateString = dateFormat.format(mealDate);

        String serverQuery = "";
        try{
            serverQuery = "http://192.168.56.1/PROJECTS/ChariteApp/commitData.php?mealName="+ URLEncoder.encode(mealName, "UTF-8")+"&mealDate="+URLEncoder.encode(mealDateString, "UTF-8");
        }
        catch (Exception e){
            Log.i("Connection", "Error while creating serverquery.");
            e.printStackTrace();
        }

        //Checking if Connection is available
        networkInfo = connectivityManager.getActiveNetworkInfo();

        //If available, submit.
        if(networkInfo != null && networkInfo.isConnected()){
            submitSuccess = true;
            dlwebtask.execute(serverQuery);
        }
        else{
            Log.i("Connection", "Submit failed. Check connection.");
        }

        Log.i("Connection", "Tasks done.");
        Log.i("Connection", "Boolean is: "+submitSuccess);

        return submitSuccess;
    }

    //Using AsyncTask to perform network-actions on differend thread
    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            //Holding response-Message
            String responseMessage = "";

            if(networkInfo != null && networkInfo.isConnected()){
                try{
                    URL url = new URL(urls[0]);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    connection.setConnectTimeout(1000); //Milliseconds
                    connection.setRequestMethod("GET");
                    connection.setDoInput(true);

                    //Start query
                    connection.connect();
                    responseMessage = connection.getResponseMessage();

                    //Success
                    Log.i("Connection", "Submitted.");

                }
                catch(Exception e){
                    Log.i("Connection", "Submit failed. Check if server is available.");
                    e.printStackTrace();
                }
            }
            else{
                Log.i("Connection", "Submit failed. Check connection.");
            }

            return responseMessage;
        }
    }
}
