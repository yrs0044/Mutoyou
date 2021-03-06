package com.example.hansangwon.mutoyou.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.hansangwon.mutoyou.Data.UserData;
import com.example.hansangwon.mutoyou.R;
import com.example.hansangwon.mutoyou.Util.ContextUtil;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;
    TextView IDtext;

    public String userName;
    public static AppCompatActivity mainActivity;
    UserData loginUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        setValues();

    }

    @Override
    public void setValues() {
        super.setValues();
        mainActivity = this;
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
//        Intent intent = getIntent();
//        userid = intent.getExtras().getString("userid");
        loginUser = ContextUtil.getMyUserData(mContext);
        userName = loginUser.userName;
        IDtext.setText("Welcome "+ userName);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder alert= new AlertDialog.Builder(MainActivity.this);
            alert.setPositiveButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alert.setNegativeButton("예", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
//                     loginUser = new UserData();
//                    ContextUtil.clearMyUserData(mContext);
//                    ContextUtil.setUserData(mContext,loginUser);
//                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
//                    startActivity(intent);
                    finish();
                }
            });
            alert.setMessage("앱을 종료 하시겠습니까?");
            alert.show();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_mylecture) {
            Intent intent = new Intent(MainActivity.this, MyPageActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_makerinfo) {
            Intent intent = new Intent(MainActivity.this, DeveloperActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_memberleave) {
            Intent intent = new Intent(MainActivity.this, MemberoutActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_profile_arrange) {
            Intent intent = new Intent(MainActivity.this, EditInfoActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_SignOut) {
            AlertDialog.Builder alert= new AlertDialog.Builder(MainActivity.this);
            alert.setPositiveButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alert.setNegativeButton("예", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    loginUser = new UserData();
                    ContextUtil.clearMyUserData(mContext);
                    ContextUtil.setUserData(mContext,loginUser);
                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            alert.setMessage("로그아웃 하시겠습니까?");
            alert.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void pastclassclick(View view) {
        Intent intent = new Intent(MainActivity.this,FormerClassActivity.class);
        startActivity(intent);
    }
    public void pocketclick(View view) {
        Intent intent = new Intent(MainActivity.this, PresentClassActivity.class);
        startActivity(intent);
    }
    public void calculatorclick(View view) {
        Intent intent = new Intent(MainActivity.this, CalculatorActivity.class);
        startActivity(intent);
    }

    public void boardclick(View view) {
        Intent intent = new Intent(MainActivity.this, BoardActivity.class);
        intent.putExtra("number",1);
        startActivity(intent);
    }
    public void informationclick(View view) {
        Intent intent = new Intent(MainActivity.this, BoardActivity.class);
        intent.putExtra("number", 2);
        startActivity(intent);
    }


    @Override
    public void bindViews() {
        super.bindViews();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        IDtext = (TextView)navigationView.getHeaderView(0).findViewById(R.id.headid);
    }
}
