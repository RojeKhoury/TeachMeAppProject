package com.example.teachmeapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teachmeapp.Helpers.BookedLesson;
import com.example.teachmeapp.Helpers.Globals;
import com.example.teachmeapp.R;
import com.example.teachmeapp.StudentPendingRequestDetailsPage;
import com.example.teachmeapp.StudentPendingRequestRow;
import com.example.teachmeapp.TeacherLessonRow;
import com.example.teachmeapp.TeacherPendingRequestDetailsPage;

import java.util.List;

import static com.example.teachmeapp.Helpers.Globals.comm;

public class StudentPendingRequestsAdapter extends RecyclerView.Adapter<StudentPendingRequestsAdapter.ViewHolder> {

    private List<StudentPendingRequestRow> m_lessons;
    private Context context;

    public StudentPendingRequestsAdapter(List<StudentPendingRequestRow> lessons, Context context) {
        this.m_lessons = lessons;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.student_pending_request_row, parent, false);
        return new StudentPendingRequestsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final StudentPendingRequestRow item = m_lessons.get(position);

        holder.textViewClassName.setText(item.getM_teacherName());
        holder.textViewClasslevel.setText(item.getM_level());
        holder.textViewClassSubject.setText(item.getM_subject());


        holder.buttonDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                if (comm.isTeacher()) {
                    intent = new Intent(view.getContext(), TeacherPendingRequestDetailsPage.class);
                } else {
                    intent = new Intent(view.getContext(), StudentPendingRequestDetailsPage.class);
                }

                intent.putExtra("lesson", comm.keyBuilder(item.getM_subject(), item.getM_timeStart().toString(), item.getM_teacherName(), item.getM_studentName()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return m_lessons.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewClassName;
        public TextView textViewClasslevel;
        public TextView textViewClassSubject;
        public Button buttonDetails;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewClassName = (TextView) itemView.findViewById(R.id.textViewPendingRequestName);
            textViewClasslevel = (TextView) itemView.findViewById(R.id.textViewPendingRequestLevel);
            textViewClassSubject = (TextView) itemView.findViewById(R.id.textViewPendingRequestSubject);
            buttonDetails = (Button) itemView.findViewById(R.id.buttonPendingRequestDetails);

        }

    }
}
