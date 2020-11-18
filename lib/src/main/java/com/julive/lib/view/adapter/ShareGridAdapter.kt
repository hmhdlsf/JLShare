package com.julive.lib.view.adapter

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.julive.lib.R
import com.julive.lib.view.adapter.base.AbsBaseGvAdapter
import com.julive.lib.view.entity.ShareGridEntity

/**
 * Created by lsf on 2020/11/10 3:25 PM
 * Function : 分享 adapter
 */
class ShareGridAdapter(
    val context: Context?,
    list: List<ShareGridEntity>
) : AbsBaseGvAdapter<ShareGridEntity>(context, list) {

    override fun getContentLayoutName(): Int {
        return R.layout.item_bottom_share
    }

    override fun getViewHolder(context: Context?, view: View?): BaseViewHolder<ShareGridEntity> {
        return MyViewHolder(context, view)
    }

    class MyViewHolder(
        context: Context?,
        view: View?
    ) : BaseViewHolder<ShareGridEntity>(context, view) {

        private var mContext: Context? = null
        private var mView: View? = null
        private var mPlatformImg: ImageView? = null
        private var mPlatformLabel: TextView? = null

        init {
            mContext = context
            mView = view

            view?.apply {
                mPlatformImg = findViewById(R.id.iv_icon)
                mPlatformLabel = findViewById(R.id.tv_name)
            }
        }

        override fun bindData(data: ShareGridEntity, position: Int) {
            data.platformImg?.let { mPlatformImg?.setImageBitmap(it) }
            data.platformLabel?.let { mPlatformLabel?.setText(it) }
        }
    }
}