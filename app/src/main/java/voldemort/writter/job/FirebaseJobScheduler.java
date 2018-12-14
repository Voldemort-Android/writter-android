package voldemort.writter.job;

import android.content.Context;

import java.sql.Driver;

public class FirebaseJobScheduler {

    private static final int SCHEDULE_INTERVAL_SECONDS = 10;
    private static final int SYNC_FLEXTIME_SECONDS = 10;
    private static final String NEWS_JOB_TAG = "news_job_tag";

    private static final FirebaseJobScheduler INSTANCE = new FirebaseJobScheduler();

    private boolean initialized = false;

    public static FirebaseJobScheduler getInstance() {
        return INSTANCE;
    }

    synchronized public void scheduleRefresh(Context context) {

        if (initialized) {
            return;
        }

        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

        Job constraintRefreshJob = dispatcher.newJobBuilder()
                .setService(FirebaseJobService.class)
                .setTag(NEWS_JOB_TAG)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(SCHEDULE_INTERVAL_SECONDS,
                        SCHEDULE_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS))
                .setRetryStrategy(RetryStrategy.DEFAULT_LINEAR)
                .setReplaceCurrent(true)
                .build();

        dispatcher.schedule(constraintRefreshJob);

        initialized = true;
    }

}
