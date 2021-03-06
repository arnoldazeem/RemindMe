package com.example.arnold.remindme;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

        private static final int ACTIVITY_CREATE=0;
        private static final int ACTIVITY_EDIT=1;

        private RemindersDbAdapter mDbHelper;
        ArrayList<RemindmeItem> data;
        Cursor getHymns = null;

        // Declaring RecyclerView
        RecyclerView mRecyclerView;
        TextView noRemind;
        RecyclerView.Adapter mAdapter;
        RecyclerView.LayoutManager mLayoutManager;
        private CustomRemindMeAdapter adapter;

    /** Called when the activity is first created. */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            mRecyclerView = (RecyclerView) findViewById(R.id.list);

           // noRemind = (TextView) findViewById(R.id.empty);

            mRecyclerView.setHasFixedSize(true);

            mDbHelper = new RemindersDbAdapter(this);


           fillData();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            createReminder();
      //  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
      //  .setAction("Action", null).show();


        }
        });

        }


        private void fillData() {

            mDbHelper.open();

            data = new ArrayList<RemindmeItem>();

            Cursor remindersCursor = mDbHelper.fetchAllReminders();

            remindersCursor.moveToFirst();

            while (!remindersCursor.isAfterLast()) {

                String title = remindersCursor
                        .getString(remindersCursor.getColumnIndex(RemindersDbAdapter.KEY_TITLE));


                String body = (remindersCursor.getString(
                        remindersCursor.getColumnIndexOrThrow(RemindersDbAdapter.KEY_BODY)));

                Long id = (remindersCursor.getLong(
                        remindersCursor.getColumnIndexOrThrow(RemindersDbAdapter.KEY_ROWID)));

                RemindmeItem remind = new RemindmeItem(id,title,body);

                data.add(remind);

                remindersCursor.moveToNext();
            }


            mDbHelper.close();

            adapter = new CustomRemindMeAdapter(MainActivity.this, data);

            mRecyclerView.setAdapter(adapter);

            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);

            mRecyclerView.setItemAnimator(new DefaultItemAnimator());


        }



        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            super.onCreateOptionsMenu(menu);
            MenuInflater mi = getMenuInflater();
            mi.inflate(R.menu.list_menu, menu);
            return true;
        }

   /*     @Override
        public boolean onMenuItemSelected(int featureId, MenuItem item) {
            switch(item.getItemId()) {
                case R.id.menu_insert:
                    createReminder();
                    return true;
                case R.id.menu_settings:
                    Intent i = new Intent(this, TaskPreferences.class);
                    startActivity(i);
                    return true;
            }

            return super.onMenuItemSelected(featureId, item);
        }*/

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v,
                                        ContextMenuInfo menuInfo) {
            super.onCreateContextMenu(menu, v, menuInfo);
            MenuInflater mi = getMenuInflater();
            mi.inflate(R.menu.list_menu_item_longpress, menu);
        }

        @Override
        public boolean onContextItemSelected(MenuItem item) {
            switch(item.getItemId()) {
                case R.id.menu_delete:
                    AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
                    mDbHelper.deleteReminder(info.id);
                    fillData();
                    return true;
            }
            return super.onContextItemSelected(item);
        }

        private void createReminder() {
            Intent i = new Intent(this, ReminderEditActivity.class);
            startActivityForResult(i, ACTIVITY_CREATE);
        }

     /*   @Override
        protected void onListItemClick(ListView l, View v, int position, long id) {
            super.onListItemClick(l, v, position, id);
            Intent i = new Intent(this, ReminderEditActivity.class);
            i.putExtra(RemindersDbAdapter.KEY_ROWID, id);
            startActivityForResult(i, ACTIVITY_EDIT);
        }*/

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
            super.onActivityResult(requestCode, resultCode, intent);
            fillData();
        }
    }