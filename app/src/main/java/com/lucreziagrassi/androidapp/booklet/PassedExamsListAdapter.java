package com.lucreziagrassi.androidapp.booklet;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lucreziagrassi.androidapp.R;
import com.lucreziagrassi.androidapp.db.PassedExam;

import java.util.List;

public class PassedExamsListAdapter extends ArrayAdapter<PassedExam> {

    private static final String TAG = "PassedExamsListAdapter";
    private Context mContext;
    private int mResource;

    public PassedExamsListAdapter(Context context, int resource, List<PassedExam> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String subject = getItem(position).getSubject();
        Integer vote = getItem(position).getVote();
        int cfu = getItem(position).getCFU();
        String date = getItem(position).getDate();

        PassedExam passedExam = new PassedExam(0, subject, vote, date, cfu);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvSubject = (TextView) convertView.findViewById(R.id.textView6);
        TextView tvVote = (TextView) convertView.findViewById(R.id.textView7);
        TextView tvDate = (TextView) convertView.findViewById(R.id.textView4);
        TextView tvCfu = (TextView) convertView.findViewById(R.id.textView5);

        tvSubject.setText(subject);
        tvVote.setText("" + vote);
        tvCfu.setText("" + cfu);
        tvDate.setText(date);

        return convertView;
    }
}

