package eu.rodrigocamara.myladybucks.screens.widgets;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RemoteViews;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import eu.rodrigocamara.myladybucks.R;
import eu.rodrigocamara.myladybucks.adapters.AnnouncementsAdapter;
import eu.rodrigocamara.myladybucks.adapters.UserOrdersAdapter;
import eu.rodrigocamara.myladybucks.pojos.Announcement;
import eu.rodrigocamara.myladybucks.pojos.Order;
import eu.rodrigocamara.myladybucks.utils.C;
import eu.rodrigocamara.myladybucks.utils.FirebaseHelper;

/**
 * Implementation of App Widget functionality.
 */
public class AnnouncerWidget extends AppWidgetProvider {
    private static ChildEventListener mMenuEventListener;
    private static List<Announcement> mAnnouncementList;
    private static DatabaseReference mDatabaseReference;
    private static RemoteViews views;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        views = new RemoteViews(context.getPackageName(), R.layout.annoucer_widget);
        loadAnnouncements(context);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static void loadAnnouncements(final Context context) {
        mAnnouncementList = new ArrayList<>();

        mDatabaseReference = FirebaseHelper.getDatabase().getReference(C.DB_ANNOUNCES_REFERENCE);
        mMenuEventListener = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                refreshAnnouncementsListValues(dataSnapshot.getValue(Announcement.class), false, context);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                refreshAnnouncementsListValues(dataSnapshot.getValue(Announcement.class), true, context);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                refreshAnnouncementsListValues(dataSnapshot.getValue(Announcement.class), true, context);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                refreshAnnouncementsListValues(dataSnapshot.getValue(Announcement.class), true, context);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                databaseError.toException();
            }
        };
        mDatabaseReference.addChildEventListener(mMenuEventListener);
    }

    private static void refreshAnnouncementsListValues(Announcement value, boolean needsClear, Context context) {
        if (needsClear) {
            mAnnouncementList.clear();
        }
        mAnnouncementList.add(value);
        try {
            views.setImageViewBitmap(R.id.iv_ad_image, Picasso.with(context).load(mAnnouncementList.get(0).getImageUrl()).get());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

