package eu.rodrigocamara.myladybucks.screens.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.rodrigocamara.myladybucks.R;
import eu.rodrigocamara.myladybucks.adapters.CoffeeAdapter;
import eu.rodrigocamara.myladybucks.pojos.Coffee;
import eu.rodrigocamara.myladybucks.listeners.ClickListeners;
import eu.rodrigocamara.myladybucks.utils.FragmentHelper;
import eu.rodrigocamara.myladybucks.utils.OrderHelper;

/**
 * Created by Rodrigo Câmara on 04/01/2018.
 */

public class CoffeeMenuFragment extends Fragment {
    @BindView(R.id.rv_generic)
    RecyclerView rvCoffee;
    @BindView(R.id.btn_generic)
    Button btnCart;
    @BindView(R.id.tv_generic_title)
    TextView tvTitle;

    private CoffeeAdapter mCoffeeAdapter;
    private List<Coffee> mCoffeeList;
    private static View coordinatorView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.generic_fragment,
                container, false);

        ButterKnife.bind(this, view);

        setUIComponents(view.getContext());

        FragmentHelper.updateDrawerMenu(this.getActivity(), R.id.action_menu);

        return view;
    }

    private void setUIComponents(Context context) {
        tvTitle.setText(R.string.menu_title);
        
        if (OrderHelper.getInstance().getOrderList().size() > 0) {
            btnCart.setVisibility(View.VISIBLE);
            btnCart.setOnClickListener(ClickListeners.goToCartListener(context));
            btnCart.setText(R.string.go_to_cart_button);
        }

        mCoffeeList = new ArrayList<>();
        mCoffeeAdapter = new CoffeeAdapter(context, mCoffeeList);
        coordinatorView = getView();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rvCoffee.setLayoutManager(mLayoutManager);
        rvCoffee.setItemAnimator(new DefaultItemAnimator());
        rvCoffee.setAdapter(mCoffeeAdapter);


        mockMenu();
    }

    private void mockMenu() {

        Coffee coffee = new Coffee("Caffe Latte", "Espresso with light foam and steamed milk", "", 15);
        mCoffeeList.add(coffee);

        coffee = new Coffee("Cappuccino", "Sweet unique espresso with foamed milk", "", 13);
        mCoffeeList.add(coffee);

        coffee = new Coffee("Doppio", "Two shots of espresso", "", 13);
        mCoffeeList.add(coffee);

        coffee = new Coffee("Flat White", "Espresso with light foam and steamed milk", "", 15);
        mCoffeeList.add(coffee);

        coffee = new Coffee("Moccha", "Espresso combined with foamed milk, layer of chocolate and a really really really really really really really really really really really really really really really really really really really really long string", "", 15);
        mCoffeeList.add(coffee);

        mCoffeeAdapter.notifyDataSetChanged();
    }


}
