package com.julive.lib.view.base

import android.app.Dialog
import android.os.Bundle
import android.view.WindowManager
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.julive.lib.R

/**
 * Created by lsf on 2020/10/12 3:26 PM
 * Function :Bottom Sheet Dialog基类
 */
open class BaseFullBottomSheetFragment : BottomSheetDialogFragment() {
    /**
     * 顶部向下偏移量
     */
    private var behavior: BottomSheetBehavior<FrameLayout>? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return if (context == null) {
            super.onCreateDialog(savedInstanceState)
        } else BottomSheetDialog(context!!, R.style.SLTransparentBottomSheetStyle)
    }

    override fun onStart() {
        super.onStart()
        // 设置软键盘不自动弹出
        dialog?.also {
            it.window?.apply {
                setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
                val dialog = it as BottomSheetDialog
                val bottomSheet =
                    dialog.delegate.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
                if (bottomSheet != null) {
                    behavior = BottomSheetBehavior.from(bottomSheet)
                    // 初始为展开状态
                    behavior?.state = BottomSheetBehavior.STATE_EXPANDED
                }
            }
        }
    }

    fun getBehavior(): BottomSheetBehavior<FrameLayout>? {
        return behavior
    }
}