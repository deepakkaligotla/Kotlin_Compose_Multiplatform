import com.kaligotla.testing.getPermissionChecker
import kotlin.test.Test
import kotlin.test.assertTrue

class ServicesTest {
    @Test
    fun checkMobileDataService() {
        assertTrue(getPermissionChecker().isMobileDataEnabled, "Mobile data should be enabled.")
    }

    @Test
    fun checkWifiService() {
        assertTrue(getPermissionChecker().isWifiEnabled, "Wifi should be enabled.")
    }
}