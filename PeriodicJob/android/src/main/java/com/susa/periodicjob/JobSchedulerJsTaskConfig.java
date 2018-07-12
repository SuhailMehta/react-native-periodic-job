package com.susa.periodicjob;

import com.facebook.react.bridge.WritableMap;
import com.facebook.react.jstasks.HeadlessJsTaskConfig;

public class JobSchedulerJsTaskConfig {

    private final String mTaskKey;
    private final WritableMap mData;
    private final long mTimeout;
    private final boolean mAllowedInForeground;

    public JobSchedulerJsTaskConfig(String taskKey, WritableMap data) {
        this(taskKey,data, 0, false);
    }

    public JobSchedulerJsTaskConfig(String taskKey, WritableMap data, long timeout) {
        this(taskKey,data, timeout, false);
    }

    public JobSchedulerJsTaskConfig(String taskKey, WritableMap data, long timeout, boolean allowedInForeground) {
        mTaskKey = taskKey;
        mData = data;
        mTimeout = timeout;
        mAllowedInForeground = allowedInForeground;
    }

    /* package */ String getTaskKey() {
        return mTaskKey;
    }

    /* package */ WritableMap getData() {
        return mData;
    }

    /* package */ long getTimeout() {
        return mTimeout;
    }

    /* package */ boolean isAllowedInForeground() {
        return mAllowedInForeground;
    }


}
