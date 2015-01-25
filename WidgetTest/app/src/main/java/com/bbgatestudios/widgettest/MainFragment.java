package com.bbgatestudios.widgettest;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by John on 10/15/2014.
 */
public class MainFragment extends Fragment {
    private final String TAG = "MAINFRAGMENT";

    private ContactListener mListener;
    private ActionMode mActionMode;
    private ArrayAdapter<Contact> mContactList;
    private int mContactSelected = -1;
    private static ListView contactListView;
    private static ContactAdapter contactAdapter;

    public interface ContactListener{
        public void viewContact(int position);
        public void deleteContact(int position);
        public ArrayList<Contact> getContacts();
    }

    public MainFragment(){
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        if (activity instanceof ContactListener){
            mListener = (ContactListener) activity;
        }else {
            throw new IllegalArgumentException("Containing activity must implement DetailListener interface");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        contactListView = (ListView) getView().findViewById(R.id.contactList);
        contactAdapter = new ContactAdapter(getActivity(), mListener.getContacts());
        contactListView.setAdapter(contactAdapter);
        contactListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (mActionMode != null){
                    return false;
                }
                mContactSelected = position;
                mActionMode = getActivity().startActionMode(mActionModeCallback);
                return true;
            }
        });
    }

    public void updateListData(){
        ListView contactList = (ListView) getView().findViewById(R.id.contactList);
        BaseAdapter contactAdapter = (BaseAdapter) contactList.getAdapter();
        contactAdapter.notifyDataSetChanged();
    }

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.contact_menu, menu);
            return true;
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.contactDelete:
                    Log.i(TAG, String.valueOf(mContactList.getItem(mContactSelected)));

                    return true;
                default:
                    return false;
            }
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
        }
    };
}
