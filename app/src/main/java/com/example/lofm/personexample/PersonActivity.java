package com.example.lofm.personexample;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.ArrayList;


public class PersonActivity extends ActionBarActivity implements View.OnFocusChangeListener, AdapterView.OnItemSelectedListener {

    EditText pFname,pLname,pGender,pHeight,pWeight;
    TextView pMode;
    PersonRow personRow;
    ImageView imgDel;
    Spinner pCountry, pState;
    ArrayAdapter<CharSequence> stateAdapter, countryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        //Get the type of edition
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String message = extras.getString("PERSON_MESSAGE");
        pMode = (TextView)findViewById(R.id.textViewMode);
        pMode.setText(message);
        imgDel = (ImageView)findViewById(R.id.imgDelete);
        if(message.equalsIgnoreCase("EDIT PERSON")){
            //Delete Button visible
            imgDel.setImageResource(R.drawable.delete);
        }else{
            //Delete Button invisible
            imgDel.setImageResource(0);
        }

        //Get the parcelable object
        personRow = extras.getParcelable("PERSON_ROW_PARCELABLE");
        if(personRow != null){
            pFname = (EditText)findViewById(R.id.EditTextFname);
            pLname = (EditText)findViewById(R.id.EditTextLname);
            pGender = (EditText)findViewById(R.id.EditTextGender);
            pHeight = (EditText)findViewById(R.id.EditTextHeight);
            pWeight = (EditText)findViewById(R.id.EditTextWeight);
            pCountry = (Spinner) findViewById(R.id.spinnerCountry);
            pState = (Spinner) findViewById(R.id.spinnerState);

            pFname.setText(personRow.getPerFirstName());
            pLname.setText(personRow.getPerLastName());
            pGender.setText(personRow.getPerGender());
            pHeight.setText(personRow.getPerHeight().setScale(2, BigDecimal.ROUND_HALF_EVEN).toString());
            pWeight.setText(personRow.getPerWeight().setScale(2, BigDecimal.ROUND_HALF_EVEN).toString());

            countryAdapter = ArrayAdapter.createFromResource(this,R.array.countries, R.layout.spinner_layout);
            countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            pCountry.setAdapter(countryAdapter);
            int spinnerCountryPosition = countryAdapter.getPosition(personRow.getPerCountry());
            pCountry.setSelection(spinnerCountryPosition);

            if(!personRow.getPerCountry().toString().equalsIgnoreCase("")){
                if(personRow.getPerCountry().toString().equalsIgnoreCase("Canada")){
                    stateAdapter = ArrayAdapter.createFromResource(this,R.array.states_canada, R.layout.spinner_layout);
                }
                if(personRow.getPerCountry().toString().equalsIgnoreCase("Mexico")){
                    stateAdapter = ArrayAdapter.createFromResource(this,R.array.states_mexico, R.layout.spinner_layout);
                }
                if(personRow.getPerCountry().toString().equalsIgnoreCase("Spain")){
                    stateAdapter = ArrayAdapter.createFromResource(this,R.array.states_spain, R.layout.spinner_layout);
                }
                if(personRow.getPerCountry().toString().equalsIgnoreCase("United States")){
                    stateAdapter = ArrayAdapter.createFromResource(this,R.array.states_usa, R.layout.spinner_layout);
                }
                stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                pState.setAdapter(stateAdapter);
                int spinnerStatePosition = stateAdapter.getPosition(personRow.getPerState());
                pState.setSelection(spinnerStatePosition);
            }

            pFname.setOnFocusChangeListener(this);
            pLname.setOnFocusChangeListener(this);
            pGender.setOnFocusChangeListener(this);
            pHeight.setOnFocusChangeListener(this);
            pWeight.setOnFocusChangeListener(this);
            pCountry.setOnItemSelectedListener(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_person, menu);
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

    /**
     * Deletes selected person.
     * @param v
     */
    public void deletePerson(View v) {
        if(pMode.getText().toString().equalsIgnoreCase("EDIT PERSON")){
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(PersonActivity.this);
            //set title
            alertBuilder.setTitle("Delete Person");
            //set dialog message
            alertBuilder.setMessage("Do you want to delete this person?");
            alertBuilder.setCancelable(false);  //Its not possible to click in other part of the screen
            //Yes action
            alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    //delete country from list
                    ArrayList<PersonRow> people = MainActivity.people;
                    for(int i=0;i<people.size();i++){
                        if(people.get(i).getPerFirstName().equalsIgnoreCase(personRow.getPerFirstName()) && people.get(i).getPerLastName().equalsIgnoreCase(personRow.getPerLastName())){
                            Intent returnIntent = new Intent();
                            Bundle extras = new Bundle();
                            extras.putInt("PERSON_TO_DELETE",i);
                            extras.putInt("PERSON_TO_UPDATE", -1);
                            returnIntent.putExtras(extras);
                            setResult(RESULT_OK,returnIntent);
                            break;
                        }
                    }
                    finish();
                    dialog.cancel();
                }
            });
            //No action
            alertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    //if this button is clicked, just close the dialog box and do nothing
                    Intent returnIntent = new Intent();
                    setResult(RESULT_CANCELED,returnIntent);
                    finish();
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
     * Saves existing/new person.
     * @param v
     */
    public void savePerson(View v) {
        //TODO: Validate fields before saving
        PersonRow personRowReturn = new PersonRow();
        personRowReturn.setPerFirstName(pFname.getText().toString());
        personRowReturn.setPerLastName(pLname.getText().toString());
        personRowReturn.setPerGender(pGender.getText().toString());
        try{
            personRowReturn.setPerHeight(new BigDecimal(pHeight.getText().toString()));
        }catch(Exception e){
            personRowReturn.setPerHeight(BigDecimal.ZERO);
            e.printStackTrace();
        }
        try{
            personRowReturn.setPerWeight(new BigDecimal(pWeight.getText().toString()));
        }catch(Exception e){
            personRowReturn.setPerWeight(BigDecimal.ZERO);
            e.printStackTrace();
        }
        personRowReturn.setPerCountry(pCountry.getSelectedItem().toString());
        personRowReturn.setPerState(pState.getSelectedItem().toString());

        ArrayList<PersonRow> people = MainActivity.people;
        if(pMode.getText().toString().equalsIgnoreCase("EDIT PERSON")){ //Update existing person
            int index = -1;
            for(int i=0;i<people.size();i++){
                if(people.get(i).getPerFirstName().equalsIgnoreCase(personRow.getPerFirstName()) && people.get(i).getPerLastName().equalsIgnoreCase(personRow.getPerLastName())){
                    index = i;
                    break;
                }
            }
            Intent returnIntent = new Intent();
            Bundle extras = new Bundle();
            extras.putInt("PERSON_TO_DELETE",-1);
            extras.putInt("PERSON_TO_UPDATE", index);
            extras.putParcelable("PERSON_ROW_RETURN", personRowReturn);
            returnIntent.putExtras(extras);
            setResult(RESULT_OK,returnIntent);
        }else{  //Insert new person
            Intent returnIntent = new Intent();
            Bundle extras = new Bundle();
            extras.putInt("PERSON_TO_DELETE",-1);
            extras.putInt("PERSON_TO_UPDATE", people.size());
            extras.putParcelable("PERSON_ROW_RETURN", personRowReturn);
            returnIntent.putExtras(extras);
            setResult(RESULT_OK,returnIntent);
        }
        finish();
    }

    /**
     * Opens Image Loader
     * @param v
     */
    public void openImageLoader(View v) {
        Toast.makeText(getApplicationContext(), "Open Image Loader", Toast.LENGTH_SHORT).show();
    }

    /**
     *
     * @param v
     * @param hasFocus
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(!hasFocus){
            switch(v.getId()){
                case R.id.EditTextFname:
                    if(validateString(pFname.getText().toString()) || validateNumber(pFname.getText().toString())){
                        pFname.setText("");
                        Toast.makeText(getApplicationContext(), "Invalid First Name", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.EditTextLname:
                    if(validateString(pLname.getText().toString()) || validateNumber(pLname.getText().toString())){
                        pLname.setText("");
                        Toast.makeText(getApplicationContext(), "Invalid Last Name", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.EditTextGender:
                    if(validateString(pGender.getText().toString()) || validateNumber(pGender.getText().toString())){
                        pGender.setText("");
                        Toast.makeText(getApplicationContext(), "Invalid Gender", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.EditTextHeight:
                    if(!validateNumber(pHeight.getText().toString())){
                        pHeight.setText("");
                        Toast.makeText(getApplicationContext(), "Invalid Height", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.EditTextWeight:
                    if(!validateNumber(pWeight.getText().toString())){
                        pWeight.setText("");
                        Toast.makeText(getApplicationContext(), "Invalid Weight", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }

    /**
     * Validates the content of the EdidtText. It must not contain ony numeric or blank char.
     * @param textInfo
     * @return true if invalid, false if valid
     */
    public boolean validateString(String textInfo){
        boolean invalid = true;
        char[] chars = textInfo.toCharArray();
        for(int i=0;i<chars.length;i++){
            if(chars[i]!=' '){
                invalid = false;
            }
        }
        if(chars.length<1){
            invalid = true;
        }
        return invalid;
    }

    /**
     * Validates if textInfo is numeric
     * @param textInfo
     * @return true if valid, false if invalid
     */
    public boolean validateNumber(String textInfo){
        boolean valid;
        try {
            new BigDecimal(textInfo);
            valid = true;
        }catch(NumberFormatException nfe){
            valid = false;
        }
        return valid;
    }

    /**
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Object item = parent.getItemAtPosition(position);
        if(!item.toString().equalsIgnoreCase("")){
            if(item.toString().equalsIgnoreCase("Canada")){
                stateAdapter = ArrayAdapter.createFromResource(this,R.array.states_canada, R.layout.spinner_layout);
            }
            if(item.toString().equalsIgnoreCase("Mexico")){
                stateAdapter = ArrayAdapter.createFromResource(this,R.array.states_mexico, R.layout.spinner_layout);
            }
            if(item.toString().equalsIgnoreCase("Spain")){
                stateAdapter = ArrayAdapter.createFromResource(this,R.array.states_spain, R.layout.spinner_layout);
            }
            if(item.toString().equalsIgnoreCase("United States")){
                stateAdapter = ArrayAdapter.createFromResource(this,R.array.states_usa, R.layout.spinner_layout);
            }
            stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            pState.setAdapter(stateAdapter);
            int spinnerStatePosition = stateAdapter.getPosition(personRow.getPerState());
            if(spinnerStatePosition == -1){
                spinnerStatePosition = 0;
            }
            pState.setSelection(spinnerStatePosition);
        }
    }

    /**
     *
     * @param parent
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //Do nothing
    }
}
