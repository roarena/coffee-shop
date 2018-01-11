package eu.rodrigocamara.myladybucks.listeners;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.Currency;
import java.util.Locale;

import eu.rodrigocamara.myladybucks.R;
import eu.rodrigocamara.myladybucks.pojos.Coffee;
import eu.rodrigocamara.myladybucks.screens.fragments.CoffeeMenuFragment;
import eu.rodrigocamara.myladybucks.screens.fragments.FullOrderFragment;
import eu.rodrigocamara.myladybucks.screens.fragments.ItemOrderFragment;
import eu.rodrigocamara.myladybucks.utils.C;
import eu.rodrigocamara.myladybucks.utils.FragmentHelper;
import eu.rodrigocamara.myladybucks.utils.Log;
import eu.rodrigocamara.myladybucks.utils.OrderHelper;

/**
 * Created by Rodrigo Câmara on 11/01/2018.
 */

public class ClickListeners {

    public static View.OnClickListener goToCartListener(final Context context) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Class fragmentClass;
                fragmentClass = FullOrderFragment.class;
                Fragment fragment = null;
                try {
                    fragment = (android.support.v4.app.Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Bundle bundle = new Bundle();
                bundle.putParcelable(C.BUNDLE_COFFEE, Parcels.wrap(OrderHelper.getInstance().getOrderList()));
                FragmentHelper.doFragmentTransaction(fragment, (AppCompatActivity) context, bundle);
            }
        };
    }

    public static View.OnClickListener menuCoffeeListener(final Coffee coffee, final Context context) {
        return new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.printLog("Coffee clicked: " + coffee.getName());

                Bundle bundle = new Bundle();
                bundle.putParcelable(C.BUNDLE_COFFEE, Parcels.wrap(coffee));
                try {
                    FragmentHelper.doFragmentTransaction(ItemOrderFragment.class.newInstance(), (AppCompatActivity) context, bundle);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public static View.OnClickListener controlCoffeeQuantityListener(final Coffee coffee, final TextView tvQuantity, final TextView tvTotalValue, final boolean isRemove) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity;
                if (isRemove) {
                    quantity = Integer.valueOf(tvQuantity.getText().toString()) - 1;
                } else {
                    quantity = Integer.valueOf(tvQuantity.getText().toString()) + 1;
                }

                tvQuantity.setText(String.format("%02d", quantity).toString());
                tvTotalValue.setText(Currency.getInstance(Locale.getDefault()).getSymbol() + String.valueOf(coffee.getPrice() * quantity));
            }
        };
    }

    public static View.OnClickListener addToCartListener(final Context context, final Coffee coffee, final TextView mTvQuantity) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                coffee.setQuantity(Integer.valueOf(mTvQuantity.getText().toString()));
                OrderHelper.getInstance().getOrderList().add(coffee);

                Snackbar.make(view, context.getResources().getString(R.string.snack_bar_item_added, coffee.getQuantity(), coffee.getName()), Snackbar.LENGTH_LONG).show();

                try {
                    FragmentHelper.doFragmentTransaction(CoffeeMenuFragment.class.newInstance(), (AppCompatActivity) context);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
    }
}
