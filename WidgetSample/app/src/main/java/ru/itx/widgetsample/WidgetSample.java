package ru.itx.widgetsample;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by d.yacenko on 23.11.17.
 */

//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// Run->EditConfiguration->LaunchOptions->launch=nothing
//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

public class WidgetSample extends AppWidgetProvider {

    final public static String TAG = WidgetSample.class.getSimpleName();
    final String UPDATE_ALL_WIDGETS = "update_all_widgets";

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Log.d(TAG, "onEnabled");
        Intent intent = new Intent(context, WidgetSample.class);
        intent.setAction(UPDATE_ALL_WIDGETS);
        PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(),      10000, pIntent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.d(TAG, "onUpdate " + Arrays.toString(appWidgetIds));
        for (int id : appWidgetIds) {
            updateWidget(context, appWidgetManager, id);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.d(TAG, "onRecieve ");
        if (intent.getAction().equalsIgnoreCase(UPDATE_ALL_WIDGETS)) {
            ComponentName thisAppWidget = new ComponentName(
                    context.getPackageName(), getClass().getName());
            AppWidgetManager appWidgetManager = AppWidgetManager
                    .getInstance(context);
            int ids[] = appWidgetManager.getAppWidgetIds(thisAppWidget);
            for (int appWidgetID : ids) {
                updateWidget(context, appWidgetManager, appWidgetID);
            }
        }
    }

    static void updateWidget(Context context, AppWidgetManager appWidgetManager,
                             int widgetID) {
        Log.d(TAG, "updateWidget " + widgetID);
        SharedPreferences sp = context.getSharedPreferences(ConfigActivity.WIDGET_PREF, Context.MODE_PRIVATE);
        // Read Preferences
        String widgetText = sp.getString(ConfigActivity.WIDGET_TEXT + widgetID, null);
        SimpleDateFormat format = new SimpleDateFormat("hh:mm");
        String dateToStr = format.format(new Date());
        if (widgetText == null) return;
        widgetText+="\n"+dateToStr;
        int widgetColor = sp.getInt(ConfigActivity.WIDGET_COLOR + widgetID, 0);
        // Configure the widget appearance
        RemoteViews widgetView = new RemoteViews(context.getPackageName(),
                R.layout.widget);
        widgetView.setTextViewText(R.id.tv, widgetText);
        widgetView.setInt(R.id.tv, "setBackgroundColor", widgetColor);
        // configure click
        Intent intent = new Intent(context, ConfigActivity.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_CONFIGURE);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID);
        Log.d(TAG, "updateWidget -> " + widgetID);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, widgetID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        widgetView.setOnClickPendingIntent(R.id.widget, pendingIntent);
        // Update widget
        appWidgetManager.updateAppWidget(widgetID, widgetView);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        Log.d(TAG, "onDeleted " + Arrays.toString(appWidgetIds));
        // Remove needed Preferences
        SharedPreferences.Editor editor = context.getSharedPreferences(
                ConfigActivity.WIDGET_PREF, Context.MODE_PRIVATE).edit();
        for (int widgetID : appWidgetIds) {
            editor.remove(ConfigActivity.WIDGET_TEXT + widgetID);
            editor.remove(ConfigActivity.WIDGET_COLOR + widgetID);
        }
        editor.commit();
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        Log.d(TAG, "onDisabled");
        Intent intent = new Intent(context, WidgetSample.class);
        intent.setAction(UPDATE_ALL_WIDGETS);
        PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pIntent);
    }

}