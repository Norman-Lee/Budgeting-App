package com.example.Quota.Activities;

import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.EditText;
import com.example.Quota.R;
import com.example.Quota.Tools.Item;

public class AddItemActivity extends Activity {

    private EditText nameEdit;
    private EditText costEdit;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item_activity);


    }


    public void addItem(View view){
        //Need to include checks afterwards
        nameEdit = (EditText) findViewById(R.id.get_name);
        costEdit = (EditText) findViewById(R.id.get_cost);
        String name;
        double cost;

        if(nameEdit.getText() != null && costEdit.getText() != null) {
            name = nameEdit.getText().toString();

            //Need a null check here
            cost = Double.parseDouble(costEdit.getText().toString());

            Item returnItem = new Item(name, cost);

            Intent returnItemIntent = new Intent(this, BudgetActivity.class);
            returnItemIntent.setAction(Intent.ACTION_INSERT);
            returnItemIntent.putExtra("newItem", returnItem);
            startActivity(returnItemIntent);
        }
    }
}