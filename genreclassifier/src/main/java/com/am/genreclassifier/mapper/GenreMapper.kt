package com.am.genreclassifier.mapper

import com.am.genreclassifier.data.cache.GenreRealmObject
import com.am.genreclassifier.model.Genre
import java.util.*


fun Genre.mapToDatabaseModel(): GenreRealmObject {
   return GenreRealmObject(this.processId, this.trackName, this.genre, this.displayableDate)
}



fun GenreRealmObject.mapToDomainModel(): Genre {
   return Genre(this.id!!, this.genre, this.trackName, this.date)
}
