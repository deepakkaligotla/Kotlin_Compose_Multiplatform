import com.kaligotla.allpermissionsimpl.testing.PermissionChecker
import com.kaligotla.allpermissionsimpl.testing.getPermissionChecker
import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

class ServicesTest {

    private lateinit var permissionChecker: PermissionChecker

    @BeforeTest
    fun setUp() {
        permissionChecker = getPermissionChecker()
    }

    @Test
    fun checkBluetoothService() {
        assertTrue(permissionChecker.isBluetoothEnabled, "Bluetooth should be enabled.")
    }

    @Test
    fun checkLocationService() {
        assertTrue(permissionChecker.isLocationEnabled, "Location should be enabled.")
    }

    @Test
    fun checkMobileDataService() {
        assertTrue(permissionChecker.isMobileDataEnabled, "Mobile data should be enabled.")
    }

    @Test
    fun checkWifiService() {
        assertTrue(permissionChecker.isWifiEnabled, "Wifi should be enabled.")
    }

    @AfterTest
    fun tearDown() {

    }
}