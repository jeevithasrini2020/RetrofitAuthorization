package com.example.proj3;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.proj3.Database.DatabaseHelper;
import com.example.proj3.Interface.Api;
import com.example.proj3.Model.Profile;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ProfileActivity extends AppCompatActivity {
    ListView listView;
    public String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjFlOWU2NDZhY2Q3MjBhNjk5Mjg4Y2EzNTgzNTMyMWVmMWUzZDQ3ODk4NzA1ZTg0YTg3ZDQzYWM1YjI3ZjdmYmRmNmEyMjIxNWI2MzI2ZjQ1In0.eyJhdWQiOiIxIiwianRpIjoiMWU5ZTY0NmFjZDcyMGE2OTkyODhjYTM1ODM1MzIxZWYxZTNkNDc4OTg3MDVlODRhODdkNDNhYzViMjdmN2ZiZGY2YTIyMjE1YjYzMjZmNDUiLCJpYXQiOjE1ODkwMTY5MDMsIm5iZiI6MTU4OTAxNjkwMywiZXhwIjoxNjIwNTUyOTAzLCJzdWIiOiI5MyIsInNjb3BlcyI6W119.hthHdTOCGAHeK7DIWa67T13vtoXBrZwTcB9D-eZ1AMZhChj-QepSUn3QlKe9LhVor0Y2q0rEaHF1iaKVI3YSHBOuC5BjX0bnaEA9d514No1JYCWpFQTSf84WgJAAwSVEvWUl6WyjLv3EfQxH4Vhnnh5U81-PKrpQcQCIWW5NzrI2onoNHkfd4fDMslYlWj28kDHBvfHjMufqcYe2OKG14I03BJwCG1dFEPyvdRwODTHN1bKVg327wI66yT2N5wT4Cgmpyo968C_2venYUNTvc_JGHUJgBhBPfKPDC0PrlNgJtYs-GS6X0ehMRAk2BCCmz1JGj-71u_WR9-2x6vYVGgw-DCwK5vCC5Pg1m8UwFQlwF459ArekoTjXKjkxWwbeL6RBFQDB29F4yihUNKqIUZrfBVXZi_N6MozPFatwD6MfdkTZ8Vo1fsy7m0cVNCr4x92-AEcizwlwJxD5UmlBAaRi-8C4cuXDCPYodK3AesvS-BPuWqCDcOG9Ccvgm5wplYmX7o3YHwoJElpsxQV-bSTBYtKGUPEmDbUQKP1kJacBmjV-95Mn3LavFQWBI7HpyNprGVSoM6N-alkZa8x8pVp5KhP4NwVWviRBLgTESH1LC1aVdpTEJgzilqIbeoxaqyyFanxm6W_5j0gVeHDIVEMBLCT2K4brLA5SKqJSL8A";
    List<Profile> heroList;
    ArrayList<Profile> dataModels;
    private static CustomAdapter adapter;
    DatabaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        listView = (ListView) findViewById(R.id.listViewHeroes);
        myDb = new DatabaseHelper(this);
        getProfile();
    }
    public void getProfile(){
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor()           {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request newRequest  = chain.request().newBuilder()
                        .addHeader("Authorization", " Bearer " + token)
                        .build();
                return chain.proceed(newRequest);
            }
        }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .client(client)
                .build();

        Api api = retrofit.create(Api.class);

        Call<List<Profile>> call = api.getHeroes();

        call.enqueue(new Callback<List<Profile>>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(Call<List<Profile>> call, Response<List<Profile>> response) {
                heroList = new ArrayList<>();
              //  List<Profile> heroList = response.body();
                Toast.makeText(getApplicationContext(),response.toString(), Toast.LENGTH_SHORT).show();
                Log.e("resp",response.toString());
                //Creating an String array for the ListView
               // String[] heroes = new String[heroList.size()];

                //looping through all the heroes and inserting the names inside the string array
//                for (int i = 0; i < heroList.size(); i++) {
//                    heroes[i] = heroList.get(i).getFirst_name();
//                   heroes[i] = heroList.get(i).getLast_name();
//                 //   heroes[i] = heroList.get(i).getMobile();
////                    Profile hero = new  Profile(
////                            heroList.get(i).getFirst_name(),
////                            heroList.get(i).getLast_name(),
////                            heroList.get(i).getMobile()
////
////                    );
//
//                }

    List<Profile> profiles=response.body();
    String[] heroes = new String[profiles.size()];
                dataModels= new ArrayList<>();
    for (int i = 0; i < profiles.size(); i++) {
      //  boolean isInserted =
        myDb.insertData(profiles.get(i).getFirst_name(),
                profiles.get(i).getLast_name(),
                profiles.get(i).getMobile(),profiles.get(i).getId(),  profiles.get(i).getYoutube_link(),profiles.get(i).getInstagram_link() );
        dataModels.add(new Profile(profiles.get(i).getFirst_name(), profiles.get(i).getLast_name(),
                        profiles.get(i).getEmail(),
                        profiles.get(i).getMobile(),
                profiles.get(i).getInstagram_link(),
                        profiles.get(i).getYoutube_link(),
                        profiles.get(i).getWebsite_link(),
                profiles.get(i).getImage(),profiles.get(i).getId()));
        adapter= new CustomAdapter(dataModels,getApplicationContext());
        listView.setAdapter(adapter);


    }




        //displaying the string array into listview
       // listView.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, heroes));
     //   listView.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,heroList));

    }

           // }
            @Override
            public void onFailure(Call<List<Profile>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



}