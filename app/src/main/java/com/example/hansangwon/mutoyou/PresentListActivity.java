package com.example.hansangwon.mutoyou;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hansangwon.mutoyou.Activity.BaseActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by yrs00 on 2016-12-19.
 * 보완점 : null 표기 해제 / 무한대 기호 생각 (정원이 0인경우 div는 비워놓자) / XML확인 /
 * 담기 버튼 구현하기 / 깜빡이는 애니메이션 구현
 */

public class PresentListActivity extends BaseActivity {
    private String myJSON;
    private String TAG_RESULTS = "result";
    private String TAG_MKY = "MKY";
    private String TAG_CREDIT = "credit";
    private String TAG_CLASSNAME = "classname";
    private String TAG_MAX = "max";
    private String TAG_NOW = "now";
    private String TAG_DIV = "div";
    private String TAG_PROF = "profname";
    private String TAG_CLASSROOM = "classroom";
    private String TAG_MAJOR = "major";

    String grade ;
    String mky ;
    String major ;
    String classname;

    JSONArray SelectedSubject = null;
    ArrayList<HashMap<String, String>> SelectedSubjectList;
    ListView list;
    TextView Major_detail;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_present_class);
        bindViews();
        setValues();
        setupEvents();

    }


    @Override
    public void setValues() {
        super.setValues();
        SelectedSubjectList = new ArrayList<>();

        Intent intent = getIntent();
        String s = intent.getStringExtra("DATA");
        grade = "";
        mky = "";
        major = "";
        classname = "";

        String[] array = s.split(",", 4);

        for (int i = 0; i < array.length; i++) {
            Log.i("array........", array[i]);
        }
        if (!array[0].contains("전체")) grade = array[0].substring(0, 1);
        if (array[1].contains("전공")) mky = "1";
        else {mky = "0"; Major_detail.setText("세부영역구분");}
        if (!array[2].isEmpty()) major = array[2];
        if (!array[3].isEmpty()) classname = array[3];

    }

    @Override
    public void setupEvents() {
        super.setupEvents();
        getData(grade, mky, major, classname);
    }


    protected void showList() {

        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            SelectedSubject = jsonObj.getJSONArray(TAG_RESULTS);
            // JSON 배열을 읽어내서 필드들 저장한다.//
            for (int i = 0; i < SelectedSubject.length(); i++) {
                JSONObject c = SelectedSubject.getJSONObject(i);
                String MKY = c.getString(TAG_MKY);
                String credit = c.getString(TAG_CREDIT);
                String classname = c.getString(TAG_CLASSNAME);
                String max = c.getString(TAG_MAX);
                String now = c.getString(TAG_NOW);
                //수강률 계산//
                Double Max = Double.parseDouble(max);
                Double Now = Double.parseDouble(now);
                Double divide = (Now / Max);
                DecimalFormat form = new DecimalFormat("#.##");
                ////
                String div = form.format(divide).toString();
                String prof = c.getString(TAG_PROF);
                String classroom = c.getString(TAG_CLASSROOM);
                if (classroom.contains("null")) classroom = "미정";
                String major = c.getString(TAG_MAJOR);

                HashMap<String, String> subject = new HashMap<String, String>();
                subject.put(TAG_MKY, MKY);
                subject.put(TAG_CREDIT, credit);
                subject.put(TAG_CLASSNAME, classname);
                subject.put(TAG_NOW, now);
                subject.put(TAG_MAX, max);
                subject.put(TAG_DIV, div);
                subject.put(TAG_PROF, prof);
                subject.put(TAG_CLASSROOM, classroom);
                subject.put(TAG_MAJOR,major);
                // ArrayList에 저장
                SelectedSubjectList.add(subject);
            }
            //ListView에 ArrayList 입력
            final ListAdapter adapter = new SimpleAdapter(
                    PresentListActivity.this, SelectedSubjectList, R.layout.present_class_item,
                    new String[]{TAG_MKY, TAG_CREDIT,TAG_MAJOR,TAG_CLASSNAME, TAG_NOW, TAG_MAX, TAG_DIV, TAG_PROF, TAG_CLASSROOM},
                    new int[]{R.id.kyokwa_tv_listview_present_list,
                            R.id.credit_tv_listview_present_list,
                            R.id.major_tv_listview_present_list,
                            R.id.name_tv_listview_present_list,
                            R.id.number_tv_listview_present_list,
                            R.id.limit_tv_listview_present_list,
                            R.id.atrate_tv_listview_present_list,
                            R.id.professor_tv_listview_present_list,
                            R.id.sitetime_tv_listview_present_list
                    }) {
                @Override
                public android.view.View getView(final int position, final View convertView, ViewGroup parent) {
                    View v = super.getView(position, convertView, parent);
                    Button  A = (Button) v.findViewById(R.id.cart_bt_listview_present_list);

                    A.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder alert = new AlertDialog.Builder(PresentListActivity.this);
                            alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    HashMap<String,String> h = SelectedSubjectList.get(position);
                                    Saving(h.get(TAG_MKY),
                                            h.get(TAG_CREDIT),
                                            h.get(TAG_MAJOR),
                                            h.get(TAG_CLASSNAME),
                                            h.get(TAG_NOW),
                                            h.get(TAG_MAX),
                                            h.get(TAG_DIV),
                                            h.get(TAG_PROF),
                                            h.get(TAG_CLASSROOM),
                                            MainActivity.userid);
                                    dialog.dismiss();
                                }
                            });
                            alert.setMessage("항목을 저장하시겠습니까?");
                            alert.show();

                        }
                    });

                    return v;
                }

            };


                list.setAdapter(adapter);
            }catch(JSONException e){
                e.printStackTrace();
            }
        }

    public void getData(final String Grade, final String MKY, final String Major, final String Classname) {
        class GetDataJSON extends AsyncTask<String, Void, String> {
            ProgressDialog loading = new ProgressDialog(PresentListActivity.this);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading.show();
            }

            @Override
            protected String doInBackground(String... params) {
                try {
                    String grade = (String) params[0];
                    String mky = (String) params[1];
                    String major = (String) params[2];
                    String classname = (String) params[3];


                    String link = "http://hansangwon.ipdisk.co.kr:8000/mutoyou/search.php";
                    String data = URLEncoder.encode("GradeData", "UTF-8") + "=" + URLEncoder.encode(grade, "UTF-8");
                    data += "&" + URLEncoder.encode("MKY", "UTF-8") + "=" + URLEncoder.encode(mky, "UTF-8");
                    data += "&" + URLEncoder.encode("major", "UTF-8") + "=" + URLEncoder.encode(major, "UTF-8");
                    data += "&" + URLEncoder.encode("classname", "UTF-8") + "=" + URLEncoder.encode(classname, "UTF-8");

                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write(data);
                    wr.flush();

                    StringBuilder sb = new StringBuilder();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    String json;

                    while ((json = reader.readLine()) != null) {
                        sb.append(json);

                    }

                    return sb.toString().trim();

                } catch (Exception e) {

                    return new String(e.getMessage());
                }


            }

            @Override
            protected void onPostExecute(String result) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG);
                loading.dismiss();
                myJSON = result;

                showList();

            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(Grade, MKY, Major, Classname);
    }
    public void Saving(final String MKY, final String credit, final String major, final String classname, final String max, final String now, final String div, final String prof, final String classroom, final String ID) { // ID추가할것
        class GetDataJSON extends AsyncTask<String, Void, String> {
            ProgressDialog loading = new ProgressDialog(PresentListActivity.this);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading.show();
            }

            @Override
            protected String doInBackground(String... params) {
                try {

                    String mky = (String) params[0];
                    String credit = (String) params[1];
                    String major = (String) params[2];
                    String classname = (String) params[3];
                    String max = (String) params[4];
                    String now = (String) params[5];
                    String div = (String) params[6];
                    String prof = (String) params[7];
                    String classroom = (String) params[8];
                    String ID = (String)params[9];

                    String link = "http://hansangwon.ipdisk.co.kr:8000/mutoyou/saving.php";
                    String data = URLEncoder.encode("ID", "UTF-8") + "=" + URLEncoder.encode(ID, "UTF-8");
                    data += "&" + URLEncoder.encode("MKY", "UTF-8") + "=" + URLEncoder.encode(mky, "UTF-8");
                    data += "&" + URLEncoder.encode("credit", "UTF-8") + "=" + URLEncoder.encode(credit, "UTF-8");
                    data += "&" + URLEncoder.encode("major", "UTF-8") + "=" + URLEncoder.encode(major, "UTF-8");
                    data += "&" + URLEncoder.encode("classname", "UTF-8") + "=" + URLEncoder.encode(classname, "UTF-8");
                    data += "&" + URLEncoder.encode("max", "UTF-8") + "=" + URLEncoder.encode(max, "UTF-8");
                    data += "&" + URLEncoder.encode("now", "UTF-8") + "=" + URLEncoder.encode(now, "UTF-8");
                    data += "&" + URLEncoder.encode("division", "UTF-8") + "=" + URLEncoder.encode(div, "UTF-8");
                    data += "&" + URLEncoder.encode("prof", "UTF-8") + "=" + URLEncoder.encode(prof, "UTF-8");
                    data += "&" + URLEncoder.encode("classroom", "UTF-8") + "=" + URLEncoder.encode(classroom, "UTF-8");

                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write(data);
                    wr.flush();

                    StringBuilder sb = new StringBuilder();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    String json;

                    while ((json = reader.readLine()) != null) {
                        sb.append(json);

                    }

                    return sb.toString().trim();

                } catch (Exception e) {

                    return new String(e.getMessage());
                }


            }

            @Override
            protected void onPostExecute(String result) {
                loading.dismiss();
                if(result.contains("1"))
                CreateAlertDialog2("완료되었습니다.");
                else
                CreateAlertDialog2("이미 선택하신 항목입니다.");
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(MKY, credit, major, classname, max, now, div, prof, classroom, ID);
    }

    public void CreateAlertDialog2(String s) {
        AlertDialog.Builder alert = new AlertDialog.Builder(PresentListActivity.this);
        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.setMessage(s);
        alert.show();
    }

    @Override
    public void bindViews() {
        super.bindViews();
        list = (ListView) findViewById(R.id.listview_present);
        Major_detail = (TextView)findViewById(R.id.changetext_present);
    }

}
