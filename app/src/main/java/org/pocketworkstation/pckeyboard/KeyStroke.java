package org.pocketworkstation.pckeyboard;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyCharacterMap;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class KeyStroke extends Activity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
        ListView lv;
        String []data;
        ArrayList<String> al = new ArrayList<String>();;
        ArrayAdapter adapter;
        DBHelper myDB = new DBHelper(this);
@Override
protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.keystroke_menu);


        lv = findViewById(R.id.lvKeyloglist);

        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,al);
        lv.setAdapter(adapter);

        Cursor data = myDB.getAllTime();
        //StringBuffer stringBuffer = new StringBuffer();
        if(data!=null && data.getCount()>0){
        while (data.moveToNext()){
        //  al.add(data.getString(0));
        adapter.add(data.getString(0));
        }
        }

        lv.setOnItemClickListener(this);
        lv.setOnItemLongClickListener(this);


        }

@Override
public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(this,KeyStrokeData.class);
        i.putExtra("getTime",parent.getItemAtPosition(position).toString());
        startActivity(i);

        }

@Override
public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Menu");
        builder.setPositiveButton("Erase Data",new DialogInterface.OnClickListener() {
@Override
public void onClick(DialogInterface dialog, int which) {
        myDB.deleteData(parent.getItemAtPosition(position).toString());
        adapter.notifyDataSetChanged();
        Intent i = new Intent(getApplicationContext(),Main.class);
        startActivity(i);
        Toast.makeText(getApplicationContext(),"Erased All Data Successfully",Toast.LENGTH_SHORT).show();

        }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
@Override
public void onClick(DialogInterface dialog, int which) {

        }
        });

        builder.create().show();

        return true;
        }
}