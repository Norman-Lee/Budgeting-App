package com.example.Quota.Tools.Test;

import com.example.Quota.Tools.Budget;
import com.example.Quota.Tools.Item;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BudgetTest {

    private Budget testBudget;
    private Item item1;
    private Budget subBudget3;

    @Before
    public void setUp() throws Exception {
        testBudget = new Budget("Test", 2500);

        item1 = new Item("item1", 100);
        Item item2 = new Item("item2", 200);
        Item item3 = new Item("item3",300);

        testBudget.addItem(item1);
        testBudget.addItem(item2);
        testBudget.addItem(item3);

        Budget subBudget1 = new Budget("sub1", 100);
        Budget subBudget2 = new Budget("sub2", 200);
        subBudget3 = new Budget("sub3", 300);
        Budget subBudget4 = new Budget("sub4",400);

        testBudget.addSubBudget(subBudget1);
        testBudget.addSubBudget(subBudget2);
        testBudget.addSubBudget(subBudget3);
        testBudget.addSubBudget(subBudget4);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testIsOverBudget() {
        Assert.assertFalse(testBudget.isOverBudget());
    }

    @Test
    public void testAddSubBudget() {
        Budget subBudget5 = new Budget("sub5", 500);
        testBudget.addSubBudget(subBudget5);
        Assert.assertEquals(testBudget.getSubBudgets().size(), 5);
    }

    @Test
    public void testRemoveSubBudget() {
        testBudget.removeSubBudget("sub2");
        testBudget.removeSubBudget("sub3");
        Assert.assertEquals(testBudget.getSubBudgets().size(), 2);
    }

    @Test
    public void testAddItem() throws Exception {
        Item item4 = new Item("item4", 400);
        testBudget.addItem(item4);
        Assert.assertEquals(testBudget.getBudgetItems().size(), 4);
    }

    @Test
    public void testRemoveItem() throws Exception {
        testBudget.removeItem("item1");
        testBudget.removeItem("item2");
        testBudget.removeItem("item3");

        Assert.assertEquals(testBudget.getBudgetItems().size(), 0);
    }

    @Test
    public void testGetBudgetItems() throws Exception {
        Assert.assertTrue(testBudget.getBudgetItems().contains(item1));
    }

    @Test
    public void testGetSubBudgets() throws Exception {
        Assert.assertTrue(testBudget.getSubBudgets().contains(subBudget3));
    }

    @Test
    public void testGetRemaining() throws Exception {
        Assert.assertThat(testBudget.getRemaining(), CoreMatchers.is(1900.0));
    }

    @Test
    public void testGetTotal() throws Exception {
        Assert.assertThat(testBudget.getTotal(), CoreMatchers.is(2500.0));
    }

    @Test
    public void testName() throws Exception {

    }
}