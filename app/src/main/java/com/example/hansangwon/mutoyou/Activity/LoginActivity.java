package com.example.hansangwon.mutoyou.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.hansangwon.mutoyou.Data.UserData;
import com.example.hansangwon.mutoyou.Network.HttpClient;
import com.example.hansangwon.mutoyou.R;
import com.example.hansangwon.mutoyou.Util.ContextUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends BaseActivity {
    ImageButton loginbutton;
    ImageButton joinbutton;
    ImageButton findbutton;
    AutoCompleteTextView id;
    AutoCompleteTextView password;

    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindViews();
        setupEvents();
    }


    @Override
    public void setupEvents() {
        super.setupEvents();

//        loginbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                login(id.getText().toString(), password.getText().toString());
//            }
//        });
        loginbutton.setOnClickListener(login2Click);

        joinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MemberJoinActivity.class);
                startActivity(intent);
            }
        });
        findbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, FindActivity.class);
                startActivity(intent);
            }
        });

    }

    View.OnClickListener login2Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            loading = ProgressDialog.show(LoginActivity.this, "Please Wait", null, true, true);

            //포스트로 넘겨줄 값을 지정해줌
            RequestParams params = new RequestParams();
            //왼쪽 인자는 PHP POST 키값을 나타내고 오른쪽 인자는 보낼 값을 나타냄
            params.put("id", id.getText().toString());
            params.put("password", password.getText().toString());

            HttpClient.post("login.php", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    //TODO : 통신에 성공했을 때 이벤트를 적어주면 됨
                    loading.dismiss();
                    String s = new String(responseBody);
                    if (s.contains("fail")) {
                        CreateAlertDialog("ID 혹은 Password가 잘못 입력되었습니다.");
                    } else {
                        Log.i("s.......",s);
                        UserData loginUser = null;
                        try {
                            JSONArray jsonarray = new JSONArray(s);
                            JSONObject jsonObject = jsonarray.getJSONObject(0);
                            loginUser = UserData.getUserDataFromJson(jsonObject);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            System.out.println(loginUser.PKey + " / " + loginUser.userId + " / " + loginUser.userName);
//                        intent.putExtra("userid", loginUser.userId);
                            ContextUtil.setUserData(mContext,loginUser);
                            startActivity(intent);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    loading.dismiss();
                    Toast.makeText(mContext, "죄송합니다. 서버상태가 불안정합니다. " , Toast.LENGTH_SHORT).show();

                }

            }

        );
    }
    };


//    private void login(final String id, final String password) {
//        class InsertData extends AsyncTask<String, Void, String> {
//            ProgressDialog loading;
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                loading = ProgressDialog.show(LoginActivity.this, "Please Wait", null, true, true);
//
//
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//                loading.dismiss();
//
//                if (s.contains("fail")) {
//                    CreateAlertDialog("ID 혹은 Password가 잘못 입력되었습니다.");
//                } else {
//                    Log.i("s.......",s);
//                    UserData loginUser = null;
//                    try {
//                        JSONArray jsonarray = new JSONArray(s);
//                        JSONObject jsonObject = jsonarray.getJSONObject(0);
//                        loginUser = UserData.getUserDataFromJson(jsonObject);
//                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                        System.out.println(loginUser.PKey + " / " + loginUser.userId + " / " + loginUser.userName);
////                        intent.putExtra("userid", loginUser.userId);
//                        ContextUtil.setUserData(mContext,loginUser);
//                        startActivity(intent);
//                        finish();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }
//
//            @Override
//            protected String doInBackground(String... params) {
//
//                try {
//                    String id = (String) params[0];
//                    String password = (String) params[1];
//
//                    String link = "http://hansangwon.ipdisk.co.kr:8000/mutoyou/login.php";
//                    String data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
//                    data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
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
//        task.execute(id, password);
//    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
        alert.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                moveTaskToBack(true);
                finish();
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
        alert.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.setMessage("앱을 종료하시겠습니까?");
        alert.show();
    }

    public void CreateAlertDialog(String s) {
        AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
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

        loginbutton = (ImageButton) findViewById(R.id.login_log_in);
        joinbutton = (ImageButton) findViewById(R.id.signin_log_in);
        findbutton = (ImageButton) findViewById(R.id.findIDPW_log_in);
        id = (AutoCompleteTextView) findViewById(R.id.ID_et_log_in);
        password = (AutoCompleteTextView) findViewById(R.id.PW_et_log_in);

    }


}

