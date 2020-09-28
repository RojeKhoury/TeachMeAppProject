package com.example.teachmeapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teachmeapp.R;
import com.example.teachmeapp.TeacherLessonRow;

import java.util.List;

import static com.example.teachmeapp.Helpers.Globals.comm;

public class AddOrRemoveLessonAdapter extends RecyclerView.Adapter<AddOrRemoveLessonAdapter.ViewHolder> {

    private List<TeacherLessonRow> m_lessons;
    private Context context;

    public AddOrRemoveLessonAdapter(List<TeacherLessonRow> m_lessons, Context context) {
        this.m_lessons = m_lessons;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.teacher_lessons_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final TeacherLessonRow item = m_lessons.get(position);

        holder.textViewClassName.setText(item.getM_subject());
        holder.textViewClasslevel.setText(item.getM_level());
        holder.textViewClassprice.setText(item.getM_price());

        holder.buttonDeleteLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comm.removeCourseFromTeacher(item.getM_subject());

                m_lessons.remove(position);
                notifyDataSetChanged();
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
        public Button buttonDeleteLesson;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewClassName = (TextView) itemView.findViewById(R.id.textViewSubject_addLesson);
            textViewClasslevel = (TextView) itemView.findViewById(R.id.textViewLevel_addLesson);
            textViewClassprice = (TextView) itemView.findViewById(R.id.textViewPrice_addLesson);
            buttonDeleteLesson = (Button) itemView.findViewById(R.id.Button_DeleteLesson_addLesson);
        }

    }
}
