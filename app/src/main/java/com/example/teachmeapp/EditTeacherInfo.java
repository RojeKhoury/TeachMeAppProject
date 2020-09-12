package com.example.teachmeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.teachmeapp.Helpers.Globals;

import static com.example.teachmeapp.Helpers.Globals.comm;

import static com.example.teachmeapp.Helpers.Globals.comm;

public class EditTeacherInfo extends AppCompatActivity {

    private Button m_editName;
    private Button m_editBio;
    private Button m_editPhone;
    private Button m_addLessons;
    String popUpFName ="";
    String popUpLName ="";
    String popUpBio ="";
    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_teacher_info);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        m_editName.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                final EditText fName = new EditText(EditTeacherInfo.this);
                final EditText lName = new EditText(EditTeacherInfo.this);
                awesomeValidation.addValidation(EditTeacherInfo.this, fName.getId(), "[a-zA-Z\\s]+",R.string.fnameerr);
                awesomeValidation.addValidation(EditTeacherInfo.this, lName.getId(), "[a-zA-Z\\s]+",R.string.fnameerr);
                AlertDialog.Builder builder= new AlertDialog.Builder(EditTeacherInfo.this);
                builder.setTitle(Globals.NAME_ALERT_TITLE);
                builder.setMessage(Globals.NAME_ALERT_TEXT);
                builder.setView(fName);
                builder.setView(lName);

                builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (awesomeValidation.validate())
                        {
                            popUpFName = fName.getText().toString();
                            popUpFName = lName.getText().toString();
                            comm.changeTeacherName(popUpFName);
                            Toast.makeText(EditTeacherInfo.this, "fuck this shit im out", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                builder.show();
            }
        });
        //--------------------------<EDIT BIO>
        m_editBio.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                final EditText BioEdit = new EditText(EditTeacherInfo.this);

                AlertDialog.Builder builder= new AlertDialog.Builder(EditTeacherInfo.this);
                builder.setTitle(Globals.BIO_ALERT_TITLE);
                builder.setMessage(Globals.BIO_ALERT_TEXT);
                builder.setView(BioEdit);

                builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        popUpBio = BioEdit.getText().toString();
                        comm.changeTeacherName(popUpBio);
                        Toast.makeText(EditTeacherInfo.this, "fuck this shit im out", Toast.LENGTH_LONG).show();
                    }
                });
                builder.show();
            }
        });

    }

}