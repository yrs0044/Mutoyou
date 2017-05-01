package com.example.hansangwon.mutoyou;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class JoinActivity extends AppCompatActivity {
    ImageButton checkidbutton;
    ImageButton confirmbutton;
    ImageButton canclebutton;
    EditText id;
    EditText password;
    EditText name;
    EditText mail;
    EditText major;
    EditText phone;
    Boolean CheckId=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.access);
        checkidbutton = (ImageButton)findViewById(R.id.IDdoublecheck_access);
        confirmbutton = (ImageButton)findViewById(R.id.signin_access);
        canclebutton = (ImageButton)findViewById(R.id.cancel_access);
        id = (EditText)findViewById(R.id.ID_et_access);
        password = (EditText)findViewById(R.id.PW_et_access);
        name = (EditText)findViewById(R.id.Name_et_access);
        mail = (EditText)findViewById(R.id.Email_et_access);
        major = (EditText)findViewById(R.id.major_et_access);
        phone = (EditText)findViewById(R.id.pmunber_et_access);

        confirmbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!CheckId)        CreateAlertDialog("ID 중복확인을 해주세요.");
                else {
                    if (id.getText().toString().length() < 4 || id.getText().toString().length() > 20)
                        CreateAlertDialog("4~20자리의 ID를 입력해주세요.");
                    else {
                        if (password.getText().toString().length() < 4 || password.getText().toString().length() > 20)
                            CreateAlertDialog("4~20자리의 Password를 입력해주세요.");
                        else {
                            if (name.getText().toString().isEmpty())
                                CreateAlertDialog("이름을 입력해주세요.");
                            else{
                                if(!mail.getText().toString().contains("@"))    CreateAlertDialog("E-mail 형식을 확인해주세요.");
                                else{
                                    if(major.getText().toString().isEmpty())    CreateAlertDialog("전공을 입력해주세요.");
                                    else{
                                        if(phone.getText().toString().isEmpty())    CreateAlertDialog("전화번호를 입력해주세요.");
                                        else    insertToDatabase(id.getText().toString(),password.getText().toString(),name.getText().toString(),mail.getText().toString(),major.getText().toString(),phone.getText().toString());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });

        canclebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        checkidbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id.getText().toString().length()<4||id.getText().toString().length()>20) CreateAlertDialog("4~20자리의 ID를 입력해주세요.");
                else       checkid(id.getText().toString());
            }
        });

        id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                CheckId=false;
            }
        });
    }

    private void insertToDatabase(String id, String password, String name, String mail, String major, String phone){
        class InsertData extends AsyncTask<String, Void, String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(JoinActivity.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if(s.contains("success")) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(JoinActivity.this);
                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    });
                    alert.setMessage("회원가입이 완료되었습니다!");
                    alert.show();
                }
                else    CreateAlertDialog("회원가입에 실패했습니다.");
            }

            @Override
            protected String doInBackground(String... params) {

                try{
                    String id = (String)params[0];
                    String password = (String)params[1];
                    String name = (String)params[2];
                    String mail = (String)params[3];
                    String major = (String)params[4];
                    String phone = (String)params[5];


                    String link="http://hansangwon.ipdisk.co.kr:8001/mutoyou/memberjoin.php";
                    String data  = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
                    data +=  "&"+ URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                    data += "&" + URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8");
                    data += "&" + URLEncoder.encode("mail", "UTF-8") + "=" + URLEncoder.encode(mail, "UTF-8");
                    data += "&" + URLEncoder.encode("major", "UTF-8") + "=" + URLEncoder.encode(major, "UTF-8");
                    data += "&" + URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8");

                    Log.i("data",data);

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
        task.execute(id, password, name, mail, major, phone);
    }

    private void checkid(String id){
        class InsertData extends AsyncTask<String, Void, String>
        {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(JoinActivity.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

                if(s.contains("1"))   CreateAlertDialog("이미 존재하는 ID입니다.");
                else
                {
                    CreateAlertDialog("사용 가능한 ID입니다.");
                    CheckId=true;
                }
            }

            @Override
            protected String doInBackground(String... params) {

                try{
                    String id = (String)params[0];

                    String link="http://hansangwon.ipdisk.co.kr:8001/mutoyou/isoverlab.php";
                    String data  = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");

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
                        break;
                    }
                    return sb.toString();
                }
                catch(Exception e){
                    return new String("Exception: " + e.getMessage());
                }

            }
        }
        InsertData task = new InsertData();
        task.execute(id);
    }
    public void CreateAlertDialog(String s)
    {
        AlertDialog.Builder alert= new AlertDialog.Builder(JoinActivity.this);
        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.setMessage(s);
        alert.show();
    }
}
