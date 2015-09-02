package com.example.harshit.servicelearning;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import io.realm.Realm;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IntentFilter mStatusIntentFilter = new IntentFilter(
                MyIntentService.BROADCAST_ACTION);

        // Adds a data filter for the HTTP scheme
        ResponseReceiver mDownloadStateReceiver =
                new ResponseReceiver();
        // Registers the DownloadStateReceiver and its intent filters
        LocalBroadcastManager.getInstance(this).registerReceiver(
                mDownloadStateReceiver,
                mStatusIntentFilter);
        IntentFilter statusIntentFilter = new IntentFilter(MyIntentService.BROADCAST_ACTION_1);
        // Registers the receiver with the new filter
        LocalBroadcastManager.getInstance(this).registerReceiver(
                mDownloadStateReceiver,
                statusIntentFilter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, MyService.class);
            intent.setAction("Action2");
            startService(intent);
            Intent intent2 = new Intent(this, MyService.class);
            intent2.setAction("Action1");
            startService(intent2);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class ResponseReceiver extends BroadcastReceiver {

        public ResponseReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO: This method is called when the BroadcastReceiver is receiving
            // an Intent broadcast.
            Log.i("Harshit", intent.getAction() + intent.getStringExtra(MyIntentService.EXTENDED_DATA_STATUS));
            Realm realm = Realm.getInstance(context);
            Log.i("Harshit realm size", realm.allObjects(TestModel.class).size()+"");
            realm.close();
        }
    }
}
