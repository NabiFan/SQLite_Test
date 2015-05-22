package com.nabi.nf70.sqlite_test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class EditContactActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        Intent intent = this.getIntent();
        //取得傳遞過來的資料
        final int id = intent.getIntExtra("id", 1);
        final String name = intent.getStringExtra("name");
        final String phone = intent.getStringExtra("phone");
        final EditText nameEditText = (EditText) EditContactActivity.this.findViewById(R.id.NameEditText);
        final EditText phoneEditText = (EditText) EditContactActivity.this.findViewById(R.id.PhoneEditText);
        nameEditText.setText(name);
        phoneEditText.setText(phone);
        final Button btnSave =(Button)this.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnSave.getId() == R.id.btnSave)
                {
                    Intent intent = getIntent();
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", id);
                    bundle.putString("name", nameEditText.getText().toString());
                    bundle.putString("phone", phoneEditText.getText().toString());
                    intent.putExtras(bundle);
                    setResult(1, intent); //requestCode需跟A.class的一樣
                    EditContactActivity.this.finish();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_contact, menu);
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
}
