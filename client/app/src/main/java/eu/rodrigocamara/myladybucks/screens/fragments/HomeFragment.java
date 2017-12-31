package eu.rodrigocamara.myladybucks.screens.fragments;

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
import eu.rodrigocamara.myladybucks.adapters.AnnouncementsAdapter;
import eu.rodrigocamara.myladybucks.pojos.Announcement;

/**
 * Created by rodri on 30/12/2017.
 */

public class HomeFragment extends Fragment {
    @BindView(R.id.rv_ads)
    RecyclerView announcementRecyclerView;

    private AnnouncementsAdapter announcementAdapter;
    private List<Announcement> announcementList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,
                container, false);
        ButterKnife.bind(this, view);

        announcementList = new ArrayList<>();
        announcementAdapter = new AnnouncementsAdapter(view.getContext(), announcementList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        announcementRecyclerView.setLayoutManager(mLayoutManager);
        announcementRecyclerView.setItemAnimator(new DefaultItemAnimator());
        announcementRecyclerView.setAdapter(announcementAdapter);

        mockAds();

        return view;
    }

    private void mockAds() {
        int[] images = new int[]{R.drawable.promotion1, R.drawable.promotion2,
                R.drawable.promotion3};

        Announcement announcement = new Announcement("globo.com", images[0]);
        announcementList.add(announcement);

        announcement = new Announcement("uol.com", images[1]);
        announcementList.add(announcement);

        announcement = new Announcement("google.com", images[2]);
        announcementList.add(announcement);

        announcementAdapter.notifyDataSetChanged();
    }
}

