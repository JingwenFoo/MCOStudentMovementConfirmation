package com.example.mcostudentmovementconfirmation;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public abstract class AccountListAdapter extends CursorAdapter {
    public AccountListAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.studid_name_layout, parent, false);
    }
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView display_name = (TextView) view.findViewById(R.id.display_name);
        TextView display_studid = (TextView) view.findViewById(R.id.display_studid);
        // Extract properties from cursor
        String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        String id = cursor.getString(cursor.getColumnIndexOrThrow("studentID"));
        // Populate fields with extracted properties
        display_name.setText(name);
        display_studid.setText(id);
    }
}