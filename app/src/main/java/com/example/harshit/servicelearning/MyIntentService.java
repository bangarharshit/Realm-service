package com.example.harshit.servicelearning;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import io.realm.Realm;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {

    public static final String BROADCAST_ACTION =
            "com.example.android.threadsample.BROADCAST";
    public static final String EXTENDED_DATA_STATUS =
            "com.example.android.threadsample.STATUS";

    public static final String BROADCAST_ACTION_1 =
            "com.example.android.threadsample.BROADCAST1";
    public static final String EXTENDED_DATA_STATUS_1 =
            "com.example.android.threadsample.STATUS1";
    /**
     * A constructor is required, and must call the super IntentService(String)
     * constructor with a name for the worker thread.
     */
    public MyIntentService() {
        super("HelloIntentService");
    }

    /**
     * The IntentService calls this method from the default worker thread with
     * the intent that started the service. When this method returns, IntentService
     * stops the service, as appropriate.
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        // Normally we would do some work here, like download a file.
        // For our sample, we just sleep for 5 seconds.
       String action = intent.getAction();
        if("Action1".equals(action)) {
            dummyActivity1();
        } else if("Action2".equals(action)) {
            dummyActivity2();
        }
    }

    public void dummyActivity1() {
        long endTime = System.currentTimeMillis() + 5*1000;
        Log.i("Harshit", "DummyActivity1");
        while (System.currentTimeMillis() < endTime) {
            synchronized (this) {
                try {
                    wait(endTime - System.currentTimeMillis());
                } catch (Exception e) {
                }

            }
        }
        Realm realm = Realm.getInstance(getBaseContext());
        realm.beginTransaction();
        TestModel testModel = realm.createObject(TestModel.class);
        testModel.setTestId("arbit");
        realm.commitTransaction();
        realm.close();
        Intent localIntent =
                new Intent(BROADCAST_ACTION)
                        // Puts the status into the Intent
                        .putExtra(EXTENDED_DATA_STATUS, "dummyActivity1");
        // Broadcasts the Intent to receivers in this app.
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);

    }

    public void dummyActivity2() {
        long endTime = System.currentTimeMillis() + 5 * 1000;
        Log.i("Harshit", "DummyActivity2");
        while (System.currentTimeMillis() < endTime) {
            synchronized (this) {
                try {
                    wait(endTime - System.currentTimeMillis());
                } catch (Exception e) {
                }

            }
        }
        Realm realm = Realm.getInstance(getBaseContext());
        realm.beginTransaction();
        TestModel testModel = realm.createObject(TestModel.class);
        testModel.setTestId("arbit");
        realm.commitTransaction();
        realm.close();
        Intent localIntent =
                new Intent(BROADCAST_ACTION_1)
                        // Puts the status into the Intent
                        .putExtra(EXTENDED_DATA_STATUS, "dummyActivity2");
        // Broadcasts the Intent to receivers in this app.
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Harshit service starting", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("Harshit", "oncreate");
    }

    @Override
    public void onDestroy() {
        Log.i("Harshit", "ondestroy");
        super.onDestroy();
    }

}
