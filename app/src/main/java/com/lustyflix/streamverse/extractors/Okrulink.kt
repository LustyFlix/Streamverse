package com.lustyflix.streamverse.extractors

import com.fasterxml.jackson.annotation.JsonProperty
import com.lustyflix.streamverse.app
import com.lustyflix.streamverse.utils.ExtractorApi
import com.lustyflix.streamverse.utils.ExtractorLink
import com.lustyflix.streamverse.utils.Qualities

data class Okrulinkdata (
    @JsonProperty("status" ) var status : String? = null,
    @JsonProperty("url"    ) var url    : String? = null
)

open class Okrulink: ExtractorApi() {
    override var mainUrl = "https://okru.link"
    override var name = "Okrulink"
    override val requiresReferer = false

    override suspend fun getUrl(url: String, referer: String?): List<ExtractorLink> {
        val sources = mutableListOf<ExtractorLink>()
        val key = url.substringAfter("html?t=")
        val request = app.post("https://apizz.okru.link/decoding", allowRedirects = false,
            data = mapOf("video" to key)
        ).parsedSafe<Okrulinkdata>()
        if (request?.url != null) {
            sources.add(
                ExtractorLink(
                    name,
                    name,
                    request.url!!,
                    "",
                    Qualities.Unknown.value,
                    isM3u8 = false
                )
            )
        }
        return sources
    }
}
