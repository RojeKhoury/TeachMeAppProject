package com.example.teachmeapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teachmeapp.Helpers.Globals;
import com.example.teachmeapp.R;
import com.example.teachmeapp.RequestLessons;
import com.example.teachmeapp.SearchResultsRow;
import com.example.teachmeapp.TeacherLessonRow;

import java.util.List;

import static com.example.teachmeapp.Helpers.Globals.comm;

public class teacherProfileLessonsAdapter extends RecyclerView.Adapter<teacherProfileLessonsAdapter.ViewHolder> {

    private List<TeacherLessonRow> m_lessons;
    private Context context;

    public teacherProfileLessonsAdapter(List<TeacherLessonRow> m_lessons, Context context) {
        this.m_lessons = m_lessons;
        this.context = context;
    }

    @NonNull
    @Override
    public teacherProfileLessonsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.activity_teacher_profile_page_offered_lessons, parent, false);
        return new teacherProfileLessonsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull teacherProfileLessonsAdapter.ViewHolder holder, int position) {
        final TeacherLessonRow item = m_lessons.get(position);

        holder.textViewClassName.setText(item.getM_subject());
        holder.textViewClasslevel.setText(item.getM_level());
        holder.textViewClassprice.setText(item.getM_price());

        if (comm.isTeacher()){
            holder.request.setVisibility(View.INVISIBLE);
        }

        holder.request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Globals.getLessonCurrentObjTeacherProfile = item;
                Globals.getLessonBoolean = false;
                Intent intent = new Intent(view.getContext(), RequestLessons.class);
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return m_lessons.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewClassName;
        public TextView textViewClasslevel;
        public TextView textViewClassprice;
        public Button request;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewClassName = (TextView) itemView.findViewById(R.id.textView_Subject_ProfilePage);
            textViewClasslevel = (TextView) itemView.findViewById(R.id.textViewElementary);
            textViewClassprice = (TextView) itemView.findViewById(R.id.textView_Price_ProfilePage);
            request = itemView.findViewById(R.id.request_btn);
        }

    }
}
