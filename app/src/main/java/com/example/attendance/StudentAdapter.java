package com.example.attendance;

import android.content.Context;
import android.graphics.Color;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private OnItemClickListener onItemClickListener;


    public interface OnItemClickListener {
        void onClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public StudentAdapter(Context context, ArrayList<StudentItem> studentItems) {
        this.studentItems = studentItems;
        this.context = context;
    }

    ArrayList<StudentItem> studentItems;
    Context context;

    public static class StudentViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        TextView roll;
        TextView name;
        TextView status;
        MaterialCardView cardView;

        public StudentViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {

            super(itemView);

            roll = itemView.findViewById(R.id.Roll_tv);
            name = itemView.findViewById(R.id.Name_tv);
            status = itemView.findViewById(R.id.Status_tv);
            cardView = itemView.findViewById(R.id.student_cardView);
            itemView.setOnClickListener(v -> onItemClickListener.onClick(getAdapterPosition()));
            itemView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(getAdapterPosition(),0,0,"EDIT");
            menu.add(getAdapterPosition(),1,0,"DELETE");
        }
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item, parent, false);
        return new StudentViewHolder(itemView, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {

        holder.roll.setText(studentItems.get(position).getRoll()+"");
        holder.name.setText(studentItems.get(position).getName());
        holder.status.setText(studentItems.get(position).getStatus());
        holder.cardView.setCardBackgroundColor(getColor(position));

    }

    private int getColor(int position) {
        String status = studentItems.get(position).getStatus();
        if (status !=null && status.equals("P")) {
            return Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context, R.color.green)));
        }
        else if (status !=null && status.equals("A")) {
            return Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context, R.color.Red)));
        }
        else {
            return Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context, R.color.normal)));
        }
    }

    @Override
    public int getItemCount() {
        return studentItems.size();
    }
}
