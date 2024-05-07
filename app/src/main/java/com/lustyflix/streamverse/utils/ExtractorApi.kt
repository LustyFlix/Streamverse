package com.lustyflix.streamverse.utils

import android.net.Uri
import com.fasterxml.jackson.annotation.JsonIgnore
import com.lustyflix.streamverse.SubtitleFile
import com.lustyflix.streamverse.TvType
import com.lustyflix.streamverse.USER_AGENT
import com.lustyflix.streamverse.app
import com.lustyflix.streamverse.extractors.AStreamHub
import com.lustyflix.streamverse.extractors.Acefile
import com.lustyflix.streamverse.extractors.Ahvsh
import com.lustyflix.streamverse.extractors.Aico
import com.lustyflix.streamverse.extractors.AsianLoad
import com.lustyflix.streamverse.extractors.Bestx
import com.lustyflix.streamverse.extractors.Blogger
import com.lustyflix.streamverse.extractors.BullStream
import com.lustyflix.streamverse.extractors.ByteShare
import com.lustyflix.streamverse.extractors.Cda
import com.lustyflix.streamverse.extractors.Cdnplayer
import com.lustyflix.streamverse.extractors.Chillx
import com.lustyflix.streamverse.extractors.CineGrabber
import com.lustyflix.streamverse.extractors.Cinestart
import com.lustyflix.streamverse.extractors.DBfilm
import com.lustyflix.streamverse.extractors.Dailymotion
import com.lustyflix.streamverse.extractors.DatabaseGdrive
import com.lustyflix.streamverse.extractors.DatabaseGdrive2
import com.lustyflix.streamverse.extractors.DesuArcg
import com.lustyflix.streamverse.extractors.DesuDrive
import com.lustyflix.streamverse.extractors.DesuOdchan
import com.lustyflix.streamverse.extractors.DesuOdvip
import com.lustyflix.streamverse.extractors.Dokicloud
import com.lustyflix.streamverse.extractors.DoodCxExtractor
import com.lustyflix.streamverse.extractors.DoodLaExtractor
import com.lustyflix.streamverse.extractors.DoodPmExtractor
import com.lustyflix.streamverse.extractors.DoodShExtractor
import com.lustyflix.streamverse.extractors.DoodSoExtractor
import com.lustyflix.streamverse.extractors.DoodToExtractor
import com.lustyflix.streamverse.extractors.DoodWatchExtractor
import com.lustyflix.streamverse.extractors.DoodWfExtractor
import com.lustyflix.streamverse.extractors.DoodWsExtractor
import com.lustyflix.streamverse.extractors.DoodYtExtractor
import com.lustyflix.streamverse.extractors.Dooood
import com.lustyflix.streamverse.extractors.Embedgram
import com.lustyflix.streamverse.extractors.Evoload
import com.lustyflix.streamverse.extractors.Evoload1
import com.lustyflix.streamverse.extractors.FEmbed
import com.lustyflix.streamverse.extractors.FEnet
import com.lustyflix.streamverse.extractors.Fastream
import com.lustyflix.streamverse.extractors.FeHD
import com.lustyflix.streamverse.extractors.Fembed9hd
import com.lustyflix.streamverse.extractors.FileMoon
import com.lustyflix.streamverse.extractors.FileMoonIn
import com.lustyflix.streamverse.extractors.FileMoonSx
import com.lustyflix.streamverse.extractors.Filesim
import com.lustyflix.streamverse.extractors.Fplayer
import com.lustyflix.streamverse.extractors.GMPlayer
import com.lustyflix.streamverse.extractors.Gdriveplayer
import com.lustyflix.streamverse.extractors.Gdriveplayerapi
import com.lustyflix.streamverse.extractors.Gdriveplayerapp
import com.lustyflix.streamverse.extractors.Gdriveplayerbiz
import com.lustyflix.streamverse.extractors.Gdriveplayerco
import com.lustyflix.streamverse.extractors.Gdriveplayerfun
import com.lustyflix.streamverse.extractors.Gdriveplayerio
import com.lustyflix.streamverse.extractors.Gdriveplayerme
import com.lustyflix.streamverse.extractors.Gdriveplayerorg
import com.lustyflix.streamverse.extractors.Gdriveplayerus
import com.lustyflix.streamverse.extractors.Gofile
import com.lustyflix.streamverse.extractors.GuardareStream
import com.lustyflix.streamverse.extractors.Guccihide
import com.lustyflix.streamverse.extractors.Hxfile
import com.lustyflix.streamverse.extractors.JWPlayer
import com.lustyflix.streamverse.extractors.Jawcloud
import com.lustyflix.streamverse.extractors.Jeniusplay
import com.lustyflix.streamverse.extractors.Keephealth
import com.lustyflix.streamverse.extractors.KotakAnimeid
import com.lustyflix.streamverse.extractors.Kotakajair
import com.lustyflix.streamverse.extractors.Krakenfiles
import com.lustyflix.streamverse.extractors.LayarKaca
import com.lustyflix.streamverse.extractors.Linkbox
import com.lustyflix.streamverse.extractors.Luxubu
import com.lustyflix.streamverse.extractors.Lvturbo
import com.lustyflix.streamverse.extractors.Maxstream
import com.lustyflix.streamverse.extractors.Mcloud
import com.lustyflix.streamverse.extractors.Megacloud
import com.lustyflix.streamverse.extractors.Meownime
import com.lustyflix.streamverse.extractors.MetaGnathTuggers
import com.lustyflix.streamverse.extractors.Minoplres
import com.lustyflix.streamverse.extractors.MixDrop
import com.lustyflix.streamverse.extractors.MixDropBz
import com.lustyflix.streamverse.extractors.MixDropCh
import com.lustyflix.streamverse.extractors.MixDropTo
import com.lustyflix.streamverse.extractors.Movhide
import com.lustyflix.streamverse.extractors.Moviehab
import com.lustyflix.streamverse.extractors.MoviehabNet
import com.lustyflix.streamverse.extractors.Moviesapi
import com.lustyflix.streamverse.extractors.Moviesm4u
import com.lustyflix.streamverse.extractors.Mp4Upload
import com.lustyflix.streamverse.extractors.Mvidoo
import com.lustyflix.streamverse.extractors.MwvnVizcloudInfo
import com.lustyflix.streamverse.extractors.MyCloud
import com.lustyflix.streamverse.extractors.Neonime7n
import com.lustyflix.streamverse.extractors.Neonime8n
import com.lustyflix.streamverse.extractors.Odnoklassniki
import com.lustyflix.streamverse.extractors.TauVideo
import com.lustyflix.streamverse.extractors.SibNet
import com.lustyflix.streamverse.extractors.ContentX
import com.lustyflix.streamverse.extractors.EmturbovidExtractor
import com.lustyflix.streamverse.extractors.Hotlinger
import com.lustyflix.streamverse.extractors.FourCX
import com.lustyflix.streamverse.extractors.PlayRu
import com.lustyflix.streamverse.extractors.FourPlayRu
import com.lustyflix.streamverse.extractors.HDMomPlayer
import com.lustyflix.streamverse.extractors.HDPlayerSystem
import com.lustyflix.streamverse.extractors.VideoSeyred
import com.lustyflix.streamverse.extractors.PeaceMakerst
import com.lustyflix.streamverse.extractors.HDStreamAble
import com.lustyflix.streamverse.extractors.RapidVid
import com.lustyflix.streamverse.extractors.TRsTX
import com.lustyflix.streamverse.extractors.VidMoxy
import com.lustyflix.streamverse.extractors.PixelDrain
import com.lustyflix.streamverse.extractors.MailRu
import com.lustyflix.streamverse.extractors.Mediafire
import com.lustyflix.streamverse.extractors.OkRuSSL
import com.lustyflix.streamverse.extractors.OkRuHTTP
import com.lustyflix.streamverse.extractors.Okrulink
import com.lustyflix.streamverse.extractors.PlayLtXyz
import com.lustyflix.streamverse.extractors.PlayerVoxzer
import com.lustyflix.streamverse.extractors.Rabbitstream
import com.lustyflix.streamverse.extractors.Rasacintaku
import com.lustyflix.streamverse.extractors.SBfull
import com.lustyflix.streamverse.extractors.Sbasian
import com.lustyflix.streamverse.extractors.Sbface
import com.lustyflix.streamverse.extractors.Sbflix
import com.lustyflix.streamverse.extractors.Sblona
import com.lustyflix.streamverse.extractors.Sblongvu
import com.lustyflix.streamverse.extractors.Sbnet
import com.lustyflix.streamverse.extractors.Sbrapid
import com.lustyflix.streamverse.extractors.Sbsonic
import com.lustyflix.streamverse.extractors.Sbspeed
import com.lustyflix.streamverse.extractors.Sbthe
import com.lustyflix.streamverse.extractors.Sendvid
import com.lustyflix.streamverse.extractors.ShaveTape
import com.lustyflix.streamverse.extractors.Simpulumlamerop
import com.lustyflix.streamverse.extractors.Solidfiles
import com.lustyflix.streamverse.extractors.Ssbstream
import com.lustyflix.streamverse.extractors.StreamM4u
import com.lustyflix.streamverse.extractors.StreamSB
import com.lustyflix.streamverse.extractors.StreamSB1
import com.lustyflix.streamverse.extractors.StreamSB10
import com.lustyflix.streamverse.extractors.StreamSB11
import com.lustyflix.streamverse.extractors.StreamSB2
import com.lustyflix.streamverse.extractors.StreamSB3
import com.lustyflix.streamverse.extractors.StreamSB4
import com.lustyflix.streamverse.extractors.StreamSB5
import com.lustyflix.streamverse.extractors.StreamSB6
import com.lustyflix.streamverse.extractors.StreamSB7
import com.lustyflix.streamverse.extractors.StreamSB8
import com.lustyflix.streamverse.extractors.StreamSB9
import com.lustyflix.streamverse.extractors.StreamTape
import com.lustyflix.streamverse.extractors.StreamTapeNet
import com.lustyflix.streamverse.extractors.StreamTapeXyz
import com.lustyflix.streamverse.extractors.StreamWishExtractor
import com.lustyflix.streamverse.extractors.StreamhideCom
import com.lustyflix.streamverse.extractors.StreamhideTo
import com.lustyflix.streamverse.extractors.Streamhub2
import com.lustyflix.streamverse.extractors.Streamlare
import com.lustyflix.streamverse.extractors.StreamoUpload
import com.lustyflix.streamverse.extractors.Streamplay
import com.lustyflix.streamverse.extractors.Streamsss
import com.lustyflix.streamverse.extractors.Supervideo
import com.lustyflix.streamverse.extractors.Tantifilm
import com.lustyflix.streamverse.extractors.Tomatomatela
import com.lustyflix.streamverse.extractors.TomatomatelalClub
import com.lustyflix.streamverse.extractors.Tubeless
import com.lustyflix.streamverse.extractors.Upstream
import com.lustyflix.streamverse.extractors.UpstreamExtractor
import com.lustyflix.streamverse.extractors.Uqload
import com.lustyflix.streamverse.extractors.Uqload1
import com.lustyflix.streamverse.extractors.Uqload2
import com.lustyflix.streamverse.extractors.Urochsunloath
import com.lustyflix.streamverse.extractors.Userload
import com.lustyflix.streamverse.extractors.Userscloud
import com.lustyflix.streamverse.extractors.Uservideo
import com.lustyflix.streamverse.extractors.Vanfem
import com.lustyflix.streamverse.extractors.Vicloud
import com.lustyflix.streamverse.extractors.VidSrcExtractor
import com.lustyflix.streamverse.extractors.VidSrcExtractor2
import com.lustyflix.streamverse.extractors.VideoVard
import com.lustyflix.streamverse.extractors.VideovardSX
import com.lustyflix.streamverse.extractors.Vidgomunime
import com.lustyflix.streamverse.extractors.Vidgomunimesb
import com.lustyflix.streamverse.extractors.Vidguardto
import com.lustyflix.streamverse.extractors.VidhideExtractor
import com.lustyflix.streamverse.extractors.Vidmoly
import com.lustyflix.streamverse.extractors.Vidmolyme
import com.lustyflix.streamverse.extractors.Vido
import com.lustyflix.streamverse.extractors.Vidplay
import com.lustyflix.streamverse.extractors.VidplayOnline
import com.lustyflix.streamverse.extractors.Vidstreamz
import com.lustyflix.streamverse.extractors.Vizcloud
import com.lustyflix.streamverse.extractors.Vizcloud2
import com.lustyflix.streamverse.extractors.VizcloudCloud
import com.lustyflix.streamverse.extractors.VizcloudDigital
import com.lustyflix.streamverse.extractors.VizcloudInfo
import com.lustyflix.streamverse.extractors.VizcloudLive
import com.lustyflix.streamverse.extractors.VizcloudOnline
import com.lustyflix.streamverse.extractors.VizcloudSite
import com.lustyflix.streamverse.extractors.VizcloudXyz
import com.lustyflix.streamverse.extractors.Voe
import com.lustyflix.streamverse.extractors.Watchx
import com.lustyflix.streamverse.extractors.WcoStream
import com.lustyflix.streamverse.extractors.Wibufile
import com.lustyflix.streamverse.extractors.XStreamCdn
import com.lustyflix.streamverse.extractors.Yipsu
import com.lustyflix.streamverse.extractors.YourUpload
import com.lustyflix.streamverse.extractors.YoutubeExtractor
import com.lustyflix.streamverse.extractors.YoutubeMobileExtractor
import com.lustyflix.streamverse.extractors.YoutubeNoCookieExtractor
import com.lustyflix.streamverse.extractors.YoutubeShortLinkExtractor
import com.lustyflix.streamverse.extractors.Yufiles
import com.lustyflix.streamverse.extractors.Zorofile
import com.lustyflix.streamverse.extractors.Zplayer
import com.lustyflix.streamverse.extractors.ZplayerV2
import com.lustyflix.streamverse.extractors.Ztreamhub
import com.lustyflix.streamverse.extractors.EPlayExtractor
import com.lustyflix.streamverse.extractors.Vtbe
import com.lustyflix.streamverse.mvvm.logError
import com.lustyflix.streamverse.mvvm.normalSafeApiCall
import kotlinx.coroutines.delay
import me.xdrop.fuzzywuzzy.FuzzySearch
import org.jsoup.Jsoup
import java.net.URL
import java.util.UUID

/**
 * For use in the ConcatenatingMediaSource.
 * If features are missing (headers), please report and we can add it.
 * @param durationUs use Long.toUs() for easier input
 * */
data class PlayListItem(
    val url: String,
    val durationUs: Long,
)

/**
 * Converts Seconds to MicroSeconds, multiplication by 1_000_000
 * */
fun Long.toUs(): Long {
    return this * 1_000_000
}

/**
 * If your site has an unorthodox m3u8-like system where there are multiple smaller videos concatenated
 * use this.
 * */
data class ExtractorLinkPlayList(
    override val source: String,
    override val name: String,
    val playlist: List<PlayListItem>,
    override val referer: String,
    override val quality: Int,
    override val headers: Map<String, String> = mapOf(),
    /** Used for getExtractorVerifierJob() */
    override val extractorData: String? = null,
    override val type: ExtractorLinkType,
) : ExtractorLink(
    source = source,
    name = name,
    url = "",
    referer = referer,
    quality = quality,
    headers = headers,
    extractorData = extractorData,
    type = type
) {
    constructor(
        source: String,
        name: String,
        playlist: List<PlayListItem>,
        referer: String,
        quality: Int,
        isM3u8: Boolean = false,
        headers: Map<String, String> = mapOf(),
        extractorData: String? = null,
    ) : this(
        source = source,
        name = name,
        playlist = playlist,
        referer = referer,
        quality = quality,
        type = if (isM3u8) ExtractorLinkType.M3U8 else ExtractorLinkType.VIDEO,
        headers = headers,
        extractorData = extractorData,
    )
}

/** Metadata about the file type used for downloads and exoplayer hint,
 * if you respond with the wrong one the file will fail to download or be played */
enum class ExtractorLinkType {
    /** Single stream of bytes no matter the actual file type */
    VIDEO,
    /** Split into several .ts files, has support for encrypted m3u8s */
    M3U8,
    /** Like m3u8 but uses xml, currently no download support */
    DASH,
    /** No support at the moment */
    TORRENT,
    /** No support at the moment */
    MAGNET,
}

private fun inferTypeFromUrl(url: String): ExtractorLinkType {
    val path = normalSafeApiCall { URL(url).path }
    return when {
        path?.endsWith(".m3u8") == true -> ExtractorLinkType.M3U8
        path?.endsWith(".mpd") == true -> ExtractorLinkType.DASH
        path?.endsWith(".torrent") == true -> ExtractorLinkType.TORRENT
        url.startsWith("magnet:") -> ExtractorLinkType.MAGNET
        else -> ExtractorLinkType.VIDEO
    }
}
val INFER_TYPE : ExtractorLinkType? = null

/**
 * UUID for the ClearKey DRM scheme.
 *
 *
 * ClearKey is supported on Android devices running Android 5.0 (API Level 21) and up.
 */
val CLEARKEY_UUID = UUID(-0x1d8e62a7567a4c37L, 0x781AB030AF78D30EL)

/**
 * UUID for the Widevine DRM scheme.
 *
 *
 * Widevine is supported on Android devices running Android 4.3 (API Level 18) and up.
 */
val WIDEVINE_UUID = UUID(-0x121074568629b532L, -0x5c37d8232ae2de13L)

/**
 * UUID for the PlayReady DRM scheme.
 *
 *
 * PlayReady is supported on all AndroidTV devices. Note that most other Android devices do not
 * provide PlayReady support.
 */
val PLAYREADY_UUID = UUID(-0x65fb0f8667bfbd7aL, -0x546d19a41f77a06bL)

open class DrmExtractorLink private constructor(
    override val source: String,
    override val name: String,
    override val url: String,
    override val referer: String,
    override val quality: Int,
    override val headers: Map<String, String> = mapOf(),
    /** Used for getExtractorVerifierJob() */
    override val extractorData: String? = null,
    override val type: ExtractorLinkType,
    open val kid : String,
    open val key : String,
    open val uuid : UUID,
    open val kty : String,

    open val keyRequestParameters : HashMap<String, String>
) : ExtractorLink(
    source, name, url, referer, quality, type, headers, extractorData
) {
    constructor(
        source: String,
        name: String,
        url: String,
        referer: String,
        quality: Int,
        /** the type of the media, use INFER_TYPE if you want to auto infer the type from the url */
        type: ExtractorLinkType?,
        headers: Map<String, String> = mapOf(),
        /** Used for getExtractorVerifierJob() */
        extractorData: String? = null,
        kid : String,
        key : String,
        uuid : UUID = CLEARKEY_UUID,
        kty : String = "oct",
        keyRequestParameters : HashMap<String, String> = hashMapOf(),
    ) : this(
        source = source,
        name = name,
        url = url,
        referer = referer,
        quality = quality,
        headers = headers,
        extractorData = extractorData,
        type = type ?: inferTypeFromUrl(url),
        kid = kid,
        key = key,
        uuid = uuid,
        keyRequestParameters = keyRequestParameters,
        kty = kty,
    )
}

open class ExtractorLink constructor(
    open val source: String,
    open val name: String,
    override val url: String,
    override val referer: String,
    open val quality: Int,
    override val headers: Map<String, String> = mapOf(),
    /** Used for getExtractorVerifierJob() */
    open val extractorData: String? = null,
    open val type: ExtractorLinkType,
) : VideoDownloadManager.IDownloadableMinimum {
    val isM3u8: Boolean get() = type == ExtractorLinkType.M3U8
    val isDash: Boolean get() = type == ExtractorLinkType.DASH

    // Cached video size
    private var videoSize: Long? = null

    /**
     * Get video size in bytes with one head request. Only available for ExtractorLinkType.Video
     * @param timeoutSeconds timeout of the head request.
     */
    suspend fun getVideoSize(timeoutSeconds: Long = 3L): Long? {
        // Content-Length is not applicable to other types of formats
        if (this.type != ExtractorLinkType.VIDEO) return null

        videoSize = videoSize ?: runCatching {
            val response =
                app.head(this.url, headers = headers, referer = referer, timeout = timeoutSeconds)
            response.headers["Content-Length"]?.toLong()
        }.getOrNull()

        return videoSize
    }

    @JsonIgnore
    fun getAllHeaders() : Map<String, String> {
        if (referer.isBlank()) {
            return headers
        } else if (headers.keys.none { it.equals("referer", ignoreCase = true) }) {
            return headers + mapOf("referer" to referer)
        }
        return headers
    }

    constructor(
        source: String,
        name: String,
        url: String,
        referer: String,
        quality: Int,
        /** the type of the media, use INFER_TYPE if you want to auto infer the type from the url */
        type: ExtractorLinkType?,
        headers: Map<String, String> = mapOf(),
        /** Used for getExtractorVerifierJob() */
        extractorData: String? = null,
    ) : this(
        source = source,
        name = name,
        url = url,
        referer = referer,
        quality = quality,
        headers = headers,
        extractorData = extractorData,
        type = type ?: inferTypeFromUrl(url)
    )

    /**
     * Old constructor without isDash, allows for backwards compatibility with extensions.
     * Should be removed after all extensions have updated their streamverse.jar
     **/
    constructor(
        source: String,
        name: String,
        url: String,
        referer: String,
        quality: Int,
        isM3u8: Boolean = false,
        headers: Map<String, String> = mapOf(),
        /** Used for getExtractorVerifierJob() */
        extractorData: String? = null
    ) : this(source, name, url, referer, quality, isM3u8, headers, extractorData, false)

    constructor(
        source: String,
        name: String,
        url: String,
        referer: String,
        quality: Int,
        isM3u8: Boolean = false,
        headers: Map<String, String> = mapOf(),
        /** Used for getExtractorVerifierJob() */
        extractorData: String? = null,
        isDash: Boolean,
    ) : this(
        source = source,
        name = name,
        url = url,
        referer = referer,
        quality = quality,
        headers = headers,
        extractorData = extractorData,
        type = if (isDash) ExtractorLinkType.DASH else if (isM3u8) ExtractorLinkType.M3U8 else ExtractorLinkType.VIDEO
    )

    override fun toString(): String {
        return "ExtractorLink(name=$name, url=$url, referer=$referer, type=$type)"
    }
}

data class ExtractorUri(
    val uri: Uri,
    val name: String,

    val basePath: String? = null,
    val relativePath: String? = null,
    val displayName: String? = null,

    val id: Int? = null,
    val parentId: Int? = null,
    val episode: Int? = null,
    val season: Int? = null,
    val headerName: String? = null,
    val tvType: TvType? = null,
)

data class ExtractorSubtitleLink(
    val name: String,
    override val url: String,
    override val referer: String,
    override val headers: Map<String, String> = mapOf()
) : VideoDownloadManager.IDownloadableMinimum

/**
 * Removes https:// and www.
 * To match urls regardless of schema, perhaps Uri() can be used?
 */
val schemaStripRegex = Regex("""^(https:|)//(www\.|)""")

enum class Qualities(var value: Int, val defaultPriority: Int) {
    Unknown(400, 4),
    P144(144, 0), // 144p
    P240(240, 2), // 240p
    P360(360, 3), // 360p
    P480(480, 4), // 480p
    P720(720, 5), // 720p
    P1080(1080, 6), // 1080p
    P1440(1440, 7), // 1440p
    P2160(2160, 8); // 4k or 2160p

    companion object {
        fun getStringByInt(qual: Int?): String {
            return when (qual) {
                0 -> "Auto"
                Unknown.value -> ""
                P2160.value -> "4K"
                null -> ""
                else -> "${qual}p"
            }
        }

        fun getStringByIntFull(quality: Int): String {
            return when (quality) {
                0 -> "Auto"
                Unknown.value -> "Unknown"
                P2160.value -> "4K"
                else -> "${quality}p"
            }
        }
    }
}

fun getQualityFromName(qualityName: String?): Int {
    if (qualityName == null)
        return Qualities.Unknown.value

    val match = qualityName.lowercase().replace("p", "").trim()
    return when (match) {
        "4k" -> Qualities.P2160
        else -> null
    }?.value ?: match.toIntOrNull() ?: Qualities.Unknown.value
}

private val packedRegex = Regex("""eval\(function\(p,a,c,k,e,.*\)\)""")
fun getPacked(string: String): String? {
    return packedRegex.find(string)?.value
}

fun getAndUnpack(string: String): String {
    val packedText = getPacked(string)
    return JsUnpacker(packedText).unpack() ?: string
}

suspend fun unshortenLinkSafe(url: String): String {
    return try {
        if (ShortLink.isShortLink(url))
            ShortLink.unshorten(url)
        else url
    } catch (e: Exception) {
        logError(e)
        url
    }
}

suspend fun loadExtractor(
    url: String,
    subtitleCallback: (SubtitleFile) -> Unit,
    callback: (ExtractorLink) -> Unit
): Boolean {
    return loadExtractor(
        url = url,
        referer = null,
        subtitleCallback = subtitleCallback,
        callback = callback
    )
}

/**
 * Tries to load the appropriate extractor based on link, returns true if any extractor is loaded.
 * */
suspend fun loadExtractor(
    url: String,
    referer: String? = null,
    subtitleCallback: (SubtitleFile) -> Unit,
    callback: (ExtractorLink) -> Unit
): Boolean {
    val currentUrl = unshortenLinkSafe(url)
    val compareUrl = currentUrl.lowercase().replace(schemaStripRegex, "")
    for (extractor in extractorApis) {
        if (compareUrl.startsWith(extractor.mainUrl.replace(schemaStripRegex, ""))) {
            extractor.getSafeUrl(currentUrl, referer, subtitleCallback, callback)
            return true
        }
    }

    // this is to match mirror domains - like example.com, example.net
    for (extractor in extractorApis) {
        if (FuzzySearch.partialRatio(
                extractor.mainUrl,
                currentUrl
            ) > 80
        ) {
            extractor.getSafeUrl(currentUrl, referer, subtitleCallback, callback)
            return true
        }
    }

    return false
}

val extractorApis: MutableList<ExtractorApi> = arrayListOf(
    //AllProvider(),
    WcoStream(),
    Vidstreamz(),
    Vizcloud(),
    Vizcloud2(),
    VizcloudOnline(),
    VizcloudXyz(),
    VizcloudLive(),
    VizcloudInfo(),
    MwvnVizcloudInfo(),
    VizcloudDigital(),
    VizcloudCloud(),
    VizcloudSite(),
    VideoVard(),
    VideovardSX(),
    Mp4Upload(),
    StreamTape(),
    StreamTapeNet(),
    ShaveTape(),
    StreamTapeXyz(),

    //mixdrop extractors
    MixDropBz(),
    MixDropCh(),
    MixDropTo(),

    MixDrop(),

    Mcloud(),
    XStreamCdn(),

    StreamSB(),
    Sblona(),
    Vidgomunimesb(),
    StreamSB1(),
    StreamSB2(),
    StreamSB3(),
    StreamSB4(),
    StreamSB5(),
    StreamSB6(),
    StreamSB7(),
    StreamSB8(),
    StreamSB9(),
    StreamSB10(),
    StreamSB11(),
    SBfull(),
    // Streamhub(), cause Streamhub2() works
    Streamhub2(),
    Ssbstream(),
    Sbthe(),
    Vidgomunime(),
    Sbflix(),
    Streamsss(),
    Sbspeed(),
    Sbsonic(),
    Sbface(),
    Sbrapid(),
    Lvturbo(),

    Fastream(),

    FEmbed(),
    FeHD(),
    Fplayer(),
    DBfilm(),
    Luxubu(),
    LayarKaca(),
    Rasacintaku(),
    FEnet(),
    Kotakajair(),
    Cdnplayer(),
    //  WatchSB(), 'cause StreamSB.kt works
    Uqload(),
    Uqload1(),
    Uqload2(),
    Evoload(),
    Evoload1(),
    UpstreamExtractor(),

    Odnoklassniki(),
    TauVideo(),
    SibNet(),
    ContentX(),
    Hotlinger(),
    FourCX(),
    PlayRu(),
    FourPlayRu(),
    HDMomPlayer(),
    HDPlayerSystem(),
    VideoSeyred(),
    PeaceMakerst(),
    HDStreamAble(),
    RapidVid(),
    TRsTX(),
    VidMoxy(),
    PixelDrain(),
    MailRu(),

    Tomatomatela(),
    TomatomatelalClub(),
    Cinestart(),
    OkRuSSL(),
    OkRuHTTP(),
    Okrulink(),
    Sendvid(),

    // dood extractors
    DoodCxExtractor(),
    DoodPmExtractor(),
    DoodToExtractor(),
    DoodSoExtractor(),
    DoodLaExtractor(),
    Dooood(),
    DoodWsExtractor(),
    DoodShExtractor(),
    DoodWatchExtractor(),
    DoodWfExtractor(),
    DoodYtExtractor(),

    AsianLoad(),

    // GenericM3U8(),
    Jawcloud(),
    Zplayer(),
    ZplayerV2(),
    Upstream(),

    Maxstream(),
    Tantifilm(),
    Userload(),
    Supervideo(),
    GuardareStream(),
    CineGrabber(),
    Vanfem(),

    // StreamSB.kt works
    //  SBPlay(),
    //  SBPlay1(),
    //  SBPlay2(),

    PlayerVoxzer(),

    BullStream(),
    GMPlayer(),

    Blogger(),
    Solidfiles(),
    YourUpload(),

    Hxfile(),
    KotakAnimeid(),
    Neonime8n(),
    Neonime7n(),
    Yufiles(),
    Aico(),

    JWPlayer(),
    Meownime(),
    DesuArcg(),
    DesuOdchan(),
    DesuOdvip(),
    DesuDrive(),

    Chillx(),
    Moviesapi(),
    Watchx(),
    Bestx(),
    Keephealth(),
    Sbnet(),
    Sbasian(),
    Sblongvu(),
    Fembed9hd(),
    StreamM4u(),
    Krakenfiles(),
    Gofile(),
    Vicloud(),
    Uservideo(),
    Userscloud(),

    Movhide(),
    StreamhideCom(),
    StreamhideTo(),
    Wibufile(),
    FileMoonIn(),
    Moviesm4u(),
    Filesim(),
    Ahvsh(),
    Guccihide(),
    FileMoon(),
    FileMoonSx(),
    Vido(),
    Linkbox(),
    Acefile(),
    Minoplres(), // formerly SpeedoStream
    Zorofile(),
    Embedgram(),
    Mvidoo(),
    Streamplay(),
    Vidmoly(),
    Vidmolyme(),
    Voe(),
    Tubeless(),
    Moviehab(),
    MoviehabNet(),
    Jeniusplay(),
    StreamoUpload(),

    Gdriveplayerapi(),
    Gdriveplayerapp(),
    Gdriveplayerfun(),
    Gdriveplayerio(),
    Gdriveplayerme(),
    Gdriveplayerbiz(),
    Gdriveplayerorg(),
    Gdriveplayerus(),
    Gdriveplayerco(),
    Gdriveplayer(),
    DatabaseGdrive(),
    DatabaseGdrive2(),
    Mediafire(),

    YoutubeExtractor(),
    YoutubeShortLinkExtractor(),
    YoutubeMobileExtractor(),
    YoutubeNoCookieExtractor(),
    Streamlare(),
    VidSrcExtractor(),
    VidSrcExtractor2(),
    PlayLtXyz(),
    AStreamHub(),
    Vidplay(),
    VidplayOnline(),
    MyCloud(),

    Cda(),
    Dailymotion(),
    ByteShare(),
    Ztreamhub(),
    Rabbitstream(),
    Dokicloud(),
    Megacloud(),
    VidhideExtractor(),
    StreamWishExtractor(),
    EmturbovidExtractor(),
    Vtbe(),
    EPlayExtractor(),
    Vidguardto(),
    Simpulumlamerop(),
    Urochsunloath(),
    Yipsu(),
    MetaGnathTuggers()
)


fun getExtractorApiFromName(name: String): ExtractorApi {
    for (api in extractorApis) {
        if (api.name == name) return api
    }
    return extractorApis[0]
}

fun requireReferer(name: String): Boolean {
    return getExtractorApiFromName(name).requiresReferer
}

fun httpsify(url: String): String {
    return if (url.startsWith("//")) "https:$url" else url
}

suspend fun getPostForm(requestUrl: String, html: String): String? {
    val document = Jsoup.parse(html)
    val inputs = document.select("Form > input")
    if (inputs.size < 4) return null
    var op: String? = null
    var id: String? = null
    var mode: String? = null
    var hash: String? = null

    for (input in inputs) {
        val value = input.attr("value") ?: continue
        when (input.attr("name")) {
            "op" -> op = value
            "id" -> id = value
            "mode" -> mode = value
            "hash" -> hash = value
            else -> Unit
        }
    }
    if (op == null || id == null || mode == null || hash == null) {
        return null
    }
    delay(5000) // ye this is needed, wont work with 0 delay

    return app.post(
        requestUrl,
        headers = mapOf(
            "content-type" to "application/x-www-form-urlencoded",
            "referer" to requestUrl,
            "user-agent" to USER_AGENT,
            "accept" to "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9"
        ),
        data = mapOf("op" to op, "id" to id, "mode" to mode, "hash" to hash)
    ).text
}

fun ExtractorApi.fixUrl(url: String): String {
    if (url.startsWith("http") ||
        // Do not fix JSON objects when passed as urls.
        url.startsWith("{\"")
    ) {
        return url
    }
    if (url.isEmpty()) {
        return ""
    }

    val startsWithNoHttp = url.startsWith("//")
    if (startsWithNoHttp) {
        return "https:$url"
    } else {
        if (url.startsWith('/')) {
            return mainUrl + url
        }
        return "$mainUrl/$url"
    }
}

abstract class ExtractorApi {
    abstract val name: String
    abstract val mainUrl: String
    abstract val requiresReferer: Boolean

    /** Determines which plugin a given extractor is from */
    var sourcePlugin: String? = null

    //suspend fun getSafeUrl(url: String, referer: String? = null): List<ExtractorLink>? {
    //    return suspendSafeApiCall { getUrl(url, referer) }
    //}

    // this is the new extractorapi, override to add subtitles and stuff
    open suspend fun getUrl(
        url: String,
        referer: String? = null,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ) {
        getUrl(url, referer)?.forEach(callback)
    }

    suspend fun getSafeUrl(
        url: String,
        referer: String? = null,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ) {
        try {
            getUrl(url, referer, subtitleCallback, callback)
        } catch (e: Exception) {
            logError(e)
        }
    }

    /**
     * Will throw errors, use getSafeUrl if you don't want to handle the exception yourself
     */
    open suspend fun getUrl(url: String, referer: String? = null): List<ExtractorLink>? {
        return emptyList()
    }

    open fun getExtractorUrl(id: String): String {
        return id
    }
}
