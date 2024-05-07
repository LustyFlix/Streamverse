package com.lustyflix.streamverse.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.lustyflix.streamverse.HomePageList
import com.lustyflix.streamverse.LoadResponse
import com.lustyflix.streamverse.R
import com.lustyflix.streamverse.databinding.HomepageParentBinding
import com.lustyflix.streamverse.mvvm.logError
import com.lustyflix.streamverse.ui.BaseAdapter
import com.lustyflix.streamverse.ui.BaseDiffCallback
import com.lustyflix.streamverse.ui.ViewHolderState
import com.lustyflix.streamverse.ui.result.FOCUS_SELF
import com.lustyflix.streamverse.ui.result.setLinearListLayout
import com.lustyflix.streamverse.ui.search.SearchClickCallback
import com.lustyflix.streamverse.ui.settings.Globals.EMULATOR
import com.lustyflix.streamverse.ui.settings.Globals.PHONE
import com.lustyflix.streamverse.ui.settings.Globals.TV
import com.lustyflix.streamverse.ui.settings.Globals.isLayout
import com.lustyflix.streamverse.utils.AppUtils.isRecyclerScrollable

class LoadClickCallback(
    val action: Int = 0,
    val view: View,
    val position: Int,
    val response: LoadResponse
)

open class ParentItemAdapter(
    open val fragment: Fragment,
    id: Int,
    private val clickCallback: (SearchClickCallback) -> Unit,
    private val moreInfoClickCallback: (HomeViewModel.ExpandableHomepageList) -> Unit,
    private val expandCallback: ((String) -> Unit)? = null,
) : BaseAdapter<HomeViewModel.ExpandableHomepageList, Bundle>(
    fragment,
    id,
    diffCallback = BaseDiffCallback(
        itemSame = { a, b -> a.list.name == b.list.name },
        contentSame = { a, b ->
            a.list.list == b.list.list
        })
) {
    data class ParentItemHolder(val binding: ViewBinding) : ViewHolderState<Bundle>(binding) {
        override fun save(): Bundle = Bundle().apply {
            val recyclerView = (binding as? HomepageParentBinding)?.homeChildRecyclerview
            putParcelable(
                "value",
                recyclerView?.layoutManager?.onSaveInstanceState()
            )
            (recyclerView?.adapter as? BaseAdapter<*,*>)?.save(recyclerView)
        }

        override fun restore(state: Bundle) {
            (binding as? HomepageParentBinding)?.homeChildRecyclerview?.layoutManager?.onRestoreInstanceState(
                state.getParcelable("value")
            )
        }
    }

    override fun submitList(list: List<HomeViewModel.ExpandableHomepageList>?) {
        super.submitList(list?.sortedBy { it.list.list.isEmpty() })
    }

    override fun onUpdateContent(
        holder: ViewHolderState<Bundle>,
        item: HomeViewModel.ExpandableHomepageList,
        position: Int
    ) {
        val binding = holder.view
        if (binding !is HomepageParentBinding) return
        (binding.homeChildRecyclerview.adapter as? HomeChildItemAdapter)?.submitList(item.list.list)
    }

    override fun onBindContent(
        holder: ViewHolderState<Bundle>,
        item: HomeViewModel.ExpandableHomepageList,
        position: Int
    ) {
        val startFocus = R.id.nav_rail_view
        val endFocus = FOCUS_SELF
        val binding = holder.view
        if (binding !is HomepageParentBinding) return
        val info = item.list
        binding.apply {
            homeChildRecyclerview.adapter = HomeChildItemAdapter(
                fragment = fragment,
                id = id + position + 100,
                clickCallback = clickCallback,
                nextFocusUp = homeChildRecyclerview.nextFocusUpId,
                nextFocusDown = homeChildRecyclerview.nextFocusDownId,
            ).apply {
                isHorizontal = info.isHorizontalImages
                hasNext = item.hasNext
                submitList(item.list.list)
            }
            homeChildRecyclerview.setLinearListLayout(
                isHorizontal = true,
                nextLeft = startFocus,
                nextRight = endFocus,
            )
            homeChildMoreInfo.text = info.name

            homeChildRecyclerview.addOnScrollListener(object :
                RecyclerView.OnScrollListener() {
                var expandCount = 0
                val name = item.list.name

                override fun onScrollStateChanged(
                    recyclerView: RecyclerView,
                    newState: Int
                ) {
                    super.onScrollStateChanged(recyclerView, newState)

                    val adapter = recyclerView.adapter
                    if (adapter !is HomeChildItemAdapter) return

                    val count = adapter.itemCount
                    val hasNext = adapter.hasNext
                    /*println(
                        "scolling ${recyclerView.isRecyclerScrollable()} ${
                            recyclerView.canScrollHorizontally(
                                1
                            )
                        }"
                    )*/
                    //!recyclerView.canScrollHorizontally(1)
                    if (!recyclerView.isRecyclerScrollable() && hasNext && expandCount != count) {
                        expandCount = count
                        expandCallback?.invoke(name)
                    }
                }
            })

            //(recyclerView.adapter as HomeChildItemAdapter).notifyDataSetChanged()
            if (isLayout(PHONE)) {
                homeChildMoreInfo.setOnClickListener {
                    moreInfoClickCallback.invoke(item)
                }
            }
        }
    }

    override fun onCreateContent(parent: ViewGroup): ParentItemHolder {
        val layoutResId = when {
            isLayout(TV) -> R.layout.homepage_parent_tv
            isLayout(EMULATOR) -> R.layout.homepage_parent_emulator
            else -> R.layout.homepage_parent
        }

        val inflater = LayoutInflater.from(parent.context)
        val binding = try {
            HomepageParentBinding.bind(inflater.inflate(layoutResId, parent, false))
        } catch (t: Throwable) {
            logError(t)
            // just in case someone forgot we don't want to crash
            HomepageParentBinding.inflate(inflater)
        }

        return ParentItemHolder(binding)
    }

    fun updateList(newList: List<HomePageList>) {
        submitList(newList.map { HomeViewModel.ExpandableHomepageList(it, 1, false) }
            .toMutableList())
    }
}