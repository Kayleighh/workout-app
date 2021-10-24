package com.example.workoutapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PersonListAdapter extends ArrayAdapter<Profile> {

    private Context mContext;
    private int mResource;

    public PersonListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Profile> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position).getName();
        String surname = getItem(position).getSurname();
        Integer points = getItem(position).getPoints();
        String stringPoints = points.toString();

//        Profile profile = new Profile(name, surname, points);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvSurname = (TextView) convertView.findViewById(R.id.tvSurname);
        TextView tvPoints = (TextView) convertView.findViewById(R.id.tvPoints);

        tvName.setText(name);
        tvSurname.setText(surname);
        tvPoints.setText(stringPoints);

        return convertView;
    }
}