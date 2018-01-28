package eu.rodrigocamara.myladybucks.listeners;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import org.parceler.Parcels;

import java.util.Calendar;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import eu.rodrigocamara.myladybucks.R;
import eu.rodrigocamara.myladybucks.adapters.OrderAdapter;
import eu.rodrigocamara.myladybucks.interfaces.QuantityHandler;
import eu.rodrigocamara.myladybucks.pojos.Coffee;
import eu.rodrigocamara.myladybucks.pojos.Order;
import eu.rodrigocamara.myladybucks.screens.dialogs.LoadingDialog;
import eu.rodrigocamara.myladybucks.screens.fragments.CoffeeMenuFragment;
import eu.rodrigocamara.myladybucks.screens.fragments.FullOrderFragment;
import eu.rodrigocamara.myladybucks.screens.fragments.HomeFragment;
import eu.rodrigocamara.myladybucks.screens.fragments.ItemOrderFragment;
import eu.rodrigocamara.myladybucks.screens.fragments.OrderDetailFragment;
import eu.rodrigocamara.myladybucks.utils.C;
import eu.rodrigocamara.myladybucks.utils.FirebaseHelper;
import eu.rodrigocamara.myladybucks.utils.FragmentHelper;
import eu.rodrigocamara.myladybucks.utils.OrderHelper;
import eu.rodrigocamara.myladybucks.utils.PlaceOrderTask;
import eu.rodrigocamara.myladybucks.utils.SharedPreferenceHelper;

import static android.app.Activity.RESULT_OK;

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

    public static View.OnClickListener menuCoffeeListener(final Coffee coffee, final Context context, final ImageView coffeeImage) {
        return new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(C.BUNDLE_COFFEE, Parcels.wrap(coffee));
                try {
                    FragmentHelper.doFragmentTransaction(ItemOrderFragment.class.newInstance(), (AppCompatActivity) context, bundle, coffeeImage);
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

                tvQuantity.setText(String.format(C.FORMAT_DIGITS, quantity).toString());
                tvTotalValue.setText(Currency.getInstance(Locale.getDefault()).getSymbol() + String.valueOf(coffee.getPrice() * quantity));
            }
        };
    }

    public static View.OnClickListener controlCoffeeQuantityListener(final Coffee coffee, final TextView tvQuantity, final TextView tvTotalValue, final boolean isRemove, final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity;
                if (isRemove) {
                    quantity = Integer.valueOf(tvQuantity.getText().toString()) - 1;
                } else {
                    quantity = Integer.valueOf(tvQuantity.getText().toString()) + 1;
                }
                OrderHelper.getInstance().getOrderList().get(position).setQuantity(quantity);

                tvQuantity.setText(String.format(C.FORMAT_DIGITS, quantity).toString());
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

                Snackbar.make(view, context.getResources().getString(R.string.snack_bar_item_added, coffee.getQuantity(), coffee.getName()), Snackbar.LENGTH_SHORT).show();

                try {
                    FragmentHelper.doFragmentTransaction(CoffeeMenuFragment.class.newInstance(), (AppCompatActivity) context);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
    }

    public static View.OnClickListener deleteCoffee(final Context context, final int position, final OrderAdapter orderAdapter, final QuantityHandler quantityHandler) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                        .setTitle(R.string.dialog_title)
                        .setMessage(R.string.dialog_text)
                        .setIcon(R.drawable.information)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                OrderHelper.getInstance().getOrderList().remove(position);
                                orderAdapter.notifyDataSetChanged();
                                quantityHandler.handleUiChanges();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();
            }
        };
    }

    public static View.OnClickListener goToMenuListener(final Context context) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FragmentHelper.doFragmentTransaction(CoffeeMenuFragment.class.newInstance(), (AppCompatActivity) context);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public static View.OnClickListener placeOrder(final Context context, final List<Coffee> order) {
        return new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                PlaceOrderTask placeOrderTask = new PlaceOrderTask(context, view);
                placeOrderTask.execute(order);
            }
        };
    }

    public static View.OnClickListener goToOrderDetail(final Context context, final List<Coffee> order) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(C.BUNDLE_ORDER, Parcels.wrap(order));
                    FragmentHelper.doFragmentTransaction(OrderDetailFragment.class.newInstance(), (AppCompatActivity) context, bundle);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public static View.OnClickListener setAsFavoriteCoffee(final Context context, final List<Coffee> order, final int widgetId, final Activity widgetConfigureActivity) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Gson gson = new Gson();
                    String json = gson.toJson(order);
                    SharedPreferenceHelper sharedPreferenceHelper = new SharedPreferenceHelper(context);
                    sharedPreferenceHelper.putString(C.WIDGET_PREFIX + widgetId, json);

                    Toast.makeText(context, R.string.favorite_widget, Toast.LENGTH_LONG).show();

                    Intent resultValue = new Intent();
                    resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);


                    RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.favorite_coffee_widget);
                    StringBuilder stringBuilder = new StringBuilder();
                    for (Coffee coffee : order) {
                        stringBuilder.append(coffee.getQuantity() + C.QTY_SYMBOL + coffee.getName() + "\n");
                    }
                    views.setTextViewText(R.id.appwidget_text, stringBuilder.toString());
                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                    appWidgetManager.updateAppWidget(widgetId, views);

                    widgetConfigureActivity.setResult(RESULT_OK, resultValue);
                    widgetConfigureActivity.finish();
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
