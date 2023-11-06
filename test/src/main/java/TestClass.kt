import com.re4rk.arkdi.Injectable
import engine.Engine
import javax.inject.Inject

@Injectable
open class TestClass {
    @Inject
    lateinit var test: Engine

    fun get(): String {
        val a = "a"
        val b = "b"
        return "test$a$b"
    }
}
