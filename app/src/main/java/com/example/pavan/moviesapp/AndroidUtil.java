package com.example.pavan.moviesapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by pavan on 3/21/2016.
 */
public class AndroidUtil {
    private Context con;
    public AndroidUtil(Context con){
        this.con=con;
    }

    public AndroidUtil() {

    }

    public boolean isOnline(){
        ConnectivityManager connectivity = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }
}

