package com.lustyflix.streamverse.ui.search

import com.lustyflix.streamverse.SearchQuality
import com.lustyflix.streamverse.SearchResponse
import com.lustyflix.streamverse.TvType
import com.lustyflix.streamverse.syncproviders.AccountManager.Companion.SyncApis

class SyncSearchViewModel {
    private val repos = SyncApis

    data class SyncSearchResultSearchResponse(
        override val name: String,
        override val url: String,
        override val apiName: String,
        override var type: TvType?,
        override var posterUrl: String?,
        override var id: Int?,
        override var quality: SearchQuality? = null,
        override var posterHeaders: Map<String, String>? = null,
    ) : SearchResponse
}