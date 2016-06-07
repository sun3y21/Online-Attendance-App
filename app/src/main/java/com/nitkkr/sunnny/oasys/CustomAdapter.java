package com.nitkkr.sunnny.oasys;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;


/**
 * Created by SUNNNY on 21-01-2016.
 */
public class CustomAdapter extends ArrayAdapter<String> {

    String names[];
    String rollNo[];
    CheckBox checkBoxes[];
    boolean attendance[];
    public CustomAdapter(Context context,String[] rollNo,String[] names,boolean at[]) {
        super(context,0,rollNo);
        this.names=names;
        this.rollNo=rollNo;
        this.attendance=at;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
     try {
           convertView = LayoutInflater.from(getContext()).inflate(R.layout.listpopulate, parent, false);
           TextView roll = (TextView) convertView.findViewById(R.id.rollNumber);
           TextView name = (TextView) convertView.findViewById(R.id.name);
           roll.setText(rollNo[position]);
           name.setText(names[position]);
           final CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkbox1);
           if(attendance[position])
           {
               checkBox.setChecked(true);
           }

           checkBox.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   if(checkBox.isChecked())
                   {
                       attendance[position]=true;
                       checkBox.setChecked(true);
                   }
                   else
                   {
                       attendance[position]=false;
                       checkBox.setChecked(false);
                   }
               }
           });
       }catch (Exception e)
       {
           System.out.println("-------"+e.getMessage());
       }
        return convertView;
    }



    public CheckBox[] getCheckBoxes()
    {
        return checkBoxes;
    }
}
