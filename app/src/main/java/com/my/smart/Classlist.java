package com.my.smart;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class Classlist extends ArrayAdapter<Class> {

    private Activity context;
    private List<Class> facultyList;

    public Classlist(Activity context, List<Class> facultyList){
        super(context, R.layout.list_layout1, facultyList);
        this.context = context;
        this.facultyList = facultyList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);
        TextView text_name = (TextView) listViewItem.findViewById(R.id.textViewName1);

        Class faculty = facultyList.get(position);

        text_name.setText(faculty.getName());

        return listViewItem;

    }
}
