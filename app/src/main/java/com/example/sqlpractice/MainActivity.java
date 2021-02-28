package com.example.sqlpractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.sqlpractice.adapter.ContactAdapter;
import com.example.sqlpractice.data.ContactData;
import com.example.sqlpractice.for_sql.DBManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, DialogInterface.OnDismissListener, AdapterView.OnItemSelectedListener {
    // for data managing (connect sqlite db)
    private DBManager dbManager;

    private FloatingActionButton addBtn;
    private RecyclerView dataView;
    private Spinner spinner;

    private ArrayList<ContactData> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** initialize variables related to db manager **/
        dbManager = new DBManager(this);

        /** initialize views **/
        addBtn = (FloatingActionButton)findViewById(R.id.add_btn);
        dataView = (RecyclerView)findViewById(R.id.data_view);
        spinner = (Spinner)findViewById(R.id.spinner);

        dataView.setLayoutManager(new LinearLayoutManager(this));

        spinner.setOnItemSelectedListener(this);
        addBtn.setOnClickListener(this);

        // set default list
        setOrderList(0);
    }

    /**
     * set sorting list by seleted spinner item
     *
     * @param position : sorting standard
     */
    private void setOrderList(int position) {
        switch (position) {
            case 0:
                dataList = dbManager.select("name");
                break;
            case 1:
                dataList = dbManager.select("age");
                break;
            case 2:
                dataList = dbManager.select("nickname");
                break;
        }

        dataView.setAdapter(new ContactAdapter(this, dataList));
    }

    public void dataChanged() {
        setOrderList(spinner.getSelectedItemPosition());
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        setOrderList(position);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        dataChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_btn:
                /** set dialog for creating new data **/
                ContactDialog dialog = new ContactDialog(this, null);
                dialog.setOnDismissListener(this);
                dialog.show();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}