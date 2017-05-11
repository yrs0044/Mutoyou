package com.example.hansangwon.mutoyou.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.hansangwon.mutoyou.R;

/**
 * Created by hansangwon on 2016-12-18.
 */

public class FormerClassActivity extends BaseActivity {

    InputMethodManager imm;
    AutoCompleteTextView major;
    RadioGroup semester;
    RadioGroup whatclass;
    RadioButton majorbutton;
    RadioButton kyowangbutton;
    Spinner gradespinner;
    Spinner yearspinner;
    ImageButton searchbutton;
    RadioButton rb1;
    RadioButton rb2;
    boolean[] x;
    String grade;
    String year;
    AutoCompleteTextView classname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_former_class);
        bindViews();
        setValues();
        setupEvents();

    }

    @Override
    public void setValues() {
        super.setValues();
        rb1 = (RadioButton) findViewById(semester.getCheckedRadioButtonId());
        rb2 = (RadioButton) findViewById(whatclass.getCheckedRadioButtonId());
        x = new boolean[]{true};
        grade = gradespinner.getSelectedItem().toString();
        year = yearspinner.getSelectedItem().toString();
    }

    @Override
    public void setupEvents() {
        super.setupEvents();
        majorbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                major.setFocusableInTouchMode(true);
                x[0] = true;
            }
        });

        kyowangbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                major.setFocusable(false);
                x[0] = false;
            }
        });

        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FormerClassActivity.this, FormerListActivity.class);
                if (grade.contains("전체")) intent.putExtra("GradeData", "");
                else intent.putExtra("GradeData", grade.substring(0, 1));
                intent.putExtra("year", year);
                if (rb1.getText().toString().contains("1")) intent.putExtra("sem", "1");
                else intent.putExtra("sem", "2");
                if (rb2.getText().toString().contains("전공")) intent.putExtra("MKY", "1");
                else intent.putExtra("MKY", "0");
                intent.putExtra("classname", classname.getText().toString());


                if (x[0]) {
                    intent.putExtra("major", major.getText().toString());
                } else {
                    intent.putExtra("major", "");
                }
                startActivity(intent);
            }
        });
    }

    public void backtouch(View v) {
        imm.hideSoftInputFromWindow(major.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(classname.getWindowToken(), 0);
    }

    @Override
    public void bindViews() {
        super.bindViews();
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        semester = (RadioGroup) findViewById(R.id.groupsemester);
        whatclass = (RadioGroup) findViewById(R.id.groupclass);
        majorbutton = (RadioButton) findViewById(R.id.jeongong_rb_formerclass);
        kyowangbutton = (RadioButton) findViewById(R.id.kyowang_rb_formerclass);
        gradespinner = (Spinner) findViewById(R.id.class_sp_formerclass);
        yearspinner = (Spinner) findViewById(R.id.year_sp_formerclass);
        searchbutton = (ImageButton) findViewById(R.id.find_formerclass);
        major = (AutoCompleteTextView) findViewById(R.id.major_tv_formerclass);
        classname = (AutoCompleteTextView) findViewById(R.id.kyogwaname_tv_formerclass);

    }


}
