package eu.rodrigocamara.myladybucks.adapters;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
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
import eu.rodrigocamara.myladybucks.listeners.ClickListeners;
import eu.rodrigocamara.myladybucks.pojos.Coffee;

/**
 * Created by Rodrigo Câmara on 04/01/2018.
 */

public class CoffeeAdapter extends RecyclerView.Adapter<CoffeeAdapter.MyViewHolder> {
    private Context mContext;
    private List<Coffee> mCoffeeList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_item_coffee_image)
        ImageView mIvCoffeePicture;
        @BindView(R.id.tv_item_coffee_name)
        TextView mTvCoffeeName;
        @BindView(R.id.tv_item_coffee_description)
        TextView mTvCoffeeDesc;
        @BindView(R.id.tv_item_coffee_price)
        TextView mTvCoffeePrice;
        @BindView(R.id.cl_item_menu)
        ConstraintLayout mClItemMenu;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }

    public CoffeeAdapter(Context mContext, List<Coffee> coffeeList) {
        this.mContext = mContext;
        this.mCoffeeList = coffeeList;
    }

    @Override
    public CoffeeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.coffee_menu_item, parent, false);

        return new CoffeeAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CoffeeAdapter.MyViewHolder holder, int position) {
        Coffee coffee = mCoffeeList.get(position);

        holder.mTvCoffeeName.setText(coffee.getName());
        holder.mTvCoffeeDesc.setText(coffee.getDescription());
        holder.mTvCoffeePrice.setText(coffee.printPrice());
        holder.mClItemMenu.setOnClickListener(ClickListeners.menuCoffeeListener(coffee, mContext));
        holder.mIvCoffeePicture.setOnClickListener(ClickListeners.menuCoffeeListener(coffee, mContext));
        Picasso.with(mContext).load(coffee.getImageURL()).placeholder(R.drawable.profile).into(holder.mIvCoffeePicture);
    }

    @Override
    public int getItemCount() {
        return mCoffeeList.size();
    }
}
