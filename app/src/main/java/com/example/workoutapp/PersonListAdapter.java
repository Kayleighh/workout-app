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
        String department = getItem(position).getDepartment();

        Profile profile = new Profile(name, surname, department);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvName = (TextView) convertView.findViewById(R.id.textView1);
        TextView tvSurname = (TextView) convertView.findViewById(R.id.textView2);
        TextView tvDepartment = (TextView) convertView.findViewById(R.id.textView3);

        tvName.setText(name);
        tvSurname.setText(surname);
        tvDepartment.setText(department);

        return convertView;
    }
}
