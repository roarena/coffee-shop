package eu.rodrigocamara.myladybucks.screens.fragments;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.rodrigocamara.myladybucks.R;
import eu.rodrigocamara.myladybucks.pojos.Coffee;
import eu.rodrigocamara.myladybucks.pojos.Order;
import eu.rodrigocamara.myladybucks.utils.FragmentHelper;
import eu.rodrigocamara.myladybucks.utils.OrderHelper;

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
    ImageView mIvRemoveCoffeee;
    @BindView(R.id.btn_order_item_add_cart)
    Button mBtnAddCart;


    private Coffee mCoffee;
    private List<Order> mOrderList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_item,
                container, false);

        ButterKnife.bind(this, view);
        mCoffee = Parcels.unwrap(getArguments().getParcelable("example"));
        mOrderList = new ArrayList<>();
        OrderHelper.getInstance().setOrderList(mOrderList);
        mTvCoffeeName.setText(mCoffee.getName());
        mTvCoffeeDesc.setText(mCoffee.getDescription());
        mTvTotalValue.setText(mCoffee.getPrice());
        mTvItemPrice.setText(mCoffee.getPrice());
        mIvAddCoffee.setOnClickListener(addCoffeeListener());
        mIvRemoveCoffeee.setOnClickListener(removeCoffeeListener());
        mBtnAddCart.setOnClickListener(addToCart());
        FragmentHelper.updateDrawerMenu(this.getActivity(), R.id.action_menu);
        toggleRemoveButton();
        return view;
    }

    private View.OnClickListener addCoffeeListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.valueOf(mTvQuantity.getText().toString()) + 1;
                mTvQuantity.setText(String.format("%02d", quantity).toString());

                float price = Float.parseFloat(mTvTotalValue.getText().toString().substring(1));
                price = price + Float.parseFloat(mCoffee.getPrice().substring(1));
                mTvTotalValue.setText("$" + String.valueOf(price));
                toggleRemoveButton();
            }
        };
    }

    private View.OnClickListener removeCoffeeListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.valueOf(mTvQuantity.getText().toString());
                if (quantity > 1) {
                    quantity = Integer.valueOf(mTvQuantity.getText().toString()) - 1;
                    mTvQuantity.setText(String.format("%02d", quantity).toString());

                    float price = Float.parseFloat(mTvTotalValue.getText().toString().substring(1));
                    price = price - Float.parseFloat(mCoffee.getPrice().substring(1));
                    mTvTotalValue.setText("$" + String.valueOf(price));
                }
                toggleRemoveButton();
            }
        };
    }

    private void toggleRemoveButton() {
        if (Integer.valueOf(mTvQuantity.getText().toString()) > 1) {
            mIvRemoveCoffeee.setEnabled(true);
            mIvRemoveCoffeee.setColorFilter(null);
        } else {
            mIvRemoveCoffeee.setEnabled(false);
            mIvRemoveCoffeee.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
        }
    }

    private View.OnClickListener addToCart() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Order order = new Order();
                order.setCoffee(mCoffee);
                order.setQuantity(Integer.valueOf(mTvQuantity.getText().toString()));
                OrderHelper.getInstance().getOrderList().add(order);
                Class fragmentClass;
                fragmentClass = FullOrderFragment.class;
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
        };
    }
}
