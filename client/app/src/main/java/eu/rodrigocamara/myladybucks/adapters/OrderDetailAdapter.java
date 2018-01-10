package eu.rodrigocamara.myladybucks.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.rodrigocamara.myladybucks.R;
import eu.rodrigocamara.myladybucks.pojos.Order;

/**
 * Created by Rodrigo Câmara on 10/01/2018.
 */

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.MyViewHolder> {
    Context mContext;
    List<Order> orderList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_order_detail_coffee_name)
        TextView mTvCoffeName;
        @BindView(R.id.tv_order_detail_coffee_price)
        TextView mTvCoffePrice;
        @BindView(R.id.tv_order_detail_quantity)
        TextView mTvQuatity;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }

    public OrderDetailAdapter(Context mContext, List<Order> orderList) {
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
        Order order = orderList.get(position);
        holder.mTvCoffeName.setText(order.getCoffee().getName());
        holder.mTvCoffePrice.setText("$" + String.valueOf(Integer.valueOf(order.getCoffee().getPrice().substring(1)) * order.getQuantity()));
        holder.mTvQuatity.setText(String.valueOf(order.getQuantity()));
    }


    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
