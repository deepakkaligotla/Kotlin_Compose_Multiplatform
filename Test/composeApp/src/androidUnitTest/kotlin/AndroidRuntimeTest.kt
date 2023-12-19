import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class AndroidRuntimeTest {
    @Test
    fun shouldDetectAndroid() {
        val runtime = determineCurrentRuntime()
        assertContains(runtime.name, "OpenJDK")
        assertEquals(runtime.version, "17.0")
    }
}