package com.lucreziagrassi.androidapp.timetable;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lucreziagrassi.androidapp.R;
import com.lucreziagrassi.androidapp.db.FutureExam;
import com.lucreziagrassi.androidapp.db.Lesson;

import java.util.List;

public class TimetableListAdapter extends ArrayAdapter<Lesson> {

        private static final String TAG = "TimetableListAdapter";
        private Context mContext;
        private int mResource;

        TimetableListAdapter(Context context, int resource, List<Lesson> objects) {
            super(context, resource, objects);
            mContext = context;
            mResource = resource;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            String subject = getItem(position).getSubject();
            String professor = getItem(position).getProfessor();
            String location = getItem(position).getLocation();
            Integer color = getItem(position).getColor();
            String startHour = getItem(position).getStartHour();
            String endHour = getItem(position).getEndHour();

            Lesson lesson = new Lesson(0, subject, professor, location, color, startHour, endHour);
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);

            TextView tvSubject = (TextView) convertView.findViewById(R.id.subject_tv);
            TextView tvProfessor = (TextView) convertView.findViewById(R.id.professor_tv);
            TextView tvLocation = (TextView) convertView.findViewById(R.id.location_tv);
            ImageView tvColor1 = (ImageView) convertView.findViewById(R.id.color_tv1);
            ImageView tvColor2 = (ImageView) convertView.findViewById(R.id.color_tv2);
            TextView tvStartHour = (TextView) convertView.findViewById(R.id.start_hour);
            TextView tvEndHour = (TextView) convertView.findViewById(R.id.end_hour);

            tvSubject.setText(subject);
            tvProfessor.setText(professor);
            tvLocation.setText(location);
            tvColor1.setBackgroundColor(color);
            tvColor2.setBackgroundColor(color);
            tvStartHour.setText(startHour);
            tvEndHour.setText(endHour);

            return convertView;
        }
    }

