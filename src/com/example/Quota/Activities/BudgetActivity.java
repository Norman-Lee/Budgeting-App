package com.example.Quota.Activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.*;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.example.Quota.Fragments.BudgetFragment;
import com.example.Quota.R;
import com.example.Quota.Tools.Actions;
import com.example.Quota.Tools.Budget;
import com.example.Quota.Tools.BudgetItemAdapter;
import com.example.Quota.Tools.Item;

import java.util.ArrayList;

public class BudgetActivity extends FragmentActivity {

    private Budget mainBudget = new Budget("Monthly", 1000);

    private ViewPager budgetPager;

    private BudgetPagerAdapter budgetPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Budget foodBudget = new Budget("Food",500);
        foodBudget.addItem(new Item("Mangoes", 10));
        mainBudget.addSubBudget(foodBudget);
        mainBudget.addItem(new Item("Electric", 200));

        budgetPager = (ViewPager) findViewById(R.id.main_budget);
        budgetPagerAdapter = new BudgetPagerAdapter(getSupportFragmentManager());

        budgetPagerAdapter.setBudgetList(mainBudget);

        budgetPager.setAdapter(budgetPagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.budget_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }


    public void addItem(View view){
        Intent addItemIntent = new Intent(this, AddItemActivity.class);
        addItemIntent.setAction(Actions.ACTION_ADD_ITEM);
        startActivity(addItemIntent);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.add_subBudget:
                Intent intent = new Intent(this, AddSubBudgetActivity.class);
                intent.putExtra("remaining", mainBudget.getRemaining());
                startActivity(intent);
                return true;
            case R.id.navigate_budgets:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {

        if (intent.getExtras() != null) {

            int fragmentNumber;
            BudgetFragment bf;

            switch (intent.getAction()) {

                case Actions.ACTION_ADD_ITEM:
                    Item item = intent.getParcelableExtra("newItem");
                    fragmentNumber = budgetPager.getCurrentItem();
                    bf = (BudgetFragment) budgetPagerAdapter.getBudgetFragment(fragmentNumber);
                    bf.addItem(item);
                    break;

                case Actions.ACTION_EDIT_ITEM:
                    int position = intent.getIntExtra("position", -1);
                    Item editItem = intent.getParcelableExtra("editItem");
                    fragmentNumber = budgetPager.getCurrentItem();
                    bf = (BudgetFragment) budgetPagerAdapter.getBudgetFragment(fragmentNumber);
                    bf.editItem(position, editItem);
                    break;

                case Actions.ACTION_ADD_BUDGET:
                    Budget newBudget = intent.getParcelableExtra("subBudget");
                    mainBudget.addSubBudget(newBudget);
                    budgetPagerAdapter.setBudgetList(mainBudget);
                    budgetPagerAdapter.notifyDataSetChanged();
                    break;

                default:
                    break;

            }
        }
    }


    private static class BudgetPagerAdapter extends FragmentStatePagerAdapter{
        private SparseArray<BudgetFragment> budgetFragments = new SparseArray<BudgetFragment>();

        private Budget budgets;

        public BudgetPagerAdapter(android.support.v4.app.FragmentManager fm){
            super(fm);
        }

        public void setBudgetList(Budget budgets){
            this.budgets = budgets;
        }

        public Fragment getBudgetFragment(int position){
            return budgetFragments.get(position);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BudgetFragment fragment = (BudgetFragment) super.instantiateItem(container, position);
            budgetFragments.put(position, fragment);
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            budgetFragments.remove(position);
            super.destroyItem(container, position, object);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment;

            if(i == 0)
                fragment = new BudgetFragment(budgets);
            else {
                ArrayList<Budget> subBudgets = budgets.getSubBudgets();
                fragment = new BudgetFragment(subBudgets.get(i-1));
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return budgets.getSubBudgets().size()+1;
        }
    }
}
