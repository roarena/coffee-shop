package eu.rodrigocamara.myladybucks.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.rodrigocamara.myladybucks.R;
import eu.rodrigocamara.myladybucks.pojos.Announcement;
import eu.rodrigocamara.myladybucks.utils.Log;

/**
 * Created by rodri on 30/12/2017.
 */

public class AnnouncementsAdapter extends RecyclerView.Adapter<AnnouncementsAdapter.MyViewHolder> {
    Context mContext;
    List<Announcement> announcementList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_ad_image)
        ImageView ivAdImage;


        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }

    public AnnouncementsAdapter(Context mContext, List<Announcement> announcementList) {
        this.mContext = mContext;
        this.announcementList = announcementList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_announcement, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Announcement announcement = announcementList.get(position);
        Picasso.with(mContext).load(announcement.getImageUrl()).placeholder(R.drawable.placeholder).into(holder.ivAdImage);
        holder.itemView.setOnClickListener(clickListener(announcement));
    }

    private View.OnClickListener clickListener(final Announcement announcement) {
        return new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.printLog("Announcement clicked: " + announcement.getUrl());
            }
        };
    }

    @Override
    public int getItemCount() {
        return announcementList.size();
    }
}
