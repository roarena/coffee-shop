package eu.rodrigocamara.myladybucks.screens.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.rodrigocamara.myladybucks.R;
import eu.rodrigocamara.myladybucks.adapters.OrderAdapter;
import eu.rodrigocamara.myladybucks.pojos.Order;
import eu.rodrigocamara.myladybucks.utils.FragmentHelper;

/**
 * Created by Rodrigo Câmara on 04/01/2018.
 */

public class FullOrderFragment extends Fragment {
    @BindView(R.id.rv_order)
    RecyclerView rvOrder;

    private OrderAdapter mOrderAdapter;
    private List<Order> mOrderList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_full,
                container, false);

        ButterKnife.bind(this, view);

        mOrderList = new ArrayList<>();
        mOrderList =  Parcels.unwrap(getArguments().getParcelable("example"));
        mOrderAdapter = new OrderAdapter(view.getContext(), mOrderList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        rvOrder.setLayoutManager(mLayoutManager);
        rvOrder.setItemAnimator(new DefaultItemAnimator());
        rvOrder.setAdapter(mOrderAdapter);


        FragmentHelper.updateDrawerMenu(this.getActivity(), R.id.action_menu);

        return view;
    }
}
