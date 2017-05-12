package com.example.hansangwon.mutoyou.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.hansangwon.mutoyou.Network.HttpClient;
import com.example.hansangwon.mutoyou.R;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import cz.msebera.android.httpclient.Header;

public class MemberJoinActivity extends BaseActivity {
    ImageButton checkidbutton;
    ImageButton confirmbutton;
    ImageButton canclebutton;
    EditText id;
    EditText password;
    EditText name;
    EditText mail;
    EditText major;
    EditText phone;
    Boolean CheckId = false;

    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        bindViews();
        setupEvents();

    }


    @Override
    public void setupEvents() {
        super.setupEvents();
        confirmbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CheckId) {
                    CreateAlertDialog("ID 중복확인을 해주세요.");
                } else {
                    if (id.getText().toString().length() < 4 || id.getText().toString().length() > 20)
                        CreateAlertDialog("4~20자리의 ID를 입력해주세요.");
                    else {
                        if (password.getText().toString().length() < 4 || password.getText().toString().length() > 20)
                            CreateAlertDialog("4~20자리의 Password를 입력해주세요.");
                        else {
                            if (name.getText().toString().isEmpty())
                                CreateAlertDialog("이름을 입력해주세요.");
                            else {
                                if (!mail.getText().toString().contains("@"))
                                    CreateAlertDialog("E-mail 형식을 확인해주세요.");
                                else {
                                    if (major.getText().toString().isEmpty())
                                        CreateAlertDialog("전공을 입력해주세요.");
                                    else {
                                        if (phone.getText().toString().isEmpty())
                                            CreateAlertDialog("전화번호를 입력해주세요.");
                                        else
                                            insertToDatabase();
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
                if (id.getText().toString().length() < 4 || id.getText().toString().length() > 20)
                    CreateAlertDialog("4~20자리의 ID를 입력해주세요.");
                else checkid();
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
                CheckId = false;
            }
        });
    }

    private void insertToDatabase() {

        loading = ProgressDialog.show(MemberJoinActivity.this, "Please Wait", null, true, true);

        RequestParams params = new RequestParams();
        params.put("id", id.getText().toString());
        params.put("password", password.getText().toString());
        params.put("name", name.getText().toString());
        params.put("mail", mail.getText().toString());
        params.put("major", major.getText().toString());
        params.put("phone", phone.getText().toString());

        HttpClient.post("memberjoin.php", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                loading.dismiss();
                String s = new String(responseBody);
                if (s.contains("success")) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(MemberJoinActivity.this);
                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    });
                    alert.setMessage("회원가입이 완료되었습니다!");
                    alert.show();
                } else CreateAlertDialog("회원가입에 실패했습니다.");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                loading.dismiss();
                Toast.makeText(mContext, "죄송합니다. 서버상태가 불안정합니다. ", Toast.LENGTH_SHORT).show();

            }
        });

    }
//    private void insertToDatabase(String id, String password, String name, String mail, String major, String phone) {
//        class InsertData extends AsyncTask<String, Void, String> {
//            ProgressDialog loading;
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                loading = ProgressDialog.show(MemberJoinActivity.this, "Please Wait", null, true, true);
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//                loading.dismiss();
//                if (s.contains("success")) {
//                    AlertDialog.Builder alert = new AlertDialog.Builder(MemberJoinActivity.this);
//                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                            finish();
//                        }
//                    });
//                    alert.setMessage("회원가입이 완료되었습니다!");
//                    alert.show();
//                } else CreateAlertDialog("회원가입에 실패했습니다.");
//            }
//
//            @Override
//            protected String doInBackground(String... params) {
//
//                try {
//                    String id = (String) params[0];
//                    String password = (String) params[1];
//                    String name = (String) params[2];
//                    String mail = (String) params[3];
//                    String major = (String) params[4];
//                    String phone = (String) params[5];
//
//
//                    String link = "http://hansangwon.ipdisk.co.kr:8000/mutoyou/memberjoin.php";
//                    String data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
//                    data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
//                    data += "&" + URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8");
//                    data += "&" + URLEncoder.encode("mail", "UTF-8") + "=" + URLEncoder.encode(mail, "UTF-8");
//                    data += "&" + URLEncoder.encode("major", "UTF-8") + "=" + URLEncoder.encode(major, "UTF-8");
//                    data += "&" + URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8");
//
//                    Log.i("data", data);
//
//                    URL url = new URL(link);
//                    URLConnection conn = url.openConnection();
//
//                    conn.setDoOutput(true);
//                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
//
//                    wr.write(data);
//                    wr.flush();
//
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//
//                    StringBuilder sb = new StringBuilder();
//                    String line = null;
//
//                    // Read Server Response
//                    while ((line = reader.readLine()) != null) {
//                        sb.append(line);
//                    }
//                    Log.i("sb=", sb.toString());
//                    return sb.toString();
//                } catch (Exception e) {
//                    return new String("Exception: " + e.getMessage());
//                }
//
//            }
//        }
//        InsertData task = new InsertData();
//        task.execute(id, password, name, mail, major, phone);
//    }

    private void checkid() {

        loading = ProgressDialog.show(MemberJoinActivity.this, "Please Wait", null, true, true);

        RequestParams params = new RequestParams();
        params.put("id", id.getText().toString());
        HttpClient.post("isoverlab.php", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                loading.dismiss();
                String s = new String(responseBody);
                if (s.contains("1")) CreateAlertDialog("이미 존재하는 ID입니다.");
                else {
                    CreateAlertDialog("사용 가능한 ID입니다.");
                    CheckId = true;
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                loading.dismiss();
                Toast.makeText(mContext, "죄송합니다. 서버상태가 불안정합니다. ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //    private void checkid(String id){
//        class InsertData extends AsyncTask<String, Void, String>
//        {
//            ProgressDialog loading;
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                loading = ProgressDialog.show(MemberJoinActivity.this, "Please Wait", null, true, true);
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//                loading.dismiss();
//
//                if(s.contains("1"))   CreateAlertDialog("이미 존재하는 ID입니다.");
//                else
//                {
//                    CreateAlertDialog("사용 가능한 ID입니다.");
//                    CheckId=true;
//                }
//            }
//
//            @Override
//            protected String doInBackground(String... params) {
//
//                try{
//                    String id = (String)params[0];
//
//                    String link="http://hansangwon.ipdisk.co.kr:8000/mutoyou/isoverlab.php";
//                    String data  = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
//
//                    URL url = new URL(link);
//                    URLConnection conn = url.openConnection();
//
//                    conn.setDoOutput(true);
//                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
//
//                    wr.write( data );
//                    wr.flush();
//
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//
//                    StringBuilder sb = new StringBuilder();
//                    String line = null;
//
//                    // Read Server Response
//                    while((line = reader.readLine()) != null)
//                    {
//                        sb.append(line);
//                        break;
//                    }
//                    return sb.toString();
//                }
//                catch(Exception e){
//                    return new String("Exception: " + e.getMessage());
//                }
//
//            }
//        }
//        InsertData task = new InsertData();
//        task.execute(id);
//    }

    public void CreateAlertDialog(String s) {
        AlertDialog.Builder alert = new AlertDialog.Builder(MemberJoinActivity.this);
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
        checkidbutton = (ImageButton) findViewById(R.id.IDdoublecheck_access);
        confirmbutton = (ImageButton) findViewById(R.id.signin_access);
        canclebutton = (ImageButton) findViewById(R.id.cancel_access);
        id = (EditText) findViewById(R.id.ID_et_access);
        password = (EditText) findViewById(R.id.PW_et_access);
        name = (EditText) findViewById(R.id.Name_et_access);
        mail = (EditText) findViewById(R.id.Email_et_access);
        major = (EditText) findViewById(R.id.major_et_access);
        phone = (EditText) findViewById(R.id.pmunber_et_access);

    }

}
