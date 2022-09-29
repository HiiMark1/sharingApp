package com.example.appproject.features.profile_settigns.domain.usecase

import android.net.Uri
import com.example.appproject.features.profile_settigns.domain.repository.UserInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetDownloadAvatarUriUseCase @Inject constructor(
    private val repo: UserInfoRepository
) {

    suspend operator fun invoke(uri: Uri): Uri? {
        return withContext(Dispatchers.Main) {
            repo.getDownloadAvatarUri(uri)
        }
    }
}