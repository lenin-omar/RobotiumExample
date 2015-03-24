package com.example.lofm.personexample;

import android.test.ActivityInstrumentationTestCase2;
import com.robotium.solo.Solo;


public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private Solo solo;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testCurrentActivity() throws Exception {
        //assert that the current activity is the SimpleListActivity.class
        solo.assertCurrentActivity("Wrong Activity", PersonActivity.class);
        //solo.assertCurrentActivity("Right Activity", MainActivity.class);
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
