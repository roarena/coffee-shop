package eu.rodrigocamara.myladybucks.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;
import java.util.List;

import eu.rodrigocamara.myladybucks.R;
import eu.rodrigocamara.myladybucks.pojos.Coffee;
import eu.rodrigocamara.myladybucks.pojos.Order;
import eu.rodrigocamara.myladybucks.screens.dialogs.LoadingDialog;
import eu.rodrigocamara.myladybucks.screens.fragments.HomeFragment;

/**
 * Created by rodri on 28/01/2018.
 */

public class PlaceOrderTask extends AsyncTask<List<Coffee>, Void, Void> {
    private Context mContext;
    private LoadingDialog mLoadingDialog;
    private View mView;

    public PlaceOrderTask(Context context, View view) {
        this.mContext = context;
        this.mView = view;
        mLoadingDialog = new LoadingDialog((AppCompatActivity) mContext);
    }

    @Override
    protected Void doInBackground(List<Coffee>[] lists) {
        try {
            // Just to show off the BEAUTIFUL loading animation =))
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        FirebaseHelper.getDatabase().getReference().child(C.DB_ORDERS_REFERENCE).child(FirebaseAuth.
                getInstance().getCurrentUser().getUid()).child(String.valueOf(Calendar.getInstance()
                .getTimeInMillis())).setValue(new Order(lists[0], Calendar.getInstance().getTime()));
        OrderHelper.getInstance().getOrderList().clear();
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mLoadingDialog.show();
    }

    @Override
    protected void onPostExecute(Void param) {
        if (OrderHelper.getInstance().getOrderList().size() < 1) {
            mLoadingDialog.dismiss();
            try {
                Snackbar.make(mView, R.string.order_requested, Snackbar.LENGTH_SHORT).show();
                FragmentHelper.doFragmentTransaction(HomeFragment.class.newInstance(), (AppCompatActivity) mContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            mLoadingDialog.dismiss();
            Snackbar.make(mView, R.string.snack_bar_item_error, Snackbar.LENGTH_SHORT).show();
        }
    }
}
