package com.example.proj3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.proj3.Database.DatabaseHelper;
import com.example.proj3.Model.Profile;

import java.util.ArrayList;

import static com.example.proj3.Database.DatabaseHelper.COL_5;
import static com.example.proj3.Database.DatabaseHelper.COL_6;
import static com.example.proj3.Database.DatabaseHelper.COL_7;
import static com.example.proj3.Database.DatabaseHelper.TABLE_NAME;

public class DetailActivity extends AppCompatActivity {
ImageView profileimage;
//EditText  youtube;
TextView youtube,insta,bio;
String youtub,inst,img,idemp;
LinearLayout lin1;
    DatabaseHelper myDb;
    public String youtubedb,instadb;
    Button edit;
    ArrayList<Profile> dataModels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDb = new DatabaseHelper(this);
        setContentView(R.layout.activity_detail);
        lin1=findViewById(R.id.lin1);
        youtube=findViewById(R.id.name);
        insta=findViewById(R.id.type);
        edit=findViewById(R.id.version_heading);
        profileimage=findViewById(R.id.item_info);
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("data",Context.MODE_PRIVATE);
        youtub=sharedPref.getString("youtube","null");
        inst=sharedPref.getString("insta","null");
        img=sharedPref.getString("image","null");
        idemp=sharedPref.getString("idemp","null");
        Log.e("emp",idemp);
       Glide.with(this).load(img).centerCrop()
                .placeholder(R.drawable.prof64).into(profileimage);
       // addd();
        youtube.setText(youtub);
        insta.setText(inst);

edit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        alt(idemp,youtub,inst);

    }
});





    }
    public void alt(final String idemp,final String youtub,final String inst) {
        this.idemp=idemp;
        this.youtub=youtub;
        this.inst=inst;
        myDb = new DatabaseHelper(this);
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.edit_layout, null);
        final EditText etyoutube= (EditText) dialogView.findViewById(R.id.etyoutube);
        final EditText etinsta = (EditText) dialogView.findViewById(R.id.etinsta);
        etyoutube.setText(youtub);
        etinsta.setText(inst);

        final Button button2 = (Button) dialogView.findViewById(R.id.version_heading);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           String     youtubee=etyoutube.getText().toString();
             String    instee=etinsta.getText().toString();
                boolean isUpdate = myDb.updateData(idemp,
                        youtubee,
                        instee);
                if(isUpdate == true) {
                   // getusermealunit(idemp, youtubee);
                    Toast.makeText(DetailActivity.this, "Data UpdateD IN DB +" + youtubee+instee, Toast.LENGTH_LONG).show();

                    SharedPreferences sharedPref =getApplicationContext(). getSharedPreferences("data",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("youtube",youtubee);
                    editor.putString("insta",instee);
                 //   editor.putString("image",dataModel.getImage());
                    editor.putString("idemp",idemp);
                    editor.commit();


                }
                else
                    Toast.makeText(DetailActivity.this,"Data not Updated",Toast.LENGTH_LONG).show();
                Intent a=new Intent(DetailActivity.this,DetailActivity.class);
                a.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(a);
                 finish();
                dialogBuilder.dismiss();



            }
        });


        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }

    public void getusermealunit(String  idemp,String youtubedbb) {
       // Profile dataModel=(Profile) ;
        myDb = new DatabaseHelper(this);
        SQLiteDatabase db =myDb.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME+ " WHERE "
                + COL_5 + " = " + idemp;

      //  Log.e("queryselect", selectQuery);
     //   Cursor c = db.rawQuery(selectQuery, null);

//        if (c != null)
//            c.moveToFirst();
//        youtubedb = c.getString(c.getColumnIndex("YOUTUBE"));
        Cursor res = myDb.getParticularData(idemp);
        if(res.getCount() == 0) {
            // show message
           Log.e("Error","Nothing found");
            return;
        }

        StringBuffer buffer = new StringBuffer();
     //  while (res.moveToNext()) {
//            buffer.append("Id :"+ res.getString(0)+"\n");
//            buffer.append("Name :"+ res.getString(1)+"\n");
            buffer.append("Surname :"+res.getString(6)+"\n");
         //   buffer.append("Marks :"+ res.getString(Integer.parseInt(COL_7))+"\n\n");
     //   }
       Log.e("Data",buffer.toString());

    }
    public void onResume(){
        super.onResume();
      //  addd();
     //   getusermealunit(idemp,youtubee);
    }

    public void addd(){
        myDb = new DatabaseHelper(this);
        SQLiteDatabase db =myDb.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME+ " WHERE "
                + COL_5 + " = " + idemp;
        Cursor cursor = db.rawQuery(selectQuery, null);

        //  Cursor cursor = db.rawQuery("select * from employee_table where IDEMP='"+idemp+"'",null);
        if (cursor.moveToFirst())
        {
            do
            {
                youtubedb = cursor.getString(cursor.getColumnIndex(COL_6));
                instadb= cursor.getString(cursor.getColumnIndex(COL_7));




            }while (cursor.moveToNext());

            youtube.setText(youtub);
            insta.setText(inst);
        }

    }
}