package Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.pavan.moviesapp.ImageAdapter;
import com.example.pavan.moviesapp.MainActivityFragment;
import com.example.pavan.moviesapp.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by pavan on 3/21/2016.
 */
public class AndroidUtil {

    public String LOG_TAG = "Utils";

    private Context con;
    private MainActivityFragment mainActivityFragment = new MainActivityFragment();
    private ImageAdapter imageAdapter = new ImageAdapter(con, null);



    public AndroidUtil(Context con) {
        this.con = con;
    }

    public AndroidUtil() {

    }

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

    public Target getTarget(final String url) {
        Target target = new Target() {

            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {

                        final File dir = new File(con.getFilesDir() + "MoviesApp Posters");
                        if (!dir.exists()) {
                            dir.mkdir();
                            Log.i(LOG_TAG, "dir created");
                        }


                        File file = new File(dir, url);

                        try {
                            file.createNewFile();
                            FileOutputStream ostream = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
                            ostream.flush();
                            ostream.close();
                        } catch (IOException e) {
                            Log.e("IOException", e.getLocalizedMessage());
                        }
                    }
                }).start();

            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                imageAdapter.movie_posters_imageView.setImageResource(R.drawable.ic_mood_bad_black_24dp);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                imageAdapter.movie_posters_imageView.setImageResource(R.drawable.InternetSlowdown_Day);
            }
        };
        return target;
    }
}







