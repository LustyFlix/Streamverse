package com.lustyflix.streamverse.extractors

import com.lustyflix.streamverse.utils.*

open class ByteShare : ExtractorApi() {
    override val name = "ByteShare"
    override val mainUrl = "https://byteshare.to"
    override val requiresReferer = false

    override suspend fun getUrl(url: String, referer: String?): List<ExtractorLink> {
        val sources = mutableListOf<ExtractorLink>()
        sources.add(
            ExtractorLink(
                name,
                name,
                url.replace("/embed/", "/download/"),
                "",
                Qualities.Unknown.value,
            )
        )
        return sources
    }
}
