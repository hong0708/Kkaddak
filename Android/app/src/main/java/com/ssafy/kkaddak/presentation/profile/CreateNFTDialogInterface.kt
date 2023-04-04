package com.ssafy.kkaddak.presentation.profile

import android.graphics.Bitmap
import com.ssafy.kkaddak.domain.entity.song.SongItem

interface CreateNFTDialogInterface {
    fun mintNFT(songItem: SongItem, bitmap: Bitmap)
}