package com.lucreziagrassi.androidapp.booklet;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lucreziagrassi.androidapp.R;
import com.lucreziagrassi.androidapp.db.Exam;

import java.util.List;

public class ExamListAdapter extends ArrayAdapter<Exam> {

    private static final String TAG = "ExamListAdapter";
    private Context mContext;
    private int mResource;

    public ExamListAdapter (Context context, int resource, List<Exam> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull @Override
    public View getView(int position, View convertView, ViewGroup parent){
        String subject = getItem(position).getSubject();
        String vote = getItem(position).getVote();
        int cfu = getItem(position).getCFU();
        String date = getItem(position).getDate();

        Exam exam = new Exam(0, subject, vote, date, cfu);
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

