package com.androidproject.comarkdown.filesystem.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

/**
 * Created by wuxinying on 2017/12/8.
 */

public class AlertUtil {
    /**
     *
     *
     * @param context
     * @param mess
     */
    public static void toastMess(Context context, String mess) {
        Toast.makeText(context, mess, Toast.LENGTH_SHORT).show();
    }

    /**
     *
     *
     * @param context
     *
     * @param title
     *
     * @param message
     */
    public static void simpleAlertDialog(Context context, String title,
                                         String message) {
        new AlertDialog.Builder(context).setTitle(title).setMessage(message)
                .setNegativeButton("确定", null).show();
    }

    /**
     *
     *
     * @param context
     *
     * @param title
     *
     * @param message
     *
     * @param okListener
     *
     * @param cancleListener
     *
     * @return
     */
    public static AlertDialog judgeAlertDialog(Context context, String title,
                                               String message, DialogInterface.OnClickListener okListener,
                                               DialogInterface.OnClickListener cancleListener) {
        AlertDialog aDialog= new AlertDialog.Builder(context).setTitle(title).setMessage(message)
                .setNegativeButton("确定", okListener)
                .setPositiveButton("取消", cancleListener).show();
        return aDialog;
    }

    public static void showSnack(View anchor, String message) {
        Snackbar.make(anchor, message, Snackbar.LENGTH_SHORT).show();

    }

}
