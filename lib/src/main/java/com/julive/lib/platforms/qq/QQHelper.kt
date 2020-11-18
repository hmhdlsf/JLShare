package com.julive.lib.platforms.qq

import android.content.Intent
import android.os.Bundle
import com.julive.lib.content.ShareContent
import com.julive.lib.content.ShareContentType
import com.julive.lib.helper.ShareLoginHelper
import com.julive.lib.interfaces.IBaseListener
import com.julive.lib.util.SlUtils
import com.tencent.connect.share.QQShare
import com.tencent.connect.share.QzonePublish
import com.tencent.connect.share.QzoneShare
import com.tencent.tauth.IUiListener
import com.tencent.tauth.UiError
import java.util.*

/**
 * Created by lsf on 2020/11/5 5:13 PM
 * Function : QQ分享 & 登录 帮助类
 */
object QQHelper {
    /**
     * "http://wiki.open.qq.com/wiki/mobile/API%E8%B0%83%E7%94%A8%E8%AF%B4%E6%98%8E#1.13_.E5.88.86.E4.BA.AB.E6.B6.88.E6.81.AF.E5.88.B0QQ.EF.BC.88.E6.97.A0.E9.9C.80QQ.E7.99.BB.E5.BD.95.EF.BC.89"
     */
    fun qqFriendBundle(shareContent: ShareContent): Bundle? {
        var intent: Intent? = null
        when (shareContent.type) {
            ShareContentType.TEXT -> {
                intent = getTextObj(shareContent)
            }
            ShareContentType.IMG -> {
                intent = getImageObj(shareContent)
            }

            ShareContentType.MUSIC -> {
                intent = getMusicObj(shareContent)
            }
            ShareContentType.APP -> {
                intent = getAppObj(shareContent)
            }
            ShareContentType.JSON -> {
                intent = getJsonObj(shareContent)
            }
            ShareContentType.MINI_PROGRAM -> {
                intent = getMiniProgramObj(shareContent)
            }
        }
        intent?.also {
            return buildQQParams(it, shareContent).extras
        }
        return Bundle()
    }

    /**
     * QQShare.SHARE_TO_QQ_KEY_TYPE		必选	分享类型，分享纯文本时填写：QQShare.SHARE_TO_QQ_TYPE_DEFAULT
     */
    private fun getTextObj(shareContent: ShareContent): Intent? {
        return Intent()
            .putExtra(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT)
            .putExtra(QQShare.SHARE_TO_QQ_TARGET_URL, shareContent.targetUrl)
    }

    /**
     * QQShare.SHARE_TO_QQ_KEY_TYPE		必选	分享类型，分享纯图片时填写：QQShare.SHARE_TO_QQ_TYPE_IMAGE
     * QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL	必选	String	需要分享的本地图片路径
     */
    private fun getImageObj(shareContent: ShareContent): Intent? {
        val intent = Intent()
        intent.putExtra(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE)
        val uri: String? = shareContent.imagePath
        if (uri != null) {
            if (uri.startsWith("http")) {
                intent.putExtra(QQShare.SHARE_TO_QQ_IMAGE_URL, uri) // net image
            } else {
                intent.putExtra(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, uri) // local image
            }
        }
        return intent
    }

    /**
     * QQShare.SHARE_TO_QQ_KEY_TYPE	必填	Int	分享的类型。分享音乐填Tencent.SHARE_TO_QQ_TYPE_AUDIO。
     * QQShare.PARAM_TARGET_URL		必选	这条分享消息被好友点击后的跳转URL，必须为真实可用的url才行
     * QQShare.SHARE_TO_QQ_AUDIO_URL	必填	String	音乐文件的远程链接, 以URL的形式传入, 不支持本地音乐。
     */
    private fun getMusicObj(shareContent: ShareContent): Intent? {
        return Intent()
            .putExtra(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_AUDIO)
            .putExtra(QQShare.SHARE_TO_QQ_AUDIO_URL, shareContent.musicUrl)
            .putExtra(QQShare.SHARE_TO_QQ_IMAGE_URL, getThumbImageUri(shareContent))
            .putExtra(QQShare.SHARE_TO_QQ_TARGET_URL, shareContent.targetUrl)
    }

    /**
     * QQShare.SHARE_TO_QQ_KEY_TYPE 	必填 	Int 	分享的类型。APP分享填：Tencent.SHARE_TO_QQ_TYPE_APP
     */
    private fun getAppObj(shareContent: ShareContent): Intent? {
        return Intent()
            .putExtra(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_APP)
            .putExtra(QQShare.SHARE_TO_QQ_IMAGE_URL, getThumbImageUri(shareContent))
            .putExtra(QQShare.SHARE_TO_QQ_AUDIO_URL, shareContent.musicUrl)
    }

    /**
     * QQShare.SHARE_TO_QQ_KEY_TYPE 	必填 	Int 	分享的类型。APP分享填：Tencent.SHARE_TO_QQ_TYPE_DEFAULT
     */
    private fun getJsonObj(shareContent: ShareContent): Intent? {
        return Intent()
            .putExtra(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT)
            .putExtra(QQShare.SHARE_TO_QQ_IMAGE_URL, getThumbImageUri(shareContent))
            .putExtra(QQShare.SHARE_TO_QQ_TARGET_URL, shareContent.targetUrl)
            .putExtra(QQShare.SHARE_TO_QQ_ARK_INFO, shareContent.jsonStr)
    }

    /**
     * QQShare.SHARE_TO_QQ_KEY_TYPE 	必填 	Int 	分享的类型。APP分享填：Tencent.SHARE_TO_QQ_MINI_PROGRAM
     */
    private fun getMiniProgramObj(shareContent: ShareContent): Intent? {
        return Intent()
            .putExtra(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_MINI_PROGRAM)
            .putExtra(QQShare.SHARE_TO_QQ_IMAGE_URL, getThumbImageUri(shareContent))
            .putExtra(QQShare.SHARE_TO_QQ_TARGET_URL, shareContent.targetUrl)
            .putExtra(QQShare.SHARE_TO_QQ_MINI_PROGRAM_APPID, shareContent.userName)
            .putExtra(QQShare.SHARE_TO_QQ_MINI_PROGRAM_PATH, shareContent.path)
            .putExtra(QQShare.SHARE_TO_QQ_MINI_PROGRAM_TYPE, shareContent.miniProgramType)
    }

    /**
     * 将分享的参数做最后一次组装
     *
     * QQShare.PARAM_TITLE 	        必填 	String 	分享的标题, 最长30个字符（对图片的分享无效）
     *
     * ---------------------------------------------------------------------------------
     *
     * QQShare.SHARE_TO_QQ_IMAGE_URL 	可选 	String 	分享图片的URL或者本地路径
     *
     * QQShare.PARAM_SUMMARY 	        可选 	String 	分享的消息摘要，最长40个字
     *
     * QQShare.SHARE_TO_QQ_APP_NAME 	可选 	String 	手Q客户端顶部，替换“返回”按钮文字，如果为空则用返回代替
     *
     * ---------------------------------------------------------------------------------
     *
     * QQShare.SHARE_TO_QQ_EXT_INT 	可选 	Int 	分享额外选项，两种类型可选（默认是不隐藏分享到QZone按钮且不自动打开分享到QZone的对话框）
     * QQShare.SHARE_TO_QQ_EXT_INT可选的值如下：
     * QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN：分享时自动打开分享到QZone的对话框。
     * QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE：分享时隐藏分享到QZone按钮
     */
    private fun buildQQParams(intent: Intent, shareContent: ShareContent): Intent {
        return intent
            .putExtra(QQShare.SHARE_TO_QQ_TITLE, shareContent.title)
            .putExtra(QQShare.SHARE_TO_QQ_SUMMARY, shareContent.desc)
            .putExtra(QQShare.SHARE_TO_QQ_APP_NAME, shareContent.appName)
            .putExtra(QQShare.SHARE_TO_QQ_EXT_INT, shareContent.extInt)
    }

    /**
     * 分享到QQ空间（目前支持图文分享、发表说说、视频或上传图片），不用用户授权
     * 调用后将打开手机QQ内QQ空间的界面，或者用浏览器打开QQ空间页面进行分享操作。
     *
     * ---------------------------------------------------------------------------------
     *
     * QzoneShare.SHARE_TO_QQ_KEY_TYPE 	    必填      Int 	SHARE_TO_QZONE_TYPE_IMAGE_TEXT（图文）
     * QzoneShare.SHARE_TO_QQ_TITLE 	    必填      Int 	分享的标题，最多200个字符
     * QzoneShare.SHARE_TO_QQ_TARGET_URL    必填      String 	跳转URL，URL字符串
     *
     * ---------------------------------------------------------------------------------
     *
     * QzoneShare.SHARE_TO_QQ_SUMMARY 	    选填      String 	分享的摘要，最多600字符
     * QzoneShare.SHARE_TO_QQ_IMAGE_URL     选填      String     图片链接ArrayList
     *
     * 注意:QZone接口暂不支持发送多张图片的能力，若传入多张图片，则会自动选入第一张图片作为预览图，多图的能力将会在以后支持。
     *
     * 如果分享的图片url是本地的图片地址那么在分享时会显示图片，如果分享的是图片的网址，那么就不会在分享时显示图片
     */
    fun zoneBundle(shareContent: ShareContent): Bundle? {
        // 分享的图片, 以ArrayList<String>的类型传入，以便支持多张图片 （注：图片最多支持9张图片，多余的图片会被丢弃）。
        val list =
            ArrayList(listOf(getThumbImageUri(shareContent)))
        return Intent()
            .putExtra(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT)
            .putExtra(QzoneShare.SHARE_TO_QQ_TITLE, shareContent.title)
            .putExtra(QzoneShare.SHARE_TO_QQ_SUMMARY, shareContent.desc)
            .putExtra(QzoneShare.SHARE_TO_QQ_TARGET_URL, shareContent.targetUrl)
            .putExtra(QzoneShare.SHARE_TO_QQ_IMAGE_URL, list)
            .getExtras()
    }

    /**
     * PUBLISH_TO_QZONE_KEY_TYP	必填	Int	QzonePublish.PUBLISH_TO_QZONE_TYPE_PUBLISHMOOD（发表说说、上传图片）
     * PUBLISH_TO_QZONE_SUMMARY	选填	String	说说正文（传图和传视频接口会过滤第三方传过来的自带描述，目的为了鼓励用户自行输入有价值信息）
     * PUBLISH_TO_QZONE_IMAGE_URL	选填	说说的图片, 以ArrayList<String>的类型传入，以便支持多张图片（注：<=9张图片为发表说说，>9张为上传图片到相册），只支持本地图片
     * PUBLISH_TO_QZONE_VIDEO_PATH	选填	发表的视频，只支持本地地址，发表视频时必填；上传视频的大小最好控制在100M以内（因为QQ普通用户上传视频必须在100M以内，黄钻用户可上传1G以内视频，大于1G会直接报错。）
    </String> */
    fun publishToQzoneBundle(shareContent: ShareContent): Bundle? {
        val list = ArrayList<String>()
        shareContent.imagePath?.let { list.add(it) }
        return Intent()
            .putExtra(
                QzoneShare.SHARE_TO_QZONE_KEY_TYPE,
                QzonePublish.PUBLISH_TO_QZONE_TYPE_PUBLISHMOOD
            )
            .putExtra(QzoneShare.SHARE_TO_QQ_SUMMARY, shareContent.desc)
            .putExtra(QzoneShare.SHARE_TO_QQ_IMAGE_URL, list)
            .getExtras()
    }

    /**
     * 得到分享时的预览小图
     */
    private fun getThumbImageUri(content: ShareContent): String? {
        return SlUtils.saveBytesToFile(
            content.thumbData,
            ShareLoginHelper.TEMP_PIC_DIR.toString() + "share_login_lib_thumb_pic"
        )
    }

    internal abstract class QQUiListener(private val listener: IBaseListener) : IUiListener {
        override fun onCancel() {
            listener.onCancel()
        }

        /**
         * 110201：未登陆
         * 110405：登录请求被限制
         * 110404：请求参数缺少appId
         * 110401：请求的应用不存在
         * 110407：应用已经下架
         * 110406：应用没有通过审核
         * 100044：错误的sign
         * 110500：获取用户授权信息失败
         * 110501：获取应用的授权信息失败
         * 110502：设置用户授权失败
         * 110503：获取token失败
         * 110504：系统内部错误
         */
        override fun onError(resp: UiError) {
            listener.onError("code:" + resp.errorCode.toString() + ", message:" + resp.errorMessage.toString() + ", detail:" + resp.errorDetail)
        }
    }
}