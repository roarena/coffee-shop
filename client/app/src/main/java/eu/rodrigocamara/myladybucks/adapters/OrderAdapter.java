package eu.rodrigocamara.myladybucks.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Currency;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.rodrigocamara.myladybucks.R;
import eu.rodrigocamara.myladybucks.listeners.ClickListeners;
import eu.rodrigocamara.myladybucks.listeners.TextChangeListeners;
import eu.rodrigocamara.myladybucks.pojos.Coffee;
import eu.rodrigocamara.myladybucks.interfaces.QuantityHandler;

/**
 * Created by Rodrigo Câmara on 04/01/2018.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {
    Context mContext;
    List<Coffee> orderList;
    private QuantityHandler quantityHandler;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_full_coffee_name)
        TextView mTvCoffeeName;
        @BindView(R.id.tv_full_coffee_price)
        TextView mTvCoffeePrice;
        @BindView(R.id.tv_order_full_quantity)
        TextView mTvQuantity;
        @BindView(R.id.iv_order_full_add)
        ImageView mIvAddCoffee;
        @BindView(R.id.iv_order_full_remove)
        ImageView mIvRemoveCoffee;
        @BindView(R.id.iv_delete_coffee)
        ImageView mIvDeleteCoffee;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }

    public OrderAdapter(Context mContext, List<Coffee> orderList, QuantityHandler quantityHandler) {
        this.mContext = mContext;
        this.orderList = orderList;
        this.quantityHandler = quantityHandler;
    }

    @Override
    public OrderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_full_item, parent, false);
        return new OrderAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final OrderAdapter.MyViewHolder holder, int position) {
        Coffee coffee = orderList.get(position);
        holder.mTvCoffeeName.setText(coffee.getName());
        holder.mTvCoffeePrice.setText(Currency.getInstance(Locale.getDefault()).getSymbol() + (coffee.getPrice()) * coffee.getQuantity());
        holder.mTvQuantity.setText(String.valueOf(coffee.getQuantity()));
        holder.mIvRemoveCoffee.setOnClickListener(ClickListeners.controlCoffeeQuantityListener(coffee, holder.mTvQuantity, holder.mTvCoffeePrice, true, position));
        holder.mIvAddCoffee.setOnClickListener(ClickListeners.controlCoffeeQuantityListener(coffee, holder.mTvQuantity, holder.mTvCoffeePrice, false, position));
        holder.mTvQuantity.addTextChangedListener(TextChangeListeners.coffeeQuantityListener(quantityHandler));
        holder.mIvDeleteCoffee.setOnClickListener(ClickListeners.deleteCoffee(mContext, position, this, quantityHandler));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}