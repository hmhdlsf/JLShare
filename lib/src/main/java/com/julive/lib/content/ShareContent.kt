package com.julive.lib.content

class ShareContent {
    // 分享的方式
    var type: Int? = 0

    /**
     * 公共参数
     */
    // 分享的标题
    var title: String? = ""

    // 分享的描述
    var desc: String? = ""

    // 分享的缩略图片
    var thumbData: ByteArray? = byteArrayOf()

    var mediaTagName: String? = ""

    var messageAction: String? = ""

    /**
     * 文本
     */
    // 数据：长度需大于 0 且不超过 10KB
    var text: String? = ""

    /**
     * 图片
     */
    // 图片的本地路径： 对应图片内容大小不超过 10MB
    var imagePath: String? = ""

    // 图片的二进制数据：内容大小不超过 10MB
    var imageData: ByteArray? = byteArrayOf()

    /**
     * 音乐
     */
    var musicUrl: String? = ""

    /**
     * 视频
     */
    var videoUrl: String? = ""

    /**
     * 网页
     */
    var webPageUrl: String? = ""

    /**
     * 小程序
     */
    var miniProgramType: Int? = 0

    var userName: String? = ""

    var path: String? = ""

    /**
     * 短信
     */
    var phone: String? = ""


    /**
     * QQ 独有的字段
     */
    var appName: String? = ""

    var extInt: Int? = 0

    var jsonStr: String? = ""

}