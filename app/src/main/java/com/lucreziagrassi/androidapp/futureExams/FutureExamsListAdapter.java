package com.lucreziagrassi.androidapp.futureExams;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lucreziagrassi.androidapp.R;
import com.lucreziagrassi.androidapp.db.FutureExam;

import java.util.List;

public class FutureExamsListAdapter extends ArrayAdapter<FutureExam> {

    private static final String TAG = "FutureExamsListAdapter";
    private Context mContext;
    private int mResource;

    public FutureExamsListAdapter(Context context, int resource, List<FutureExam> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String subject = getItem(position).getSubject();
        int cfu = getItem(position).getCFU();
        String date = getItem(position).getDate();

        FutureExam futureExam = new FutureExam(0, subject, date, cfu);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvSubject = (TextView) convertView.findViewById(R.id.textView6);
        TextView tvDate = (TextView) convertView.findViewById(R.id.textView4);
        TextView tvCfu = (TextView) convertView.findViewById(R.id.textView5);

        tvSubject.setText(subject);
        tvCfu.setText("" + cfu);
        tvDate.setText(date);

        return convertView;
    }
}

