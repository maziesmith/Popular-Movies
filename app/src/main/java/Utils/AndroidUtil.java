package Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.Map;
import java.util.Set;

/**
 * Created by pavan on 3/21/2016.
 */
public class AndroidUtil {
    private Context con;

    public AndroidUtil(Context con) {
        this.con = con;
    }

    public AndroidUtil() {

    }

    public static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();

        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();

            int idx = valueCursor.getColumnIndex(columnName);
            if (idx == -1)
                System.out.println("Column ' " + columnName + " ' not found. " + error);
            else {
                String expectedValue = entry.getValue().toString();
                System.out.println("Value ' " + entry.getValue().toString() + "' &  " + expectedValue + " '.");

                valueCursor.moveToFirst();
                do {
                    System.out.println("cursor contents : " + valueCursor.getString(idx));
                } while (valueCursor.moveToNext());

            }
        }

    }

    public boolean isOnline() {
        ConnectivityManager connectivity = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }
}




