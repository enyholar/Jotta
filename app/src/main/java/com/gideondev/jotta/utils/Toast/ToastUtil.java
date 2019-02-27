package com.gideondev.jotta.utils.Toast;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;
import com.gideondev.jotta.base.AppApplication;

public class ToastUtil {

    public static void show(String text) {
        Toast.makeText(AppApplication.get(), text, Toast.LENGTH_LONG).show();
    }

    public static void show(int resId) {
        Toast.makeText(AppApplication.get(), AppApplication.get().getString(resId), Toast.LENGTH_LONG).show();
    }

    /**
     * Show toast message
     * @param message
     */
    public static void showToastMessage(String message) {
        Toast.makeText(AppApplication.get(), message, Toast.LENGTH_LONG).show();
    }

    /**
     * Show toast message following message id
     *
     * @param context
     * @param messageId
     */
    public static void show(Context context, int messageId) {
        if (context != null) {
            Toast.makeText(context, context.getString(messageId),
                           Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Show short toast message
     *
     * @param context
     * @param message
     */
    public static void showShortToastMessage(Context context, String message) {
        if (context != null && message != null) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Show short toast message following message id
     *
     * @param context
     * @param messageId
     */
    public static void showShortToastMessage(Context context, int messageId) {
        if (context != null) {
            Toast.makeText(context, context.getString(messageId),
                           Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Show short toast message following message id
     *
     * @param context
     * @param messageId
     */
    public static void showShortToastMessageWithPosition(Context context,
                                                         int messageId) {
        if (context != null) {
            Toast toast = Toast.makeText(context, context.getString(messageId),
                                         Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
        }
    }

    /**
     * Show toast message following duration
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void showToastMessage(Context context, String message,
                                        int duration) {
        if (context != null && message != null) {
            Toast.makeText(context, message, duration).show();
        }
    }

    /**
     * Show toast message following message id and duration
     *
     * @param context
     * @param messageId
     * @param duration
     */
    public static void showToastMessage(Context context, int messageId,
                                        int duration) {
        if (context != null) {
            Toast.makeText(context, context.getString(messageId), duration)
                    .show();
        }
    }

}
