package com.example.lofm.personexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Android1 on 3/7/2015.
 */
public class MyArrayAdapter extends ArrayAdapter<String> {

    Context context;
    ArrayList<PersonRow> people;

    /**
     * Constructor
     * @param context
     * @param resource
     * @param textViewResourceId
     * @param string_people
     */
    public MyArrayAdapter(Context context, int resource, int textViewResourceId, ArrayList<String> string_people) {
        super(context, resource, textViewResourceId, string_people);
        this.context = context;
    }

    /**
     * Gets the view. It executes when you put this adapter to a list.
     */
    public View getView(int position, View convertView, ViewGroup parent){
        //It executes when "list.setAdapter(myAdpter);" is executed in MainAcctivity.java
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.list_item, parent, false);

        TextView tvFname = null;
        TextView tvLname = null;
        TextView tvCountry = null;

        tvFname = (TextView)row.findViewById(R.id.fName);
        tvFname.setText(people.get(position).getPerFirstName());

        tvLname = (TextView)row.findViewById(R.id.lName);
        tvLname.setText(people.get(position).getPerLastName());

        tvCountry = (TextView)row.findViewById(R.id.country);
        tvCountry.setText(people.get(position).getPerCountry());

        return row;
    }
}
