package eu.rodrigocamara.myladybucks.screens.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.rodrigocamara.myladybucks.R;
import eu.rodrigocamara.myladybucks.adapters.CoffeeAdapter;
import eu.rodrigocamara.myladybucks.pojos.Coffee;
import eu.rodrigocamara.myladybucks.listeners.ClickListeners;
import eu.rodrigocamara.myladybucks.utils.FragmentHelper;
import eu.rodrigocamara.myladybucks.utils.OrderHelper;

/**
 * Created by Rodrigo Câmara on 04/01/2018.
 */

public class CoffeeMenuFragment extends Fragment {
    @BindView(R.id.rv_generic)
    RecyclerView rvCoffee;
    @BindView(R.id.btn_generic)
    Button btnCart;
    @BindView(R.id.tv_generic_title)
    TextView tvTitle;

    private CoffeeAdapter mCoffeeAdapter;
    private List<Coffee> mCoffeeList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.generic_fragment,
                container, false);

        ButterKnife.bind(this, view);

        setUIComponents(view.getContext());

        FragmentHelper.updateDrawerMenu(this.getActivity(), R.id.action_menu);

        return view;
    }

    private void setUIComponents(Context context) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("menu");
        mCoffeeList = new ArrayList<>();
        ChildEventListener childEventListener = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Coffee value = dataSnapshot.getValue(Coffee.class);
                mCoffeeList.add(value);
                mCoffeeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                mCoffeeList.clear();

                Coffee value = dataSnapshot.getValue(Coffee.class);
                mCoffeeList.add(value);
                mCoffeeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                mCoffeeList.clear();

                Coffee value = dataSnapshot.getValue(Coffee.class);
                mCoffeeList.add(value);
                mCoffeeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                mCoffeeList.clear();

                Coffee value = dataSnapshot.getValue(Coffee.class);
                mCoffeeList.add(value);
                mCoffeeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO Treat error properly
                databaseError.toException();
            }
        };
        myRef.addChildEventListener(childEventListener);
        tvTitle.setText(R.string.menu_title);

        if (OrderHelper.getInstance().getOrderList().size() > 0) {
            btnCart.setVisibility(View.VISIBLE);
            btnCart.setOnClickListener(ClickListeners.goToCartListener(context));
            btnCart.setText(R.string.go_to_cart_button);
        }


        mCoffeeAdapter = new CoffeeAdapter(context, mCoffeeList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rvCoffee.setLayoutManager(mLayoutManager);
        rvCoffee.setItemAnimator(new DefaultItemAnimator());
        rvCoffee.setAdapter(mCoffeeAdapter);

    }
}
