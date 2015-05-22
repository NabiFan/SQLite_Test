package com.nabi.nf70.sqlite_test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends ActionBarActivity {
    private List<Contact> _Contacts;
    private ContactAdapter _ContactAdapter;
    private ListView _List_view;
    public DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHelper(this);
        /**
         * CRUD Operations
         */
        // Inserting Contacts
        Log.d("Insert: ", "Inserting ..");
        db.addContact(new Contact("Ravi", "9100000000"));
        db.addContact(new Contact("Srinivas", "9199999999"));
        db.addContact(new Contact("Tommy", "9522222222"));
        db.addContact(new Contact("Karthik", "9533333333"));

        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        _Contacts = db.getAllContacts();

        _List_view = (ListView) this.findViewById(R.id.listView);
        _ContactAdapter = new ContactAdapter(this, android.R.layout.list_content, _Contacts);
        _List_view.setAdapter(_ContactAdapter);


        for (Contact cn : _Contacts) {
            String log = "Id " + cn.getID() + " ,Name: " + cn.getName() + " ,Phone: " + cn.getPhoneNumber();
            //Writing Contacts to log
            Log.d("Name: ", log);

            if (db.deleteContact(cn) > 0) {
                Log.d("Delete:", "Contact ID:" + cn.getID() + "Deleted Successfully!");
            }
        }

    }

    public void removeContact(Contact contact) {
        try {
            db.deleteContact(contact);
            Toast.makeText(getApplicationContext(), "Remove Sucessfully!", Toast.LENGTH_SHORT).show();
            _Contacts.clear();
            _Contacts.addAll(db.getAllContacts());
            _ContactAdapter.notifyDataSetChanged();
        } catch (Exception ex) {
            Log.e("Remove: ", "Remove Error", ex);
        }
    }

    public void updateContact(Contact contact) {
        try {
            Intent intent = new Intent(MainActivity.this, EditContactActivity.class);
            intent.putExtra("id", contact.getID());
            intent.putExtra("name", contact.getName());
            intent.putExtra("phone", contact.getPhoneNumber());
            //requestCode(識別碼) 型別為 int ,從B傳回來的物件將會有一樣的requestCode
            startActivityForResult(intent, 1);
        } catch (Exception ex) {
            Log.e("Update: ", "Update Error", ex);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        switch (resultCode){//resultCode是剛剛你A切換到B時設的resultCode
            case 1://當B傳回來的Intent的requestCode 等於當初A傳出去的話
                Contact contact = new Contact();
                contact.setID(data.getExtras().getInt("id"));
                contact.setName(data.getExtras().getString("name"));
                contact.setPhoneNumber(data.getExtras().getString("phone"));

                db.updateContact(contact);
                Toast.makeText(getApplicationContext(), " Update Sucessfully!", Toast.LENGTH_SHORT).show();
                _Contacts.clear();
                _Contacts.addAll(db.getAllContacts());
                _ContactAdapter.notifyDataSetChanged();
                break;
        }
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

    public void btnAdd_Click(View view) {
        EditText nameEditText = (EditText) this.findViewById(R.id.NameEditText);
        EditText phoneEditText = (EditText) this.findViewById(R.id.PhoneEditText);

        try {
            if (nameEditText.getText().toString().equals(""))
                return;
            Contact c = new Contact(nameEditText.getText().toString(), phoneEditText.getText().toString());
            db.addContact(c);
            nameEditText.setText("");
            phoneEditText.setText("");
            Toast.makeText(getApplicationContext(), "Insert Sucessfully!", Toast.LENGTH_SHORT).show();
            //ShowMsgDialog("Insert Sucessfully!", "Insert");
            // Reading all contacts
            _Contacts.clear();
            _Contacts.addAll(db.getAllContacts());
            _ContactAdapter.notifyDataSetChanged();
        } catch (Exception ex) {
            Log.e("Insert: ", "Insert Error", ex);
        }

    }
}
