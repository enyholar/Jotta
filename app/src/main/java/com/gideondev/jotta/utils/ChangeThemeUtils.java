package com.gideondev.jotta.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.LinearLayout;
import com.gideondev.jotta.R;
import com.gideondev.jotta.utils.PreferenUtil.PreferenUtil;

/**
 * Created by ${ENNY} on 10/24/2017.
 */

public class ChangeThemeUtils {


    private static int sTheme;
    public final static int THEME_DEFAULT = 0;
    public final static int THEME_WHITE = 1;
    public final static int THEME_BLUE = 2;
    /**
     * Set the theme of the Activity, and restart it by creating a new Activity of the same type.
     */
    public static void changeToTheme(Activity activity, int theme)
    {
        sTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }
    /** Set the theme of the activity, according to the configuration. */
    public static void onActivityCreateSetTheme(Activity activity, Context mContext) {
        if ( PreferenUtil.getInstant(mContext).getThemeColor().equals("gray")){
            activity.setTheme(R.style.GrayTheme);
        }else if ( PreferenUtil.getInstant(mContext).getThemeColor().equals("green")){
            activity.setTheme(R.style.GreenTheme);
        }else if ( PreferenUtil.getInstant(mContext).getThemeColor().equals("yellow")){
            activity.setTheme(R.style.YellowTheme);
        }else if ( PreferenUtil.getInstant(mContext).getThemeColor().equals("pink")){
            activity.setTheme(R.style.PinkTheme);
        }else if ( PreferenUtil.getInstant(mContext).getThemeColor().equals("blue")){
            activity.setTheme(R.style.BlueTheme);
        }else {
            activity.setTheme(R.style.AppTheme);
        }
    }

    //@BindingAdapter("headerColor")
    public static void onActivityCreateSetHeaderColor( LinearLayout header) {
        Context mContext = header.getContext();
        if ( PreferenUtil.getInstant(mContext).getThemeColor().equals("gray")){
            header.setBackgroundColor(mContext.getResources().getColor(R.color.grayprimary));
        }else if ( PreferenUtil.getInstant(mContext).getThemeColor().equals("green")){
            header.setBackgroundColor(mContext.getResources().getColor(R.color.greencolorPrimary));
        }else if ( PreferenUtil.getInstant(mContext).getThemeColor().equals("yellow")){
            header.setBackgroundColor(mContext.getResources().getColor(R.color.yellowprimary));
        }else if ( PreferenUtil.getInstant(mContext).getThemeColor().equals("pink")){
            header.setBackgroundColor(mContext.getResources().getColor(R.color.pinkprimary));
        }else if ( PreferenUtil.getInstant(mContext).getThemeColor().equals("blue")){
            header.setBackgroundColor(mContext.getResources().getColor(R.color.lightblueprimary));
        }else {
            header.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
        }
    }
}
