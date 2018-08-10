package com.example.android.flexitask;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.example.android.flexitask.data.taskContract;
import com.example.android.flexitask.data.taskDBHelper;

import java.util.Calendar;

/**
 * Created by rymcg on 8/08/2018.
 */

public class AlertReceiver extends BroadcastReceiver {

    private taskDBHelper mDbHelper;


    @Override
    public void onReceive(Context context, Intent intent) {

        //called when alarm is fired, do this....
        //channel 1 ignored on lower API <26

        mDbHelper = new taskDBHelper(context);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        Log.e("HEEE: ", "Inside onReceive method");

        //long today = Calendar.getInstance().getTimeInMillis();
        String notificationMessage = "hey";

        //Creates a raw SQL statment to retrieve the recurring number and due date for the selected task
        Cursor cursorc = db.rawQuery("SELECT * FROM " + taskContract.TaskEntry.TABLE_NAME +
                " WHERE " + taskContract.TaskEntry.COLUMN_TYPE_TASK
                + " = " + taskContract.TaskEntry.TYPE_FLEXI, null);
        while (cursorc.moveToNext()) {
            Log.e("alarm: ", "Inside getting stuff");
            int titleColumnIndex = cursorc.getColumnIndex(taskContract.TaskEntry.COLUMN_TASK_TITLE);
            String taskTitle = cursorc.getString(titleColumnIndex);
            notificationMessage += " \n" +taskTitle;

        }
        NotificationHelper mNotificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = mNotificationHelper.getChannel("s",notificationMessage);
        mNotificationHelper.getNotificationManager().notify(1,nb.build());

        long c = Calendar.getInstance().getTimeInMillis();
        c+= 15000;

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,1,intent,0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,c,pendingIntent);
    }
}
