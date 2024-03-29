package com.bbgatestudios.widgettest;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RemoteViews;

import java.util.ArrayList;

public class CollectionWidgetProvider extends AppWidgetProvider {
	
	public static final String ACTION_VIEW_DETAILS = "com.bbgatestudios.android.ACTION_VIEW_DETAILS";
	public static final String EXTRA_ITEM = "com.bbgatestudios.android.CollectionWidgetProvider.EXTRA_ITEM";
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		
		for(int i = 0; i < appWidgetIds.length; i++) {
			
			int widgetId = appWidgetIds[i];
			
			Intent intent = new Intent(context, CollectionWidgetService.class);
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
			
			RemoteViews widgetView = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
			widgetView.setRemoteAdapter(R.id.article_list, intent);
			widgetView.setEmptyView(R.id.article_list, R.id.empty);
			
			Intent detailIntent = new Intent(ACTION_VIEW_DETAILS);
			PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, detailIntent, PendingIntent.FLAG_UPDATE_CURRENT);
			widgetView.setPendingIntentTemplate(R.id.article_list, pIntent);
			
			appWidgetManager.updateAppWidget(widgetId, widgetView);
		}
		
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		if(intent.getAction().equals(ACTION_VIEW_DETAILS)) {
			NewsArticle article = (NewsArticle)intent.getSerializableExtra(EXTRA_ITEM);
			if(article != null) {
				Intent details = new Intent(context, DetailsActivity.class);
				details.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				details.putExtra(DetailsActivity.EXTRA_ITEM, article);
				context.startActivity(details);
			}
		}
		
		super.onReceive(context, intent);
	}
}