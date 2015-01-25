package com.bbgatestudios.widgettest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by John on 10/15/2014.
 */
public class DetailActivity extends Activity implements DetailFragment.DetailListener {

    private final String TAG = "DETAILACTIVITY";

    private Contact mContact;
    private int mDelete;

    public static final String CONTACTEXTRA = "com.bbgatestudios.week3.com.bbgatestudios.widgettest.Contact";
    public static final String DELETEEXTRA = "com.bbgatestudios.week3.Delete";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null){
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new DetailFragment())
                    .commit();
        }

        Intent detailIntent = getIntent();
        if (detailIntent != null){
            mContact = (Contact) detailIntent.getSerializableExtra(CONTACTEXTRA);
            mDelete = detailIntent.getIntExtra(DELETEEXTRA, 0);
        }
    }

    @Override
    public Contact getContact(){
        return mContact;
    }

    @Override
    public int getDelete(){
        return mDelete;
    }

    @Override
    public void deleteContact(){
        Intent returnIntent = new Intent();
        returnIntent.putExtra(MainActivity.DELETECONTACTEXTRA, mDelete);
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.deleteButton:
                deleteContact();
                break;

            case R.id.contactButton:
                implicitSendEmail();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void implicitSendEmail(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, "Put your email text here.");
        intent.setType("text/plain");
        startActivity(intent);
    }


}