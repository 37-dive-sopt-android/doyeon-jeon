package com.sopt.dive.data.repositoryimpl

import com.sopt.dive.core.util.apiRunCatching
import com.sopt.dive.data.datasource.local.datasource.DataStoreDataSource
import com.sopt.dive.data.datasource.remote.datasource.ReqresDataSource
import com.sopt.dive.domain.repository.ReqresRepository

class ReqresRepositoryImpl(
    private val dataStoreDataSource: DataStoreDataSource,
    private val reqresDataSource: ReqresDataSource,
) : ReqresRepository {

    override suspend fun getUserList(
        page: Int,
        perPage: Int,
    ): Result<List<String>> {
        val localImages = dataStoreDataSource.getProfileImages()

        return if (localImages != null) {
            Result.success(localImages)
        } else {
            val remoteImages = apiRunCatching {
                reqresDataSource.getUserList(page, perPage).data.map { dto ->
                    dto.avatar
                }
            }
            remoteImages.onSuccess { data ->
                dataStoreDataSource.setProfileImages(data)
            }
        }
    }

}