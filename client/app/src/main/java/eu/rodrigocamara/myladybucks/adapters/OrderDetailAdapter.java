package eu.rodrigocamara.myladybucks.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Currency;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.rodrigocamara.myladybucks.R;
import eu.rodrigocamara.myladybucks.pojos.Coffee;

/**
 * Created by Rodrigo Câmara on 10/01/2018.
 */

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.MyViewHolder> {
    Context mContext;
    List<Coffee> orderList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_order_detail_coffee_name)
        TextView mTvCoffeeName;
        @BindView(R.id.tv_order_detail_coffee_price)
        TextView mTvCoffeePrice;
        @BindView(R.id.tv_order_detail_quantity)
        TextView mTvQuantity;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }

    public OrderDetailAdapter(Context mContext, List<Coffee> orderList) {
        this.mContext = mContext;
        this.orderList = orderList;
    }

    @Override
    public OrderDetailAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_detail_item, parent, false);
        return new OrderDetailAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final OrderDetailAdapter.MyViewHolder holder, int position) {
        Coffee coffee = orderList.get(position);
        holder.mTvCoffeeName.setText(coffee.getName());
        holder.mTvCoffeePrice.setText(Currency.getInstance(Locale.getDefault()).getSymbol() + (coffee.getPrice()) * coffee.getQuantity());
        holder.mTvQuantity.setText(String.valueOf(coffee.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
