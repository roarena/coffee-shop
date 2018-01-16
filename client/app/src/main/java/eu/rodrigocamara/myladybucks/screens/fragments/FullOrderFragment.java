package eu.rodrigocamara.myladybucks.screens.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Currency;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.rodrigocamara.myladybucks.R;
import eu.rodrigocamara.myladybucks.adapters.OrderAdapter;
import eu.rodrigocamara.myladybucks.listeners.ClickListeners;
import eu.rodrigocamara.myladybucks.utils.FragmentHelper;
import eu.rodrigocamara.myladybucks.utils.OrderHelper;
import eu.rodrigocamara.myladybucks.interfaces.QuantityHandler;
import eu.rodrigocamara.myladybucks.utils.Utils;

/**
 * Created by Rodrigo Câmara on 04/01/2018.
 */

public class FullOrderFragment extends Fragment implements QuantityHandler {
    @BindView(R.id.rv_generic)
    RecyclerView mRvOrder;
    @BindView(R.id.btn_generic_add_more_items)
    Button mBtnAddItems;
    @BindView(R.id.btn_generic)
    Button mBtnPlaceOrder;
    @BindView(R.id.tv_generic_title)
    TextView mTvTitle;
    @BindView(R.id.tv_generic_total_value)
    TextView tvTotalValue;
    @BindView(R.id.tv_generic_coffee_label_value)
    TextView tvTotalLabel;
    @BindView(R.id.generic_divider)
    View mDivider;

    private OrderAdapter mOrderAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.generic_fragment,
                container, false);

        ButterKnife.bind(this, view);

        mOrderAdapter = new OrderAdapter(view.getContext(), OrderHelper.getInstance().getOrderList(), this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        mRvOrder.setLayoutManager(mLayoutManager);
        mRvOrder.setItemAnimator(new DefaultItemAnimator());
        mRvOrder.setAdapter(mOrderAdapter);

        FragmentHelper.updateDrawerMenu(this.getActivity(), R.id.action_menu);
        setUIComponents();
        return view;
    }

    private void setUIComponents() {
        mBtnAddItems.setVisibility(View.VISIBLE);
        mBtnPlaceOrder.setVisibility(View.VISIBLE);
        mDivider.setVisibility(View.VISIBLE);
        tvTotalValue.setVisibility(View.VISIBLE);
        tvTotalLabel.setVisibility(View.VISIBLE);

        mTvTitle.setText(R.string.order_full_title);
        tvTotalValue.setText(Currency.getInstance(Locale.getDefault()).getSymbol() + String.valueOf(Utils.getFinalOrderValue()));

        mBtnAddItems.setOnClickListener(ClickListeners.goToMenuListener(getContext()));
        mBtnPlaceOrder.setOnClickListener(ClickListeners.placeOrder(getContext(), OrderHelper.getInstance().getOrderList()));
    }


    @Override
    public void handleUiChanges() {
        if (Utils.getFinalOrderValue() < 1) {
            mBtnPlaceOrder.setVisibility(View.GONE);
        }
        tvTotalValue.setText(Currency.getInstance(Locale.getDefault()).getSymbol() + Utils.getFinalOrderValue());
    }
}
