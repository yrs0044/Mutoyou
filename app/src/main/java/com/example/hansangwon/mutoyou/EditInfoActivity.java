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

public class EditInfoActivity extends BaseActivity {
    EditText nowpassword;
    EditText newpassword;
    EditText name;
    EditText major;
    EditText mail;
    EditText phone;
    Button button;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editinfo);
        bindViews();
        setValues();
        setupEvents();

    }

    @Override
    public void setValues() {
        super.setValues();
        getinf(MainActivity.userid);// ContextUtil의 userData로 바꿔보자
    }

    @Override
    public void setupEvents() {
        super.setupEvents();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nowpassword.getText().toString().isEmpty())
                    CreateAlertDialog("현재 비밀번호를 입력해주세요.");
                else {
                    if (newpassword.getText().toString().isEmpty())
                        CreateAlertDialog("새로운 비밀번호를 입력해주세요.");
                    else {
                        if (newpassword.getText().length() > 20 || newpassword.getText().length() < 4)
                            CreateAlertDialog("4~20자리의 비밀번호를 입력해주세요.");
                        else {
                            if (name.getText().toString().isEmpty())
                                CreateAlertDialog("이름을 입력해주세요.");
                            else {
                                if (major.getText().toString().isEmpty())
                                    CreateAlertDialog("전공을 입력해주세요.");
                                else {
                                    if (!mail.getText().toString().contains("@"))
                                        CreateAlertDialog("메일 형식을 확인해주세요.");
                                    else {
                                        if (phone.getText().toString().isEmpty())
                                            CreateAlertDialog("전화번호를 확인해주세요.");
                                        else
                                            changemember(MainActivity.userid, nowpassword.getText().toString(), newpassword.getText().toString(), name.getText().toString(), major.getText().toString(), mail.getText().toString(), phone.getText().toString());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    private void getinf(final String id) {
        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(EditInfoActivity.this, "Please Wait", null, true, true);

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

                System.out.println(s);
                String x[] = s.split("/");

                name.setText(x[0]);
                mail.setText(x[1]);
                major.setText(x[2]);
                phone.setText(x[3]);
            }

            @Override
            protected String doInBackground(String... params) {

                try {
                    String id = (String) params[0];

                    String link = "http://hansangwon.ipdisk.co.kr:8000/mutoyou/getInfo.php";
                    String data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");

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
        task.execute(id);
    }

    private void changemember(final String id, String password, String newpassword, String name, String major, String mail, String phone) {
        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(EditInfoActivity.this, "Please Wait", null, true, true);

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                System.out.println(s);
                if (s.contains("success")) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(EditInfoActivity.this);
                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent intent = new Intent(EditInfoActivity.this, MainActivity.class);
                            intent.putExtra("userid", MainActivity.userid);
                            startActivity(intent);
                            finish();
                        }
                    });
                    alert.setMessage("회원정보 변경이 완료되었습니다.");
                    alert.show();
                } else {
                    CreateAlertDialog("비밀번호를 확인해주세요.");
                }
            }

            @Override
            protected String doInBackground(String... params) {

                try {
                    String id = (String) params[0];
                    String password = (String) params[1];
                    String newpassword = (String) params[2];
                    String name = (String) params[3];
                    String major = (String) params[4];
                    String mail = (String) params[5];
                    String phone = (String) params[6];

                    String link = "http://hansangwon.ipdisk.co.kr:8000/mutoyou/rewrite.php";
                    String data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
                    data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                    data += "&" + URLEncoder.encode("newpassword", "UTF-8") + "=" + URLEncoder.encode(newpassword, "UTF-8");
                    data += "&" + URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8");
                    data += "&" + URLEncoder.encode("major", "UTF-8") + "=" + URLEncoder.encode(major, "UTF-8");
                    data += "&" + URLEncoder.encode("mail", "UTF-8") + "=" + URLEncoder.encode(mail, "UTF-8");
                    data += "&" + URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8");

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
        task.execute(id, password, newpassword, name, major, mail, phone);
    }

    public void CreateAlertDialog(String s) {
        AlertDialog.Builder alert = new AlertDialog.Builder(EditInfoActivity.this);
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
        nowpassword = (EditText) findViewById(R.id.Password_rewrite);
        newpassword = (EditText) findViewById(R.id.Repassword_rewrite);
        name = (EditText) findViewById(R.id.name_rewrite);
        major = (EditText) findViewById(R.id.major_rewrite);
        mail = (EditText) findViewById(R.id.Mail_rewrite);
        phone = (EditText) findViewById(R.id.PhoneNumber_rewrite);
        button = (Button) findViewById(R.id.btnSubmit_rewrite);
    }
}
