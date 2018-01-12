package eu.rodrigocamara.myladybucks.screens.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import eu.rodrigocamara.myladybucks.adapters.AnnouncementsAdapter;
import eu.rodrigocamara.myladybucks.listeners.ClickListeners;
import eu.rodrigocamara.myladybucks.pojos.Announcement;
import eu.rodrigocamara.myladybucks.utils.FragmentHelper;

/**
 * Created by rodri on 30/12/2017.
 */

public class HomeFragment extends Fragment {
    @BindView(R.id.rv_ads)
    RecyclerView mAnnouncementRecyclerView;

    @BindView(R.id.fab_home_menu)
    FloatingActionButton mFabMenu;

    private AnnouncementsAdapter mAnnouncementAdapter;
    private List<Announcement> mAnnouncementList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,
                container, false);
        ButterKnife.bind(this, view);

        mAnnouncementList = new ArrayList<>();
        mAnnouncementAdapter = new AnnouncementsAdapter(view.getContext(), mAnnouncementList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        mAnnouncementRecyclerView.setLayoutManager(mLayoutManager);
        mAnnouncementRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAnnouncementRecyclerView.setAdapter(mAnnouncementAdapter);

        mFabMenu.setOnClickListener(ClickListeners.goToMenuListener(getContext()));
        mockAds();
        FragmentHelper.updateDrawerMenu(this.getActivity(), R.id.action_home);
        return view;
    }

    private void mockAds() {
        int[] images = new int[]{R.drawable.promotion1, R.drawable.promotion2,
                R.drawable.promotion3};

        Announcement announcement = new Announcement("globo.com", images[0]);
        mAnnouncementList.add(announcement);

        announcement = new Announcement("uol.com", images[1]);
        mAnnouncementList.add(announcement);

        announcement = new Announcement("google.com", images[2]);
        mAnnouncementList.add(announcement);

        mAnnouncementAdapter.notifyDataSetChanged();
    }
}

