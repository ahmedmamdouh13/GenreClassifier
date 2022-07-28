package com.am.genreclassifier.data

import com.am.core.state.Result
import com.am.genreclassifier.GenreClassifier
import com.am.genreclassifier.data.cache.DatabaseHelper
import com.am.genreclassifier.mapper.mapToDatabaseModel
import com.am.genreclassifier.mapper.mapToDomainModel
import com.am.genreclassifier.model.Genre
import com.am.genreclassifier.model.Track

class GenreClassifierRepositoryImpl(
    private val genreClassifier: GenreClassifier,
    private val databaseHelper: DatabaseHelper
) :
    GenreClassifierRepository {

    override suspend fun scan(track: Track): Result<Genre> {
        return try {
            val genre = genreClassifier.scan(track)
            cacheGenre(genre)
        } catch (e: Exception) {
            Result.Error(e)
        }

    }


    suspend fun cacheGenre(genreModel: Genre): Result<Genre> {
        return try {
            val genre = genreModel.mapToDatabaseModel()
            val genreRealmObject = databaseHelper.put(genre)
            val data = genreRealmObject.mapToDomainModel()
            Result.Success(data)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e)
        }
    }

    override suspend fun getAllGenres(from: Int): Result<List<Genre>> {
        return try {
            val genreRealmObject = databaseHelper.nextGenreItems(from)
            val data = genreRealmObject.map {
                it.mapToDomainModel()
            }
            Result.Success(data)
        } catch (e: Exception) {
           Result.Error(e)
        }
    }
}