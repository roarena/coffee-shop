package eu.rodrigocamara.myladybucks.screens.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.rodrigocamara.myladybucks.R;
import eu.rodrigocamara.myladybucks.adapters.OrderAdapter;
import eu.rodrigocamara.myladybucks.adapters.OrderDetailAdapter;
import eu.rodrigocamara.myladybucks.pojos.Coffee;
import eu.rodrigocamara.myladybucks.pojos.Order;
import eu.rodrigocamara.myladybucks.utils.C;

/**
 * Created by Rodrigo Câmara on 10/01/2018.
 */

public class OrderDetailFragment extends Fragment {
    private OrderDetailAdapter mOrderAdapter;
    private List<Coffee> mOrderList;
    @BindView(R.id.rv_order_detail)
    RecyclerView rvOrder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_detail,
                container, false);
        ButterKnife.bind(this, view);

        mOrderList = new ArrayList<>();
        mOrderList = Parcels.unwrap(getArguments().getParcelable(C.BUNDLE_COFFEE));
        mOrderAdapter = new OrderDetailAdapter(view.getContext(), mOrderList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        rvOrder.setLayoutManager(mLayoutManager);
        rvOrder.setItemAnimator(new DefaultItemAnimator());
        rvOrder.setAdapter(mOrderAdapter);
        return view;
    }
}
