package com.am.genreclassifier.data.cache

import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.Sort

class DatabaseHelper(context: Context) {

    private val config: RealmConfiguration

    init {
        Realm.init(context)
        config = RealmConfiguration.Builder()
            .allowQueriesOnUiThread(false)
            .allowWritesOnUiThread(false)
            .deleteRealmIfMigrationNeeded()
            .build()
    }


    suspend fun nextGenreItems(from: Int, next: Int = 10): List<GenreRealmObject> {
        return Realm.getInstance(config)
            .where(GenreRealmObject::class.java)
            .sort("timeStamp", Sort.ASCENDING)
            .findAll()
            .toList()
    }

    suspend fun put(genre: GenreRealmObject): GenreRealmObject {
        val instance = Realm.getInstance(config)
        instance.executeTransaction {
            it.copyToRealm(genre)
        }
        return genre
    }


    suspend fun delete(genre: GenreRealmObject) {
        if (genre.isValid) {
            genre.deleteFromRealm()
        }
    }

}