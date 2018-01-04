package eu.rodrigocamara.myladybucks.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.rodrigocamara.myladybucks.R;
import eu.rodrigocamara.myladybucks.pojos.Order;
import eu.rodrigocamara.myladybucks.utils.Log;

/**
 * Created by Rodrigo Câmara on 04/01/2018.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {
    Context mContext;
    List<Order> orderList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_full_coffee_name)
        TextView mTvCoffeName;
        @BindView(R.id.tv_full_coffee_price)
        TextView mTvCoffePrice;
        @BindView(R.id.tv_order_full_quantity)
        TextView mTvQuatity;
        @BindView(R.id.iv_order_full_add)
        ImageView mIvAddCoffee;
        @BindView(R.id.iv_order_full_remove)
        ImageView mIvRemoveCoffee;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }

    public OrderAdapter(Context mContext, List<Order> orderList) {
        this.mContext = mContext;
        this.orderList = orderList;
    }

    @Override
    public OrderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_full_item, parent, false);
        return new OrderAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final OrderAdapter.MyViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.mTvCoffeName.setText(order.getCoffee().getName());
        holder.mTvCoffePrice.setText(String.valueOf(Integer.valueOf(order.getCoffee().getPrice().substring(1)) * order.getQuantity()));
        holder.mTvQuatity.setText(String.valueOf(order.getQuantity()));
    }

    private View.OnClickListener clickListener(final Order order) {
        return new View.OnClickListener() {

            @Override
            public void onClick(View view) {

            }
        };
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}