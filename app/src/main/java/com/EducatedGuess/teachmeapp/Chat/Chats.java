package com.EducatedGuess.teachmeapp.Chat;

import androidx.annotation.NonNull;

import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.WindowManager;

import com.EducatedGuess.teachmeapp.HamburgerMenu;
import com.EducatedGuess.teachmeapp.Helpers.Globals;
import com.EducatedGuess.teachmeapp.R;
import com.EducatedGuess.teachmeapp.model.TalkedWithModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.EducatedGuess.teachmeapp.Helpers.Globals.comm;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.widget.TextView;

public class Chats extends HamburgerMenu {

    private List<TalkedWithModel> m_TalkedWithUidsList = new ArrayList<>();
    private AdapterTalkedWith m_Adapter;
    private FirebaseAuth m_Auth;
    private RecyclerView m_RecyclerView;
    private boolean m_mITeacher;
    private TextView emptyListTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
        emptyListTextView= findViewById(R.id.emptyView);
        m_Auth = FirebaseAuth.getInstance();
        m_mITeacher = comm.isTeacher();
        initTalkedWithList();
    }

    private void initTalkedWithList() {

        m_TalkedWithUidsList.clear();
        m_Adapter = new AdapterTalkedWith(m_TalkedWithUidsList, m_mITeacher, comm.getUid() , Chats.this);
        m_RecyclerView = findViewById(R.id.recycler_Chats);
        m_RecyclerView.setHasFixedSize(true);
        m_RecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        m_RecyclerView.setItemAnimator(new DefaultItemAnimator());
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        m_RecyclerView.setAdapter(m_Adapter);
        getAllOffMyChatsUsingValueListeners();
    }

    private void getAllOffMyChatsUsingValueListeners() {
        DatabaseReference userTalkedWithRef = FirebaseDatabase.getInstance().getReference(Globals.TALKEDWITH).child(comm.getUid());
        userTalkedWithRef.orderByChild("timeStamp").limitToLast(100).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                updateTalkedWithList(dataSnapshot);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void updateTalkedWithList(DataSnapshot snapshot) {

        m_TalkedWithUidsList.clear();
        for(DataSnapshot dataSnapshot : snapshot.getChildren())
        {
            TalkedWithModel talkedWithModel = dataSnapshot.getValue(TalkedWithModel.class);
            m_TalkedWithUidsList.add(talkedWithModel);
        }

        if(m_TalkedWithUidsList.isEmpty())
        {
            emptyListTextView.setVisibility(View.VISIBLE);
        }
        else
        {
            emptyListTextView.setVisibility(View.GONE);
        }

        Collections.reverse(m_TalkedWithUidsList);

        m_RecyclerView.getAdapter().notifyDataSetChanged();
    }
}
