package cs2130.trojanhorses.e_receipt;

/**
 * Created by Aleix on 11/1/2017.
 */

public class Item {

    private String mItemName;
    private double mItemPrice;

    public Item(String itemName, double itemPrice) {
        mItemName = itemName;
        mItemPrice = itemPrice;
    }

    public String getItemName() {
        return mItemName;
    }

    public void setItemName(String itemName) {
        this.mItemName = itemName;
    }

    public double getItemPrice() {
        return mItemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.mItemPrice = itemPrice;
    }
}
