package ru.itx.loadersample;

import android.content.Context;
import android.content.Loader;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

/**
 * Created by d.yacenko on 14.11.17.
 */

public class MyLoader extends Loader<String> {
    Context ctx;
    GetFileAsync getFileAsync;
    Bundle bndl;

    public static final String TAG=MyLoader.class.getCanonicalName();

    public MyLoader(Context context,Bundle bndl) {
        super(context);
        ctx=context;
        Log.d(TAG,"Create MyLoader("+hashCode()+")");
        this.bndl=bndl;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        Log.d(TAG,"onStartLoading("+hashCode()+")");
         if (takeContentChanged())
            forceLoad();
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
        Log.d(TAG,"onStopLoading("+hashCode()+")");
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
        Log.d(TAG,"onForceLoad("+hashCode()+")");
        if(getFileAsync!=null)
            getFileAsync.cancel(true);
        getFileAsync=new GetFileAsync();
        getFileAsync.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,bndl.getStringArray(MainActivity.ARG));
    }

    @Override
    protected void onAbandon() {
        super.onAbandon();
        Log.d(TAG,"onAbandon("+hashCode()+")");
    }

    @Override
    protected void onReset() {
        super.onReset();
        Log.d(TAG,"onReset("+hashCode()+")");
    }

    void getResultFromTask(String result) {
        deliverResult(result);
    }

    // 1)String - arg for doInBackground
    // 2)Void - arg for onProgressUpdate
    // 3)String - return type doInBackground
    class GetFileAsync extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... files) {
            try
            {
                Log.d(TAG,"doInBackground("+MyLoader.this.hashCode()+")");
                int imgNum=new Random().nextInt(4);
                InputStream ims = ctx.getResources().openRawResource(ctx.getResources().getIdentifier(files[imgNum], "raw", ctx.getPackageName()));
                Bitmap bm = BitmapFactory.decodeStream(ims);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 100 , baos);
                byte[] b = baos.toByteArray();
                String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                ims.close();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return  encodedImage;
            }
            catch(Exception ex)
            {
                Log.e(TAG,"load image is failed("+MyLoader.this.hashCode()+")");
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d(TAG,"onPostExecute("+MyLoader.this.hashCode()+")");
            getResultFromTask(s);
        }
    }

}
