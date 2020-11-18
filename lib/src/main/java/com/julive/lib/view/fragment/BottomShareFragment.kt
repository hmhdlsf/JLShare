package com.julive.lib.view.fragment

import android.app.Activity
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.julive.lib.OneKeyShare.Companion.CUSTOM_PLATFORM
import com.julive.lib.OneKeyShareHelper
import com.julive.lib.R
import com.julive.lib.common.Constants
import com.julive.lib.content.ShareContent
import com.julive.lib.entity.CustomPlatform
import com.julive.lib.entity.Platform
import com.julive.lib.extensions.isValid
import com.julive.lib.helper.ShareLoginHelper
import com.julive.lib.interfaces.IPlatformClickListener
import com.julive.lib.listener.ShareListener
import com.julive.lib.platforms.qq.QQPlatform
import com.julive.lib.platforms.wx.WXPlatform
import com.julive.lib.view.adapter.ShareGridAdapter
import com.julive.lib.view.base.BaseFullBottomSheetFragment
import com.julive.lib.view.entity.ShareGridEntity
import java.util.*

/**
 * Created by lsf on 2020/11/10 11:07 AM
 * Function : 分享弹窗
 */
class BottomShareFragment(private val map: HashMap<String, Any>) : BaseFullBottomSheetFragment() {

    private lateinit var mRootView: View
    private lateinit var mGridView: GridView
    private lateinit var mAdapter: ShareGridAdapter
    private var mDataList: MutableList<ShareGridEntity> = mutableListOf()
    private var mShareContent = ShareContent()

    private val platformLabel = arrayOf("微信好友", "微信朋友圈", "微信收藏", "QQ", "QQ空间", "短信")
    private val platformImg = arrayOf(
        R.drawable.wx_friends,
        R.drawable.wx_moment,
        R.drawable.wx_favorite,
        R.drawable.qq_friends,
        R.drawable.qq_zone,
        R.drawable.short_msg
    )
    private val platformName = arrayOf(
        Constants.WX_FRIENDS,
        Constants.WX_MOMENT,
        Constants.WX_FAVORITE,
        Constants.QQ_FRIENDS,
        Constants.QQ_ZONE,
        Constants.SHORT_MSG
    )

    //<editor-fold desc="一键分享传递参数">
    /**
     * 隐藏的平台列表
     */
    private var mHiddenList: ArrayList<String> = arrayListOf()

    /**
     * 自定义的平台列表
     */
    private var mCustomPlatforms: ArrayList<CustomPlatform> = arrayListOf()

    /**
     * 平台点击回调
     */
    private var mPlatformClickListener: IPlatformClickListener? = null

    /**
     * 分享回调
     */
    private var mShareListener: ShareListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mRootView = inflater.inflate(R.layout.fragment_bottom_share, container, false)
        initView()
        return mRootView
    }

    private fun initView() {
        // 模拟分享内容
//        mShareContent = ShareContent()
//        mShareContent.type = ShareContentType.TEXT
//        mShareContent.text = "啦啦啦"

        // 一键分享传递参数
        mHiddenList = OneKeyShareHelper.getHiddenPlatform(map)
        mCustomPlatforms = OneKeyShareHelper.getCustomPlatforms(map)
        mPlatformClickListener = OneKeyShareHelper.getPlatformClickListener(map)
        mShareListener = OneKeyShareHelper.getShareListener(map)

        // 获取展示的平台
        getPlatformList()

        mGridView = mRootView.findViewById(R.id.gv_share)
        mAdapter = ShareGridAdapter(context, mDataList)
        mGridView.adapter = mAdapter
        mGridView.setOnItemClickListener { parent, view, position, id ->
            val platformName = mDataList[position].platformName
            var platformType: String = ""
            mPlatformClickListener?.onPlatformClick(Platform(platformName), mShareContent)
            when (platformName) {
                // 微信好友
                Constants.WX_FRIENDS -> {
                    platformType = WXPlatform.FRIEND
                }

                // 微信朋友圈
                Constants.WX_MOMENT -> {
                    platformType = WXPlatform.TIMELINE
                }

                // 微信分享
                Constants.WX_FAVORITE -> {
                    platformType = WXPlatform.FAVORITE
                }

                // QQ好友
                Constants.QQ_FRIENDS -> {
                    platformType = QQPlatform.FRIEND
                }

                // QQ空间
                Constants.QQ_ZONE -> {
                    platformType = QQPlatform.ZONE
                }

                // 短信分享
                Constants.SHORT_MSG -> {
                    ShareLoginHelper.doShortMsg(
                        activity as Activity,
                        mShareContent.phone,
                        mShareContent.desc
                    )
                }

                else -> {
                    val listener = mDataList[position].listener
                    if (listener.isValid()) {
                        listener?.onClick(view)
                    }
                }
            }

            if (platformType.isNotEmpty()) {
                ShareLoginHelper.doShare(
                    activity as Activity, platformType,
                    mShareContent,
                    mShareListener
                )
            }

            doFinish()
        }
    }

    private fun getPlatformList(): List<ShareGridEntity> {
        for (index in platformLabel.indices) {
            val bean = ShareGridEntity()
            bean.platformName = platformName[index]
            bean.platformLabel = platformLabel[index]
            bean.platformImg = BitmapFactory.decodeResource(context?.resources, platformImg[index])

            if (!mHiddenList.contains(bean.platformName)) {
                mDataList.add(bean)
            }
        }

        if (mCustomPlatforms.isValid()) {
            for (index in mCustomPlatforms.indices) {
                val bean = ShareGridEntity()
                bean.platformName = CUSTOM_PLATFORM
                bean.platformLabel = mCustomPlatforms[index].label
                bean.platformImg = mCustomPlatforms[index].bitmap
                bean.listener = mCustomPlatforms[index].listener
                mDataList.add(bean)
            }
        }
        return mDataList
    }

    private fun doFinish() {
        if (getBehavior() != null) {
            getBehavior()?.state = BottomSheetBehavior.STATE_HIDDEN
        }
    }

}