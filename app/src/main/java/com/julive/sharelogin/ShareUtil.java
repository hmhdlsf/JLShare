package com.julive.sharelogin;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.julive.lib.OneKeyShare;
import com.julive.lib.content.ShareContent;
import com.julive.lib.content.ShareContentType;
import com.julive.lib.entity.Platform;
import com.julive.lib.interfaces.IPlatformClickListener;
import com.julive.lib.listener.MyShareListener;

import org.jetbrains.annotations.NotNull;

/**
 * Created by lsf on 2020/11/13 2:22 PM
 * Function : 分享工具类
 */
class ShareUtil {
    public static void doShare(final Context context, final FragmentManager fragmentManager, @NonNull final ShareInfo shareInfo) {
        OneKeyShare oks = new OneKeyShare();
        oks.addHiddenPlatform(Constants.Wechat);
        oks.addHiddenPlatform(Constants.ShortMessage);
        oks.addHiddenPlatform(Constants.QQ);
        oks.setShareContentCustomizeCallback(new IPlatformClickListener() {
            @Override
            public void onPlatformClick(@NotNull Platform platform, @NotNull ShareContent shareContent) {
                switch (platform.getName()) {
                    // 微信好友
                    case Constants.Wechat:
                        shareContent.setType(ShareContentType.TEXT);
                        shareContent.setText("你好啊");
                        break;

                    // 微信朋友圈
                    case Constants.WechatMoments:
                        shareContent.setType(ShareContentType.WEB);
                        shareContent.setWebPageUrl("www.baidu.com");
                        break;

                    // 微信收藏
                    case Constants.WechatFavorite:
                        shareContent.setType(ShareContentType.IMG);
                        shareContent.setImagePath("123");
                        break;

                    // QQ好友
                    case Constants.QQ:
                        shareContent.setType(ShareContentType.TEXT);
                        shareContent.setTitle("你好啊");
                        shareContent.setDesc("我是一个描述啊啊啊啊啊");
                        shareContent.setTargetUrl("http://www.qq.com/news/1.html");
                        break;

                    // QQ空间
                    case Constants.QZone:
                        // 本地路径
                        String filePath = context.getExternalFilesDir(null) + "/shareData/test.jpg";
                        // byte数组
                        shareContent.setType(ShareContentType.IMG);
                        shareContent.setImagePath(filePath);
                        break;

                    // 短信
                    case Constants.ShortMessage:
                        shareContent.setType(ShareContentType.SHORT_MSG);
                        shareContent.setPhone("手机号");
                        shareContent.setDesc("内容");
                        break;

                    default:
                        break;
                }
            }
        });
        oks.setShareCallback(new MyShareListener(context) {
            @Override
            public void onSuccess() {
                super.onSuccess();
            }

            @Override
            public void onCancel() {
                super.onCancel();
            }

            @Override
            public void onError(@NotNull String msg) {
                super.onError(msg);
            }
        });

        // 自定义平台
        Bitmap enableLogo = BitmapFactory.decodeResource(context.getResources(), R.drawable.short_msg);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "复制成功", Toast.LENGTH_LONG).show();
            }
        };
        oks.addCustomPlatform("复制视频口令", enableLogo, listener);


        oks.show(fragmentManager);
    }
}
