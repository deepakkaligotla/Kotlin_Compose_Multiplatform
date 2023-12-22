import android.bluetooth.BluetoothManager
import android.content.Context
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import permissions.delegate.PermissionDelegate
import permissions.model.Permission
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import permissions.delegate.CalendarPermissionDelegate
import permissions.delegate.BluetoothPermissionDelegate
import permissions.delegate.BluetoothServicePermissionDelegate
import permissions.delegate.LocationBackgroundPermissionDelegate
import permissions.delegate.LocationForegroundPermissionDelegate
import permissions.delegate.LocationServicePermissionDelegate
import permissions.delegate.AudioMusicPermissionDelegate
import permissions.delegate.BodySensorBackgroundPermissionDelegate
import permissions.delegate.BodySensorsPermissionDelegate
import permissions.delegate.CallLogsPermissionDelegate
import permissions.delegate.CameraPermissionDelegate
import permissions.delegate.ContactPermissionDelegate
import permissions.delegate.MicrophonePermissionDelegate
import permissions.delegate.MobileDataServicePermissionDelegate
import permissions.delegate.NotificationPermissionDelegate
import permissions.delegate.PhonePermissionDelegate
import permissions.delegate.PhotosVideosPermissionDelegate
import permissions.delegate.PhysicalActivityPermissionDelegate
import permissions.delegate.SmsPermissionDelegate
import permissions.delegate.WifiServicePermissionDelegate
import permissions.model.Permission_Android
import permissions.model.Service

internal actual fun platformModule(): Module = module {

    single<PermissionDelegate>(named(Permission.AUDIO_MUSIC.name)) {
        AudioMusicPermissionDelegate(
            context = get(),
            activity = inject(),
        )
    }
    single<PermissionDelegate>(named(Permission_Android.BODY_SENSOR.name)) {
        BodySensorsPermissionDelegate(
            context = get(),
            activity = inject(),
        )
    }
    single<PermissionDelegate>(named(Permission.CALENDAR.name)) {
        CalendarPermissionDelegate(
            context = get(),
            activity = inject(),
        )
    }
    single<PermissionDelegate>(named(Service.BLUETOOTH_SERVICE_ON.name)) {
        BluetoothServicePermissionDelegate(
            context = get(),
            bluetoothAdapter = get(),
        )
    }
    single<PermissionDelegate>(named(Permission.BLUETOOTH.name)) {
        BluetoothPermissionDelegate(
            context = get(),
            activity = inject(),
        )
    }
    single<PermissionDelegate>(named(Permission.CAMERA.name)) {
        CameraPermissionDelegate(
            context = get(),
            activity = inject(),
        )
    }
    single<PermissionDelegate>(named(Permission_Android.CALL_LOGS.name)) {
        CallLogsPermissionDelegate(
            context = get(),
            activity = inject(),
        )
    }
    single<PermissionDelegate>(named(Permission.CONTACT.name)) {
        ContactPermissionDelegate(
            context = get(),
            activity = inject(),
        )
    }
    single {
        get<Context>().getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    }
    single {
        get<BluetoothManager>().adapter
    }
    single {
        get<Context>().getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }
    single {
        get<Context>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
    single {
        get<Context>().getSystemService(Context.WIFI_SERVICE) as WifiManager
    }
    single<PermissionDelegate>(named(Service.LOCATION_SERVICE_ON.name)) {
        LocationServicePermissionDelegate(
            context = get(),
            locationManager = get(),
        )
    }
    single<PermissionDelegate>(named(Permission.LOCATION_FOREGROUND.name)) {
        LocationForegroundPermissionDelegate(
            context = get(),
            activity = inject(),
        )
    }
    single<PermissionDelegate>(named(Permission_Android.BODY_SENSOR_BACKGROUND.name)) {
        BodySensorBackgroundPermissionDelegate(
            context = get(),
            activity = inject(),
            bodySensorPermissionDelegate = get(named(Permission_Android.BODY_SENSOR.name)),
        )
    }
    single<PermissionDelegate>(named(Permission.LOCATION_BACKGROUND.name)) {
        LocationBackgroundPermissionDelegate(
            context = get(),
            activity = inject(),
            locationForegroundPermissionDelegate = get(named(Permission.LOCATION_FOREGROUND.name)),
        )
    }
    single<PermissionDelegate>(named(Service.MOBILE_DATA_SERVICE_ON.name)) {
        MobileDataServicePermissionDelegate(
            context = get(),
            connectivityManager = get(),
        )
    }
    single<PermissionDelegate>(named(Service.WIFI_SERVICE_ON.name)) {
        WifiServicePermissionDelegate(
            context = get(),
            wifiManager = get(),
        )
    }
    single<PermissionDelegate>(named(Permission.MICROPHONE.name)) {
        MicrophonePermissionDelegate(
            context = get(),
            activity = inject(),
        )
    }
    single<PermissionDelegate>(named(Permission.NOTIFICATION.name)) {
        NotificationPermissionDelegate(
            context = get(),
            activity = inject(),
        )
    }
    single<PermissionDelegate>(named(Permission_Android.PHONE.name)) {
        PhonePermissionDelegate(
            context = get(),
            activity = inject(),
        )
    }
    single<PermissionDelegate>(named(Permission.PHOTO.name)) {
        PhotosVideosPermissionDelegate(
            context = get(),
            activity = inject(),
        )
    }
    single<PermissionDelegate>(named(Permission_Android.PHYSICAL_ACTIVITY.name)) {
        PhysicalActivityPermissionDelegate(
            context = get(),
            activity = inject(),
        )
    }
    single<PermissionDelegate>(named(Permission_Android.SMS.name)) {
        SmsPermissionDelegate(
            context = get(),
            activity = inject(),
        )
    }
}

class AndroidPlatform : Platform {
    override val name: String = "Android"
    override val isSystemInDarkTheme: Boolean
        get() = true
}

actual fun getPlatform(): Platform = AndroidPlatform()