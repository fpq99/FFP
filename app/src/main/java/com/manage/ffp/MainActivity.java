package com.manage.ffp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.manage.ffp.SQLite.DBHelper;

public class MainActivity extends AppCompatActivity {

    static TextView textview;
    static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(!isContainedInNotificationListeners(getApplicationContext())) {
            startActivityForResult(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"), 1);
        }

        textview = (TextView)findViewById(R.id.test);
        context = MainActivity.this;

        //노티 생성
        final NotificationManager notificationManager= (NotificationManager)MainActivity.this.getSystemService(MainActivity.this.NOTIFICATION_SERVICE);
        final Intent intent = new Intent(MainActivity.this.getApplicationContext(),MainActivity.class);

        final Notification.Builder builder = new Notification.Builder(getApplicationContext());
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                PendingIntent pendnoti = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setSmallIcon(R.drawable.ic_launcher_background).setTicker("HIMAN").setWhen(System.currentTimeMillis())
                        .setNumber(1).setContentTitle("KB스타알림").setContentText("01/03 11:55 114001-**-***394 주식회사웰스 체크카드출금 4,000 잔액103,538")
                        .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE).setContentIntent(pendnoti).setAutoCancel(true).setOngoing(true);
                notificationManager.notify(1, builder.build());
            }
        });

        DBHelper helper = new DBHelper(getApplicationContext(), "test.db", null, 1);
        //helper.createTable("test");
        //Log.d("LOGGGGG", helper.getResult("test"));
    }

    public static boolean isContainedInNotificationListeners(Context $context)
    {
        String enabledListeners = Settings.Secure.getString($context.getContentResolver(), "enabled_notification_listeners");
        return !TextUtils.isEmpty(enabledListeners) && enabledListeners.contains($context.getPackageName());
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
