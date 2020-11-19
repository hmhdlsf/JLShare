# JLShare
为解决Mobsdk引入版本号带加号，影响项目的打包速度，此分享库旨在减少打包时间和包体积，集成了QQ分享、微信分享、微信登录功能

## 一、功能概述
目前该库支持微信、QQ、短信分享，以及微信登录功能，其中微信分享包括微信好友、微信朋友圈、微信收藏；QQ分享包括QQ好友、QQ空间

## 二、接入准备
在微信和QQ开发者平台上注册账号，创建应用。获取到必要的配置参数appId、appScreat等

## 三、接入步骤
1. maven依赖
```
implementation 'com.github.hmhdlsf:JLShare:v1.0.5'
```
   如果你的平台需要支持微信平台
```
implementation 'com.github.hmhdlsf:JLShareWX:v1.0.5'
```
   如果你的平台需要支持QQ平台
```
implementation 'com.github.hmhdlsf:JLShareQQ:v1.0.2'
```
2. gradle 文件参数配置
需要在工程的build.gradle文件分别配置平台参数
```
manifestPlaceholders = [
        "wx_app_id"    : "",
        "wx_app_secret": "",

        "qq_app_id"    : "",
         "qq_scope"    : "",
]
```
3. 初始化
只需配置你需要的平台文件
```
// 初始化第三方平台的信息
ShareLoginHelper.initPlatforms(
    listOf(
        QQPlatform::class.java,
        WXPlatform::class.java
    )
)
```
4. 调起分享弹窗
```
OneKeyShare oks = new OneKeyShare();
oks.show(fragmentManager);
```
5. 隐藏部分分享功能
```
class Constants {
  // 微信好友
  public static final String Wechat = "Wechat";
  // 微信朋友圈
  public static final String WechatMoments = "WechatMoments";
  // 微信收藏
  public static final String WechatFavorite = "WechatFavorite";
  // QQ好友
  public static final String QQ = "QQ";
  // QQ空间
  public static final String QZone = "QZone";
  // 短信
  public static final String ShortMessage = "ShortMessage";
}

OneKeyShare oks = new OneKeyShare();
oks.addHiddenPlatform(Constants.Wechat);
oks.addHiddenPlatform(Constants.ShortMessage);
oks.addHiddenPlatform(Constants.QQ);
```
6. 自定义分享功能
```
OneKeyShare oks = new OneKeyShare();
// 自定义平台
Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.xxx);
View.OnClickListener listener = new View.OnClickListener() {
     @Override
     public void onClick(View v) {
          // do something
     }
};
oks.addCustomPlatform("功能名称", bitmap, listener);
```
7. 设置平台分享内容
```
OneKeyShare oks = new OneKeyShare();
   oks.setShareContentCustomizeCallback(new IPlatformClickListener() {
       @Override
       public void onPlatformClick(@NotNull Platform platform, @NotNull ShareContent shareContent) {
           switch (platform.getName()) {
              // 微信好友
              case Constants.Wechat:
                  shareContent.setType(ShareContentType.TEXT);
                  shareContent.setText("你好啊");
                  break;
                        
             // QQ好友
             case Constants.QQ:
                 shareContent.setType(ShareContentType.TEXT);
                 shareContent.setTitle("你好啊");
                 shareContent.setDesc("我是一个描述啊啊啊啊啊");
                 shareContent.setTargetUrl("http://www.qq.com/news/1.html");
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
```
8. 分享回调
```
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
```
9. 调起微信登录及回调
```
ShareLoginHelper.doLogin(this, WXPlatform.LOGIN, object : WXLoginListener(this) {
    override fun onReceiveToken(
        accessToken: String,
        userId: String,
        expiresIn: Long,
        data: String?
    ) {
        super.onReceiveToken(accessToken, userId, expiresIn, data)
    }

    override fun onReceiveUserInfo(userInfo: OAuthUserInfo) {
        super.onReceiveUserInfo(userInfo)
    }

    override fun onCancel() {
        super.onCancel()
    }

    override fun onError(msg: String) {
         super.onError(msg)
    }
})
```







