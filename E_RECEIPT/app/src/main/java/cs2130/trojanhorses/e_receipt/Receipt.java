package cs2130.trojanhorses.e_receipt;

import java.util.UUID;

/**
 * Created by Aleix on 10/20/2017.
 */

public class Receipt {

    private UUID mId;
    private String mDate;
    private String mStore;
    private Item[] mItems;

    public Receipt (String date, String store){//}, Item[] itemList){
        mDate = date;
        mStore = store;
        //mItems = itemList;
        mId = UUID.randomUUID();
    }


    public Receipt() {
        mId = UUID.randomUUID();
    } /** I don't think that we should create empty Receipts */

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

    /** You don't need to add Items to a receipt, it comes with items already. */
    /*public void addItems(String items) {
        mItems.add(items);
    }*/
}
