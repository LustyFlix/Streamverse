package com.lustyflix.streamverse

import android.view.LayoutInflater
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.lustyflix.streamverse.ui.HeaderViewDecoration

fun setHeaderDecoration(view: RecyclerView, @LayoutRes headerViewRes: Int) {
    val headerView = LayoutInflater.from(view.context).inflate(headerViewRes, null)
    view.addItemDecoration(HeaderViewDecoration(headerView))
}