package com.lustyflix.streamverse.ui.player

import com.lustyflix.streamverse.AcraApplication.Companion.context
import com.lustyflix.streamverse.R
import com.lustyflix.streamverse.ui.player.PlayerSubtitleHelper.Companion.toSubtitleMimeType
import com.lustyflix.streamverse.utils.ExtractorLink
import com.lustyflix.streamverse.utils.ExtractorUri
import com.lustyflix.streamverse.utils.VideoDownloadManager
import kotlin.math.max
import kotlin.math.min

class DownloadFileGenerator(
    private val episodes: List<ExtractorUri>,
    private var currentIndex: Int = 0
) : IGenerator {
    override val hasCache = false

    override fun hasNext(): Boolean {
        return currentIndex < episodes.size - 1
    }

    override fun hasPrev(): Boolean {
        return currentIndex > 0
    }

    override fun next() {
        if (hasNext())
            currentIndex++
    }

    override fun prev() {
        if (hasPrev())
            currentIndex--
    }

    override fun goto(index: Int) {
        // clamps value
        currentIndex = min(episodes.size - 1, max(0, index))
    }

    override fun getCurrentId(): Int? {
        return episodes[currentIndex].id
    }

    override fun getCurrent(offset: Int): Any? {
        return episodes.getOrNull(currentIndex + offset)
    }

    override fun getAll(): List<Any>? {
        return null
    }

    fun cleanDisplayName(name: String): String {
        return name.substringBeforeLast('.').trim()
    }

    override suspend fun generateLinks(
        clearCache: Boolean,
        type: LoadType,
        callback: (Pair<ExtractorLink?, ExtractorUri?>) -> Unit,
        subtitleCallback: (SubtitleData) -> Unit,
        offset: Int
    ): Boolean {
        val meta = episodes[currentIndex + offset]
        callback(null to meta)

        val ctx = context ?: return true
        val relative = meta.relativePath ?: return true
        val display = meta.displayName ?: return true

        val cleanDisplay = cleanDisplayName(display)

        VideoDownloadManager.getFolder(ctx, relative, meta.basePath)
            ?.forEach { (name, uri) ->
                // only these files are allowed, so no videos as subtitles
                if (listOf(
                        ".vtt",
                        ".srt",
                        ".txt",
                        ".ass",
                        ".ttml",
                        ".sbv",
                        ".dfxp"
                    ).none { name.contains(it, true) }
                ) return@forEach

                // cant have the exact same file as a subtitle
                if (name.equals(display, true)) return@forEach

                val cleanName = cleanDisplayName(name)

                // we only want files with the approx same name
                if (!cleanName.startsWith(cleanDisplay, true)) return@forEach

                val realName = cleanName.removePrefix(cleanDisplay)

                subtitleCallback(
                    SubtitleData(
                        realName.ifBlank { ctx.getString(R.string.default_subtitles) },
                        uri.toString(),
                        SubtitleOrigin.DOWNLOADED_FILE,
                        name.toSubtitleMimeType(),
                        emptyMap(),
                        null
                    )
                )
            }

        return true
    }
}