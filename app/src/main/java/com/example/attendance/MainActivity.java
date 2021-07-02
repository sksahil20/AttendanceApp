package com.example.attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    ClassAdapter classAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ClassItem> classItems = new ArrayList<>();
    Toolbar toolbar;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        dbHelper = new DbHelper(this);

        floatingActionButton = findViewById(R.id.fab_main);
        floatingActionButton.setOnClickListener(V -> showDialog());

        loadData();


        recyclerView = findViewById(R.id.att_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        classAdapter = new ClassAdapter(this, classItems);
        recyclerView.setAdapter(classAdapter);
        classAdapter.setOnItemClickListener(position -> gotoItemActivity(position));


        setToolbar();

    }

    private void loadData() {
        Cursor cursor = dbHelper.getClassTable();

       classItems.clear();
        while (cursor.moveToNext()){
            long id = cursor.getLong(cursor.getColumnIndex(DbHelper.C_ID));
            String department = cursor.getString(cursor.getColumnIndex(DbHelper.CLASS_NAME_KEY));
            String subject = cursor.getString(cursor.getColumnIndex(DbHelper.SUBJECT_NAME_KEY));

            classItems.add(new ClassItem(id,department,subject));
        }
        cursor.close();
    }

    private void setToolbar() {

        toolbar = findViewById(R.id.toolbar);

        TextView title = toolbar.findViewById(R.id.tool_title);
        TextView subTitle = toolbar.findViewById(R.id.tool_sub_title);
        TextView samiTitle = toolbar.findViewById(R.id.tool_sami_title);
        ImageButton back = toolbar.findViewById(R.id.tool_back);
        ImageButton save = toolbar.findViewById(R.id.tool_save);

        title.setText("Attendance");
        subTitle.setVisibility(View.GONE);
        samiTitle.setVisibility(View.GONE);
        save.setVisibility(View.GONE);
        back.setOnClickListener(v -> onBackPressed());

    }

    private void gotoItemActivity(int position) {
        Intent intent = new Intent(this,StudentActivity.class);
        intent.putExtra("departmentName",classItems.get(position).getDepartment());
        intent.putExtra("subjectName",classItems.get(position).getSubject());
        intent.putExtra("position",position);
        intent.putExtra("cid",classItems.get(position).getCid());
        startActivity(intent);
    }

    private void showDialog() {
        MyDialog dialog = new MyDialog();
        dialog.show(getSupportFragmentManager(),MyDialog.CLASS_ADD_DIALOG);
        dialog.setListener((department,subject)->addClass(department,subject));

    }

    private void addClass(String department, String subject) {
        long cid = dbHelper.addClass(department,subject);
        ClassItem classItem = new ClassItem(cid,department,subject);
        classItems.add(classItem);
        classAdapter.notifyDataSetChanged();

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case 0:
                showUpdateDialog(item.getGroupId());
            case 1:
                deleteClass(item.getGroupId());
        }
        return super.onContextItemSelected(item);
    }

    private void showUpdateDialog(int position) {
        MyDialog updateDialog = new MyDialog();
        updateDialog.show(getSupportFragmentManager(),MyDialog.CLASS_UPDATE_DIALOG);
        updateDialog.setListener((department,subject)->updateClass(position,department,subject));
    }

    private void updateClass(int position, String department, String subject) {
        dbHelper.updateClass(classItems.get(position).getCid(),department,subject);
        classItems.get(position).setDepartment(department);
        classItems.get(position).setSubject(subject);
        classAdapter.notifyItemChanged(position);

    }

    private void deleteClass(int position) {
        dbHelper.deleteClass(classItems.get(position).getCid());
        classItems.remove(position);
        classAdapter.notifyItemRemoved(position);
    }

}