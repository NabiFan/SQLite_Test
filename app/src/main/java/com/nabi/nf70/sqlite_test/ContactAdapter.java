package com.nabi.nf70.sqlite_test;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Nabi.T.Fan on 2015/5/22.
 */
public class ContactAdapter extends ArrayAdapter<Contact> {
    private MainActivity mainActivity;

    public ContactAdapter(Context context, int resource) {
        super(context, resource);
    }

    public ContactAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public ContactAdapter(Context context, int resource, Contact[] objects) {
        super(context, resource, objects);
    }

    public ContactAdapter(Context context, int resource, int textViewResourceId, Contact[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public ContactAdapter(Context context, int resource, List<Contact> objects) {
        super(context, resource, objects);
        this.mainActivity = (MainActivity) context;
    }

    public ContactAdapter(Context context, int resource, int textViewResourceId, List<Contact> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Contact contact = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_item, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.NameTextView);
        TextView tvPhone = (TextView) convertView.findViewById(R.id.PhoneTextView);
        ImageButton btnRemove = (ImageButton) convertView.findViewById(R.id.btnRemove);
        btnRemove.setOnClickListener(new ItemButton_Click(this.mainActivity, position, contact) {
        });
        ImageButton btnUpdate = (ImageButton) convertView.findViewById(R.id.btnEdit);
        btnUpdate.setOnClickListener(new ItemButton_Click(this.mainActivity, position, contact) {
        });
        // Populate the data into the template view using the data object
        tvName.setText(contact.getName());
        tvPhone.setText(contact.getPhoneNumber());
        // Return the completed view to render on screen
        return convertView;
    }

    //自訂按鈕監聽事件
    class ItemButton_Click implements View.OnClickListener {
        private int position;
        private MainActivity mainActivity;
        private Contact contact;

        ItemButton_Click(MainActivity context, int pos, Contact contact) {
            this.mainActivity = context;
            this.position = pos;
            this.contact = contact;
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnRemove:
                    this.mainActivity.removeContact(this.contact);
                    Log.d("remove", "remove");
                    break;
                case R.id.btnEdit:
                    this.mainActivity.updateContact(this.contact);
                    Log.d("update", "update");
                    break;
                default:
                    break;
            }
        }
    }
}

