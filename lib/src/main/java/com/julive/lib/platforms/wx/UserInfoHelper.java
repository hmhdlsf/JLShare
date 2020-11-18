package com.julive.lib.platforms.wx;

import android.content.Context;
import android.widget.Toast;

import com.julive.lib.entity.OAuthUserInfo;
import com.julive.lib.http.HttpCallBackListener;
import com.julive.lib.http.HttpUtil;
import com.julive.lib.listener.LoginListener;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * @author Kale
 * @date 2018/9/14
 */
public class UserInfoHelper {

    /**
     * 通过网络请求得到用户信息，这里用的是微博自带的网络请求库，也可以用腾讯的请求库
     */
    public static void getUserInfo(final Context context, String url, final LoginListener listener, final UserAdapter adapter) {
        HttpUtil.sendHttpRequest(url, new HttpCallBackListener() {
            @Override
            public void onFinish(String json) {
                try {
                    OAuthUserInfo userInfo = null;
                    try {
                        userInfo = adapter.json2UserInfo(new JSONObject(json));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        listener.onError(e.getMessage());
                    }
                    if (userInfo != null) {
                        listener.onReceiveUserInfo(userInfo);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onError(Exception e) {
                Toast.makeText(context, "通过openid获取数据没有成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public interface UserAdapter {
        OAuthUserInfo json2UserInfo(JSONObject jsonObj) throws JSONException;
    }

}