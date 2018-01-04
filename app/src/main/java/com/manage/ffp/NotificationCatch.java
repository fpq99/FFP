package com.manage.ffp;

import android.app.Activity;
import android.app.Notification;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NotificationCatch extends NotificationListenerService {

    private String[][] CopName = {{"주식회사 비케이알", "버거킹"}};

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        //super.onNotificationPosted(sbn);

        String pack = sbn.getPackageName();
        /*String text = sbn.getNotification().tickerText+", "+sbn.getNotification().extras.size();*/

        final TextView tv = MainActivity.textview;

        Date time = new Date(sbn.getPostTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd hh:mm");

        String str = "";

        Bundle bundle =  sbn.getNotification().extras;
        for(String str1 : bundle.keySet()) {
            if(bundle.get(str1) != null) {
                if(str1.equals("android.text")) {
                    String notitext = bundle.get(str1).toString();
                    String[] tmp = notitext.split("[ ]");
                    for(int i = 0; i < tmp.length; i++) {
                        if(tmp[i].contains("카드")) {
                            str += "결제장소: "+tmp[i-1];
                            str += "\n결제시간: "+format.format(time);
                            str += "\n결제금액: "+tmp[i+1];
                            str += "  잔액: "+tmp[tmp.length-1];
                            str += "\n알림전문: "+notitext+"\n";
                            break;
                        }
                    }
                }
                //str += "Extra KEY: "+str1+" :: "+bundle.get(str1).toString()+"\n";
            }
        }
        final String text = str + "\n" + tv.getText().toString();

        ((Activity)MainActivity.context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv.setText(text);
            }
        });

        Log.d("NOTI LOGG", pack+"\n"+str);

    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        //super.onNotificationRemoved(sbn);
    }
}
