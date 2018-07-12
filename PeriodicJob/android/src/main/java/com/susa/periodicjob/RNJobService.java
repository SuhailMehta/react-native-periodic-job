package com.susa.periodicjob;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.jstasks.HeadlessJsTaskConfig;
import com.facebook.react.jstasks.HeadlessJsTaskContext;
import com.facebook.react.jstasks.HeadlessJsTaskEventListener;

import javax.annotation.Nullable;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public abstract class RNJobService extends JobService implements HeadlessJsTaskEventListener {

    protected JobParameters params;

    @Override
    public boolean onStartJob(JobParameters params) {

        this.params = params;

        HeadlessJsTaskConfig config = getTaskConfig(params);

        if (config != null) {
            startTask(config);
            return true;
        }

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        if (getReactNativeHost().hasInstance()) {
            ReactInstanceManager reactInstanceManager = getReactNativeHost().getReactInstanceManager();
            ReactContext reactContext = reactInstanceManager.getCurrentReactContext();
            if (reactContext != null) {
                HeadlessJsTaskContext jobSchedulerJsTaskContext =
                        HeadlessJsTaskContext.getInstance(reactContext);
                jobSchedulerJsTaskContext.removeTaskEventListener(this);
            }
        }

        return true;
    }

    @Override
    public void onHeadlessJsTaskStart(int taskId) {

    }

    @Override
    public void onHeadlessJsTaskFinish(int taskId) {

        jobFinished(, false);

    }

    /**
     * Start a task. This method handles starting a new React instance if required.
     * <p>
     * Has to be called on the UI thread.
     *
     * @param taskConfig describes what task to start and the parameters to pass to it
     */
    protected void startTask(final HeadlessJsTaskConfig taskConfig) {
        final ReactInstanceManager reactInstanceManager =
                getReactNativeHost().getReactInstanceManager();
        ReactContext reactContext = reactInstanceManager.getCurrentReactContext();
        if (reactContext == null) {
            reactInstanceManager
                    .addReactInstanceEventListener(new ReactInstanceManager.ReactInstanceEventListener() {
                        @Override
                        public void onReactContextInitialized(ReactContext reactContext) {
                            invokeStartTask(reactContext, taskConfig);
                            reactInstanceManager.removeReactInstanceEventListener(this);
                        }
                    });
            if (!reactInstanceManager.hasStartedCreatingInitialContext()) {
                reactInstanceManager.createReactContextInBackground();
            }
        } else {
            invokeStartTask(reactContext, taskConfig);
        }
    }

    private void invokeStartTask(ReactContext reactContext,
                                 final HeadlessJsTaskConfig taskConfig) {
        final HeadlessJsTaskContext jobSchedulerJsTaskContext = HeadlessJsTaskContext.getInstance(reactContext);
        jobSchedulerJsTaskContext.addTaskEventListener(this);

        UiThreadUtil.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        int taskId = jobSchedulerJsTaskContext.startTask(taskConfig);
//                        mActiveTasks.add(taskId);
                    }
                }
        );
    }

    /**
     * Get the {@link ReactNativeHost} used by this app. By default, assumes {@link #getApplication()}
     * is an instance of {@link ReactApplication} and calls
     * {@link ReactApplication#getReactNativeHost()}. Override this method if your application class
     * does not implement {@code ReactApplication} or you simply have a different mechanism for
     * storing a {@code ReactNativeHost}, e.g. as a static field somewhere.
     */
    protected ReactNativeHost getReactNativeHost() {
        return ((ReactApplication) getApplication()).getReactNativeHost();
    }

    public abstract @Nullable
    HeadlessJsTaskConfig getTaskConfig(JobParameters params);
}
