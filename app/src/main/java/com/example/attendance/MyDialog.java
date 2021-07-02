package com.example.attendance;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextSwitcher;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MyDialog extends DialogFragment {

    public static final String CLASS_ADD_DIALOG = "addClass";
    public static final String CLASS_UPDATE_DIALOG = "updateClass";
    public static final String STUDENT_ADD_DIALOG = "addStudent";
    public static final String STUDENT_UPDATE_DIALOG = "updateStudent";


    private OnClickListener listener;
    private int roll;
    private String name;

    public MyDialog(int roll, String name) {

        this.roll = roll;
        this.name = name;
    }

    public MyDialog() {

    }

    public interface OnClickListener {

        void onClick(String text1, String text2);
    }

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        Dialog dialog = null;

        if (getTag().equals(CLASS_ADD_DIALOG)) dialog = getAddClassDialog();

        if (getTag().equals(STUDENT_ADD_DIALOG)) dialog = getAddStudentDialog();

        if (getTag().equals(CLASS_UPDATE_DIALOG)) dialog = getUpdateClassDialog();

        if (getTag().equals(STUDENT_UPDATE_DIALOG)) dialog = getUpdateStudentDialog();


        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        return dialog;
    }

    private Dialog getUpdateStudentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog, null);
        builder.setView(view);

        TextView title = view.findViewById(R.id.title_dialog);
        title.setText("Update Student");

        TextInputLayout add_student01 = view.findViewById(R.id.edit01);
        add_student01.setHint("Roll");
        TextInputLayout add_student02 = view.findViewById(R.id.edit02);
        add_student02.setHint("Name");

        TextInputEditText roll_edit = view.findViewById(R.id.edit_01);
        roll_edit.setInputType(InputType.TYPE_CLASS_NUMBER);
        TextInputEditText name_edit = view.findViewById(R.id.edit_02);
        roll_edit.setText(roll+"");
        name_edit.setText(name);
        add_student01.setEnabled(false);
        roll_edit.setEnabled(false);

        Button can_btn = view.findViewById(R.id.btn_cancel);
        Button add_btn = view.findViewById(R.id.btn_add);
        add_btn.setText("Update");

        can_btn.setOnClickListener(v -> dismiss());
        add_btn.setOnClickListener(v -> {
            String roll = roll_edit.getText().toString();
            String name = name_edit.getText().toString();
            listener.onClick(roll, name);
            dismiss();
        });

        return builder.create();
    }

    private Dialog getUpdateClassDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog, null);
        builder.setView(view);

        TextView title = view.findViewById(R.id.title_dialog);
        title.setText("Update Class");

        TextInputLayout edit02 = view.findViewById(R.id.edit02);
        edit02.setHint("Subject");
        TextInputLayout edit01 = view.findViewById(R.id.edit01);
        edit01.setHint("Department & Semester");

        TextInputEditText dep_edit = view.findViewById(R.id.edit_01);
        TextInputEditText sub_edit = view.findViewById(R.id.edit_02);

        Button can_btn = view.findViewById(R.id.btn_cancel);
        Button add_btn = view.findViewById(R.id.btn_add);
        add_btn.setText("UPDATE");

        can_btn.setOnClickListener(v -> dismiss());
        add_btn.setOnClickListener(v -> {
            String department = dep_edit.getText().toString();
            String subject = sub_edit.getText().toString();

            if (department.isEmpty()) {
                dep_edit.setError("Empty");
                dep_edit.requestFocus();
            } else if (subject.isEmpty()) {
                sub_edit.setError("Empty");
                sub_edit.requestFocus();

            } else {
                listener.onClick(department, subject);
                dismiss();
            }
        });

        return builder.create();

    }

    private Dialog getAddStudentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog, null);
        builder.setView(view);

        TextView title = view.findViewById(R.id.title_dialog);
        title.setText("Add New Student");

        TextInputLayout add_student01 = view.findViewById(R.id.edit01);
        add_student01.setHint("Roll");
        TextInputLayout add_student02 = view.findViewById(R.id.edit02);
        add_student02.setHint("Name");

        TextInputEditText roll_edit = view.findViewById(R.id.edit_01);
        roll_edit.setInputType(InputType.TYPE_CLASS_NUMBER);
        roll_edit.setText("1");
        TextInputEditText name_edit = view.findViewById(R.id.edit_02);

        Button can_btn = view.findViewById(R.id.btn_cancel);
        Button add_btn = view.findViewById(R.id.btn_add);

        can_btn.setOnClickListener(v -> dismiss());
        add_btn.setOnClickListener(v -> {
            String roll = roll_edit.getText().toString();
            String name = name_edit.getText().toString();

            if (roll.isEmpty()) {
                roll_edit.setError("Empty");
                roll_edit.requestFocus();
            } else if (name.isEmpty()) {
                name_edit.setError("Empty");
                name_edit.requestFocus();

            } else {
                try {
                    roll_edit.setText(String.valueOf(Integer.parseInt(roll) + 1));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                name_edit.setText("");
                listener.onClick(roll, name);

            }
        });

        return builder.create();
    }

    private Dialog getAddClassDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog, null);
        builder.setView(view);

        TextView title = view.findViewById(R.id.title_dialog);
        title.setText("Add New Class");

        TextInputLayout edit02 = view.findViewById(R.id.edit02);
        edit02.setHint("Subject");
        TextInputLayout edit01 = view.findViewById(R.id.edit01);
        edit01.setHint("Department & Semester");

        TextInputEditText dep_edit = view.findViewById(R.id.edit_01);
        TextInputEditText sub_edit = view.findViewById(R.id.edit_02);

        Button can_btn = view.findViewById(R.id.btn_cancel);
        Button add_btn = view.findViewById(R.id.btn_add);

        can_btn.setOnClickListener(v -> dismiss());
        add_btn.setOnClickListener(v -> {
            String department = dep_edit.getText().toString();
            String subject = sub_edit.getText().toString();

            if (department.isEmpty()) {
                dep_edit.setError("Empty");
                dep_edit.requestFocus();
            } else if (subject.isEmpty()) {
                sub_edit.setError("Empty");
                sub_edit.requestFocus();

            } else {
                listener.onClick(department, subject);
                dismiss();
            }
        });

        return builder.create();
    }
}
