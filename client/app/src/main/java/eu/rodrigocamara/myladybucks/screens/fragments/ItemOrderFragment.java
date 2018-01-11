package eu.rodrigocamara.myladybucks.screens.fragments;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.rodrigocamara.myladybucks.R;
import eu.rodrigocamara.myladybucks.listeners.ClickListeners;
import eu.rodrigocamara.myladybucks.listeners.TextChangeListeners;
import eu.rodrigocamara.myladybucks.pojos.Coffee;
import eu.rodrigocamara.myladybucks.utils.C;
import eu.rodrigocamara.myladybucks.utils.FragmentHelper;

/**
 * Created by Rodrigo Câmara on 04/01/2018.
 */

public class ItemOrderFragment extends Fragment {
    @BindView(R.id.tv_order_item_price)
    TextView mTvItemPrice;
    @BindView(R.id.tv_order_item_coffee_name)
    TextView mTvCoffeeName;
    @BindView(R.id.tv_order_item_coffee_description)
    TextView mTvCoffeeDesc;
    @BindView(R.id.tv_order_item_total_value)
    TextView mTvTotalValue;
    @BindView(R.id.tv_order_item_quantity)
    TextView mTvQuantity;
    @BindView(R.id.iv_order_item_add)
    ImageView mIvAddCoffee;
    @BindView(R.id.iv_order_item_remove)
    ImageView mIvRemoveCoffee;
    @BindView(R.id.btn_order_item_add_cart)
    Button mBtnAddCart;

    private Coffee mCoffee;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_item,
                container, false);

        ButterKnife.bind(this, view);
        mCoffee = Parcels.unwrap(getArguments().getParcelable(C.BUNDLE_COFFEE));

        mTvCoffeeName.setText(mCoffee.getName());
        mTvCoffeeDesc.setText(mCoffee.getDescription());
        mTvTotalValue.setText(mCoffee.printPrice());
        mTvItemPrice.setText(mCoffee.printPrice());

        mIvAddCoffee.setOnClickListener(ClickListeners.controlCoffeeQuantityListener(mCoffee, mTvQuantity, mTvTotalValue, false));
        mIvRemoveCoffee.setOnClickListener(ClickListeners.controlCoffeeQuantityListener(mCoffee, mTvQuantity, mTvTotalValue, true));
        mBtnAddCart.setOnClickListener(ClickListeners.addToCartListener(getContext(), mCoffee, mTvQuantity));
        mTvQuantity.addTextChangedListener(TextChangeListeners.coffeeQuantityListener(mIvRemoveCoffee));

        mIvRemoveCoffee.setEnabled(false);
        mIvRemoveCoffee.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);

        FragmentHelper.updateDrawerMenu(this.getActivity(), R.id.action_menu);
        return view;
    }
}
