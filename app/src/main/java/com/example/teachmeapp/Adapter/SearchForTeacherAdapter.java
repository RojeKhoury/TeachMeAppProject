package com.example.teachmeapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teachmeapp.R;
import com.example.teachmeapp.SearchResultsRow;

import java.util.List;

public class SearchForTeacherAdapter extends RecyclerView.Adapter<SearchForTeacherAdapter.ViewHolder> {

    private List<SearchResultsRow> m_teachers;
    private Context context;

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
    public void onBindViewHolder(@NonNull SearchForTeacherAdapter.ViewHolder holder, int position) {

        SearchResultsRow item = m_teachers.get(position);

        holder.textViewTeacherName.setText(item.getM_teacherName() + " " + item.getM_surname());
        holder.textViewPrice.setText(item.getM_price().toString());
        holder.teacherRating.setRating(Float.parseFloat(item.getM_rating().toString()));
        holder.textViewCity.setText(item.getM_teacherCity());
        holder.textViewSubject.setText(item.getM_subject());
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTeacherName = (TextView) itemView.findViewById(R.id.SearchView_TeacherResult_TextView_TeacherName);
            textViewCity = (TextView) itemView.findViewById(R.id.SearchView_TeacherResult_TextView_TeacherCity);
            textViewPrice = (TextView) itemView.findViewById(R.id.SearchView_TeacherResult_TextView_TeacherPrice);
            teacherRating = (RatingBar) itemView.findViewById(R.id.SearchView_TeacherResult_RatingBar);
            textViewSubject = (TextView) itemView.findViewById(R.id.searchViewSubjectSearch);
        }
    }
}
