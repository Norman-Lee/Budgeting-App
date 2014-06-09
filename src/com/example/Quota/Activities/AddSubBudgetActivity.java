package com.example.Quota.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.Quota.R;
import com.example.Quota.Tools.Actions;
import com.example.Quota.Tools.Budget;

public class AddSubBudgetActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_subbudget_layout);
    }


    public void addSubBudget(View view){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            double remaining = bundle.getDouble("remaining");

            EditText nameEdit = (EditText) findViewById(R.id.get_subBudget_name);
            EditText totalEdit = (EditText) findViewById(R.id.get_total);

            if(nameEdit.getText() != null && totalEdit.getText() != null){
                String budgetName = nameEdit.getText().toString();
                //Need null check
                double total = Double.parseDouble(totalEdit.getText().toString());

                if(total <= remaining) {
                    Budget newBudget = new Budget(budgetName, total);
                    Intent addSubBudgetIntent = new Intent(this, BudgetActivity.class);
                    addSubBudgetIntent.setAction(Actions.ACTION_ADD_BUDGET);
                    addSubBudgetIntent.putExtra("subBudget", newBudget);
                    startActivity(addSubBudgetIntent);
                }
                else{
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    Toast.makeText(context, "Total is bigger than the remaining amount allotted", duration).show();

                }

            }


        }

    }
}