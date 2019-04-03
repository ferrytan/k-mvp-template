package com.meetferrytan.kotlinmvptemplate.base.repository

import androidx.room.*

@Dao
abstract class BaseDao<T> {
    /**
     * Insert an object in the database.
     *
     * @param object the object to be inserted.
     * @return rowId of the inserted object
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(`object`: T): Long

    /**
     * Insert an array of objects in the database.
     *
     * @param objects the object to be inserted.
     * @return array of rowId of the inserted object
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(vararg objects: T): LongArray

    /**
     * Insert a list of objects in the database.
     *
     * @param objects the object to be inserted.
     * @return collection rowId of the inserted object
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(objects: List<T>): List<Long>

    /**
     * Update an object from the database.
     *
     * @param object the object to be updated.
     */
    @Update
    abstract fun update(`object`: T)

    /**
     * Update an object from the database.
     *
     * @param objects the objects to be updated.
     */
    @Update
    abstract fun update(objects: List<T>)

    /**
     * Delete an object from the database
     *
     * @param object the object to be deleted.
     */
    @Delete
    abstract fun delete(`object`: T)

    /**
     * Delete an object from the database
     *
     * @param objects the objects to be deleted.
     * @return rowId of the updated object
     */
    @Delete
    abstract fun delete(objects: List<T>)

    /**
     * @param object
     */
    @Transaction
    open fun upsert(`object`: T) {
        val id = insert(`object`)
        if (id == -1L) {
            update(`object`)
        }
    }

    @Transaction
    open fun upsert(objList: List<T>) {
        val insertResult = insert(objList)
        val updateList = ArrayList<T>()

        for (i in insertResult.indices) {
            if (insertResult[i] == -1L) {
                updateList.add(objList[i])
            }
        }

        if (!updateList.isEmpty()) {
            update(updateList)
        }
    }
}
