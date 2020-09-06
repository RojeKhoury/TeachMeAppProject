
package com.example.teachmeapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teachmeapp.R;
import com.example.teachmeapp.ScheduleExpandingButton;

public class AdapterCardViewList extends RecyclerView.Adapter<AdapterCardViewList.ViewHolder> {
    String data1[], data2[], data3[];
    Button button;
    Context context;

    public AdapterCardViewList(Context ct, String s1[], String s2[], String s3[],Button b1) {
        context = ct;
        data1 = s1;
        data2 = s2;
        data3 = s3;
        button = b1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.schedule_row_layout, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.textView1.setText(data1[position]);
        holder.textView2.setText(data2[position]);
        holder.textView3.setText(data3[position]);

        holder.button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ScheduleExpandingButton.class);
                intent.putExtra("data1",data1[position]);
                intent.putExtra("data2",data2[position]);
                intent.putExtra("data3",data3[position]);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data1.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView1, textView2, textView3;
        Button button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.textViewName);
            textView2 = itemView.findViewById(R.id.textViewSubject);
            textView3 = itemView.findViewById(R.id.textViewTime);
            button = itemView.findViewById(R.id.moreInfoButton);
        }
    }
}