package com.sparc.frjvcapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import jxl.write.DateTime;

public class MainContainerActivity extends AppCompatActivity {
    public static final String userlogin = "userlogin";
    public static final String coltlogin = "coltlogin";
    ImageView DataCollector, MapViewer, DGPSSurvey;

    ArrayList<Integer> headlist = new ArrayList<>();
    ArrayList<Integer> subheadlist = new ArrayList<>();
    int[] head = new int[]{1, 2, 3, 12, 4};
    int[] subhead = new int[]{5, 6, 7, 10, 11};
    SimpleDateFormat _startDateFormat, _endDateFormat;

    SQLiteDatabase db, db1, db2, db3;
    TextView logout;
    SharedPreferences shared;
    //final String pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_container);

        DataCollector = findViewById(R.id.mis_imageView);
        MapViewer = findViewById(R.id.gis_imageView);
        DGPSSurvey = findViewById(R.id.dgps_imageview);

        try {
            Util.scheduleJob(getApplicationContext());
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        //  Util.scheduleJob(getApplicationContext());
        for (int id : head) {
            headlist.add(id);
        }
        for (int id : subhead) {
            subheadlist.add(id);
        }
        //runService();
        logout = findViewById(R.id.name_textView);

        shared = getSharedPreferences(userlogin, MODE_PRIVATE);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy hh:mm:ss");
        String _startTime=shared.getString("logintime", "0");
        _endDateFormat = new SimpleDateFormat("dd/M/yyyy hh:mm:ss");
        String _endTime = _endDateFormat.format(new Date());
        Date _sDate=null,_eDate=null;
        try {
            _sDate= sdf.parse(_startTime);
            _eDate = sdf.parse(_endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long different = _eDate.getTime() - _sDate.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;



       /* Calendar calendar = Calendar.getInstance();
        calendar.setTime(_sDate);
        calendar.add(Calendar.MINUTE, 60);
        System.out.println("Time here "+calendar.getTime());
        String calculated_time =sdf.format(calendar.getTime());*/

       /* SimpleDateFormat df = new SimpleDateFormat("dd/M/yyyy HH:mm:ss");
        Calendar calendar1 = Calendar.getInstance();
        String formattedDate = df.format(calendar1.getTime());

        if(calendar1.compareTo(calendar)>0)
        {
            Logout();
        }else{
            //Logout();
        }
*/
        DataCollector.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (subheadlist.contains(Integer.parseInt(shared.getString("userdivid", "0")))) {
                    Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    SharedPreferences sharedPreferences = getSharedPreferences(coltlogin, 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.putString("uemail", shared.getString("uemail", "0"));
                    editor.putString("upass", shared.getString("upass", "0"));
                    editor.putString("uname", shared.getString("uname", "0"));
                    editor.putString("upos", shared.getString("upos", "0"));
                    editor.putString("ucir", shared.getString("ucir", "0"));
                    editor.putString("uid", shared.getString("uid", "0"));
                    editor.putString("udivid", shared.getString("udivid", "0"));
                    editor.putString("udivname", shared.getString("udivname", "0"));
                    //editor.putString("userid", jsonobject.getString("div_id"));
                    editor.commit();
                    startActivity(intent);
                } else {
                    Toast.makeText(MainContainerActivity.this, "Sorry.You are not authorized for this module", Toast.LENGTH_LONG).show();
                }
            }
        });

        MapViewer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Intent intent = new Intent(getApplicationContext(), MapViewerActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                SharedPreferences sharedPreferences = getSharedPreferences(coltlogin, 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.putString("uemail", shared.getString("uemail", "0"));
                editor.putString("upass", shared.getString("upass", "0"));
                editor.putString("uname", shared.getString("uname", "0"));
                editor.putString("upos", shared.getString("upos", "0"));
                editor.putString("ucir", shared.getString("ucir", "0"));
                editor.putString("uid", shared.getString("uid", "0"));
                editor.putString("udivid", shared.getString("udivid", "0"));
                editor.putString("udivname", shared.getString("udivname", "0"));
                //editor.putString("userid", jsonobject.getString("div_id"));
                editor.commit();
                startActivity(intent);*/
                Toast.makeText(MainContainerActivity.this, "Module is Under Development", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        DGPSSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View customLayout = getLayoutInflater().inflate(R.layout.pupup_check_file_availability, null);
                alertDialogBuilder.setView(customLayout);

                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                Intent intent = new Intent(getApplicationContext(), SelectFBForDGPSActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                SharedPreferences sharedPreferences = getSharedPreferences(coltlogin, 0);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.clear();
                                editor.putString("uemail", shared.getString("uemail", "0"));
                                editor.putString("upass", shared.getString("upass", "0"));
                                editor.putString("uname", shared.getString("uname", "0"));
                                editor.putString("upos", shared.getString("upos", "0"));
                                editor.putString("ucir", shared.getString("ucir", "0"));
                                editor.putString("uid", shared.getString("uid", "0"));
                                editor.putString("udivid", shared.getString("udivid", "0"));
                                editor.putString("udivname", shared.getString("udivname", "0"));
                                //editor.putString("userid", jsonobject.getString("div_id"));
                                editor.commit();
                                startActivity(intent);
                            }
            });
                            alertDialogBuilder.setNegativeButton("No",
                                    new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick (DialogInterface arg0,int arg1){
                                // Toast.makeText(getApplicationContext(), "You canceled the request...please try again", Toast.LENGTH_LONG).show();
                            }
                            });

                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                            //Toast.makeText(MainContainerActivity.this, "Module is Under Development", Toast.LENGTH_SHORT).show();

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout();
            }
        });
    }

    private void runService() {
        String divid, userid;
        final SharedPreferences shared = getSharedPreferences(userlogin, MODE_PRIVATE);
        divid = shared.getString("udivid", "0");
        userid = shared.getString("uemail", "0");

        db = openOrCreateDatabase("sltp.db", MODE_PRIVATE, null);

        Cursor c = db.rawQuery("select * from m_pillar_reg where d_id='" + divid + "' and uid='" + userid + "' and img_status='0' and p_pic is not null", null);
        int count = c.getCount();
        if (c.getCount() >= 1) {
            if (c.moveToFirst()) {
                try {
                    Util.scheduleJob(getApplicationContext());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            c.close();
            db.close();
        }

        Cursor c1 = db1.rawQuery("select * from m_shifting_pillar_reg where uid='" + userid + "' and simg_status='0' and s_pic is not null", null);
        int count1 = c.getCount();
        if (c1.getCount() >= 1) {
            if (c1.moveToFirst()) {
                try {
                    Util.scheduleJob(getApplicationContext());
                    ///uploadImage(Utility.getByeArr(Utility.setPic(c1.getString(c1.getColumnIndex("s_pic")))), c1.getString(c1.getColumnIndex("s_pic")), "2");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            c1.close();
            db1.close();
        }

        Cursor c2 = db1.rawQuery("select * from m_fb_dgps_survey_pill_pic where u_id='" + userid + "' and pic_status='0' and pic_name is not null", null);
        int count2 = c.getCount();
        if (c2.getCount() >= 1) {
            if (c2.moveToFirst()) {
                try {
                    Util.scheduleJob(getApplicationContext());
                    //uploadDGPSImage(Utility.getByeArr(Utility.setPic(c2.getString(c2.getColumnIndex("pic_name")))), c2.getString(c2.getColumnIndex("pic_name")), "1", c2.getString(c2.getColumnIndex("pic_view")));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            c2.close();
            db2.close();
        } else {

        }
    }
    private void Logout()
    {
        if (subheadlist.contains(Integer.parseInt(shared.getString("userdivid", "0")))) {
            SQLiteDatabase db = openOrCreateDatabase("sltp.db", MODE_PRIVATE, null);
            db.execSQL("DELETE from m_fb");
            db.delete("m_range", null, null);
            db.close();

            SharedPreferences sharedPreferences = getSharedPreferences(userlogin, 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            i.setFlags(i.FLAG_ACTIVITY_NEW_TASK | i.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();

        } else {
            SharedPreferences sharedPreferences = getSharedPreferences(userlogin, 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            i.setFlags(i.FLAG_ACTIVITY_NEW_TASK | i.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        }
    }


}
