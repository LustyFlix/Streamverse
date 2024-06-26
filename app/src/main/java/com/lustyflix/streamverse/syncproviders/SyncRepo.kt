package com.lustyflix.streamverse.syncproviders

import com.lustyflix.streamverse.ErrorLoadingException
import com.lustyflix.streamverse.mvvm.Resource
import com.lustyflix.streamverse.mvvm.normalSafeApiCall
import com.lustyflix.streamverse.mvvm.safeApiCall

class SyncRepo(private val repo: SyncAPI) {
    val idPrefix = repo.idPrefix
    val name = repo.name
    val icon = repo.icon
    val mainUrl = repo.mainUrl
    val requiresLogin = repo.requiresLogin
    val syncIdName = repo.syncIdName
    var requireLibraryRefresh: Boolean
        get() = repo.requireLibraryRefresh
        set(value) {
            repo.requireLibraryRefresh = value
        }

    suspend fun score(id: String, status: SyncAPI.AbstractSyncStatus): Resource<Boolean> {
        return safeApiCall { repo.score(id, status) }
    }

    suspend fun getStatus(id: String): Resource<SyncAPI.AbstractSyncStatus> {
        return safeApiCall { repo.getStatus(id) ?: throw ErrorLoadingException("No data") }
    }

    suspend fun getResult(id: String): Resource<SyncAPI.SyncResult> {
        return safeApiCall { repo.getResult(id) ?: throw ErrorLoadingException("No data") }
    }

    suspend fun search(query: String): Resource<List<SyncAPI.SyncSearchResult>> {
        return safeApiCall { repo.search(query) ?: throw ErrorLoadingException() }
    }

    suspend fun getPersonalLibrary(): Resource<SyncAPI.LibraryMetadata> {
        return safeApiCall { repo.getPersonalLibrary() ?: throw ErrorLoadingException() }
    }

    fun hasAccount(): Boolean {
        return normalSafeApiCall { repo.loginInfo() != null } ?: false
    }

    fun getIdFromUrl(url: String): String? = normalSafeApiCall {
        repo.getIdFromUrl(url)
    }
}