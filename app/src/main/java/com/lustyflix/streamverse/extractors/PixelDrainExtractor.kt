// ! Bu araç @keyiflerolsun tarafından | @KekikAkademi için yazılmıştır.

package com.lustyflix.streamverse.extractors

import com.lustyflix.streamverse.*
import com.lustyflix.streamverse.utils.*

open class PixelDrain : ExtractorApi() {
    override val name            = "PixelDrain"
    override val mainUrl         = "https://pixeldrain.com"
    override val requiresReferer = true

    override suspend fun getUrl(url: String, referer: String?, subtitleCallback: (SubtitleFile) -> Unit, callback: (ExtractorLink) -> Unit) {
        val mId = Regex("/([ul]/[\\da-zA-Z\\-]+)(?:\\?download)?").find(url)?.groupValues?.get(1)?.split("/")
        callback.invoke(
            ExtractorLink(
                this.name,
                this.name,
                "$mainUrl/api/file/${mId?.last() ?: return}?download",
                url,
                Qualities.Unknown.value,
            )
        )
    }
}