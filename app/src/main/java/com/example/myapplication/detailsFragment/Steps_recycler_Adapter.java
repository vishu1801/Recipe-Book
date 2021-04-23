package com.example.myapplication.detailsFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.AllResponse.Recipe_Step_Response.Recipe_steps;
import com.example.myapplication.R;

import java.util.ArrayList;

public class Steps_recycler_Adapter extends RecyclerView.Adapter<Steps_recycler_Adapter.viewHolder> {

    Context context;
    ArrayList<Recipe_steps> data;

    public Steps_recycler_Adapter(ArrayList<Recipe_steps> data, Context context){
        this.context=context;
        this.data=data;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.steps_recyclerview_item,parent,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.step_number.setText(data.get(position).getSteps().get(position).getNumber());
        holder.step_theory.setText(data.get(position).getSteps().get(position).getStep());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView step_number,step_theory;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            step_number = itemView.findViewById(R.id.step_number_textview);
            step_theory = itemView.findViewById(R.id.step_thory_textview);
        }
    }
}
