package com.julive.lib.view.adapter.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by lsf on 2020/11/10 2:43 PM
 * Function : GridView Adapter 基类
 */
abstract class AbsBaseGvAdapter<T>(
    private val context: Context?,
    list: List<T>
) : AbsBaseAdapter<T>(list) {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(
        position: Int,
        view: View?,
        parent: ViewGroup?
    ): View? {
        var convertView = view
        val holder: BaseViewHolder<T>
        if (convertView == null) {
            convertView = mInflater.inflate(getContentLayoutName(), parent, false)
            holder = getViewHolder(context, convertView)
            convertView.tag = holder
        } else {
            holder = convertView.tag as BaseViewHolder<T>
        }
        holder.bindData(list[position], position)
        return convertView
    }

    abstract class BaseViewHolder<T>(
        context: Context?,
        view: View?
    ) {
        abstract fun bindData(data: T, position: Int)
    }

    /**
     * 获取item的布局
     *
     * @return
     */
    protected abstract fun getContentLayoutName(): Int

    /**
     * 获取Viewholder
     *
     * @return
     */
    protected abstract fun getViewHolder(
        context: Context?,
        view: View?
    ): BaseViewHolder<T>
}