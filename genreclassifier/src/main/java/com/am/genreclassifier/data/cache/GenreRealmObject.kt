package com.am.genreclassifier.data.cache

import com.am.core.util.DisplayableDate
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.UUID

const val TRACK_NAME = "trackName"
const val ID = "id"

open class GenreRealmObject(
    @PrimaryKey var id: UUID? = null,
    var trackName: String = "",
    var genre: String = "",
    var date: DisplayableDate = "",
    var timeStamp: Long? = null
) : RealmObject()