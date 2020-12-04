package com.EducatedGuess.teachmeapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.EducatedGuess.teachmeapp.Helpers.Globals;
import com.EducatedGuess.teachmeapp.R;
import com.EducatedGuess.teachmeapp.StudentPendingRequestDetailsPage;
import com.EducatedGuess.teachmeapp.TeacherPendingRequestDetailsPage;
import com.EducatedGuess.teachmeapp.model.Request;

import java.util.List;

import static com.EducatedGuess.teachmeapp.Helpers.Globals.comm;

public class StudentPendingRequestsAdapter extends RecyclerView.Adapter<StudentPendingRequestsAdapter.ViewHolder> {

    private List<Request> m_lessons;
    private Context context;

    public StudentPendingRequestsAdapter(List<Request> lessons, Context context) {
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
        final Request item = m_lessons.get(position);

        if (comm.isTeacher()){
            holder.textViewClassName.setText(item.getM_studentName());
        } else {
            holder.textViewClassName.setText(item.getM_teacherName());
        }

        holder.textViewClasslevel.setText(item.getM_level());
        String subject = item.getM_subject().substring(0,item.getM_subject().indexOf("_"));
        holder.textViewClassSubject.setText(subject);

        if (item.getPending()){
            holder.status.setText("Pending");
            holder.status.setTextColor(Color.YELLOW);
        } else if (item.getRejecting()) {
            holder.status.setText("Rejected");
            holder.status.setTextColor(Color.RED);
        } else {
            holder.status.setText("Accepted");
            holder.status.setTextColor(Color.GREEN);
        }


        holder.buttonDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                if (comm.isTeacher()) {
                    intent = new Intent(view.getContext(), TeacherPendingRequestDetailsPage.class);
                } else {
                    intent = new Intent(view.getContext(), StudentPendingRequestDetailsPage.class);
                }

                Globals.currentRequest = item;
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
        public TextView status;
        public Button buttonDetails;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewClassName = (TextView) itemView.findViewById(R.id.textViewPendingRequestName);
            textViewClasslevel = (TextView) itemView.findViewById(R.id.textViewPendingRequestLevel);
            textViewClassSubject = (TextView) itemView.findViewById(R.id.textViewPendingRequestSubject);
            status = itemView.findViewById(R.id.textViewPendingRequestStatus);
            buttonDetails = (Button) itemView.findViewById(R.id.buttonPendingRequestDetails);

        }

    }
}
