package com.lustyflix.streamverse.extractors

import android.util.Log
import com.lustyflix.streamverse.app
import com.lustyflix.streamverse.utils.ExtractorApi
import com.lustyflix.streamverse.utils.ExtractorLink
import com.lustyflix.streamverse.utils.Qualities

open class AStreamHub : ExtractorApi() {
    override val name = "AStreamHub"
    override val mainUrl = "https://astreamhub.com"
    override val requiresReferer = true

    override suspend fun getUrl(url: String, referer: String?): List<ExtractorLink> {
        val sources = mutableListOf<ExtractorLink>()
        app.get(url).document.selectFirst("body > script").let { script ->
            val text = script?.html() ?: ""
            Log.i("Dev", "text => $text")
            if (text.isNotBlank()) {
                val m3link = "(?<=file:)(.*)(?=,)".toRegex().find(text)
                    ?.groupValues?.get(0)?.trim()?.trim('"') ?: ""
                Log.i("Dev", "m3link => $m3link")
                if (m3link.isNotBlank()) {
                    sources.add(
                        ExtractorLink(
                            name = name,
                            source = name,
                            url = m3link,
                            isM3u8 = true,
                            quality = Qualities.Unknown.value,
                            referer = referer ?: url
                        )
                    )
                }
            }
        }
        return sources
    }

}