package com.example.hansangwon.mutoyou;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.hansangwon.mutoyou.Activity.BaseActivity;

/**
 * Created by yrs00 on 2016-12-18.
 */

public class PresentClassActivity extends BaseActivity{

    Spinner Grade;
    RadioButton Major;
    RadioButton KY;
    AutoCompleteTextView Major_TV;
    AutoCompleteTextView ClassName;
    ImageButton SearchButton;
    InputMethodManager imm;
    String Major_KY;
    String grade ;
    Boolean Major_bool ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_present_class);
        bindViews();
        setValues();
        setupEvents();
    }

    @Override
    public void setValues() {
        super.setValues();
        Major_KY = "전공";
        grade = "전체";
        Major_bool = true;

    }

    @Override
    public void setupEvents() {
        super.setupEvents();
        Grade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                grade = parent.getItemAtPosition(position).toString();
                Log.i("GradeData", grade);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Major.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Major_TV.setFocusableInTouchMode(true);
                Major_KY = "전공";
                Major_bool= true;
            }
        });

        KY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Major_TV.setFocusable(false);
                Major_KY = "교양";
                Major_bool = false;
            }
        });

        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Data ;
                if (Major_bool)
                    Data = grade+ "," + Major_KY + "," + Major_TV.getText() + "," + ClassName.getText();
                else Data = grade+ "," + Major_KY + "," + "," + ClassName.getText();
                Toast.makeText(getApplicationContext(), Data, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PresentClassActivity.this,PresentListActivity.class);
                intent.putExtra("DATA",Data);
                startActivity(intent);
            }
        });

    }


    public void backTouch(View v) {
        imm.hideSoftInputFromWindow(Major_TV.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(ClassName.getWindowToken(), 0);
    }

    @Override
    public void bindViews() {
        super.bindViews();
        Major = (RadioButton) findViewById(R.id.jeongong_rb__presentclass);
        KY = (RadioButton) findViewById(R.id.kyowang_rb__presentclass);
        Grade = (Spinner) findViewById(R.id.class_sp_presentclass);
        Major_TV = (AutoCompleteTextView) findViewById(R.id.major_tv__presentclass);
        ClassName = (AutoCompleteTextView) findViewById(R.id.kyogwaname_tv__presentclass);
        SearchButton = (ImageButton) findViewById(R.id.find_presentclass);
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
    }
}
