package com.julive.lib.view.adapter.base

import android.widget.BaseAdapter

/**
 * Created by lsf on 2020/11/10 2:37 PM
 * Function : Adapter 基类
 */
abstract class AbsBaseAdapter<T>(
    val list: List<T>
) : BaseAdapter() {

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any? {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    open fun getDatas(): List<T>? {
        return list
    }
}