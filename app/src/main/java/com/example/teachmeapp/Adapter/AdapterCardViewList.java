package com.example.teachmeapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teachmeapp.R;
import com.example.teachmeapp.ScheduleExpandingButton;
import com.example.teachmeapp.SearchForTeacher;

import static com.example.teachmeapp.Helpers.Globals.SEARCH_FOR_TEACHER_VIEW;
import static com.example.teachmeapp.Helpers.Globals.SEARCH_RESULT;

public class AdapterCardViewList extends RecyclerView.Adapter<AdapterCardViewList.ViewHolder> {
    String data1[], data2[], data3[];
    Button button;
    Context context;
    int recyclerViewName;
    int images[];
    double rating[];


    public AdapterCardViewList(int RecyclerViewName, Context ct, String s1[], String s2[], String s3[], Button b1, int i1[], double r1[]) {
        recyclerViewName = RecyclerViewName;
        context = ct;
        data1 = s1;
        data2 = s2;
        data3 = s3;
        button = b1;
        images = i1;
        rating = r1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = null;

        switch (recyclerViewName) {

            case SEARCH_RESULT:
                view = layoutInflater.inflate(R.layout.schedule_row_layout, parent, false);
                break;
            case SEARCH_FOR_TEACHER_VIEW:
                view = layoutInflater.inflate(R.layout.search_results_row, parent, false);
                break;
            default:
                break;

        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        switch (recyclerViewName) {
            case SEARCH_RESULT:
                holder.textView1.setText(data1[position]);
                holder.textView2.setText(data2[position]);
                holder.textView3.setText(data3[position]);

                holder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, ScheduleExpandingButton.class);
                        intent.putExtra("data1", data1[position]);
                        intent.putExtra("data2", data2[position]);
                        intent.putExtra("data3", data3[position]);
                        context.startActivity(intent);
                    }
                });
                break;
            case SEARCH_FOR_TEACHER_VIEW:
                holder.textView1.setText(data1[position]);
                holder.textView2.setText(data2[position]);
                holder.textView3.setText(data3[position]);

                holder.image.setImageResource(images[position]);

                holder.ratingBar.setRating(Float.parseFloat(String.valueOf(rating[position])));
                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //TODO send student to teacher profile
                        Toast.makeText(context, data1[position], Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            default:

                break;
        }


    }

    @Override
    public int getItemCount() {
        return data1.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView1, textView2, textView3;
        Button button;
        ImageView image;
        RatingBar ratingBar;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            switch (recyclerViewName) {
                case SEARCH_RESULT:
                    textView1 = itemView.findViewById(R.id.textViewName);
                    textView2 = itemView.findViewById(R.id.textViewSubject);
                    textView3 = itemView.findViewById(R.id.textViewTime);
                    button = itemView.findViewById(R.id.moreInfoButton);
                    break;
                case SEARCH_FOR_TEACHER_VIEW:
                    textView1 = itemView.findViewById(R.id.SearchView_TeacherResult_TextView_TeacherName);
                    textView2 = itemView.findViewById(R.id.SearchView_TeacherResult_TextView_TeacherCity);
                    textView3 = itemView.findViewById(R.id.SearchView_TeacherResult_TextView_TeacherPrice);
                    image = itemView.findViewById(R.id.SearchView_TeacherResult_ImageView_TeacherImage);
                    ratingBar = itemView.findViewById(R.id.SearchView_TeacherResult_RatingBar);
                    ratingBar.setMax(5);
                    ratingBar.setStepSize((float) 0.1);
                    cardView = itemView.findViewById(R.id.SearchResultsCardView);
                    break;
                default:

                    break;
            }
        }
    }
}