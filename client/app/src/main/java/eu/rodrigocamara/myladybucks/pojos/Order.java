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
    private List<Coffee> mCoffeeList;
    private String mStatus;
    private String mUserID;
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

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        this.mDate = date;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        Date compareDate = ((Order) o).getDate();
        /* For Ascending order*/
        return compareDate.compareTo(this.mDate);

    }
}
