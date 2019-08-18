package com.example.beingamit.blooddonor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.Intent.ACTION_CALL;
//import static android.support.v4.app.ActivityCompat.startActivity;


/**
 * Created by BeingAmit on 31-10-2015.
 */
public class Customadapter extends ArrayAdapter {
    ArrayList nameList,mobileList,addList,latlist,lnglist;
    Context cntx;
    TextView tvCallIcon,tvMapIcon;
    String mob;

    public Customadapter(Context context,  ArrayList name,ArrayList mobile,ArrayList address,ArrayList latlist,ArrayList lnglist) {
        super(context, R.layout.customlist, name);
        cntx=context;
        nameList=name;
        mobileList=mobile;
        addList=address;
        this.latlist=latlist;
        this.lnglist=lnglist;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflator=(LayoutInflater)cntx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View cview=inflator.inflate(R.layout.customlist,null);
        TextView tvName=(TextView)cview.findViewById(R.id.textNameList);
        TextView tvMobile=(TextView)cview.findViewById(R.id.textMobileList);
        TextView tvAddress=(TextView)cview.findViewById(R.id.textAddressList);

        if(!nameList.isEmpty()) {

            tvName.setText("Name: " + nameList.get(position).toString());
            tvMobile.setText("Mobile No: " + mobileList.get(position).toString());
            tvAddress.setText("Address: " + addList.get(position).toString());
            tvCallIcon = (TextView) cview.findViewById(R.id.textCallIcon);
            tvMapIcon = (TextView) cview.findViewById(R.id.textMapIcon);
            tvCallIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        mob = mobileList.get(position).toString();
                        Intent inte = new Intent(Intent.ACTION_CALL);
                        //inte.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        inte.setData(Uri.parse("tel:" + mob));
                        cntx.startActivity(inte);
                    }catch (SecurityException ex)
                    {
                        String er=ex.toString();
                        Log.d("errr",er);
                    }
                    //Toast.makeText(getContext(),"call intent",Toast.LENGTH_SHORT).show();
                }
            });

            tvMapIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String add = "Address:"+addList.get(position).toString();
                    Bundle bd = new Bundle();
                    bd.putString("addr", add);
                    bd.putString("lt", latlist.get(position).toString());
                    bd.putString("lng", lnglist.get(position).toString());
                    Intent intent = new Intent(cntx, MapsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("data", bd);
                    cntx.startActivity(intent);

                    //    Toast.makeText(getContext(), "map intent", Toast.LENGTH_SHORT).show();
                }


            });
        }else
        {
            tvName.setText(" Sorry ! Donor Not Found");

        }

        return cview;
    }

    


}
