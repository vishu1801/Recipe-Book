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

    private ArrayList<Recipe_steps> data;
    Context mcontext;

    public Steps_recycler_Adapter(ArrayList<Recipe_steps> data, Context context) {
        this.data = data;
        this.mcontext = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.steps_recyclerview_item, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder holder, final int position) {
        holder.step_theory.setText(data.get(0).getSteps().get(position).getStep());
        holder.step_number.setText(data.get(0).getSteps().get(position).getNumber().toString());
    }

    @Override
    public int getItemCount() {
        if(data.size() > 0) {
            return (data.get(0).getSteps().size());
        }
        else {
            return 0;
        }
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView step_number, step_theory;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            step_number = itemView.findViewById(R.id.step_number_textview);
            step_theory = itemView.findViewById(R.id.step_thory_textview);
        }
    }
}
