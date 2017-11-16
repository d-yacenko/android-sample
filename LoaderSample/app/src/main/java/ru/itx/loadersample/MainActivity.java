package ru.itx.loadersample;

import android.app.LoaderManager;
import android.content.Loader;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    public static final String[] IMG = {"beach", "beach1", "beach2", "beach3"};
    public static final String ARG = "IMG";
    static final int LOADER_IMG_ID = 1;
    public static final String TAG = MyLoader.class.getCanonicalName();

    ImageView ivResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle bndl = new Bundle();
        bndl.putStringArray(ARG, IMG);
        ivResult = findViewById(R.id.ivResult);
        getLoaderManager().initLoader(LOADER_IMG_ID, bndl, this);
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle bundle) {
        Loader<String> loader = null;
        if (id == LOADER_IMG_ID) {
            loader = new MyLoader(this, bundle);
            Log.d(TAG, "onCreateLoader(" + loader.hashCode() + ")");
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String s) {
        Log.d(TAG, "onLoadFinished(" + loader.hashCode() + ")");
        byte b[] = Base64.decode(s, Base64.DEFAULT);
        Bitmap bm = BitmapFactory.decodeByteArray(b, 0, b.length);
//        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//        imgViewer.setMinimumHeight(dm.heightPixels);
//        imgViewer.setMinimumWidth(dm.widthPixels);
        ivResult.setImageBitmap(bm);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        Log.d(TAG, "onLoaderReset(" + loader.hashCode() + ")");
    }

    public void getImgClick(View view) {
        Bundle bndl = new Bundle();
        bndl.putStringArray(ARG, IMG);
        Loader<String> loader = getLoaderManager().restartLoader(LOADER_IMG_ID, bndl,
                this);
        loader.forceLoad();
    }

    public void observerClick(View v) {
        Log.d(TAG, "observerClick");
        Loader<String> loader = getLoaderManager().getLoader(LOADER_IMG_ID);
        final ContentObserver observer = loader.new ForceLoadContentObserver();
        v.postDelayed(new Runnable() {
            @Override
            public void run() {
                observer.dispatchChange(false);
            }
        }, 5000);
    }


}
