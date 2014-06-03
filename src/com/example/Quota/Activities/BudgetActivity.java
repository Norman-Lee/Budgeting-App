package com.example.Quota.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.*;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.example.Quota.R;
import com.example.Quota.Tools.Budget;
import com.example.Quota.Tools.BudgetItemAdapter;
import com.example.Quota.Tools.Item;

public class BudgetActivity extends Activity {

    private Budget mainBudget = new Budget("Monthly", 1000);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ListView list = (ListView) findViewById(R.id.item_list);
        list.setAdapter(new BudgetItemAdapter(this,mainBudget.getBudgetItems()));

        registerForContextMenu(list);

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

    public void addItem(View view){
        Intent addItemIntent = new Intent(this, AddItemActivity.class);
        addItemIntent.setAction(Intent.ACTION_INSERT);
        startActivity(addItemIntent);
    }


    @Override
    protected void onNewIntent(Intent intent) {

        if(intent.getExtras() != null){

            TextView textView;

            if(intent.getAction() == Intent.ACTION_INSERT) {
                Item item = intent.getParcelableExtra("newItem");
                mainBudget.addItem(item);
            }
            else if(intent.getAction() == Intent.ACTION_EDIT){
                int position = intent.getIntExtra("position", -1);
                Item newItem = intent.getParcelableExtra("editItem");

                mainBudget.editItem(position, newItem);
            }

            ListView list = (ListView) findViewById(R.id.item_list);
            list.setAdapter(new BudgetItemAdapter(this,mainBudget.getBudgetItems()));

            textView = (TextView) findViewById(R.id.remaining_cash);
            String remainder = "$" + String.valueOf(mainBudget.getRemaining());
            textView.setText(remainder);

        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_item, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        ListView list = (ListView) findViewById(R.id.item_list);
        switch(item.getItemId()){
            case R.id.delete_item:
                mainBudget.removeItem(info.position);
                ((BaseAdapter)list.getAdapter()).notifyDataSetChanged();

                TextView textView = (TextView) findViewById(R.id.remaining_cash);
                String remainder = "$" + String.valueOf(mainBudget.getRemaining());
                textView.setText(remainder);

                return true;

            case R.id.edit_item:
                    Intent intent = new Intent(this, AddItemActivity.class);
                    intent.setAction(Intent.ACTION_EDIT);
                    intent.putExtra("position", info.position);
                    intent.putExtra("editItem", mainBudget.getItem(info.position));
                    startActivity(intent);
                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }
}
