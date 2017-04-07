package com.wayloren.keddit.model

import android.os.Parcel
import android.os.Parcelable
import com.wayloren.keddit.adapter.AdapterConstants
import com.wayloren.keddit.adapter.ViewType
import com.wayloren.keddit.commons.createParcel

data class RedditNews(
        val after:String,
        val before: String,
        val news: List<RedditNewsItem>) : Parcelable {

    companion object {
        @JvmField @Suppress("unused")
        val CREATOR = createParcel{ RedditNews(it) }
    }

    protected constructor(parcelIn: Parcel) : this(
        parcelIn.readString(),
        parcelIn.readString(),
            mutableListOf<RedditNewsItem>().apply {
                parcelIn.readTypedList(this, RedditNewsItem.CREATOR)
            }
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(after)
        dest.writeString(before)
        dest.writeTypedList(news)
    }

    override fun describeContents() = 0
}

data class RedditNewsItem(
        val author: String,
        val title: String,
        val numComments: Int,
        val created: Long,
        val thumbnail: String,
        val url: String) : ViewType, Parcelable {
    override fun getViewType() = AdapterConstants.NEWS

    companion object{
        @JvmField @Suppress("unused")
        val CREATOR = createParcel { RedditNewsItem(it) }
    }

    protected constructor(parcelIn: Parcel) : this(
            parcelIn.readString(),
            parcelIn.readString(),
            parcelIn.readInt(),
            parcelIn.readLong(),
            parcelIn.readString(),
            parcelIn.readString()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(author)
        dest.writeString(title)
        dest.writeInt(numComments)
        dest.writeLong(created)
        dest.writeString(thumbnail)
        dest.writeString(url)
    }

    override fun describeContents() = 0
}