package com.ciayo.comics.data.repository.base

abstract class Mapper<FROM, TO> constructor(
        val afterMapAction: (Int, TO) -> Unit = { i: Int, to: TO -> },
        val afterReverseMapAction: (Int, FROM) -> Unit = {i: Int, from: FROM ->}) {
    abstract fun map(value: FROM): TO

    abstract fun reverseMap(value: TO): FROM

    fun map(values: List<FROM>): List<TO> {
        val returnValues = mutableListOf<TO>()
        values.forEachIndexed { index, from ->
            val mappedValue = map(from)
            afterMapAction(index, mappedValue)
            returnValues.add(mappedValue)
        }
        return returnValues
    }

    fun reverseMap(values: List<TO>): List<FROM> {
        val returnValues = mutableListOf<FROM>()
        values.forEachIndexed { index, to ->
            val reverseMappedValue = reverseMap(to)
            afterReverseMapAction(index, reverseMappedValue)
            returnValues.add(reverseMappedValue)
        }
        return returnValues
    }
}