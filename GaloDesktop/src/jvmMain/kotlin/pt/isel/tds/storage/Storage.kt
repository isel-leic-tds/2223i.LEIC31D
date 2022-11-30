package pt.isel.tds.storage

interface Storage<K,T> {
    suspend fun create(id: K, value: T)
    suspend fun read(id: K): T?
    suspend fun update(id: K, value: T)
    suspend fun delete(id: K)
}