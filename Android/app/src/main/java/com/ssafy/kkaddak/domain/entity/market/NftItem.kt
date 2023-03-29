package com.ssafy.kkaddak.domain.entity.market

import android.os.Parcel
import android.os.Parcelable

data class NftItem(
    val marketId: Int,
    val nftId: String,
    val nftCreator: String,
    val nftSongTitle: String,
    val nftImagePath: String,
    val nftCreateDate: String,
    val nftPrice: Double,
    val isClose: Boolean,
    val cntLikeMarket: Int,
    var isLike: Boolean
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readDouble(),
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(marketId)
        parcel.writeString(nftId)
        parcel.writeString(nftCreator)
        parcel.writeString(nftSongTitle)
        parcel.writeString(nftImagePath)
        parcel.writeString(nftCreateDate)
        parcel.writeDouble(nftPrice)
        parcel.writeByte(if (isClose) 1 else 0)
        parcel.writeInt(cntLikeMarket)
        parcel.writeByte(if (isLike) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NftItem> {
        override fun createFromParcel(parcel: Parcel): NftItem {
            return NftItem(parcel)
        }

        override fun newArray(size: Int): Array<NftItem?> {
            return arrayOfNulls(size)
        }
    }
}