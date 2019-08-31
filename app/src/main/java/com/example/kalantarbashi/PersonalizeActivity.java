package com.example.kalantarbashi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PersonalizeActivity extends AppCompatActivity {
    EditText username;
    RadioGroup userPic;
    boolean isEditMode = false;
    RadioButton radioUserPic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalize);

        Button saveButton = (Button)findViewById(R.id.saveButton);
        username = (EditText)findViewById(R.id.PrUsername);
        userPic= (RadioGroup)findViewById(R.id.RGuserPic);

        final SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        if(sharedPreferences.getString("username",null) != null){
            isEditMode = true;
            username.setText(sharedPreferences.getString("username",null));
        }
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if(validate()) {
                    editor.clear();
                    editor.putString("username", username.getText().toString());
                    editor.putString("userPic", radioUserPic.getText().toString());
                    editor.apply();
                    if(isEditMode){
                        startActivity(new Intent(PersonalizeActivity.this, LandPageActivity.class));
                    }else {
                        startActivity(new Intent(PersonalizeActivity.this, InformationActivity.class));
                    }
                }
            }
        });
    }

    private boolean validate(){
        String usernameText = username.getText().toString();
        if(usernameText.length()<3 || usernameText.length()>40){
            Toast.makeText(PersonalizeActivity.this,"طول اسمت مناسب نیست",Toast.LENGTH_LONG).show();
            return false;
        }else{
            int selectedID = userPic.getCheckedRadioButtonId();
            radioUserPic = (RadioButton)findViewById(selectedID);
            if(radioUserPic == null){
                Toast.makeText(PersonalizeActivity.this,"عکستو انتخاب کن کابوی",Toast.LENGTH_LONG).show();
                return false;
            }else {
                return true;
            }
        }
    }
}
