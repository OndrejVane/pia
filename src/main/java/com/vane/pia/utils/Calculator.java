package com.vane.pia.utils;

import com.vane.pia.domain.Bill;
import com.vane.pia.domain.Item;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Calculator {


    /**
     * Funkce pro výpočet celkové ceny jedné položky faktury. Dle zákona o dani
     * z přidané hodnoty, se zaokrouhluje matematicky na nejbližší měnovou jednotku
     * v oběhu (tj. v současné době na celé koruny). To znamená, že částku 0,50 Kč
     * a vyšší zaokrouhlíme na celou korunu nahoru a částku nižší než 0,50 Kč
     * zaokrouhlíme na celou korunu dolů.
     *
     * @param item položka pro kterou jsou hodnoty počítány
     */
    public static void calculateFieldForItem(Item item) {
        // calculate vat amount from vat percentage rounded on two decimal places
        Float total = item.getCount() * item.getPrice();
        Float vatAmount = total * (item.getVatPercentage().floatValue() / 100);
        vatAmount = ((Double) (Math.round(vatAmount * 100.0) / 100.0)).floatValue();
        item.setVatAmount(vatAmount);

        //calculate total price as total + vat amount rounded on two decimal places
        Float totalPrice = ((Double) (Math.round((vatAmount + total) * 100.0) / 100.0)).floatValue();
        item.setTotalPrice(totalPrice);

        //calculate price without vat
        item.setPriceWithoutVat(((Double) (Math.round((total) * 100.0) / 100.0)).floatValue());
    }

    public static void calculateFieldsForBill(Bill bill){

        Float price = 0.0f;
        Float vat = 0.0f;

        for(Item item : bill.getItems()){
            price = price + item.getPriceWithoutVat();
            vat = vat + item.getVatAmount();
        }
        Float totalPrice = price + vat;

        bill.setPrice(((Double) (Math.round((price) * 100.0) / 100.0)).floatValue());
        bill.setVat(((Double) (Math.round((vat) * 100.0) / 100.0)).floatValue());
        bill.setTotalPrice(((Double) (Math.round((totalPrice) * 100.0) / 100.0)).floatValue());


    }
}
