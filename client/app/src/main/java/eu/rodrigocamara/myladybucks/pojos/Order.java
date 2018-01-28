package eu.rodrigocamara.myladybucks.pojos;

import android.support.annotation.NonNull;

import org.parceler.Parcel;

import java.util.Date;
import java.util.List;

/**
 * Created by Rodrigo Câmara on 04/01/2018.
 */
@Parcel
public class Order implements Comparable {
    public List<Coffee> mCoffeeList;
    public String mStatus;
    public String mUserID;

    public Date getmDate() {
        return mDate;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

    private Date mDate;

    public Order() {

    }

    public Order(List<Coffee> coffeeList, Date date) {
        this.mCoffeeList = coffeeList;
        this.mStatus = "NEW";
        this.mDate = date;
    }

    public String getmUserID() {
        return mUserID;
    }

    public void setmUserID(String mUserID) {
        this.mUserID = mUserID;
    }

    public String getmStatus() {
        return mStatus;
    }

    public void setmStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    public List<Coffee> getmCoffeeList() {
        return mCoffeeList;
    }

    public void setmCoffeeList(List<Coffee> mCoffeeList) {
        this.mCoffeeList = mCoffeeList;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        Date compareDate = ((Order) o).getmDate();
        return compareDate.compareTo(this.mDate);
    }
}
