package cs2130.trojanhorses.e_receipt;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Aleix on 10/20/2017.
 */

/**This class represents an individual receipt*/

public class Receipt implements Serializable{

    private UUID mId;
    private String mDate;
    private String mStore;
    private Item[] mItems;

    public Receipt (String date, String store, Item[] itemList){
        mDate = date;
        mStore = store;
        mItems = itemList;
        mId = UUID.randomUUID();
    }

    public Receipt(UUID id) {
        mId = UUID.randomUUID();
    }

    /** Getters & Setters */
    public UUID getId() {
        return mId;
    }
    
    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getStore() {
        return mStore;
    }

    public void setStore(String store) {
        mStore = store;
    }

    public Item[] getItems() {
        return mItems;
    }

    /**
     * Converts the Array of Items into an ArrayList
     * */
    public ArrayList<Item> convertToArrayList() {
        ArrayList<Item> arrayList = new ArrayList<>();
        for (Item i : mItems) {
            arrayList.add(i);
        }
        return arrayList;
    }

    /**
     * Receive a JSONObject to parse and create an Item
     * */
    public void setItems(Item[] items) {
        mItems = items;
    }

    /**
     * Returns a String with the total price of the Receipt
     * */
    public String getTotal() {

        DecimalFormat df = new DecimalFormat("#.00");
        double total = 0;
        for (Item i: mItems) {
            total += splitPrice(i.toString());
        }
        return "$"+df.format(total);
    }

    /**
     * SplitPrice gets the price value of the Item on the receipt
     * */
    public Double splitPrice(String item) {
        String[] arrSplit = item.split(":");
        return Double.parseDouble(arrSplit[1].substring(2,arrSplit[1].length()-1));
    }

    /**
     * SplitName gets the name value of the Item on the receipt
     * */
    public String splitName(String item) {
        String[] arrSplit = item.split(":");
        return (arrSplit[0]);
    }
}
