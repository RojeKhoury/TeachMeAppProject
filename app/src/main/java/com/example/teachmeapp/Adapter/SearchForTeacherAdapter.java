package com.example.teachmeapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teachmeapp.Helpers.Globals;
import com.example.teachmeapp.ProfilePageOfTeacherForStudent;
import com.example.teachmeapp.R;
import com.example.teachmeapp.RequestLessons;
import com.example.teachmeapp.SearchResultsRow;

import java.util.List;

import static com.example.teachmeapp.Helpers.Globals.comm;

public class SearchForTeacherAdapter extends RecyclerView.Adapter<SearchForTeacherAdapter.ViewHolder> {

    private List<SearchResultsRow> m_teachers;
    private Context context;
    private int pos;

    public SearchForTeacherAdapter(List<SearchResultsRow> m_teachers, Context context) {
        this.m_teachers = m_teachers;
        this.context = context;
    }

    @NonNull
    @Override
    public SearchForTeacherAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.cardview_search_results_row, parent, false);
        return new SearchForTeacherAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchForTeacherAdapter.ViewHolder holder, final int position) {

        SearchResultsRow item = m_teachers.get(position);

        holder.textViewTeacherName.setText(item.getM_teacherName() + " " + item.getM_surname());
        holder.textViewTeacherName.setPaintFlags(holder.textViewTeacherName.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        holder.textViewPrice.setText(item.getM_price().toString());
        holder.teacherRating.setRating(Float.parseFloat(item.getM_rating().toString()));
        holder.textViewCity.setText(item.getM_teacherCity());
        holder.textViewSubject.setText(item.getM_subject());

        holder.textViewTeacherName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SearchResultsRow item = m_teachers.get(position);
                Intent intent = new Intent(view.getContext(), ProfilePageOfTeacherForStudent.class);
                comm.getViewedUserData(item.getM_teacherUID(), true, view.getContext(), intent);

            }
        });

        holder.getLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchResultsRow item = m_teachers.get(position);
                Globals.getLessonCurrentObj = item;
                Globals.getLessonBoolean = true;
                Intent intent = new Intent(view.getContext(), RequestLessons.class);
                comm.getViewedUserData(item.getM_teacherUID(), true, view.getContext(), intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return m_teachers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewTeacherName;
        public TextView textViewCity;
        public TextView textViewPrice;
        public TextView textViewSubject;
        public RatingBar teacherRating;
        public Button getLesson;


        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            textViewTeacherName = (TextView) itemView.findViewById(R.id.SearchView_TeacherResult_TextView_TeacherName);
            textViewCity = (TextView) itemView.findViewById(R.id.SearchView_TeacherResult_TextView_TeacherCity);
            textViewPrice = (TextView) itemView.findViewById(R.id.SearchView_TeacherResult_TextView_TeacherPrice);
            teacherRating = (RatingBar) itemView.findViewById(R.id.SearchView_TeacherResult_RatingBar);
            textViewSubject = (TextView) itemView.findViewById(R.id.searchViewSubjectSearch);
            getLesson = itemView.findViewById(R.id.request_btn);

            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SearchResultsRow item = m_teachers.get(pos);
                    Intent intent = new Intent(view.getContext(), ProfilePageOfTeacherForStudent.class);
                    comm.getViewedUserData(item.getM_teacherUID(), true, view.getContext(), intent);
                  //  view.getContext().startActivity(intent);
                }
            });*/
        }
    }
}
