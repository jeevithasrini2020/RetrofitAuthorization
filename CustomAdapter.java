package com.example.proj3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.proj3.Model.Profile;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import static android.provider.LiveFolders.INTENT;

public class CustomAdapter extends ArrayAdapter<Profile> implements View.OnClickListener{

    private ArrayList<Profile> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtType;
        TextView txtVersion;
        ImageView info;
    }

    public CustomAdapter(ArrayList<Profile> data, Context context) {
        super(context, R.layout.row_layout, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        Profile dataModel=(Profile) object;

        switch (v.getId())
        {
            case R.id.item_info:
                SharedPreferences sharedPref =mContext. getSharedPreferences("data",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("youtube",dataModel.getYoutube_link());
                editor.putString("insta",dataModel.getInstagram_link());
                editor.putString("image",dataModel.getImage());
                editor.putString("idemp",dataModel.getId());
                editor.commit();

                Snackbar.make(v, "Release date " +dataModel.getMobile(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                Intent a=new Intent(mContext,DetailActivity.class);
               a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               mContext.startActivity(a);

                break;
        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Profile dataModel = getItem(position);
        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_layout, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.txtType = (TextView) convertView.findViewById(R.id.type);
            viewHolder.txtVersion = (TextView) convertView.findViewById(R.id.version_number);
            viewHolder.info = (ImageView) convertView.findViewById(R.id.item_info);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;
        viewHolder.txtName.setText("F_Name:"+dataModel.getFirst_name());
        viewHolder.txtType.setText("L_Name: "+dataModel.getLast_name());
        viewHolder.txtVersion.setText(dataModel.getMobile());
        viewHolder.info.setOnClickListener(this);
        viewHolder.info.setTag(position);

        return convertView;
    }
}