package com.lucreziagrassi.androidapp.subjects;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.lucreziagrassi.androidapp.R;
import com.lucreziagrassi.androidapp.db.Subject;
import java.util.List;

public class SubjectsListAdapter extends ArrayAdapter<Subject> {

    private static final String TAG = "SubjectsListAdapter";
    private Context mContext;
    private int mResource;

    SubjectsListAdapter(Context context, int resource, List<Subject> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String subject_name = getItem(position).getSubject();
        String prof = getItem(position).getProfessor();
        Integer color = getItem(position).getColor();

        Subject subject = new Subject(0, subject_name, prof, color);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvSubject = (TextView) convertView.findViewById(R.id.subject_tv);
        TextView tvProf = (TextView) convertView.findViewById(R.id.professor_tv);
        ImageView tvColor1 = (ImageView) convertView.findViewById(R.id.color_tv1);
        ImageView tvColor2 = (ImageView) convertView.findViewById(R.id.color_tv2);

        tvSubject.setText(subject_name);
        tvProf.setText(prof);
        tvColor1.setBackgroundColor(color);
        tvColor2.setBackgroundColor(color);

        return convertView;
    }
}

