package com.example.hansangwon.mutoyou;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CalculatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator);

        final Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        final Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        final Spinner spinner3 = (Spinner) findViewById(R.id.spinner3);
        final Spinner spinner4 = (Spinner) findViewById(R.id.spinner4);
        final Spinner spinner5 = (Spinner) findViewById(R.id.spinner5);
        final Spinner spinner6 = (Spinner) findViewById(R.id.spinner6);
        final Spinner spinner7 = (Spinner) findViewById(R.id.spinner7);
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this, R.array.gradelist, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this, R.array.gradelist, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        ArrayAdapter adapter3 = ArrayAdapter.createFromResource(this, R.array.gradelist, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);
        ArrayAdapter adapter4 = ArrayAdapter.createFromResource(this, R.array.gradelist, android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(adapter4);
        ArrayAdapter adapter5 = ArrayAdapter.createFromResource(this, R.array.gradelist, android.R.layout.simple_spinner_item);
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner5.setAdapter(adapter5);
        ArrayAdapter adapter6 = ArrayAdapter.createFromResource(this, R.array.gradelist, android.R.layout.simple_spinner_item);
        adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner6.setAdapter(adapter6);
        ArrayAdapter adapter7 = ArrayAdapter.createFromResource(this, R.array.gradelist, android.R.layout.simple_spinner_item);
        adapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner7.setAdapter(adapter7);

        ImageButton btncal = (ImageButton) findViewById(R.id.btncal);

        btncal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                double wa;
                double ma;
                double nma;
                double nmascore = 0;
                double nmagrade = 0;
                double mascore = 0;
                double magrade = 0;

                String score1 = spinner1.getSelectedItem().toString();
                String score2 = spinner2.getSelectedItem().toString();
                String score3 = spinner3.getSelectedItem().toString();
                String score4 = spinner4.getSelectedItem().toString();
                String score5 = spinner5.getSelectedItem().toString();
                String score6 = spinner6.getSelectedItem().toString();
                String score7 = spinner7.getSelectedItem().toString();

                double dscore1 = (double) grade.gradechange(score1);
                double dscore2 = (double) grade.gradechange(score2);
                double dscore3 = (double) grade.gradechange(score3);
                double dscore4 = (double) grade.gradechange(score4);
                double dscore5 = (double) grade.gradechange(score5);
                double dscore6 = (double) grade.gradechange(score6);
                double dscore7 = (double) grade.gradechange(score7);

                EditText egrade1 = (EditText) findViewById(R.id.grade1);
                EditText egrade2 = (EditText) findViewById(R.id.grade2);
                EditText egrade3 = (EditText) findViewById(R.id.grade3);
                EditText egrade4 = (EditText) findViewById(R.id.grade4);
                EditText egrade5 = (EditText) findViewById(R.id.grade5);
                EditText egrade6 = (EditText) findViewById(R.id.grade6);
                EditText egrade7 = (EditText) findViewById(R.id.grade7);

                String grade1 = (String) egrade1.getText().toString();
                String grade2 = (String) egrade2.getText().toString();
                String grade3 = (String) egrade3.getText().toString();
                String grade4 = (String) egrade4.getText().toString();
                String grade5 = (String) egrade5.getText().toString();
                String grade6 = (String) egrade6.getText().toString();
                String grade7 = (String) egrade7.getText().toString();

                int igrade1;
                int igrade2;
                int igrade3;
                int igrade4;
                int igrade5;
                int igrade6;
                int igrade7;

                if (grade1.getBytes().length <= 0) igrade1 = 0;
                else igrade1 = Integer.parseInt(grade1);
                if (grade2.getBytes().length <= 0) igrade2 = 0;
                else igrade2 = Integer.parseInt(grade2);
                if (grade3.getBytes().length <= 0) igrade3 = 0;
                else igrade3 = Integer.parseInt(grade3);
                if (grade4.getBytes().length <= 0) igrade4 = 0;
                else igrade4 = Integer.parseInt(grade4);
                if (grade5.getBytes().length <= 0) igrade5 = 0;
                else igrade5 = Integer.parseInt(grade5);
                if (grade6.getBytes().length <= 0) igrade6 = 0;
                else igrade6 = Integer.parseInt(grade6);
                if (grade7.getBytes().length <= 0) igrade7 = 0;
                else igrade7 = Integer.parseInt(grade7);


                try {
                    CheckBox major1 = (CheckBox) findViewById(R.id.major1);
                    CheckBox major2 = (CheckBox) findViewById(R.id.major2);
                    CheckBox major3 = (CheckBox) findViewById(R.id.major3);
                    CheckBox major4 = (CheckBox) findViewById(R.id.major4);
                    CheckBox major5 = (CheckBox) findViewById(R.id.major5);
                    CheckBox major6 = (CheckBox) findViewById(R.id.major6);
                    CheckBox major7 = (CheckBox) findViewById(R.id.major7);

                    if (major1.isChecked()) {
                        mascore += (dscore1 * igrade1);
                        magrade += igrade1;
                    } else {
                        nmascore += (dscore1 * igrade1);
                        nmagrade += igrade1;
                    }
                    if (major2.isChecked()) {
                        mascore += (dscore2 * igrade2);
                        magrade += igrade2;
                    } else {
                        nmascore += (dscore2 * igrade2);
                        nmagrade += igrade2;
                    }
                    if (major3.isChecked()) {
                        mascore += (dscore3 * igrade3);
                        magrade += igrade3;
                    } else {
                        nmascore += (dscore3 * igrade3);
                        nmagrade += igrade3;
                    }
                    if (major4.isChecked()) {
                        mascore += (dscore4 * igrade4);
                        magrade += igrade4;
                    } else {
                        nmascore += (dscore4 * igrade4);
                        nmagrade += igrade4;
                    }
                    if (major5.isChecked()) {
                        mascore += (dscore5 * igrade5);
                        magrade += igrade5;
                    } else {
                        nmascore += (dscore5 * igrade5);
                        nmagrade += igrade5;
                    }
                    if (major6.isChecked()) {
                        mascore += (dscore6 * igrade6);
                        magrade += igrade6;
                    } else {
                        nmascore += (dscore6 * igrade6);
                        nmagrade += igrade6;
                    }
                    if (major7.isChecked()) {
                        mascore += (dscore7 * igrade7);
                        magrade += igrade7;
                    } else {
                        nmascore += (dscore7 * igrade7);
                        nmagrade += igrade7;
                    }
                    if (mascore == 0) {
                        ma = 0;
                    } else {
                        ma = Double.parseDouble(String.format("%.2f", (mascore / magrade)));
                    }
                    if (nmascore == 0) {
                        nma = 0;
                    } else {
                        nma = Double.parseDouble(String.format("%.2f", (nmascore / nmagrade)));
                    }
                    if (mascore + nmascore == 0) {
                        wa = 0;
                        Toast.makeText(getApplicationContext(),
                                "적어도 하나의 성적과 학점을 입력하세요.", Toast.LENGTH_SHORT).show();
                    } else {
                        wa = Double.parseDouble(String.format("%.2f", ((mascore + nmascore) / (nmagrade + magrade))));
                    }

                    TextView Whole_average = (TextView) findViewById(R.id.Whole_average);
                    TextView Major_average = (TextView) findViewById(R.id.Major_average);
                    TextView Non_Major_average = (TextView) findViewById(R.id.Non_Major_average);
                    TextView Non_Major_Grades = (TextView) findViewById(R.id.Non_Major_Grades);
                    TextView Major_Grades = (TextView) findViewById(R.id.Major_Grades);

                    Whole_average.setText(String.valueOf(wa));
                    Major_average.setText(String.valueOf(ma));
                    Non_Major_average.setText(String.valueOf(nma));
                    Non_Major_Grades.setText(String.valueOf(nmagrade));
                    Major_Grades.setText(String.valueOf(magrade));

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),
                            "학점을 적어도 하나 입력하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
