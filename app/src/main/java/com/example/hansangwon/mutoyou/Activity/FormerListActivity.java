package com.example.hansangwon.mutoyou.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hansangwon.mutoyou.R;
import com.example.hansangwon.mutoyou.Util.FormerlistAdapter;
import com.example.hansangwon.mutoyou.Util.ListViewItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public class FormerListActivity extends BaseActivity implements FormerlistAdapter.ListBtnClickListener {

    ArrayList<ListViewItem> items = new ArrayList<ListViewItem>();
    FormerlistAdapter adapter;
    ListView listview;
    TextView change;
    String year;
    String grade ;
    String sem ;
    String MKY ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_former_class);
        bindViews();
        setValues();

    }

    @Override
    public void setValues() {
        super.setValues();
        adapter = new FormerlistAdapter(this,R.layout.former_list_item,items);
        Intent intent = getIntent();
        year = intent.getExtras().getString("year");
        grade = intent.getExtras().getString("GradeData");
        sem = intent.getExtras().getString("sem");
        MKY = intent.getExtras().getString("MKY");
        if(MKY.equals("1"))     change.setText("학과");
        else                    change.setText("세부영역구분");
        String classname = "";
        classname = intent.getExtras().getString("classname");
        String major = "";
        major = intent.getExtras().getString("major");
        PrintReserve(year, sem, grade, MKY, major, classname);

    }

    private void PrintReserve(String year, String sem, String grade, String MKY, String major, String classname) {
        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(FormerListActivity.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                try {
                    JSONArray jarray=new JSONArray(s);
                    for(int i=0;i<jarray.length();i++){
                        ListViewItem item = new ListViewItem();
                        JSONObject jObject = jarray.getJSONObject(i);
                        item.setText1(jObject.getString("MKY"));
                        item.setText2(jObject.getString("credit"));
                        item.setText3(jObject.getString("major"));
                        item.setText4(jObject.getString("classname"));
                        item.setText5(jObject.getString("max"));
                        item.setText6(jObject.getString("now"));
                        item.setText7(Double.toString(Math.ceil(jObject.getDouble("now")/jObject.getDouble("max")*100)/100));
                        item.setText8(jObject.getString("profname"));
                        if(jObject.getString("classroom").contains("null"))  item.setText9("");
                        else                                                    item.setText9(jObject.getString("classroom"));

                        items.add(item);
                    }
                    listview.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {

                try {
                    String year = (String) params[0];
                    String sem = (String) params[1];
                    String grade = (String) params[2];
                    String MKY = (String) params[3];
                    String major = (String) params[4];
                    String classname = (String) params[5];

                    String link = "http://hansangwon.ipdisk.co.kr:8000/mutoyou/former_search.php";
                    String data = URLEncoder.encode("year", "UTF-8") + "=" + URLEncoder.encode(year, "UTF-8");
                    data += "&"+URLEncoder.encode("sem", "UTF-8") + "=" + URLEncoder.encode(sem, "UTF-8");
                    data += "&"+URLEncoder.encode("GradeData", "UTF-8") + "=" + URLEncoder.encode(grade, "UTF-8");
                    data += "&"+URLEncoder.encode("MKY", "UTF-8") + "=" + URLEncoder.encode(MKY, "UTF-8");
                    data += "&"+URLEncoder.encode("major", "UTF-8") + "=" + URLEncoder.encode(major, "UTF-8");
                    data += "&"+URLEncoder.encode("classname", "UTF-8") + "=" + URLEncoder.encode(classname, "UTF-8");

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
        task.execute(year, sem, grade, MKY, major, classname);
    }

    @Override
    public void onListBtnClick(int position) {

    }

    @Override
    public void bindViews() {
        super.bindViews();
        change=(TextView)findViewById(R.id.changetext);
        listview=(ListView)findViewById(R.id.listview_former);
    }

}
