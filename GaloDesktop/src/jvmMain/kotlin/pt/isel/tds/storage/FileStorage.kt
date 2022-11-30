package pt.isel.tds.storage

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.nio.file.Path
import kotlin.io.path.*

suspend fun Path.writeText(text: CharSequence) {
    withContext(Dispatchers.IO) {
        writeText(text, Charsets.UTF_8)
        repeat(3) {
            Thread.sleep(1000)
            print('w')
        }
        println()
    }
}

suspend fun Path.readText(): String =
    withContext(Dispatchers.IO) {
        repeat(3) {
            Thread.sleep(1000)
            print('r')
        }
        println()
        readText(Charsets.UTF_8)
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
    override suspend fun create(id: K, value: T) {
        val path = path(id)
        val file = Path(path)
        require(!file.exists()) { "There is already a file with that id $id" }
        val stream = serializer.write(value)
        file.writeText(stream)
    }

    override suspend fun read(id: K): T? {
        val file = Path(path(id))
        if(!file.exists()) return null
        val stream = file.readText()
        return serializer.parse(stream)
    }

    override suspend fun update(id: K, value: T) {
        val path = path(id)
        val file = Path(path)
        require(file.exists()) { "There is no file with that id $id" }
        val stream = serializer.write(value)
        file.writeText(stream)
    }

    override suspend fun delete(id: K) {
        val path = path(id)
        val file = Path(path)
        require(file.exists()) { "There is no file with that id $id" }
        file.deleteExisting()
    }
}
