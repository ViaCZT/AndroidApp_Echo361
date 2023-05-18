package com.example.echo361.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Author: Yijun Huang
 *
 * The ToastUtil class is for easily using toast without redundant codes each time.
 *
 * reference: <a href="https://www.bilibili.com/video/BV1MK411p7dp?p=6&vd_source=71738b01ee54255dd94f95dabdee0e2d">...</a>
 */
public class ToastUtil {
    public static Toast mToast;
    public static void showMsg(Context context, String msg){
        if(mToast == null){
            mToast = Toast.makeText(context,msg,Toast.LENGTH_SHORT);
        }
        else{
            mToast.setText(msg);
        }
        mToast.show();
    }
}
