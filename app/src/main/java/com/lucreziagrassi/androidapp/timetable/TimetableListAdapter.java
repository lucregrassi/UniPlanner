package com.lucreziagrassi.androidapp.timetable;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lucreziagrassi.androidapp.R;
import com.lucreziagrassi.androidapp.db.DatabaseManager;
import com.lucreziagrassi.androidapp.db.Lesson;
import com.lucreziagrassi.androidapp.db.Subject;

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
            String subjectName = getItem(position).getSubject();

            Subject subject = DatabaseManager.getDatabase().getSubjectDao().get(subjectName);

            String professor = subject.getProfessor();
            Integer color = subject.getColor();

            String location = getItem(position).getLocation();
            String startHour = getItem(position).getStartHour();
            String endHour = getItem(position).getEndHour();

            Lesson lesson = new Lesson(0, subjectName, location, startHour, endHour, position);
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);

            TextView tvSubject = convertView.findViewById(R.id.subject_tv);
            TextView tvProfessor = convertView.findViewById(R.id.professor_tv);
            TextView tvLocation = convertView.findViewById(R.id.location_tv);
            ImageView tvColor1 = convertView.findViewById(R.id.color_tv1);
            ImageView tvColor2 = convertView.findViewById(R.id.color_tv2);
            TextView tvStartHour = convertView.findViewById(R.id.start_hour);
            TextView tvEndHour = convertView.findViewById(R.id.end_hour);

            tvSubject.setText(subjectName);
            tvProfessor.setText(professor);
            tvLocation.setText(location);
            tvColor1.setBackgroundColor(color);
            tvColor2.setBackgroundColor(color);
            tvStartHour.setText(startHour);
            tvEndHour.setText(endHour);

            return convertView;
        }
    }

