package Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;

import com.example.pavan.moviesapp.MovieSQLiteDatabase.UpdateMovieRecord;
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
    public File dir;
    public File file;
    private Context con;
    private UpdateMovieRecord updateMovieRecord;


    public AndroidUtil(Context con) {
        this.con = con;
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


    public Target getTarget(final String url, final long movieID) {
        updateMovieRecord = new UpdateMovieRecord(con);
        Target target = new Target() {


            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {

                        dir = new File(Environment.getExternalStorageDirectory() + "/MoviesApp_Posters");
                        if (!dir.exists()) {
                            dir.mkdir();
                            Log.i(LOG_TAG, "dir created");
                        }


                        file = new File(dir, String.valueOf(url.hashCode()) + ".jpg");
                        Log.i(LOG_TAG, "file name : " + file);
                        try {
                            file.createNewFile();
                            FileOutputStream ostream = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
                            ostream.flush();
                            ostream.close();
                            updateMovieRecord.UpdateMoviePoster(String.valueOf(file), movieID);
                            Log.i(LOG_TAG, "saved into the file system");
                        } catch (IOException e) {
                            Log.e("IOException", e.getLocalizedMessage());
                        }
                    }
                }).start();

            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        return target;
    }

    public void deleteMoviePosterFromFileSystem(String poster_path) {

        File moviePoster = new File(poster_path);
        if (moviePoster.exists()) {
            if (moviePoster.delete())
                Log.i(LOG_TAG, "file Deleted :" + poster_path);
            else
                Log.i(LOG_TAG, "file not Deleted :" + poster_path);
        } else
            Log.i(LOG_TAG, "file does not exist in the file system to delete.");
    }


}







