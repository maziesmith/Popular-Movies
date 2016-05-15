package Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by pavan on 3/21/2016.
 */
public class AndroidUtil {

    public String LOG_TAG = "Utils";

    private Context con;


    public AndroidUtil(Context con) {
        this.con = con;
    }

    public AndroidUtil() {

    }


//    public static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
//        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
//
//        for (Map.Entry<String, Object> entry : valueSet) {
//            String columnName = entry.getKey();
//
//            int idx = valueCursor.getColumnIndex(columnName);
//            if (idx == -1)
//                System.out.println("Column ' " + columnName + " ' not found. " + error);
//            else {
//                String expectedValue = entry.getValue().toString();
//                System.out.println("Value ' " + entry.getValue().toString() + "' &  " + expectedValue + " '.");
//
//                valueCursor.moveToFirst();
//                do {
//                    System.out.println("cursor contents : " + valueCursor.getString(idx));
//                } while (valueCursor.moveToNext());
//
//            }
//        }

//    }

    public boolean isOnline() {
        boolean connected = false;
        ConnectivityManager connectivity = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();
        if (connectivity != null)
            //Check Mobile data or Wifi net is present
            //we are connected to a network
            connected = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;

        return connected;
    }
}







