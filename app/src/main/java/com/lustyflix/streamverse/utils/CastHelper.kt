package com.lustyflix.streamverse.utils

import android.net.Uri
import androidx.media3.common.MimeTypes
import com.google.android.gms.cast.*
import com.google.android.gms.cast.framework.CastSession
import com.google.android.gms.cast.framework.media.RemoteMediaClient
import com.google.android.gms.common.api.PendingResult
import com.google.android.gms.common.images.WebImage
import com.lustyflix.streamverse.mvvm.logError
import com.lustyflix.streamverse.ui.MetadataHolder
import com.lustyflix.streamverse.ui.player.SubtitleData
import com.lustyflix.streamverse.ui.result.ResultEpisode
import com.lustyflix.streamverse.utils.AppUtils.toJson
import com.lustyflix.streamverse.utils.Coroutines.main
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

object CastHelper {
    fun getMediaInfo(
        epData: ResultEpisode,
        holder: MetadataHolder,
        index: Int,
        data: JSONObject?,
        subtitles: List<SubtitleData>
    ): MediaInfo {
        val link = holder.currentLinks[index]
        val movieMetadata = MediaMetadata(MediaMetadata.MEDIA_TYPE_MOVIE)
        movieMetadata.putString(
            MediaMetadata.KEY_SUBTITLE,
            if (holder.isMovie)
                "${link.name} ${Qualities.getStringByInt(link.quality)}"
            else
                (epData.name ?: "Episode ${epData.episode}") + " - ${link.name} ${Qualities.getStringByInt(link.quality)}"
        )

        holder.title?.let {
            movieMetadata.putString(MediaMetadata.KEY_TITLE, it)
        }

        val srcPoster = epData.poster ?: holder.poster
        if (srcPoster != null) {
            movieMetadata.addImage(WebImage(Uri.parse(srcPoster)))
        }

        var subIndex = 0
        val tracks = subtitles.map {
            MediaTrack.Builder(subIndex++.toLong(), MediaTrack.TYPE_TEXT)
                .setName(it.name)
                .setSubtype(MediaTrack.SUBTYPE_SUBTITLES)
                .setContentId(it.url)
                .build()
        }

        val builder = MediaInfo.Builder(link.url)
            .setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
            .setContentType(when(link.type) {
                ExtractorLinkType.M3U8 -> MimeTypes.APPLICATION_M3U8
                ExtractorLinkType.DASH -> MimeTypes.APPLICATION_MPD
                else -> MimeTypes.VIDEO_MP4
            })
            .setMetadata(movieMetadata)
            .setMediaTracks(tracks)
        data?.let {
            builder.setCustomData(data)
        }

        return builder.build()
    }

    fun awaitLinks(
        pending: PendingResult<RemoteMediaClient.MediaChannelResult>?,
        callback: (Boolean) -> Unit
    ) {
        if (pending == null) return
        main {
            val res = withContext(Dispatchers.IO) { pending.await() }
            when (res.status.statusCode) {
                CastStatusCodes.FAILED -> {
                    callback.invoke(true)
                    println("FAILED AND LOAD NEXT")
                }
                else -> Unit //IDK DO SMTH HERE
            }
        }
    }

    fun CastSession?.startCast(
        apiName: String,
        isMovie: Boolean,
        title: String?,
        poster: String?,
        currentEpisodeIndex: Int,
        episodes: List<ResultEpisode>,
        currentLinks: List<ExtractorLink>,
        subtitles: List<SubtitleData>,
        startIndex: Int? = null,
        startTime: Long? = null,
    ): Boolean {
        try {
            if (this == null) return false
            if (episodes.isEmpty()) return false
            if (currentEpisodeIndex >= episodes.size) return false

            val epData = episodes[currentEpisodeIndex]

            val holder =
                MetadataHolder(
                    apiName,
                    isMovie,
                    title,
                    poster,
                    currentEpisodeIndex,
                    episodes,
                    currentLinks,
                    subtitles
                )

            val index = if (startIndex == null || startIndex < 0) 0 else startIndex

            val mediaItem =
                getMediaInfo(epData, holder, index, JSONObject(holder.toJson()), subtitles)

            awaitLinks(
                this.remoteMediaClient?.load(
                    MediaLoadRequestData.Builder().setMediaInfo(mediaItem)
                        .setCurrentTime(startTime ?: 0L).build()
                )
            ) {
                if (currentLinks.size > index + 1)
                    startCast(
                        apiName,
                        isMovie,
                        title,
                        poster,
                        currentEpisodeIndex,
                        episodes,
                        currentLinks,
                        subtitles,
                        index + 1,
                        startTime
                    )
            }
            return true
        } catch (e: Exception) {
            logError(e)
            return false
        }
    }
}