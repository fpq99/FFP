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
import java.util.ArrayList;
import java.util.Date;

public class NotificationCatch extends NotificationListenerService {

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        //super.onNotificationPosted(sbn);

        String pack = sbn.getPackageName();
        /*String text = sbn.getNotification().tickerText+", "+sbn.getNotification().extras.size();*/

        final TextView tv = MainActivity.textview;

        String str = new Date(sbn.getPostTime()).toString()+"\n";

        Bundle bundle =  sbn.getNotification().extras;
        for(String str1 : bundle.keySet()) {
            if(bundle.get(str1) != null) {
                //Log.i("noti bundle", "Extra KEY: "+str1+" :: "+bundle.get(str1).toString());
                str += "Extra KEY: "+str1+" :: "+bundle.get(str1).toString()+"\n";
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


    private String getText(StatusBarNotification event)
    {
        String result = null;
        try
        {
            Notification notification = event.getNotification();
            RemoteViews views = notification.contentView;
            Class<?> secretClass = views.getClass();

            Field outerFields[] = secretClass.getDeclaredFields();
            for (int i = 0; i < outerFields.length; i++)
            {
                if (!outerFields[i].getName().equals("mActions"))
                    continue;

                outerFields[i].setAccessible(true);

                @SuppressWarnings("unchecked")
                ArrayList<Object> actions = (ArrayList<Object>) outerFields[i].get(views);
                for (Object action : actions)
                {
                    Field innerFields[] = action.getClass().getDeclaredFields();

                    Object value = null;
                    Integer type = null;
                    for (Field field : innerFields)
                    {
                        field.setAccessible(true);
                        if (field.getName().equals("value"))
                            value = field.get(action);
                        else if (field.getName().equals("type"))
                            type = field.getInt(action);
                    }

                    if (type != null && type == 10)
                        result = value.toString();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }
}
