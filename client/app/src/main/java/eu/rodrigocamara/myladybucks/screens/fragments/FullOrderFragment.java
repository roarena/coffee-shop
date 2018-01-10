package eu.rodrigocamara.myladybucks.screens.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.rodrigocamara.myladybucks.R;
import eu.rodrigocamara.myladybucks.adapters.OrderAdapter;
import eu.rodrigocamara.myladybucks.pojos.Coffee;
import eu.rodrigocamara.myladybucks.pojos.Order;
import eu.rodrigocamara.myladybucks.utils.FragmentHelper;
import eu.rodrigocamara.myladybucks.utils.OrderHelper;

/**
 * Created by Rodrigo Câmara on 04/01/2018.
 */

public class FullOrderFragment extends Fragment {
    @BindView(R.id.rv_order)
    RecyclerView rvOrder;

    @Nullable
    @BindView(R.id.btn_add_more_items)
    Button btnAddItems;

    @BindView(R.id.tv_order_full_item_total_value)
    TextView tvTotalValue;

    @BindView(R.id.btn_place_order)
    Button btnPlaceOrder;

    private OrderAdapter mOrderAdapter;
    private List<Order> mOrderList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_full,
                container, false);

        ButterKnife.bind(this, view);

        mOrderList = new ArrayList<>();
        mOrderList = Parcels.unwrap(getArguments().getParcelable("example"));
        mOrderAdapter = new OrderAdapter(view.getContext(), mOrderList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        rvOrder.setLayoutManager(mLayoutManager);
        rvOrder.setItemAnimator(new DefaultItemAnimator());
        rvOrder.setAdapter(mOrderAdapter);

        tvTotalValue.setText("$" + String.valueOf(setFinalOrderValue()));

        if (btnAddItems != null) {
            btnAddItems.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Class fragmentClass;
                    fragmentClass = CoffeeMenuFragment.class;
                    Fragment fragment = null;
                    try {
                        fragment = (android.support.v4.app.Fragment) fragmentClass.newInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    FragmentHelper.doFragmentTransaction(fragment, (AppCompatActivity) getContext());
                }
            });
        }

        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Class fragmentClass;
                fragmentClass = OrderDetailFragment.class;
                Fragment fragment = null;
                try {
                    fragment = (android.support.v4.app.Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Bundle bundle = new Bundle();
                bundle.putParcelable("example", Parcels.wrap(OrderHelper.getInstance().getOrderList()));
                FragmentHelper.doFragmentTransaction(fragment, (AppCompatActivity) getContext(), bundle);
            }
        });

        FragmentHelper.updateDrawerMenu(this.getActivity(), R.id.action_menu);

        return view;
    }

    private int setFinalOrderValue() {
        int price = 0;
        for (Order order : mOrderList) {
            price = price + (Integer.valueOf(order.getQuantity()) * Integer.valueOf(order.getCoffee().getPrice().substring(1)));
        }
        return price;
    }
}
