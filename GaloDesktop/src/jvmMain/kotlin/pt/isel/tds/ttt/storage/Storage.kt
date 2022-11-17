package pt.isel.tds.ttt.storage

interface Storage<K,T> {
    fun create(id: K, value: T)
    fun read(id: K): T?
    fun update(id: K, value: T)
    fun delete(id: K)
}