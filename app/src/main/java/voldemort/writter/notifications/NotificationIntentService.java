package voldemort.writter.notifications;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;

public class NotificationIntentService {

    public NotificationIntentService() {
        super("NotificationIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();
        if (action.equals(NotificationUtils.ACTION_IGNORE_NOTIFICATION)) {
            clearAllNotifications();
        }
    }

    private void clearAllNotifications() {
        NotificationManager notificationManager = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }
}
