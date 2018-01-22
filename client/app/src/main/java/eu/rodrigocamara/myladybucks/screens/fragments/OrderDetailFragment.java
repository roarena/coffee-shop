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
import android.widget.Toolbar;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.rodrigocamara.myladybucks.R;
import eu.rodrigocamara.myladybucks.adapters.OrderAdapter;
import eu.rodrigocamara.myladybucks.adapters.OrderDetailAdapter;
import eu.rodrigocamara.myladybucks.pojos.Coffee;
import eu.rodrigocamara.myladybucks.pojos.Order;
import eu.rodrigocamara.myladybucks.utils.C;
import eu.rodrigocamara.myladybucks.utils.Utils;

/**
 * Created by Rodrigo Câmara on 10/01/2018.
 */

public class OrderDetailFragment extends Fragment {
    private OrderDetailAdapter mOrderAdapter;
    private List<Coffee> mOrderList;
    @BindView(R.id.rv_generic)
    RecyclerView rvOrder;
    @BindView(R.id.iv_generic_title_image)
    ImageView mIvTitleImage;
    @BindView(R.id.tv_generic_total_value)
    TextView mTvTotalValue;
    @BindView(R.id.tv_generic_coffee_label_value)
    TextView mTvTotalLabel;
    @BindView(R.id.tv_generic_title)
    TextView mTvTitle;
    @BindView(R.id.generic_divider)
    View mDivider;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.generic_fragment,
                container, false);
        ButterKnife.bind(this, view);

        mOrderList = new ArrayList<>();
        mOrderList = Parcels.unwrap(getArguments().getParcelable(C.BUNDLE_ORDER));
        mOrderAdapter = new OrderDetailAdapter(view.getContext(), mOrderList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        rvOrder.setLayoutManager(mLayoutManager);
        rvOrder.setItemAnimator(new DefaultItemAnimator());
        rvOrder.setAdapter(mOrderAdapter);
        setUIComponents();
        return view;
    }

    private void setUIComponents() {
        mIvTitleImage.setVisibility(View.VISIBLE);
        mTvTotalValue.setVisibility(View.VISIBLE);
        mTvTotalLabel.setVisibility(View.VISIBLE);
        mTvTitle.setVisibility(View.VISIBLE);
        mDivider.setVisibility(View.VISIBLE);
        mTvTitle.setText(R.string.order_title);
        mTvTotalValue.setText(Currency.getInstance(Locale.getDefault()).getSymbol() + Utils.getFinalOrderValue(mOrderList));
    }
}
