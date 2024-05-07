package com.lustyflix.streamverse.ui.search

import android.widget.Toast
import com.lustyflix.streamverse.CommonActivity.activity
import com.lustyflix.streamverse.CommonActivity.showToast
import com.lustyflix.streamverse.MainActivity
import com.lustyflix.streamverse.R
import com.lustyflix.streamverse.ui.download.DOWNLOAD_ACTION_PLAY_FILE
import com.lustyflix.streamverse.ui.download.DownloadButtonSetup.handleDownloadClick
import com.lustyflix.streamverse.ui.download.DownloadClickEvent
import com.lustyflix.streamverse.ui.result.START_ACTION_LOAD_EP
import com.lustyflix.streamverse.ui.settings.Globals.PHONE
import com.lustyflix.streamverse.ui.settings.Globals.isLayout
import com.lustyflix.streamverse.utils.AppUtils.loadSearchResult
import com.lustyflix.streamverse.utils.DataStoreHelper
import com.lustyflix.streamverse.utils.VideoDownloadHelper

object SearchHelper {
    fun handleSearchClickCallback(callback: SearchClickCallback) {
        val card = callback.card
        when (callback.action) {
            SEARCH_ACTION_LOAD -> {
                loadSearchResult(card)
            }
            SEARCH_ACTION_PLAY_FILE -> {
                if (card is DataStoreHelper.ResumeWatchingResult) {
                    val id = card.id
                    if(id == null) {
                        showToast(R.string.error_invalid_id, Toast.LENGTH_SHORT)
                    } else {
                        if (card.isFromDownload) {
                            handleDownloadClick(
                                DownloadClickEvent(
                                    DOWNLOAD_ACTION_PLAY_FILE,
                                    VideoDownloadHelper.DownloadEpisodeCached(
                                        card.name,
                                        card.posterUrl,
                                        card.episode ?: 0,
                                        card.season,
                                        id,
                                        card.parentId ?: return,
                                        null,
                                        null,
                                        System.currentTimeMillis()
                                    )
                                )
                            )
                        } else {
                            loadSearchResult(card, START_ACTION_LOAD_EP, id)
                        }
                    }
                } else {
                    handleSearchClickCallback(
                        SearchClickCallback(SEARCH_ACTION_LOAD, callback.view, -1, callback.card)
                    )
                }
            }
            SEARCH_ACTION_SHOW_METADATA -> {
                if(isLayout(PHONE)) { // we only want this on phone as UI is not done yet on tv
                    (activity as? MainActivity?)?.apply {
                        loadPopup(callback.card)
                    } ?: kotlin.run {
                        showToast(callback.card.name, Toast.LENGTH_SHORT)
                    }
                } else {
                    showToast(callback.card.name, Toast.LENGTH_SHORT)
                }
            }
        }
    }
}