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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.rodrigocamara.myladybucks.R;
import eu.rodrigocamara.myladybucks.adapters.CoffeeAdapter;
import eu.rodrigocamara.myladybucks.pojos.Coffee;
import eu.rodrigocamara.myladybucks.utils.FragmentHelper;

/**
 * Created by Rodrigo Câmara on 04/01/2018.
 */

public class CoffeeMenuFragment extends Fragment {
    @BindView(R.id.rv_coffee_menu)
    RecyclerView rvCoffee;

    private CoffeeAdapter mCoffeeAdapter;
    private List<Coffee> mCoffeeList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coffee_menu,
                container, false);

        ButterKnife.bind(this, view);

        mCoffeeList = new ArrayList<>();
        mCoffeeAdapter = new CoffeeAdapter(view.getContext(), mCoffeeList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        rvCoffee.setLayoutManager(mLayoutManager);
        rvCoffee.setItemAnimator(new DefaultItemAnimator());
        rvCoffee.setAdapter(mCoffeeAdapter);

        mockMenu();

        FragmentHelper.updateDrawerMenu(this.getActivity(), R.id.action_menu);

        return view;
    }

    private void mockMenu() {

        Coffee coffee = new Coffee("Caffe Latte", "Espresso with light foam and steamed milk", "", "$15");
        mCoffeeList.add(coffee);

        coffee = new Coffee("Cappuccino", "Sweet unique espresso with foamed milk", "", "$13");
        mCoffeeList.add(coffee);

        coffee = new Coffee("Doppio", "Two shots of espresso", "", "$13");
        mCoffeeList.add(coffee);

        coffee = new Coffee("Flat White", "Espresso with light foam and steamed milk", "", "$15");
        mCoffeeList.add(coffee);

        coffee = new Coffee("Moccha", "Espresso combined with foamed milk, layer of chocolate and a really really really really really really really really really really really really really really really really really really really really long string", "", "$15");
        mCoffeeList.add(coffee);

        mCoffeeAdapter.notifyDataSetChanged();
    }
}
