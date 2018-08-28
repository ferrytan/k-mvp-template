package com.meetferrytan.kotlinmvptemplate.base.repository

import android.arch.persistence.room.*

@Dao
abstract class BaseDao<T> {
    /**
     * Insert an object in the database.
     *
     * @param object the object to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(data: T): Long

    /**
     * Insert an array of objects in the database.
     *
     * @param object the object to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg dataset: T): LongArray

    /**
     * Update an object from the database.
     *
     * @param object the object to be inserted.
     */
    @Update
    abstract fun update(data: T): Int

    /**
     * Delete an object from the database
     *
     * @param object the object to be inserted.
     */
    @Delete
    abstract fun delete(data: T): Int
}