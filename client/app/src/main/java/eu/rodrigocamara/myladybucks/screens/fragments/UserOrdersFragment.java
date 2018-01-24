package eu.rodrigocamara.myladybucks.screens.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

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
 * Created by Rodrigo Câmara on 16/01/2018.
 */

public class UserOrdersFragment extends Fragment {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.generic_fragment,
                container, false);
        ButterKnife.bind(this, view);

        setUIComponents();
        return view;
    }

    private void setUIComponents() {
        mTvTitle.setText(R.string.orders_title);
        loadOrders();

        mOrderAdapter = new UserOrdersAdapter(mOrderList, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvOrder.setLayoutManager(mLayoutManager);
        rvOrder.setItemAnimator(new DefaultItemAnimator());
        rvOrder.setAdapter(mOrderAdapter);
    }

    private void loadOrders() {
        mOrderList = new ArrayList<>();

        mDatabaseReference = FirebaseHelper.getDatabase().getReference(C.DB_ORDERS_REFERENCE).child(FirebaseAuth.getInstance().getUid());
        mMenuEventListener = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                refreshListValues(dataSnapshot.getValue(Order.class), false);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
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
        mLoadingHelper = new LoadingHelper(ivCoffeeAnimation, mMenuEventListener, mDatabaseReference, getContext());
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
    }
}
