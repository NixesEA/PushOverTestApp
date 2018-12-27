package com.nixesea.pushovertestapp.database;

import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.nixesea.pushovertestapp.R;

import java.util.Collections;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    RecyclerView rv;
    TextView tv;

    private HistoryDao hd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        tv = findViewById(R.id.emptyDataBaseTV);
        rv = findViewById(R.id.historyRecyclerView);
        LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(lm);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppDatabase db = Room.databaseBuilder(this, AppDatabase.class,"database")
                .allowMainThreadQueries()
                .build();
        hd = db.historyDao();

        readDataBase();
    }

    public void readDataBase(){
        List<HistoryUnit> historyUnitList = hd.getAll();

        if (!historyUnitList.isEmpty()){
            Collections.reverse(historyUnitList);
            update(historyUnitList);
        }else{
            showDefaultUI();
        }
    }

    public void update(List<HistoryUnit> historyUnitList) {
        mAdapter adapter = new mAdapter(getApplicationContext(), historyUnitList);
        rv.setAdapter(adapter);

        rv.setVisibility(View.VISIBLE);
        tv.setVisibility(View.GONE);
    }

    public void showDefaultUI() {
        rv.setVisibility(View.GONE);
        tv.setVisibility(View.VISIBLE);
    }
}
