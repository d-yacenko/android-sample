package ru.samsung.itschool.book.jobschedulersample;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by d.yacenko on 19.11.17.
 */

public class TestJobService extends JobService {
    private static final String TAG = TestJobService.class.getSimpleName();

    @Override
    public boolean onStartJob(JobParameters params) {
        // start other activity or service
        Log.d(TAG,"onStartJob");
        Intent activity = new Intent(getApplicationContext(), Main2Activity.class);
        getApplicationContext().startActivity(activity);
        Util.scheduleJob(getApplicationContext()); // reschedule the job
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG,"onStopJob");
        return true;
    }

}