package eu.rodrigocamara.myladybucks.adapters;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.rodrigocamara.myladybucks.R;
import eu.rodrigocamara.myladybucks.pojos.Coffee;
import eu.rodrigocamara.myladybucks.screens.fragments.ItemOrderFragment;
import eu.rodrigocamara.myladybucks.utils.FragmentHelper;
import eu.rodrigocamara.myladybucks.utils.Log;

/**
 * Created by Rodrigo Câmara on 04/01/2018.
 */

public class CoffeeAdapter extends RecyclerView.Adapter<CoffeeAdapter.MyViewHolder> {
    private Context mContext;
    private List<Coffee> coffeeList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_item_coffee_image)
        ImageView mIvCoffePicture;
        @BindView(R.id.tv_item_coffee_name)
        TextView mTvCoffeName;
        @BindView(R.id.tv_item_coffee_description)
        TextView mTvCoffeDesc;
        @BindView(R.id.tv_item_coffee_price)
        TextView mTvCoffePrice;
        @BindView(R.id.cl_item_menu)
        ConstraintLayout mClItemMenu;


        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }

    public CoffeeAdapter(Context mContext, List<Coffee> coffeeList) {
        this.mContext = mContext;
        this.coffeeList = coffeeList;
    }

    @Override
    public CoffeeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.coffee_menu_item, parent, false);

        return new CoffeeAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CoffeeAdapter.MyViewHolder holder, int position) {
        Coffee coffee = coffeeList.get(position);
        holder.mTvCoffeName.setText(coffee.getName());
        holder.mTvCoffeDesc.setText(coffee.getDescription());
        holder.mTvCoffePrice.setText(coffee.getPrice());
        holder.mClItemMenu.setOnClickListener(clickListener(coffee));
        //Picasso.with(mContext).load(coffee.getImageURL()).into(holder.mIvCoffePicture);
        holder.mIvCoffePicture.setOnClickListener(clickListener(coffee));
    }

    private View.OnClickListener clickListener(final Coffee coffee) {
        return new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.printLog("Coffee clicked: " + coffee.getName());
                Parcelable wrapped = Parcels.wrap(coffee);
                Class fragmentClass;
                fragmentClass = ItemOrderFragment.class;
                Fragment fragment = null;
                try {
                    fragment = (android.support.v4.app.Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Bundle bundle = new Bundle();
                bundle.putParcelable("example", Parcels.wrap(coffee));
                FragmentHelper.doFragmentTransaction(fragment, (AppCompatActivity) mContext, bundle);
            }
        };
    }

    @Override
    public int getItemCount() {
        return coffeeList.size();
    }
}
