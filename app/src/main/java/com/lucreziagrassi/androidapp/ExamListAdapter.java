package com.lucreziagrassi.androidapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lucreziagrassi on 01/08/18.
 */

public class ExamListAdapter extends ArrayAdapter<Exam> {

    private static final String TAG = "ExamListAdapter";
    private Context mContext;
    private int mResource;

    public ExamListAdapter (Context context, int resource, ArrayList<Exam>objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull @Override
    public View getView(int position, View convertView, ViewGroup parent){
        String subject = getItem(position).getSubject();
        String vote = getItem(position).getVote();
        int cfu = getItem(position).getCfu();
        String date = getItem(position).getDate();

        Exam exam = new Exam(subject, vote, date, cfu);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView= inflater.inflate(mResource, parent, false);

        TextView tvSubject = (TextView) convertView.findViewById(R.id.textView6);
        TextView tvVote = (TextView) convertView.findViewById(R.id.textView7);
        TextView tvDate = (TextView) convertView.findViewById(R.id.textView4);
        TextView tvCfu = (TextView) convertView.findViewById(R.id.textView5);

        tvSubject.setText(subject);
        tvVote.setText(vote);
        tvCfu.setText("" + cfu);
        tvDate.setText(date);

        return convertView;
    }
}

