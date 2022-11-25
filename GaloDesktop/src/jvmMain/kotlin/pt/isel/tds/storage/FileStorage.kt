package pt.isel.tds.storage

import java.nio.file.Path
import kotlin.io.path.*

fun Path.writeText(text: CharSequence) {
    writeText(text,Charsets.UTF_8)
    repeat(3) {
        Thread.sleep(1000)
        print('.')
    }
    println()
}

class FileStorage<K, T>(
    val folder: String,
    val serializer: Serializer<T, String>)
    : Storage<K, T>
{
    fun path(id: K) = "$folder/$id.txt"

    /**
     * Create new file with the name of the id with suffix .txt
     * if it does not exist yet.
     * Throws IllegalArgumentException if there is already a file with that name.
     */
    override fun create(id: K, value: T) {
        val path = path(id)
        val file = Path(path)
        require(!file.exists()) { "There is already a file with that id $id" }
        val stream = serializer.write(value)
        file.writeText(stream)
    }

    override fun read(id: K): T? {
        val file = Path(path(id))
        if(!file.exists()) return null
        val stream = file.readText()
        return serializer.parse(stream)
    }

    override fun update(id: K, value: T) {
        val path = path(id)
        val file = Path(path)
        require(file.exists()) { "There is no file with that id $id" }
        val stream = serializer.write(value)
        file.writeText(stream)
    }

    override fun delete(id: K) {
        val path = path(id)
        val file = Path(path)
        require(file.exists()) { "There is no file with that id $id" }
        file.deleteExisting()
    }
}
