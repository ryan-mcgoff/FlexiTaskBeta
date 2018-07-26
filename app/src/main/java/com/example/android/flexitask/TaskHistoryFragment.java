package com.example.android.flexitask;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.android.flexitask.data.taskContract;
import com.example.android.flexitask.data.taskDBHelper;

/**
 * Created by rymcg on 21/07/2018.
 */

public class TaskHistoryFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int TASKLOADER = 0;
    TaskHistoryAdaptor mTaskCursorAdaptor;
    private taskDBHelper mDbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_task_history, container, false);

        mDbHelper = new taskDBHelper(getActivity());

        // Find the ListView which will be populated with the tasks data
        final ListView timeLineListView = (ListView) rootView.findViewById(R.id.historyListView);

        mTaskCursorAdaptor = new TaskHistoryAdaptor(getActivity(), null);
        timeLineListView.setAdapter(mTaskCursorAdaptor);

        getLoaderManager().initLoader(TASKLOADER, null, this);
        return rootView;
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {

        //Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                taskContract.TaskEntry._ID,
                taskContract.TaskEntry.COLUMN_TASK_TITLE,
                taskContract.TaskEntry.COLUMN_DESCRIPTION,
                taskContract.TaskEntry.COLUMN_TYPE_TASK,
                taskContract.TaskEntry.COLUMN_LAST_COMPLETED,
                taskContract.TaskEntry.COLUMN_DATE,
                taskContract.TaskEntry.COLUMN_TIME,
                taskContract.TaskEntry.COLUMN_HISTORY,
                taskContract.TaskEntry.COLUMN_STATUS,
                taskContract.TaskEntry.COLUMN_RECCURING_PERIOD};

        return new CursorLoader(getActivity(),
                taskContract.TaskEntry.CONTENT_URI,
                projection,
                null,
                null,
                "CAST(" + taskContract.TaskEntry.COLUMN_DATE + " AS DOUBLE)");
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mTaskCursorAdaptor.swapCursor(data);

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

        mTaskCursorAdaptor.swapCursor(null);
    }
}
