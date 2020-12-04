package com.EducatedGuess.teachmeapp.Chat;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.EducatedGuess.teachmeapp.Helpers.Globals;
import com.EducatedGuess.teachmeapp.R;
import com.EducatedGuess.teachmeapp.model.TalkedWithModel;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.List;

import static com.EducatedGuess.teachmeapp.Helpers.Globals.COLLECTION_STUDENT;
import static com.EducatedGuess.teachmeapp.Helpers.Globals.COLLECTION_TEACHER;
import static com.EducatedGuess.teachmeapp.Helpers.Globals.comm;

public class AdapterTalkedWith extends RecyclerView.Adapter<AdapterTalkedWith.myViewHolder> {

    private List<TalkedWithModel> m_TalkedWithUidsList;
    private boolean m_IsTeacher;
    private String m_MyUid;

    public AdapterTalkedWith(List<TalkedWithModel> i_TalkedWithUidsList, boolean i_IsImBabySitter, String i_MyUid, Context i_context) {
        m_TalkedWithUidsList = i_TalkedWithUidsList;
        m_IsTeacher = i_IsImBabySitter;
        m_MyUid = i_MyUid;
        Log.d("T17adp", i_TalkedWithUidsList.toString());
    }

    @Override
    public AdapterTalkedWith.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AdapterTalkedWith.myViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_talked_with_user, parent, false));
    }


    public void onBindViewHolder(final AdapterTalkedWith.myViewHolder holder, int i) {
        String TalkedWithUserUID = m_TalkedWithUidsList.get(i).getUid();
        holder.setM_UserUID(TalkedWithUserUID);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        if (comm.isTeacher()){

            final DocumentReference docRef = db.collection(COLLECTION_STUDENT).document(TalkedWithUserUID);
            docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                    holder.m_UserFullName.setText(snapshot.get(Globals.FIELD_NAME).toString()+" "+snapshot.get(Globals.FIELD_SURNAME).toString());
                }
            });

        } else {

            final DocumentReference docRef = db.collection(COLLECTION_TEACHER).document(TalkedWithUserUID);
            docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                    holder.m_UserFullName.setText(snapshot.get(Globals.FIELD_NAME).toString()+" "+snapshot.get(Globals.FIELD_SURNAME).toString());
                }
            });

        }

//        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference(Globals.PROFILE_PIC_STORAGE_PATH).child(TalkedWithUserUID + ".jpg");
//        mStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                Log.e("Test6", "Success upload" + uri);
//                Picasso.get().load(uri).into(holder.m_TalkedWithPicture);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.e("Test6", "unSuccess upload  : " + e.getMessage());
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return m_TalkedWithUidsList.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        private CardView m_UserCardView;
        private String m_UserUID;
        private TextView m_UserFullName;

        public void setM_UserUID(String m_UserUID) {
            this.m_UserUID = m_UserUID;
        }

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            m_UserFullName = (TextView) itemView.findViewById(R.id.TextView_UserFullName);
            m_UserCardView = (CardView) itemView.findViewById(R.id.CardView_TalkedWithUser);
            m_UserCardView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, ChatWindow.class);
                    intent.putExtra(Globals.IS_TEACHER, m_IsTeacher);
                    if (m_IsTeacher) {
                        intent.putExtra(Globals.STUDENTS, m_UserUID);
                        intent.putExtra(Globals.TEACHERS, m_MyUid);
                    } else {
                        intent.putExtra(Globals.STUDENTS, m_MyUid);
                        intent.putExtra(Globals.TEACHERS, m_UserUID);
                    }
                    Globals.currentTalkedWithUID = m_UserUID;
                    context.startActivity(intent);
                }
            });
        }
    }
}
