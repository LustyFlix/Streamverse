package com.lustyflix.streamverse.extractors

import com.lustyflix.streamverse.SubtitleFile
import com.lustyflix.streamverse.app
import com.lustyflix.streamverse.utils.*

open class Acefile : ExtractorApi() {
    override val name = "Acefile"
    override val mainUrl = "https://acefile.co"
    override val requiresReferer = false

    override suspend fun getUrl(
        url: String,
        referer: String?,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ) {
        val id = "/(?:d|download|player|f|file)/(\\w+)".toRegex().find(url)?.groupValues?.get(1)
        val script = getAndUnpack(app.get("$mainUrl/player/${id ?: return}").text)
        val service = """service\s*=\s*['"]([^'"]+)""".toRegex().find(script)?.groupValues?.get(1)
        val serverUrl = """['"](\S+check&id\S+?)['"]""".toRegex().find(script)?.groupValues?.get(1)
            ?.replace("\"+service+\"", service ?: return)

        val video = app.get(serverUrl ?: return, referer = "$mainUrl/").parsedSafe<Source>()?.data

        callback.invoke(
            ExtractorLink(
                this.name,
                this.name,
                video ?: return,
                "",
                Qualities.Unknown.value,
                INFER_TYPE
            )
        )

    }

    data class Source(
        val data: String? = null,
    )

}