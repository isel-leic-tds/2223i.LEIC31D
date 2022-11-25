package pt.isel.tds.storage

/**
 * Given an object obj it should be equivalent to the resulting
 * object from parse(write(obj)).
 *
 * @param T Type of the entity
 * @param S Type of the resulting Stream, e.g. ByteArray, String, etc
 */
interface Serializer<T, S> {
    fun write(obj: T): S
    fun parse(stream: S): T

}
