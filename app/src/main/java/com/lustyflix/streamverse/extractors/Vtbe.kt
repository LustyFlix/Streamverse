package com.lustyflix.streamverse.extractors

import android.util.Log
import com.fasterxml.jackson.annotation.JsonProperty
import com.lustyflix.streamverse.app
import com.lustyflix.streamverse.utils.*
import com.lustyflix.streamverse.utils.AppUtils.tryParseJson
import com.lustyflix.streamverse.utils.JsUnpacker
import com.lustyflix.streamverse.utils.ExtractorApi
import com.lustyflix.streamverse.utils.ExtractorLink
import com.lustyflix.streamverse.utils.Qualities
import com.lustyflix.streamverse.utils.getQualityFromName
import java.net.URI


open class Vtbe : ExtractorApi() {
    override var name = "Vtbe"
    override var mainUrl = "https://vtbe.to"
    override val requiresReferer = true

    override suspend fun getUrl(url: String, referer: String?): List<ExtractorLink>? {
        val response = app.get(url,referer=mainUrl).document
        val extractedpack =response.selectFirst("script:containsData(function(p,a,c,k,e,d))")?.data().toString()
            JsUnpacker(extractedpack).unpack()?.let { unPacked ->
                Regex("sources:\\[\\{file:\"(.*?)\"").find(unPacked)?.groupValues?.get(1)?.let { link ->
                    return listOf(
                        ExtractorLink(
                            this.name,
                            this.name,
                            link,
                            referer ?: "",
                            Qualities.Unknown.value,
                            URI(link).path.endsWith(".m3u8")
                        )
                    )
                }
            }
            return null
    }
}
