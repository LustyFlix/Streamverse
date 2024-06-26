package com.lustyflix.streamverse.extractors

import com.lustyflix.streamverse.SubtitleFile
import com.lustyflix.streamverse.mvvm.logError
import com.lustyflix.streamverse.utils.ExtractorApi
import com.lustyflix.streamverse.utils.ExtractorLink
import com.lustyflix.streamverse.utils.schemaStripRegex
import org.schabi.newpipe.extractor.ServiceList
import org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeStreamExtractor
import org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubeStreamLinkHandlerFactory
import org.schabi.newpipe.extractor.stream.SubtitlesStream
import org.schabi.newpipe.extractor.stream.VideoStream

class YoutubeShortLinkExtractor : YoutubeExtractor() {
    override val mainUrl = "https://youtu.be"

    override fun getExtractorUrl(id: String): String {
        return "$mainUrl/$id"
    }
}

class YoutubeMobileExtractor  : YoutubeExtractor() {
    override val mainUrl = "https://m.youtube.com"
}
class YoutubeNoCookieExtractor  : YoutubeExtractor() {
    override val mainUrl = "https://www.youtube-nocookie.com"
}

open class YoutubeExtractor : ExtractorApi() {
    override val mainUrl = "https://www.youtube.com"
    override val requiresReferer = false
    override val name = "YouTube"

    companion object {
        private var ytVideos: MutableMap<String, List<VideoStream>> = mutableMapOf()
        private var ytVideosSubtitles: MutableMap<String, List<SubtitlesStream>> = mutableMapOf()
    }

    override fun getExtractorUrl(id: String): String {
        return "$mainUrl/watch?v=$id"
    }

    override suspend fun getUrl(
        url: String,
        referer: String?,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ) {
        if (ytVideos[url].isNullOrEmpty()) {
            val link =
                YoutubeStreamLinkHandlerFactory.getInstance().fromUrl(
                    url.replace(
                        schemaStripRegex, ""
                    )
                )

            val s = object : YoutubeStreamExtractor(
                ServiceList.YouTube,
                link
            ) {

            }
            s.fetchPage()
            ytVideos[url] = s.videoStreams
            ytVideosSubtitles[url] = try {
                s.subtitlesDefault.filterNotNull()
            } catch (e: Exception) {
                logError(e)
                emptyList()
            }
        }
        ytVideos[url]?.mapNotNull {
            if (it.isVideoOnly() || it.height <= 0) return@mapNotNull null

            ExtractorLink(
                this.name,
                this.name,
                it.content ?: return@mapNotNull null,
                "",
                it.height
            )
        }?.forEach(callback)
        ytVideosSubtitles[url]?.mapNotNull {
            SubtitleFile(it.languageTag ?: return@mapNotNull null, it.content ?: return@mapNotNull null)
        }?.forEach(subtitleCallback)
    }
}