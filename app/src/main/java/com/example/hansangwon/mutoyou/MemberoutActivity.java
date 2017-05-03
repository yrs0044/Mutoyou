package com.example.hansangwon.mutoyou;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hansangwon.mutoyou.Activity.BaseActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class MemberoutActivity extends BaseActivity {
    EditText password;
    Button button;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memberout);
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
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memberout(MainActivity.userid, password.getText().toString());
            }
        });
    }

    private void memberout(String id, String password){
        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MemberoutActivity.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

                if(s.contains("diff"))      CreateAlertDialog("비밀번호가 틀렸습니다.");
                else{
                    AlertDialog.Builder alert= new AlertDialog.Builder(MemberoutActivity.this);
                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            MainActivity.userid="";
                            Intent intent = new Intent(MemberoutActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    alert.setMessage("회원탈퇴가 완료되었습니다.");
                    alert.show();
                }
            }

            @Override
            protected String doInBackground(String... params) {

                try{
                    String id = (String)params[0];
                    String password = (String)params[1];

                    String link="http://hansangwon.ipdisk.co.kr:8001/mutoyou/deletemember.php";
                    String data  = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
                    data  += "&"+URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write( data );
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    // Read Server Response
                    while((line = reader.readLine()) != null)
                    {
                        sb.append(line);
                    }
                    Log.i("sb=",sb.toString());
                    return sb.toString();
                }
                catch(Exception e){
                    return new String("Exception: " + e.getMessage());
                }

            }
        }
        InsertData task = new InsertData();
        task.execute(id, password);
    }
    public void CreateAlertDialog(String s)
    {
        AlertDialog.Builder alert= new AlertDialog.Builder(MemberoutActivity.this);
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
        password=(EditText)findViewById(R.id.PW_memberout);
        button = (Button)findViewById(R.id.member_outBtn);
    }
}
