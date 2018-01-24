package eu.rodrigocamara.myladybucks.screens.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.parceler.Parcels;

import java.util.List;

import eu.rodrigocamara.myladybucks.R;
import eu.rodrigocamara.myladybucks.pojos.Coffee;
import eu.rodrigocamara.myladybucks.screens.MainActivity;
import eu.rodrigocamara.myladybucks.utils.C;
import eu.rodrigocamara.myladybucks.utils.SharedPreferenceHelper;

public class FavoriteCoffeeWidget extends AppWidgetProvider {
    private static int widgetId;
    private static List<Coffee> coffeeList;
    public static final String WIDGET_CLICK = "widget_click";

    void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                         int appWidgetId) {
        widgetId = appWidgetId;

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.favorite_coffee_widget);
        views.setOnClickPendingIntent(R.id.btn_order, getPendingSelfIntent(context,WIDGET_CLICK));

        Gson gson = new Gson();
        SharedPreferenceHelper sharedPreferenceHelper = new SharedPreferenceHelper(context);

        coffeeList = gson.fromJson(sharedPreferenceHelper.getString(C.WIDGET_PREFIX + widgetId), new TypeToken<List<Coffee>>() {
        }.getType());

        if (coffeeList != null && !coffeeList.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Coffee coffee : coffeeList) {
                stringBuilder.append(coffee.getQuantity() + C.QTY_SYMBOL + coffee.getName() + "\n");
            }
            views.setTextViewText(R.id.appwidget_text, stringBuilder.toString());
        }
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equals(WIDGET_CLICK)) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(C.BUNDLE_ORDER, Parcels.wrap(coffeeList));

            Intent openApp = new Intent(context, MainActivity.class);
            openApp.putExtra(C.WIDGET_EXTRA, bundle);
            context.startActivity(openApp);
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
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        SharedPreferenceHelper sharedPreferenceHelper = new SharedPreferenceHelper(context);
        for (int appWidgetId : appWidgetIds) {
            sharedPreferenceHelper.removePref(C.WIDGET_PREFIX + appWidgetId);
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

