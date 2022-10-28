import pt.isel.tds.ttt.storage.FileStorage
import pt.isel.tds.ttt.storage.Serializer
import java.io.File
import kotlin.test.*

data class Student(val nr: Int, val name: String)

object StudentSerializer : Serializer<Student, String> {
    override fun write(obj: Student) = "${obj.nr},${obj.name}"

    override fun parse(stream: String): Student {
        val words = stream.split(",")
        require(words.size == 2) { "Source stream in illegal format it should contain <nr>,<name>!" }
        val nr = words[0].toIntOrNull()
        require(nr != null) { "Number is in illegal format. Not a valid integer." }
        return Student(nr, words[1])
    }
}

class TestFileStorage {
    private val studentNr = 934973
    private val folder = "out"

    @BeforeTest fun setup() {
        val f = File("$folder/$studentNr.txt")
        if(f.exists()) f.delete()
    }

    @Test fun `Check we cannot create the same file twice`() {
        val fs = FileStorage<Int, Student>(folder, StudentSerializer)
        fs.create(studentNr, Student(studentNr, "Ze Manel"))
        assertFailsWith<IllegalArgumentException> {
            fs.create(studentNr, Student(studentNr, "Maria Papoila"))
        }
    }

    @Test fun `Serialize and deserialize and check we get an equivalent object`() {
        val fs = FileStorage<Int, Student>(folder, StudentSerializer)
        val student = Student(studentNr, "Ze Manel")
        fs.create(student.nr, student)
        val actual = fs.read(student.nr)
        assertEquals(student, actual)
        assertNotSame(student, actual)
    }
}
