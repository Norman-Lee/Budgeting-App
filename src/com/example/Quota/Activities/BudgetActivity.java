package com.example.Quota.Activities;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ListView;
import android.widget.TextView;
import com.example.Quota.R;
import com.example.Quota.Tools.Budget;
import com.example.Quota.Tools.BudgetItemAdapter;
import com.example.Quota.Tools.Item;

public class BudgetActivity extends Activity {

    Budget mainBudget = new Budget("Monthly", 1000);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ListView list = (ListView) findViewById(R.id.item_list);
        list.setAdapter(new BudgetItemAdapter(this,mainBudget.getBudgetItems()));

        TextView textView = (TextView) findViewById(R.id.budget_name);
        textView.setText(String.valueOf(mainBudget.name()));

        textView = (TextView) findViewById(R.id.remaining_cash);
        String remainder = "$" + String.valueOf(mainBudget.getRemaining());
        textView.setText(remainder);

        Typeface font = Typeface.createFromAsset(getAssets(), "Timeless.ttf");
        textView.setTypeface(font);

        textView = (TextView) findViewById(R.id.total_cash);
        String total = "Out of $" + String.valueOf(mainBudget.getTotal()) +" total.";
        textView.setText(total);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        return super.onTouchEvent(event);
    }



}
