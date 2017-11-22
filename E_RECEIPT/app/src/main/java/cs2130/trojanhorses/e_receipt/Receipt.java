package cs2130.trojanhorses.e_receipt;

import android.util.Log;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Aleix on 10/20/2017.
 */

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

    public ArrayList<Item> convertToArrayList() {
        ArrayList<Item> arrayList = new ArrayList<>();
        for (Item i : mItems) {
            arrayList.add(i);
        }
        return arrayList;
    }

    /** Receive a JSONObject to parse and create an Item */
    public void setItems(Item[] items) {
        mItems = items;
    }

    public String getTotal() {

        DecimalFormat df = new DecimalFormat("#.00");
        double total = 0;
        for (Item i: mItems) {
            total += splitPrice(i.toString());
        }
        return "$"+df.format(total);
    }

    public Double splitPrice(String item) {
        //Log.d("MAN",item);
        String[] arrSplit = item.split(":");
        return Double.parseDouble(arrSplit[1].substring(2,arrSplit[1].length()-1));
    }

    public String splitName(String item) {
        String[] arrSplit = item.split(":");
        return (arrSplit[0]);
    }
    /** You don't need to add Items to a receipt, it comes with items already. */
    /*public void addItems(String items) {
        mItems.add(items);
    }*/

    public String itemToString() {
        String res="";

        res += splitName(mItems[0].toString())+" :$"+splitPrice(mItems[0].toString())+"\n";

        return res;
    }
}
