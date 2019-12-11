package com.vane.pia.utils;

import com.vane.pia.domain.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    @Test
    void calculateFieldForItem() {
        // check first calculated fields
        Item item1 = new Item("Item1", "Description", 1000f, 3f, 21);
        Calculator.calculateFieldForItem(item1);
        Assertions.assertEquals(630.0f, item1.getVatAmount());
        Assertions.assertEquals(3630.0f, item1.getTotalPrice());
        Assertions.assertEquals(3000f, item1.getPriceWithoutVat());

        // check second calculated fields
        Item item2 = new Item("Item2", "Description", 3555f, 1f, 15);
        Calculator.calculateFieldForItem(item2);
        Assertions.assertEquals(533.25f, item2.getVatAmount());
        Assertions.assertEquals(4088.25f, item2.getTotalPrice());
        Assertions.assertEquals(3555f, item2.getPriceWithoutVat());

        // check third calculated fields
        Item item3 = new Item("Item3", "Description", 3555f, 2f, 10);
        Calculator.calculateFieldForItem(item3);
        Assertions.assertEquals(711.00f, item3.getVatAmount());
        Assertions.assertEquals(7821.00f, item3.getTotalPrice());
        Assertions.assertEquals(7110f, item3.getPriceWithoutVat());
    }

    @Test
    void calculateFieldsForBill() {
        // prepare data
        // TODO: Dopsat test až bude hotovej bill
        // assert results
    }
}
