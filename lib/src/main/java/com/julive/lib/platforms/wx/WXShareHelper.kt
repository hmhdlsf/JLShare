package com.julive.lib.platforms.wx

import com.julive.lib.content.ShareContent
import com.julive.lib.content.ShareContentType
import com.julive.lib.listener.ShareListener
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelmsg.*
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage.IMediaObject

/**
 * Created by lsf on 2020/11/5 5:13 PM
 * Function : 微信分享 & 登录 帮助类
 */
object WXShareHelper {
    fun createRequest(
        shareContent: ShareContent,
        shareType: String
    ): SendMessageToWX.Req {
        // 1. 建立信息体
        val msg = WXMediaMessage()
        msg.title = shareContent.title
        msg.description = shareContent.desc
        msg.thumbData = shareContent.thumbData
        msg.mediaObject = createMediaObject(shareContent)
        msg.mediaTagName = shareContent.mediaTagName
        msg.messageAction = shareContent.messageAction

        // 2. 发送信息
        val req = SendMessageToWX.Req()
        req.transaction = System.currentTimeMillis().toString()
        req.message = msg
        req.scene = Integer.valueOf(shareType.substring(shareType.length - 1))
        return req
    }

    private fun createMediaObject(shareContent: ShareContent): IMediaObject? {
        val mediaObject: IMediaObject?
        when (shareContent.type) {
            ShareContentType.TEXT -> {
                mediaObject = getTextObj(shareContent)
            }
            ShareContentType.IMG -> {
                mediaObject = getImageObj(shareContent)
            }
            ShareContentType.MUSIC -> {
                mediaObject = getMusicObj(shareContent)
            }
            ShareContentType.VIDEO -> {
                mediaObject = getVideoObj(shareContent)
            }
            ShareContentType.WEB -> {
                mediaObject = getWebPageObj(shareContent)
            }
            ShareContentType.MINI_PROGRAM -> {
                mediaObject = getMiniProgramObj(shareContent)
            }

            else -> {
                return null
            }
        }

        return mediaObject
    }

    private fun getTextObj(shareContent: ShareContent): IMediaObject {
        val text = WXTextObject()
        text.text = shareContent.text
        return text
    }

    private fun getImageObj(shareContent: ShareContent): IMediaObject {
        val image = WXImageObject()
        image.imagePath = shareContent.imagePath
        image.imageData = shareContent.imageData
        return image
    }

    private fun getMusicObj(shareContent: ShareContent): IMediaObject {
        val music = WXMusicObject()
        //Str1+"#wechat_music_url="+str2（str1是跳转的网页地址，str2是音乐地址）
        music.musicUrl =
            shareContent.webPageUrl
                .toString() + "#wechat_music_url=" + shareContent.musicUrl
        return music
    }

    private fun getVideoObj(shareContent: ShareContent): IMediaObject {
        val video = WXVideoObject()
        //Str1+"#wechat_video_url="+str2（str1是跳转的网页地址，str2是视频地址）
        video.videoUrl =
            shareContent.webPageUrl
                .toString() + "#wechat_video_url=" + shareContent.videoUrl
        return video
    }

    private fun getWebPageObj(shareContent: ShareContent): IMediaObject {
        val webPage = WXWebpageObject()
        webPage.webpageUrl = shareContent.webPageUrl
        return webPage
    }

    private fun getMiniProgramObj(shareContent: ShareContent): IMediaObject {
        val miniProgramObject = WXMiniProgramObject()
        miniProgramObject.webpageUrl = shareContent.webPageUrl
        miniProgramObject.miniprogramType = shareContent.miniProgramType!!
        miniProgramObject.userName = shareContent.userName
        miniProgramObject.path = shareContent.path
        return miniProgramObject
    }

    /**
     * 解析分享到微信的结果
     *
     * https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419318634&token=&lang=zh_CN
     */
    fun parseShareResp(resp: BaseResp, listener: ShareListener) {
        when (resp.errCode) {
            BaseResp.ErrCode.ERR_OK -> listener.onSuccess()
            BaseResp.ErrCode.ERR_USER_CANCEL -> listener.onCancel()
            BaseResp.ErrCode.ERR_AUTH_DENIED -> listener.onError("用户拒绝授权")
            BaseResp.ErrCode.ERR_SENT_FAILED -> listener.onError("发送失败")
            else -> listener.onError("未知错误，code：" + resp.errCode + ", message：" + resp.errStr)
        }
    }
}