package com.example.Quota.Activities;

import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.Quota.R;
import com.example.Quota.Tools.Actions;
import com.example.Quota.Tools.Item;

import java.util.StringTokenizer;

public class AddItemActivity extends Activity {

    private EditText nameEdit;
    private EditText costEdit;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item_activity);

        Intent intent = getIntent();
        Button button = (Button) findViewById(R.id.confirm);

        if(intent.getAction() == Actions.ACTION_ADD_ITEM){
            button.setText(R.string.add_item_text);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addItem(v);
                }
            });
        }
        else if(intent.getAction() == Actions.ACTION_EDIT_ITEM){
            button.setText(R.string.edit_item_text);

            Item item = intent.getParcelableExtra("editItem");

            nameEdit = (EditText) findViewById(R.id.get_name);
            costEdit = (EditText) findViewById(R.id.get_cost);

            nameEdit.setText(item.name());
            costEdit.setText(String.valueOf(-1* item.cost()));


            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editItem(v);
                }
            });
        }
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
            returnItemIntent.setAction(Actions.ACTION_ADD_ITEM);
            returnItemIntent.putExtra("newItem", returnItem);
            startActivity(returnItemIntent);

        }
    }

    //Need to refactor
    public void editItem(View view){
        int position = getIntent().getIntExtra("position", -1);
        if(nameEdit.getText() != null && costEdit.getText() != null) {
            String name = nameEdit.getText().toString();

            //Need a null check here
            Double cost = Double.parseDouble(costEdit.getText().toString());

            Item returnItem = new Item(name, cost);

            Intent returnItemIntent = new Intent(this, BudgetActivity.class);
            returnItemIntent.setAction(Actions.ACTION_EDIT_ITEM);
            returnItemIntent.putExtra("position", position);
            returnItemIntent.putExtra("editItem", returnItem);
            startActivity(returnItemIntent);

        }
    }
}