package com.example.hansangwon.mutoyou;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by hansangwon on 2016-12-18.
 */

public class FormerClassActivity extends AppCompatActivity {

    InputMethodManager imm;
    AutoCompleteTextView major;
    AutoCompleteTextView classname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formerclass);

        imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

        final RadioGroup semester = (RadioGroup)findViewById(R.id.groupsemester);
        final RadioGroup whatclass = (RadioGroup)findViewById(R.id.groupclass);
        RadioButton majorbutton = (RadioButton)findViewById(R.id.jeongong_rb_formerclass);
        RadioButton kyowangbutton = (RadioButton)findViewById(R.id.kyowang_rb_formerclass);
        final Spinner gradespinner = (Spinner)findViewById(R.id.class_sp_formerclass);
        final Spinner yearspinner = (Spinner)findViewById(R.id.year_sp_formerclass);
        ImageButton searchbutton=(ImageButton)findViewById(R.id.find_formerclass);
        major = (AutoCompleteTextView)findViewById(R.id.major_tv_formerclass);
        classname=(AutoCompleteTextView)findViewById(R.id.kyogwaname_tv_formerclass);

        final boolean[] x = {true};

        majorbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                major.setFocusableInTouchMode(true);
                x[0] =true;
            }
        });

        kyowangbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                major.setFocusable(false);
                x[0] =false;
            }
        });

        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String grade = gradespinner.getSelectedItem().toString();
                String year = yearspinner.getSelectedItem().toString();
                RadioButton rb1 = (RadioButton)findViewById(semester.getCheckedRadioButtonId());
                RadioButton rb2 = (RadioButton)findViewById(whatclass.getCheckedRadioButtonId());

                Intent intent = new Intent(FormerClassActivity.this,FormerListActivity.class);
                if(grade.contains("전체"))    intent.putExtra("grade", "");
                else                           intent.putExtra("grade", grade.substring(0,1));
                intent.putExtra("year", year);
                if(rb1.getText().toString().contains("1")) intent.putExtra("sem", "1");
                else                                            intent.putExtra("sem", "2");
                if(rb2.getText().toString().contains("전공")) intent.putExtra("MKY", "1");
                else                                            intent.putExtra("MKY", "0");
                intent.putExtra("classname", classname.getText().toString());


                if(x[0]) {
                    intent.putExtra("major", major.getText().toString());
                }
                else{
                    intent.putExtra("major", "");
                }
                startActivity(intent);
            }
        });
    }

    public void backtouch(View v)
    {
        imm.hideSoftInputFromWindow(major.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(classname.getWindowToken(), 0);
    }
}
