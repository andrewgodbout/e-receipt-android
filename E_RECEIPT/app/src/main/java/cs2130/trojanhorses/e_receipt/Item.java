package cs2130.trojanhorses.e_receipt;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Aleix on 11/1/2017.
 */

/**This class represents an individual item that contains name of the product and its price*/

public class Item implements Parcelable{

    private String mItemName;
    private double mItemPrice;

    public Item(String itemName, double itemPrice) {
        mItemName = itemName;
        mItemPrice = itemPrice;
    }

    protected Item(Parcel in) {
        mItemName = in.readString();
        mItemPrice = in.readDouble();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    /**
     * Getters & Setters
     * */
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

    @Override
    public String toString() {
        return mItemName+": $"+mItemPrice;
    }

    /**
     * Parcelable Override methods
     * */
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mItemName);
        parcel.writeDouble(mItemPrice);
    }
}
