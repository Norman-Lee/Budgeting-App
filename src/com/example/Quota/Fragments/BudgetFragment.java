package com.example.Quota.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.*;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.example.Quota.Activities.AddItemActivity;
import com.example.Quota.Activities.AddSubBudgetActivity;
import com.example.Quota.R;
import com.example.Quota.Tools.Actions;
import com.example.Quota.Tools.Budget;
import com.example.Quota.Tools.BudgetItemAdapter;
import com.example.Quota.Tools.Item;


public class BudgetFragment extends android.support.v4.app.Fragment {

    private View rootView;
    private Budget subBudget;

    public BudgetFragment(Budget subBudget){
        this.subBudget = subBudget;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.budget_fragment_layout, container, false);

        if(rootView != null) {

            ListView list = (ListView) rootView.findViewById(R.id.item_list);
            list.setAdapter(new BudgetItemAdapter(getActivity(), subBudget.getBudgetItems()));

            registerForContextMenu(list);

            TextView textView = (TextView) rootView.findViewById(R.id.budget_name);
            textView.setText(String.valueOf(subBudget.name()));

            textView = (TextView) rootView.findViewById(R.id.remaining_cash);
            String remainder = "$" + String.valueOf(subBudget.getRemaining());
            textView.setText(remainder);

            Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "Timeless.ttf");
            textView.setTypeface(font);

            textView = (TextView) rootView.findViewById(R.id.total_cash);
            String total = "Out of $" + String.valueOf(subBudget.getTotal()) + " total.";
            textView.setText(total);
        }

        return rootView;
    }



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if(getActivity() != null) {
            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.edit_item, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        if(rootView != null && getUserVisibleHint()) {

            ListView list = (ListView) rootView.findViewById(R.id.item_list);

            switch (item.getItemId()) {
                case R.id.delete_item:
                    subBudget.removeItem(info.position);
                    ((BaseAdapter) list.getAdapter()).notifyDataSetChanged();

                    TextView textView = (TextView) rootView.findViewById(R.id.remaining_cash);
                    String remainder = "$" + String.valueOf(subBudget.getRemaining());
                    textView.setText(remainder);
                    return true;

                case R.id.edit_item:
                    Intent intent = new Intent(getActivity(), AddItemActivity.class);
                    intent.setAction(Actions.ACTION_EDIT_ITEM);
                    intent.putExtra("position", info.position);
                    intent.putExtra("editItem", subBudget.getItem(info.position));
                    startActivity(intent);
                    return true;

                default:
                    return super.onContextItemSelected(item);
            }
        }
        return  super.onContextItemSelected(item);
    }

    public void addItem(Item item){
        subBudget.addItem(item);

        ListView list = (ListView) rootView.findViewById(R.id.item_list);
        list.setAdapter(new BudgetItemAdapter(getActivity(), subBudget.getBudgetItems()));

        TextView textView = (TextView) rootView.findViewById(R.id.remaining_cash);
        String remainder = "$" + String.valueOf(subBudget.getRemaining());
        textView.setText(remainder);
    }

    public void editItem(int position, Item item){
        subBudget.editItem(position, item);

        ListView list = (ListView) rootView.findViewById(R.id.item_list);
        list.setAdapter(new BudgetItemAdapter(getActivity(), subBudget.getBudgetItems()));

        TextView textView = (TextView) rootView.findViewById(R.id.remaining_cash);
        String remainder = "$" + String.valueOf(subBudget.getRemaining());
        textView.setText(remainder);
    }
}