package com.gideondev.jotta.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.CardView;
import android.view.View;
import com.gideondev.jotta.model.NoteModel;
import com.gideondev.jotta.R;

/**
 * Created by ${ENNY} on 12/9/2017.
 */

public class ChangePageColorUtil {

    public static void ChangeBackGroundOfNote(Context context, Activity activity, View mParentRootView, AppBarLayout toolbar, CardView cardView, NoteModel model){
        switch (model.getColor()) {
            case "white":
                mParentRootView.setBackgroundColor(context.getResources().getColor(R.color.white));
                if (Build.VERSION.SDK_INT >= 21) {
                    activity.getWindow()
                        .setStatusBarColor(context.getResources()
                                               .getColor(R.color.dark_light_pink));
                }
                cardView.setBackgroundColor(context.getResources().getColor(R.color.white));
                toolbar.setBackgroundColor(context.getResources().getColor(R.color.white));
                break;
            case "pink":
                mParentRootView.setBackgroundColor(context.getResources()
                                                       .getColor(R.color.light_pink));

                if (Build.VERSION.SDK_INT >= 21) {
                    activity.getWindow()
                        .setStatusBarColor(context.getResources()
                                               .getColor(R.color.dark_light_pink));
                }
                cardView.setBackgroundColor(context.getResources().getColor(R.color.light_pink));
                toolbar.setBackgroundColor(context.getResources().getColor(R.color.light_pink));
                break;
            case "purple":
                mParentRootView.setBackgroundColor(context.getResources()
                                                       .getColor(R.color.light_purple));

                if (Build.VERSION.SDK_INT >= 21) {
                    activity.getWindow()
                        .setStatusBarColor(context.getResources()
                                               .getColor(R.color.dark_light_pink));
                }
                cardView.setBackgroundColor(context.getResources().getColor(R.color.light_purple));
                toolbar.setBackgroundColor(context.getResources().getColor(R.color.light_purple));
                break;
            case "orange":
                mParentRootView.setBackgroundColor(context.getResources()
                                                       .getColor(R.color.light_orange));

                if (Build.VERSION.SDK_INT >= 21) {
                    activity.getWindow()
                        .setStatusBarColor(context.getResources()
                                               .getColor(R.color.dark_light_pink));
                }
                cardView.setBackgroundColor(context.getResources().getColor(R.color.light_orange));
                toolbar.setBackgroundColor(context.getResources().getColor(R.color.light_orange));
                break;
            case "lime":
                mParentRootView.setBackgroundColor(context.getResources()
                                                       .getColor(R.color.light_lime));

                if (Build.VERSION.SDK_INT >= 21) {
                    activity.getWindow()
                        .setStatusBarColor(context.getResources()
                                               .getColor(R.color.dark_light_pink));
                }
                cardView.setBackgroundColor(context.getResources().getColor(R.color.light_lime));
                toolbar.setBackgroundColor(context.getResources().getColor(R.color.light_lime));
                break;
            case "green":
                mParentRootView.setBackgroundColor(context.getResources()
                                                       .getColor(R.color.light_green));

                if (Build.VERSION.SDK_INT >= 21) {
                    activity.getWindow()
                        .setStatusBarColor(context.getResources()
                                               .getColor(R.color.dark_light_pink));
                }
                cardView.setBackgroundColor(context.getResources().getColor(R.color.light_green));
                toolbar.setBackgroundColor(context.getResources().getColor(R.color.light_green));
                break;
            case "blue":
                mParentRootView.setBackgroundColor(context.getResources()
                                                       .getColor(R.color.light_blue));

                if (Build.VERSION.SDK_INT >= 21) {
                    activity.getWindow()
                        .setStatusBarColor(context.getResources()
                                               .getColor(R.color.dark_light_pink));
                }
                cardView.setBackgroundColor(context.getResources().getColor(R.color.light_blue));
                toolbar.setBackgroundColor(context.getResources().getColor(R.color.light_blue));
                break;
        }

    }

    public static void ChangeItemBackGround(Context context, View mParentRootView, NoteModel model){
        switch (model.getColor()) {
            case "white":
                mParentRootView.setBackgroundColor(context.getResources().getColor(R.color.white));
                break;
            case "pink":
                mParentRootView.setBackgroundColor(context.getResources()
                                                       .getColor(R.color.light_pink));

                break;
            case "purple":
                mParentRootView.setBackgroundColor(context.getResources()
                                                       .getColor(R.color.light_purple));
                break;
            case "orange":
                mParentRootView.setBackgroundColor(context.getResources()
                                                       .getColor(R.color.light_orange));
                break;
            case "lime":
                mParentRootView.setBackgroundColor(context.getResources()
                                                       .getColor(R.color.light_lime));
                break;
            case "green":
                mParentRootView.setBackgroundColor(context.getResources()
                                                       .getColor(R.color.light_green));
                break;
            case "blue":
                mParentRootView.setBackgroundColor(context.getResources()
                                                       .getColor(R.color.light_blue));
                break;
        }

    }

    public static void ChangeBackGroundMenu(Context context,View mMenuNoteLayout, NoteModel model){
        switch (model.getColor()) {
            case "white":
                mMenuNoteLayout.findViewById(R.id.img_light_purple).setVisibility(View.GONE);
                mMenuNoteLayout.findViewById(R.id.img_light_pink).setVisibility(View.GONE);
                mMenuNoteLayout.findViewById(R.id.img_light_blue).setVisibility(View.GONE);
                mMenuNoteLayout.findViewById(R.id.img_light_green).setVisibility(View.GONE);
                mMenuNoteLayout.findViewById(R.id.img_light_lime).setVisibility(View.GONE);
                mMenuNoteLayout.findViewById(R.id.img_light_orange).setVisibility(View.GONE);
                mMenuNoteLayout.findViewById(R.id.img_default).setVisibility(View.VISIBLE);
                break;
            case "pink":

                mMenuNoteLayout.setBackgroundColor(context.getResources()
                                                       .getColor(R.color.light_pink));
                mMenuNoteLayout.findViewById(R.id.img_light_pink).setVisibility(View.VISIBLE);
                mMenuNoteLayout.findViewById(R.id.img_light_purple).setVisibility(View.GONE);
                mMenuNoteLayout.findViewById(R.id.img_light_blue).setVisibility(View.GONE);
                mMenuNoteLayout.findViewById(R.id.img_light_green).setVisibility(View.GONE);
                mMenuNoteLayout.findViewById(R.id.img_light_lime).setVisibility(View.GONE);
                mMenuNoteLayout.findViewById(R.id.img_light_orange).setVisibility(View.GONE);
                mMenuNoteLayout.findViewById(R.id.img_default).setVisibility(View.GONE);
                break;
            case "purple":
                mMenuNoteLayout.setBackgroundColor(context.getResources()
                                                       .getColor(R.color.light_purple));
                mMenuNoteLayout.findViewById(R.id.img_light_purple).setVisibility(View.VISIBLE);
                mMenuNoteLayout.findViewById(R.id.img_light_pink).setVisibility(View.GONE);
                mMenuNoteLayout.findViewById(R.id.img_light_blue).setVisibility(View.GONE);
                mMenuNoteLayout.findViewById(R.id.img_light_green).setVisibility(View.GONE);
                mMenuNoteLayout.findViewById(R.id.img_light_lime).setVisibility(View.GONE);
                mMenuNoteLayout.findViewById(R.id.img_light_orange).setVisibility(View.GONE);
                mMenuNoteLayout.findViewById(R.id.img_default).setVisibility(View.GONE);
                break;
            case "orange":
                mMenuNoteLayout.setBackgroundColor(context.getResources()
                                                       .getColor(R.color.light_orange));
                mMenuNoteLayout.findViewById(R.id.img_light_purple).setVisibility(View.GONE);
                mMenuNoteLayout.findViewById(R.id.img_light_pink).setVisibility(View.GONE);
                mMenuNoteLayout.findViewById(R.id.img_light_blue).setVisibility(View.GONE);
                mMenuNoteLayout.findViewById(R.id.img_light_green).setVisibility(View.GONE);
                mMenuNoteLayout.findViewById(R.id.img_light_lime).setVisibility(View.GONE);
                mMenuNoteLayout.findViewById(R.id.img_light_orange).setVisibility(View.VISIBLE);
                mMenuNoteLayout.findViewById(R.id.img_default).setVisibility(View.GONE);
                break;
            case "lime":
                mMenuNoteLayout.setBackgroundColor(context.getResources()
                                                       .getColor(R.color.light_lime));
                mMenuNoteLayout.findViewById(R.id.img_light_purple).setVisibility(View.GONE);
                mMenuNoteLayout.findViewById(R.id.img_light_pink).setVisibility(View.GONE);
                mMenuNoteLayout.findViewById(R.id.img_light_blue).setVisibility(View.GONE);
                mMenuNoteLayout.findViewById(R.id.img_light_green).setVisibility(View.GONE);
                mMenuNoteLayout.findViewById(R.id.img_light_lime).setVisibility(View.VISIBLE);
                mMenuNoteLayout.findViewById(R.id.img_light_orange).setVisibility(View.GONE);
                mMenuNoteLayout.findViewById(R.id.img_default).setVisibility(View.GONE);
                break;
            case "green":
                mMenuNoteLayout.setBackgroundColor(context.getResources()
                                                       .getColor(R.color.light_green));
                mMenuNoteLayout.findViewById(R.id.img_light_purple).setVisibility(View.GONE);
                mMenuNoteLayout.findViewById(R.id.img_light_pink).setVisibility(View.GONE);
                mMenuNoteLayout.findViewById(R.id.img_light_blue).setVisibility(View.GONE);
                mMenuNoteLayout.findViewById(R.id.img_light_green).setVisibility(View.VISIBLE);
                mMenuNoteLayout.findViewById(R.id.img_light_lime).setVisibility(View.GONE);
                mMenuNoteLayout.findViewById(R.id.img_light_orange).setVisibility(View.GONE);
                mMenuNoteLayout.findViewById(R.id.img_default).setVisibility(View.GONE);
                break;
            case "blue":
                mMenuNoteLayout.setBackgroundColor(context.getResources()
                                                       .getColor(R.color.light_blue));
                mMenuNoteLayout.findViewById(R.id.img_light_purple).setVisibility(View.GONE);
                mMenuNoteLayout.findViewById(R.id.img_light_pink).setVisibility(View.GONE);
                mMenuNoteLayout.findViewById(R.id.img_light_blue).setVisibility(View.VISIBLE);
                mMenuNoteLayout.findViewById(R.id.img_light_green).setVisibility(View.GONE);
                mMenuNoteLayout.findViewById(R.id.img_light_lime).setVisibility(View.GONE);
                mMenuNoteLayout.findViewById(R.id.img_light_orange).setVisibility(View.GONE);
                mMenuNoteLayout.findViewById(R.id.img_default).setVisibility(View.GONE);
                break;
        }

    }

}
