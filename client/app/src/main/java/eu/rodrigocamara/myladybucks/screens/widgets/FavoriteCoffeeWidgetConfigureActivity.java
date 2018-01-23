package eu.rodrigocamara.myladybucks.screens.widgets;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.rodrigocamara.myladybucks.R;
import eu.rodrigocamara.myladybucks.adapters.UserOrdersAdapter;
import eu.rodrigocamara.myladybucks.pojos.Order;
import eu.rodrigocamara.myladybucks.utils.C;
import eu.rodrigocamara.myladybucks.utils.FirebaseHelper;
import eu.rodrigocamara.myladybucks.utils.LoadingHelper;

/**
 * The configuration screen for the {@link FavoriteCoffeeWidget FavoriteCoffeeWidget} AppWidget.
 */
public class FavoriteCoffeeWidgetConfigureActivity extends Activity {

    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    @BindView(R.id.rv_generic)
    RecyclerView rvOrder;
    @BindView(R.id.tv_generic_title)
    TextView mTvTitle;
    List<Order> mOrderList;
    UserOrdersAdapter mOrderAdapter;
    @BindView(R.id.iv_generic_coffee_animation)
    ImageView ivCoffeeAnimation;

    private DatabaseReference mDatabaseReference;
    private ChildEventListener mMenuEventListener;

    private LoadingHelper mLoadingHelper;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);
        setContentView(R.layout.generic_fragment);

        ButterKnife.bind(this);

        mTvTitle.setText(R.string.favorite_order_title);
        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }


        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Toast.makeText(getBaseContext(), R.string.favorite_order_login_error, Toast.LENGTH_LONG).show();
            finish();
            return;
        } else {
            checkForOrders();

            mOrderList = new ArrayList<>();
            mDatabaseReference = FirebaseHelper.getDatabase().getReference(C.DB_ORDERS_REFERENCE).child(FirebaseAuth.getInstance().getUid());
            mOrderAdapter = new UserOrdersAdapter(mOrderList, getBaseContext(), mAppWidgetId, this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
            rvOrder.setLayoutManager(mLayoutManager);
            rvOrder.setItemAnimator(new DefaultItemAnimator());
            rvOrder.setAdapter(mOrderAdapter);

            loadOrders();
        }
    }

    private void checkForOrders() {
        mDatabaseReference = FirebaseHelper.getDatabase().getReference(C.DB_ORDERS_REFERENCE).child(FirebaseAuth.getInstance().getUid());
        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (!data.exists()) {
                        Toast.makeText(getBaseContext(), R.string.favorite_order_error, Toast.LENGTH_LONG).show();
                        finish();
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void loadOrders() {
        mDatabaseReference = FirebaseHelper.getDatabase().getReference(C.DB_ORDERS_REFERENCE).child(FirebaseAuth.getInstance().getUid());
        mMenuEventListener = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                refreshListValues(dataSnapshot.getValue(Order.class), false);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if (!dataSnapshot.exists()) {

                }
                refreshListValues(dataSnapshot.getValue(Order.class), true);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                refreshListValues(dataSnapshot.getValue(Order.class), true);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                refreshListValues(dataSnapshot.getValue(Order.class), true);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                databaseError.toException();
                mLoadingHelper.stopLoading();
            }
        };
        mDatabaseReference.addChildEventListener(mMenuEventListener);
        mLoadingHelper = new LoadingHelper(ivCoffeeAnimation, mMenuEventListener, mDatabaseReference, getBaseContext());
        mLoadingHelper.startLoading();
    }

    private void refreshListValues(Order value, boolean needsClear) {
        if (needsClear) {
            mOrderList.clear();
        }
        mOrderList.add(value);
        mOrderAdapter.notifyDataSetChanged();
        Collections.sort(mOrderList);
        mLoadingHelper.stopLoading();
        if (mOrderList.size() == 0) {

        }
    }

    public FavoriteCoffeeWidgetConfigureActivity() {
        super();
    }
}