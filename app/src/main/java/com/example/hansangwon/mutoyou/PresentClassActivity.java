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

/**
 * Created by yrs00 on 2016-12-18.
 */

public class PresentClassActivity extends AppCompatActivity {

    Spinner Grade;
    RadioButton Major;
    RadioButton KY;
    //    RadioGroup RG;
    AutoCompleteTextView Major_TV;
    AutoCompleteTextView ClassName;
    ImageButton SearchButton;
    InputMethodManager imm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presentclass);

        final String[] Major_KY = {"전공"};
        final String[] grade = {"전체"};
        final Boolean[] Major_bool = {true};

        Major = (RadioButton) findViewById(R.id.jeongong_rb__presentclass);
        KY = (RadioButton) findViewById(R.id.kyowang_rb__presentclass);
//        RG = (RadioGroup)findViewById(R.id.RG_presentclass);
//        RadioButton RB = (RadioButton)findViewById(RG.getCheckedRadioButtonId());
        Grade = (Spinner) findViewById(R.id.class_sp_presentclass);
        Major_TV = (AutoCompleteTextView) findViewById(R.id.major_tv__presentclass);
        ClassName = (AutoCompleteTextView) findViewById(R.id.kyogwaname_tv__presentclass);
        SearchButton = (ImageButton) findViewById(R.id.find_presentclass);
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);


        Grade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                grade[0] = parent.getItemAtPosition(position).toString();
                Log.i("grade", grade[0]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Major.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Major_TV.setFocusableInTouchMode(true);
                Major_KY[0] = "전공";
                Major_bool[0] = true;
            }
        });

        KY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Major_TV.setFocusable(false);
                Major_KY[0] = "교양";
                Major_bool[0] = false;
            }
        });

        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] Data = new String[1];
                if (Major_bool[0])
                    Data[0] = grade[0] + "," + Major_KY[0] + "," + Major_TV.getText() + "," + ClassName.getText();
                else Data[0] = grade[0] + "," + Major_KY[0] + "," + "," + ClassName.getText();
                Toast.makeText(getApplicationContext(), Data[0], Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PresentClassActivity.this,PresentListActivity.class);
                intent.putExtra("DATA",Data[0]);
                startActivity(intent);
            }
        });

    }

    public void backTouch(View v) {
        imm.hideSoftInputFromWindow(Major_TV.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(ClassName.getWindowToken(), 0);
    }

}
