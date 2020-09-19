package com.example.teachmeapp.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teachmeapp.ProfilePageOfTeacherForStudent;
import com.example.teachmeapp.R;
import com.example.teachmeapp.ScheduleExpandingButton;
import com.example.teachmeapp.StudentPendingRequestDetailsPage;
import com.example.teachmeapp.TeacherPendingRequestDetailsPage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.teachmeapp.Helpers.Globals.HISTORY_OF_LESSONS_VIEW;
import static com.example.teachmeapp.Helpers.Globals.LESSONS_FOR_TEACHER_VIEW;
import static com.example.teachmeapp.Helpers.Globals.PROFILE_PAGE_OF_SPECIFIC_TEACHER;
import static com.example.teachmeapp.Helpers.Globals.SEARCH_FOR_TEACHER_VIEW;
import static com.example.teachmeapp.Helpers.Globals.SEARCH_RESULT_FOR_SCHDULE;
import static com.example.teachmeapp.Helpers.Globals.STUDENT_PENDING_REQUESTS_VIEW;
import static com.example.teachmeapp.Helpers.Globals.TEACHER_PENDING_REQUESTS_VIEW;

public class AdapterCardViewList extends RecyclerView.Adapter<AdapterCardViewList.ViewHolder> {
    ArrayList<String> data1, data2, data3, data4,UID;
    ArrayList<Uri> images;
    ArrayList<Double> rating;

    Context context;
    int recyclerViewName;
    public AdapterCardViewList(int RecyclerViewName, Context ct, ArrayList<String> s1, ArrayList<String> s2, ArrayList<String> s3, ArrayList<String> s4,
                               ArrayList<Uri> i1, ArrayList<Double> r1,  ArrayList<String> UIDS) {
        recyclerViewName = RecyclerViewName;
        context = ct;
        data1 = s1;
        data2 = s2;
        data3 = s3;
        data4 = s4;
        images = i1;
        rating = r1;
        UID = UIDS;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = null;

        switch (recyclerViewName) {
            case SEARCH_RESULT_FOR_SCHDULE | TEACHER_PENDING_REQUESTS_VIEW:
                view = layoutInflater.inflate(R.layout.schedule_row_layout, parent, false);
                break;
            case SEARCH_FOR_TEACHER_VIEW:
                view = layoutInflater.inflate(R.layout.search_results_row, parent, false);
                break;
            case LESSONS_FOR_TEACHER_VIEW:
                view = layoutInflater.inflate(R.layout.teacher_lessons_row, parent, false);
                break;
            case HISTORY_OF_LESSONS_VIEW:
                view = layoutInflater.inflate(R.layout.history_row_layout, parent, false);
                break;
            case STUDENT_PENDING_REQUESTS_VIEW:
                view = layoutInflater.inflate(R.layout.student_pending_request_row, parent, false);
                break;
            case PROFILE_PAGE_OF_SPECIFIC_TEACHER:
                view = layoutInflater.inflate(R.layout.profile_page_for_specific_teacher_row, parent, false);
                break;
            default:
                break;
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        switch (recyclerViewName) {
            case SEARCH_RESULT_FOR_SCHDULE:
                holder.textView1.setText(data1.get(position));
                holder.textView2.setText(data2.get(position));
                holder.textView3.setText(data3.get(position));

                holder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, ScheduleExpandingButton.class);
                        intent.putExtra("data1", data1.get(position));
                        intent.putExtra("data2", data2.get(position));
                        intent.putExtra("data3", data3.get(position));
                        context.startActivity(intent);
                    }
                });
                break;

            case SEARCH_FOR_TEACHER_VIEW:
                holder.textView1.setText(data1.get(position));
                holder.textView2.setText(data2.get(position));
                holder.textView3.setText(data3.get(position));

                Picasso.get().load((Uri) images.get(position)).into(holder.image);

                holder.ratingBar.setMax(5);
                holder.ratingBar.setStepSize((float) 0.1);
                if (rating.get(position) == null) {
                    holder.ratingBar.setRating(0);
                } else {
                    holder.ratingBar.setRating(Float.parseFloat(String.valueOf(rating.get(position))));
                }
                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, ProfilePageOfTeacherForStudent.class);
                        intent.putExtra("UID", UID.get(position));
                        context.startActivity(intent);
                    }
                });
                break;

            case LESSONS_FOR_TEACHER_VIEW:
                holder.textView1.setText(data1.get(position));
                holder.textView2.setText(data2.get(position));
                holder.textView3.setText(data3.get(position));

                holder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new AlertDialog.Builder(view.getContext())
                                .setTitle("Delete Lesson?")
                                .setMessage("Are you sure you want to delete the lesson?")
                                .setNegativeButton("Back", null)
                                .setPositiveButton("Yes, Delete Lesson", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        String Subject = data1.get(position);
                                        String Price = data2.get(position);
                                        String Level = data3.get(position);
                                        //TODO Delete lesson from database use these to identify ur lesson in the database and delete
                                    }
                                }).create().show();
                    }
                });
                break;
            case HISTORY_OF_LESSONS_VIEW:
                holder.textView1.setText(data1.get(position));
                holder.textView2.setText(data2.get(position));
                holder.textView3.setText(data3.get(position));

                holder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String Subject = data1.get(position);
                        String Price = data2.get(position);
                        String Level = data3.get(position);
                        //TODO Delete History from database use these to identify ur History in the database and delete (may remove)
                    }
                });
                break;
            case TEACHER_PENDING_REQUESTS_VIEW:
                holder.textView1.setText(data1.get(position));
                holder.textView2.setText(data2.get(position));
                holder.textView3.setText(data3.get(position));

                holder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, TeacherPendingRequestDetailsPage.class);
                        intent.putExtra("Subject", data1.get(position));
                        intent.putExtra("Price", data2.get(position));
                        intent.putExtra("Level", data3.get(position));
                        intent.putExtra("UID", UID.get(position));
                        context.startActivity(intent);
                    }
                });
                break;
            case STUDENT_PENDING_REQUESTS_VIEW:
                holder.textView1.setText(data1.get(position));
                holder.textView2.setText(data2.get(position));
                holder.textView3.setText(data3.get(position));
                holder.textView4.setText(data4.get(position));
                holder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, StudentPendingRequestDetailsPage.class);
                        intent.putExtra("Subject", data1.get(position));
                        intent.putExtra("Price", data2.get(position));
                        intent.putExtra("Level", data3.get(position));
                        intent.putExtra("Status", data4.get(position));
                        context.startActivity(intent);
                    }
                });
                break;
            case PROFILE_PAGE_OF_SPECIFIC_TEACHER:
                holder.textView1.setText(data1.get(position));
                holder.textView2.setText(data2.get(position));
                holder.textView3.setText(data3.get(position));
                break;
            default:

                break;
        }


    }

    @Override
    public int getItemCount() {
        return data1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView1, textView2, textView3, textView4;
        Button button;
        ImageView image;
        RatingBar ratingBar;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            switch (recyclerViewName) {
                case SEARCH_RESULT_FOR_SCHDULE | TEACHER_PENDING_REQUESTS_VIEW:
                    textView1 = itemView.findViewById(R.id.textViewScheduleName);
                    textView2 = itemView.findViewById(R.id.textViewScheduleSubject);
                    textView3 = itemView.findViewById(R.id.textViewScheduleTime);
                    button = itemView.findViewById(R.id.textViewScheduleButton);
                    break;
                case SEARCH_FOR_TEACHER_VIEW:
                    textView1 = itemView.findViewById(R.id.SearchView_TeacherResult_TextView_TeacherName);
                    textView2 = itemView.findViewById(R.id.SearchView_TeacherResult_TextView_TeacherCity);
                    textView3 = itemView.findViewById(R.id.SearchView_TeacherResult_TextView_TeacherPrice);
                    image = itemView.findViewById(R.id.SearchView_TeacherResult_ImageView_TeacherImage);
                    ratingBar = itemView.findViewById(R.id.SearchView_TeacherResult_RatingBar);

                    cardView = itemView.findViewById(R.id.SearchResultsCardView);
                    break;
                case LESSONS_FOR_TEACHER_VIEW:
                    textView1 = itemView.findViewById(R.id.textViewSubject_addLesson);
                    textView2 = itemView.findViewById(R.id.textViewPrice_addLesson);
                    textView3 = itemView.findViewById(R.id.textViewLevel_addLesson);
                    button = itemView.findViewById(R.id.Button_DeleteLesson_addLesson);
                    break;
                case HISTORY_OF_LESSONS_VIEW:
                    textView1 = itemView.findViewById(R.id.textViewHistoryName);
                    textView2 = itemView.findViewById(R.id.textViewHistorySubject);
                    textView3 = itemView.findViewById(R.id.textViewHistoryTime);
                    button = itemView.findViewById(R.id.PointsAndDeleteHistoryButton);
                    break;
                case STUDENT_PENDING_REQUESTS_VIEW:
                    textView1 = itemView.findViewById(R.id.textViewPendingRequestName);
                    textView2 = itemView.findViewById(R.id.textViewPendingRequestSubject);
                    textView3 = itemView.findViewById(R.id.textViewPendingRequestLevel);
                    textView4 = itemView.findViewById(R.id.textViewPendingRequestStatus);
                    button = itemView.findViewById(R.id.textViewPendingRequestDetails);
                    break;
                case PROFILE_PAGE_OF_SPECIFIC_TEACHER:
                    textView1 = itemView.findViewById(R.id.textView_Subject_ProfilePage);
                    textView2 = itemView.findViewById(R.id.textView_Price_ProfilePage);
                    textView3 = itemView.findViewById(R.id.textView_Level_ProfilePage);

                    break;

                default:

                    break;
            }
        }
    }
}