package com.example.android.flexitask;

import android.database.Cursor;
import android.graphics.Color;
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
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.android.flexitask.data.taskContract;
import com.example.android.flexitask.data.taskDBHelper;

/**
 * Created by rymcg on 21/07/2018.
 */

public class TaskHistoryFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int TASKLOADER = 0;
    TaskHistoryAdaptor mTaskCursorAdaptor;
    private taskDBHelper mDbHelper;

    private Button timeBtnSelected;
    private Button timeBtnDeselect;
    private Button taskBtnSelected;
    private Button taskBtnDeselect;

    private View rootView;

    private long todayDate;
    private long dateFilter;
    private int taskFilter;
    

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);

         rootView = inflater.inflate(R.layout.fragment_task_history, container, false);

        timeBtnSelected = rootView.findViewById(R.id.btnAnyTime);
        timeBtnSelected.setBackgroundResource(R.drawable.oval_shape_selected);

        taskBtnSelected = rootView.findViewById(R.id.btnAllTasks);
        taskBtnSelected.setBackgroundResource(R.drawable.oval_shape_selected);


        mDbHelper = new taskDBHelper(getActivity());

        // Find the ListView which will be populated with the tasks data
        final ListView timeLineListView = (ListView) rootView.findViewById(R.id.historyListView);

        mTaskCursorAdaptor = new TaskHistoryAdaptor(getActivity(), null);
        timeLineListView.setAdapter(mTaskCursorAdaptor);

        getLoaderManager().initLoader(TASKLOADER, null, this);

        RadioGroup timeButtonGroup = rootView.findViewById(R.id.btnTimeGroup);
        RadioGroup taskButtonGroup = rootView.findViewById(R.id.btnTaskGroup);

        timeButtonGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.btnAnyTime:

                        timeBtnSelectDeselect(checkedId);
                        ///do stuff
                        break;
                    case R.id.btnLastDay:
                        timeBtnSelectDeselect(checkedId);
                        break;
                    case R.id.btnLastWeek:
                        timeBtnSelectDeselect(checkedId);
                        break;
                    case R.id.btnLastMonth:
                        timeBtnSelectDeselect(checkedId);
                        break;
                    case R.id.btnLastYear:
                        timeBtnSelectDeselect(checkedId);
                        break;

                }

            }
        });

        taskButtonGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.btnAllTasks:
                        taskBtnSelectDeselect(checkedId);
                        ///do stuff
                        break;
                    case R.id.btnFixedTasks:
                        taskBtnSelectDeselect(checkedId);
                        break;
                    case R.id.btnFlexiTasks:
                        taskBtnSelectDeselect(checkedId);
                        break;

                }

            }
        });


        return rootView;
    }

    private void timeBtnSelectDeselect(int checkID){
        timeBtnDeselect = timeBtnSelected;
        timeBtnSelected = rootView.findViewById(checkID);
        timeBtnDeselect.setBackgroundResource(R.drawable.oval_shape);
        timeBtnSelected.setBackgroundResource(R.drawable.oval_shape_selected);
    }
    private void taskBtnSelectDeselect(int checkID){
        taskBtnDeselect = taskBtnSelected;
        taskBtnSelected = rootView.findViewById(checkID);
        taskBtnDeselect.setBackgroundResource(R.drawable.oval_shape);
        taskBtnSelected.setBackgroundResource(R.drawable.oval_shape_selected);
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

        String WHERE = "status='0'";

        return new CursorLoader(getActivity(),
                taskContract.TaskEntry.CONTENT_URI,
                projection,
                WHERE,
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
