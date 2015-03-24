package com.example.lofm.personexample;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    TextView personView;
    ListView list;
    ImageView imgDel;
    ImageView imgEdit;
    static ArrayList<PersonRow> people;
    ArrayList<String> string_people;
    Context context;
    MyArrayAdapter myAdpter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        personView = (TextView)findViewById(R.id.personName);
        list = (ListView)findViewById(R.id.list);

        imgDel = (ImageView)findViewById(R.id.imgDelete);
        imgEdit = (ImageView)findViewById(R.id.imgEdit);

        people = new ArrayList<PersonRow>();
        string_people = new ArrayList<String>();
        //Fill with data
        for(int i=0;i<4;i++){
            PersonRow pr = new PersonRow();
            if(i==0){
                pr.setPerFirstName("Omar");
                pr.setPerLastName("Flores");
                pr.setPerGender("Male");
                pr.setPerHeight(new BigDecimal("1.74"));
                pr.setPerWeight(new BigDecimal("73"));
                pr.setPerCountry("Mexico");
                pr.setPerState("Coahuila");
            }
            if(i==1){
                pr.setPerFirstName("Marisela");
                pr.setPerLastName("Woo");
                pr.setPerGender("Female");
                pr.setPerHeight(new BigDecimal("1.62"));
                pr.setPerWeight(new BigDecimal("58"));
                pr.setPerCountry("Mexico");
                pr.setPerState("Coahuila");
            }
            if(i==2){
                pr.setPerFirstName("Scarlett");
                pr.setPerLastName("Johansson");
                pr.setPerGender("Female");
                pr.setPerHeight(new BigDecimal("1.60"));
                pr.setPerWeight(new BigDecimal("55"));
                pr.setPerCountry("United States");
                pr.setPerState("California");
            }
            if(i==3){
                pr.setPerFirstName("Michael");
                pr.setPerLastName("J. Fox");
                pr.setPerGender("Male");
                pr.setPerHeight(new BigDecimal("1.64"));
                pr.setPerWeight(new BigDecimal("63"));
                pr.setPerCountry("Canada");
                pr.setPerState("Alberta");
            }
            people.add(pr);
            string_people.add(pr.getPerFirstName());
        }
        people.trimToSize();
        string_people.trimToSize();
        context = getBaseContext();

        myAdpter = new MyArrayAdapter(this, android.R.layout.simple_list_item_1, R.id.personName, string_people);
        myAdpter.people = people;
        list.setAdapter(myAdpter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String person = people.get(position).getPerFirstName();
                personView.setText(person);
                imgDel.setImageResource(R.drawable.delete);
                imgEdit.setImageResource(R.drawable.edit);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //----------------------------------------------------------------------------------------------

    /**
     * Deletes selected person.
     * @param v
     */
    public void deletePerson(View v) {
        if(!personView.getText().toString().equalsIgnoreCase("")){
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
            //set title
            alertBuilder.setTitle("Delete Person");
            //set dialog message
            alertBuilder.setMessage("Do you want to delete this person?");
            alertBuilder.setCancelable(false);  //Its not possible to click in other part of the screen
            //Yes action
            alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    //delete person from list
                    for(int i=0;i<people.size();i++){
                        if(people.get(i).getPerFirstName().equalsIgnoreCase(personView.getText().toString())){
                            Toast.makeText(getApplicationContext(), "Deleted: "+people.get(i).getPerFirstName()+" "+people.get(i).getPerLastName(), Toast.LENGTH_SHORT).show();
                            myAdpter.remove(personView.getText().toString());
                            people.remove(i);
                            myAdpter.notifyDataSetChanged();
                            personView.setText("");
                            imgDel.setImageResource(0);
                            imgEdit.setImageResource(0);
                        }
                    }
                    dialog.cancel();
                }
            });
            //No action
            alertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    //if this button is clicked, just close the dialog box and do nothing
                    dialog.cancel();
                }
            });
            //create alert dialog
            AlertDialog alertDialog = alertBuilder.create();
            //show it
            alertDialog.show();
        }
    }

    /**
     * Opens PersonActivity with information of the selected person and Delete button
     * @param v
     */
    public void editPerson(View v) {
        Intent intent = new Intent(this, PersonActivity.class);
        Bundle extras = new Bundle();
        extras.putString("PERSON_MESSAGE", "EDIT PERSON");
        PersonRow personRow = null;
        for(int i=0;i<people.size();i++){
            if(people.get(i).getPerFirstName().equalsIgnoreCase(personView.getText().toString())){
                personRow = people.get(i);
                break;
            }
        }
        extras.putParcelable("PERSON_ROW_PARCELABLE", personRow);
        intent.putExtras(extras);
        startActivityForResult(intent, 1);
    }

    /**
     * Opens PersonActivity with empty fields and NO delete button
     * @param v
     */
    public void addPerson(View v) {
        Intent intent = new Intent(this, PersonActivity.class);
        Bundle extras = new Bundle();
        extras.putString("PERSON_MESSAGE", "ADD PERSON");
        PersonRow personRow = new PersonRow();
        extras.putParcelable("PERSON_ROW_PARCELABLE", personRow);
        intent.putExtras(extras);
        startActivityForResult(intent, 2);
    }

    /**
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        //check if the request code is same as what is passed here
        if(requestCode == 1){ //Listener for edit person. You can return deleting or saving data.
            if(resultCode == RESULT_OK){
                int person_to_delete = data.getIntExtra("PERSON_TO_DELETE",-1);
                int person_to_update = data.getIntExtra("PERSON_TO_UPDATE",-1);
                if(person_to_delete > -1){  //Delete person
                    Toast.makeText(getApplicationContext(), "Deleted: "+people.get(person_to_delete).getPerFirstName()+" "+people.get(person_to_delete).getPerLastName(), Toast.LENGTH_SHORT).show();
                    myAdpter.remove(personView.getText().toString());
                    people.remove(person_to_delete);
                    myAdpter.notifyDataSetChanged();
                    personView.setText("");
                    imgDel.setImageResource(0);
                    imgEdit.setImageResource(0);
                }
                if(person_to_update > -1){  //Update person
                    Toast.makeText(getApplicationContext(), "Person Updated", Toast.LENGTH_SHORT).show();
                    PersonRow personRowUpdate = data.getParcelableExtra("PERSON_ROW_RETURN");
                    people.set(person_to_update, personRowUpdate);
                    people.trimToSize();
                    myAdpter.notifyDataSetChanged();
                    personView.setText("");
                    imgDel.setImageResource(0);
                    imgEdit.setImageResource(0);
                }
            }
        }
        if(requestCode == 2){ //Listener for add person. You only can return saving data
            if(resultCode == RESULT_OK){
                int person_to_update = data.getIntExtra("PERSON_TO_UPDATE",-1);
                if(person_to_update > -1){  //Update person
                    Toast.makeText(getApplicationContext(), "Person Inserted", Toast.LENGTH_SHORT).show();
                    PersonRow personRowUpdate = data.getParcelableExtra("PERSON_ROW_RETURN");
                    myAdpter.add(personRowUpdate.getPerFirstName());
                    people.add(person_to_update,personRowUpdate);
                    people.trimToSize();
                    myAdpter.notifyDataSetChanged();
                    personView.setText("");
                    imgDel.setImageResource(0);
                    imgEdit.setImageResource(0);
                }
            }
        }
    }
}
