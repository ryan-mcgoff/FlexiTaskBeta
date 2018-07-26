package com.example.android.flexitask;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.flexitask.data.taskContract;

/**
 * Created by rymcg on 26/07/2018.
 */

public class TaskHistoryAdaptor extends CursorAdapter {

    public TaskHistoryAdaptor(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        //find views for list item
        TextView titleTextView = view.findViewById(R.id.titleListView);
        TextView descriptionTextView = view.findViewById(R.id.descriptionListView);
        TextView dateView = view.findViewById(R.id.testView);

        //find the column values
        int titleColumnIndex = cursor.getColumnIndex(taskContract.TaskEntry.COLUMN_TASK_TITLE);
        int descriptionColumnIndex = cursor.getColumnIndex(taskContract.TaskEntry.COLUMN_DESCRIPTION);
        int lastCompletedIndex = cursor.getColumnIndex(taskContract.TaskEntry.COLUMN_LAST_COMPLETED);

        //Read the values for current Tasks
        String titleString = cursor.getString(titleColumnIndex);
        String descriptionString = cursor.getString(descriptionColumnIndex);
        //int taskType = cursor.getInt(taskTypeColumnIndex);
        long lastCompletedLong = cursor.getLong(lastCompletedIndex);

        dateView.setText(String.valueOf(lastCompletedIndex));
        titleTextView.setText(titleString);
        descriptionTextView.setText(descriptionString);

    }
}
