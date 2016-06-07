package com.nitkkr.sunnny.oasys;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * Created by SUNNNY on 23-01-2016.
 */
public class CustomResultAdapter extends ArrayAdapter<String> {

    String names[];
    String rollNo[];
    String totalA[];
    int total_lec;

    public CustomResultAdapter(Context context,String[] rollNo,String[] names,String totalA[],int total_lec) {
        super(context,0,rollNo);
        this.names=names;
        this.rollNo=rollNo;
        this.totalA=totalA;
        this.total_lec=total_lec;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.resultpopulate, parent, false);
            }
            TextView roll = (TextView) convertView.findViewById(R.id.rollNumberR);
            TextView name = (TextView) convertView.findViewById(R.id.nameR);
            TextView score=(TextView)convertView.findViewById(R.id.percentageR);
            roll.setText(rollNo[position]);
            name.setText(names[position]);
            double percentage=Integer.parseInt(totalA[position])*100.00/total_lec;
            DecimalFormat df=new DecimalFormat("#.#");
            score.setText(df.format(percentage)+"%");
        }catch (Exception e)
        {
            System.out.println("-------"+e.getMessage());
        }
        return convertView;
    }


}
