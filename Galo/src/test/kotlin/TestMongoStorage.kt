import com.mongodb.ConnectionString
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import org.litote.kmongo.KMongo
import org.litote.kmongo.deleteOneById
import pt.isel.tds.ttt.BoardSerializer
import pt.isel.tds.ttt.model.Position
import pt.isel.tds.ttt.model.initialBoard
import pt.isel.tds.ttt.model.play
import pt.isel.tds.ttt.storage.MongoStorage
import kotlin.random.Random
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class MongoStorageTest {

    class Student(val _id: Any, val name: String)

    private val connStr = "mongodb+srv://gamboa:G34JmOYJLOkjW3Or@tictactoe.scrluo1.mongodb.net/?retryWrites=true&w=majority"
    private val db: MongoDatabase = KMongo
            .createClient(ConnectionString(connStr))
            .getDatabase("ttt-console-ui")
    private val coll = "games"
    private val gameId = "super"

    @BeforeTest fun setup() {
        db.getCollection(coll).deleteOneById(gameId)
    }

    @Test fun `Check Mongo Db Connection`() {
        val collStudents: MongoCollection<Student> = db.getCollection("test", Student::class.java)
        collStudents.insertOne(Student(Random.nextInt(), "Ze Manel"))
    }
    @Test fun `Save and load a complex entity Board - Move - Position`() {
        val fs = MongoStorage(coll, db, BoardSerializer)
        val board = initialBoard().also { fs.create(gameId, it) }
            .play(Position(1, 2))
            .play(Position(2, 0))
        fs.update(gameId, board)
        assertEquals(board.moves, fs.read(gameId)?.moves)
    }

}
