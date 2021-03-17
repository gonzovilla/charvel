package com.ust.charvel.utils

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.max

class CustomRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : RecyclerView(context, attrs, defStyle) {

    private var emptyTextView: TextView? = null
    private var emptyRefreshButton: ImageView? = null
    private var progressBar: ProgressBar? = null
    private var columnWidth: Int = 0

    init {
        gone()
        if (attrs != null) {
            val attrsArray = intArrayOf(android.R.attr.columnWidth)
            val array = context.obtainStyledAttributes(
                attrs, attrsArray
            )
            columnWidth = array.getDimensionPixelSize(0, -1)
            array.recycle()
        }
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        visible()
        val oldAdapter = getAdapter()
        oldAdapter?.unregisterAdapterDataObserver(mAdapterObserver)
        super.setAdapter(adapter)
        adapter?.registerAdapterDataObserver(mAdapterObserver)
        refreshState()
    }

    private fun refreshState() {
        adapter?.let {
            val noItems = 0 == it.itemCount
            if (noItems) {
                progressBar?.gone()
                emptyTextView?.visible()
                emptyRefreshButton?.visible()
                gone()
            } else {
                progressBar?.gone()
                emptyTextView?.gone()
                emptyRefreshButton?.gone()
                visible()
            }
        }
    }

    fun setEmptyListViews(emptyTextView: TextView, refreshButton: ImageView) {
        this.emptyTextView = emptyTextView
        this.emptyRefreshButton = refreshButton
        this.emptyTextView?.gone()
        this.emptyRefreshButton?.gone()
    }

    fun setProgressBar(progressBar: ProgressBar) {
        this.progressBar = progressBar
        this.progressBar?.visible()
    }

    fun showLoading() {
        emptyTextView?.gone()
        emptyRefreshButton?.gone()
        progressBar?.visible()
    }

    fun isLoading(): Boolean {
        return progressBar?.isVisible == true
    }

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        super.onMeasure(widthSpec, heightSpec)
        if (layoutManager is GridLayoutManager) {
            val manager = layoutManager as GridLayoutManager
            if (columnWidth > 0) {
                val spanCount = max(1, measuredWidth / columnWidth)
                manager.spanCount = spanCount
            }
        }
    }

    private val mAdapterObserver = object : RecyclerView.AdapterDataObserver() {
        override fun onChanged() = refreshState()
        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) = refreshState()
        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) = refreshState()
    }

}
