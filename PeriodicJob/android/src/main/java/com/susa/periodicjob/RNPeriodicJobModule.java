
package com.susa.periodicjob;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RNPeriodicJobModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;
    private final AtomicInteger mLastTaskId = new AtomicInteger(0);

    public RNPeriodicJobModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "RNPeriodicJob";
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @ReactMethod
    public void scheduleJob(String pkgName, String jobServiceName, Callback callback) {

        ComponentName mServiceComponent = new ComponentName(pkgName, jobServiceName);
        JobInfo.Builder builder = new JobInfo.Builder(mLastTaskId.incrementAndGet(), mServiceComponent);


        // Schedule job
        JobScheduler tm = (JobScheduler) reactContext.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        int  result = tm.schedule(builder.build());



        callback.invoke(mLastTaskId.get());
    }

    /**
     * Executed when user clicks on CANCEL ALL.
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @ReactMethod
    public void cancelAllJobs() {
        JobScheduler tm = (JobScheduler) reactContext.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        tm.cancelAll();
    }

    /**
     * Executed when user clicks on FINISH LAST TASK.
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @ReactMethod
    public void finishLastJob() {
        JobScheduler jobScheduler = (JobScheduler) reactContext.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        List<JobInfo> allPendingJobs = jobScheduler.getAllPendingJobs();
        if (allPendingJobs.size() > 0) {
            // Finish the last one
            int jobId = allPendingJobs.get(0).getId();
            jobScheduler.cancel(jobId);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @ReactMethod
    public void cancelJob(int jobId) {
        JobScheduler jobScheduler = (JobScheduler) reactContext.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.cancel(jobId);
    }


}