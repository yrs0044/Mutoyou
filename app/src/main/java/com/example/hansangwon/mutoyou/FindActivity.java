package com.example.hansangwon.mutoyou;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;

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

public class FindActivity extends BaseActivity {

    ImageButton idcheck;
    ImageButton pwcheck;
    AutoCompleteTextView mail_id;
    AutoCompleteTextView mail_pw;
    AutoCompleteTextView id_pw;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findidpw);
        bindViews();
        setValues();
        setupEvents();

    }

    @Override
    public void setValues() {
        super.setValues();
    }

    @Override
    public void setupEvents() {
        super.setupEvents();
        idcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mail_id.getText().toString().isEmpty())  CreateAlertDialog("E-mail을 입력해주세요.");
                else                                        findidpw("",mail_id.getText().toString(),"0");
            }
        });

        pwcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mail_pw.getText().toString().isEmpty())  CreateAlertDialog("E-mail을 입력해주세요.");
                else{
                    if(id_pw.getText().toString().isEmpty())    CreateAlertDialog("ID를 입력해주세요.");
                    else                        findidpw(id_pw.getText().toString(),mail_pw.getText().toString(),"1");
                }
            }
        });
    }


    private void findidpw(String id, String mail, String choice){
        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(FindActivity.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                try {
                    JSONArray jarray=new JSONArray(s);
                    String inf="";
                    for(int i=0;i<jarray.length();i++){
                        JSONObject jObject = jarray.getJSONObject(i);
                        inf+=jObject.getString("id")+"\n";
                    }
                    AlertDialog.Builder alert= new AlertDialog.Builder(FindActivity.this);
                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alert.setMessage(inf);
                    alert.show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {

                try {
                    String id = (String) params[0];
                    String mail = (String)params[1];
                    String choice = (String)params[2];

                    String link = "http://hansangwon.ipdisk.co.kr:8001/mutoyou/findID.php";
                    String data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
                    data += "&"+URLEncoder.encode("mail", "UTF-8") + "=" + URLEncoder.encode(mail, "UTF-8");
                    data += "&"+URLEncoder.encode("choice", "UTF-8") + "=" + URLEncoder.encode(choice, "UTF-8");

                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write(data);
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    // Read Server Response
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    Log.i("sb=", sb.toString());
                    return sb.toString();
                } catch (Exception e) {
                    return new String("Exception: " + e.getMessage());
                }

            }
        }
        InsertData task = new InsertData();
        task.execute(id, mail, choice);
    }

    public void CreateAlertDialog(String s)
    {
        AlertDialog.Builder alert= new AlertDialog.Builder(FindActivity.this);
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
        idcheck = (ImageButton)findViewById(R.id.checkid_find_id_pw);
        pwcheck = (ImageButton)findViewById(R.id.PW_find_id_pw);
        mail_id = (AutoCompleteTextView)findViewById(R.id.Email1_et_find_id_pw);
        mail_pw = (AutoCompleteTextView)findViewById(R.id.Email2_et_find_id_pw);
        id_pw = (AutoCompleteTextView)findViewById(R.id.ID_et_find_id_pw);
    }


}
