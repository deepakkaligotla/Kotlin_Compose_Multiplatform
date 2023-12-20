import permissions.delegate.BluetoothPermissionDelegate
import permissions.delegate.BluetoothServicePermissionDelegate
import permissions.delegate.LocationBackgroundPermissionDelegate
import permissions.delegate.LocationForegroundPermissionDelegate
import permissions.delegate.LocationServicePermissionDelegate
import permissions.delegate.PermissionDelegate
import permissions.model.Permission
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import permissions.delegate.AppTrackingTransparencyPermissionDelegate
import permissions.delegate.CalendarPermissionDelegate
import permissions.delegate.CameraPermissionDelegate
import permissions.delegate.ContactPermissionDelegate
import permissions.delegate.HotspotPermissionDelegate
import permissions.delegate.MediaAndAppleMusicPermissionDelegate
import permissions.delegate.MicrophonePermissionDelegate
import permissions.delegate.MobileDataServicePermissionDelegate
import permissions.delegate.MotionFitnessPermissionDelegate
import permissions.delegate.NotificationsPermissionDelegate
import permissions.delegate.PhotoPermissionDelegate
import permissions.delegate.ReminderPermissionDelegate
import permissions.delegate.SmsPermissionDelegate
import permissions.delegate.SpeechRecognitionPermissionDelegate
import permissions.delegate.WifiServicePermissionDelegate
import permissions.model.Permission_iOS
import permissions.model.Service
import platform.UIKit.UIDevice

internal actual fun platformModule(): Module = module {
    single<PermissionDelegate>(named(Permission_iOS.APP_TRACKING_TRANSPARENCY.name)) {
        AppTrackingTransparencyPermissionDelegate()
    }
    single<PermissionDelegate>(named(Permission.CALENDAR.name)) {
        CalendarPermissionDelegate()
    }
    single<PermissionDelegate>(named(Permission.CAMERA.name)) {
        CameraPermissionDelegate()
    }
    single<PermissionDelegate>(named(Permission.CONTACT.name)) {
        ContactPermissionDelegate()
    }
    single<PermissionDelegate>(named(Permission_iOS.MOTION_FITNESS.name)) {
        MotionFitnessPermissionDelegate()
    }
    single<PermissionDelegate>(named(Permission_iOS.REMINDER.name)) {
        ReminderPermissionDelegate()
    }
    single<PermissionDelegate>(named(Service.BLUETOOTH_SERVICE_ON.name)) {
        BluetoothServicePermissionDelegate()
    }
    single<PermissionDelegate>(named(Permission.BLUETOOTH.name)) {
        BluetoothPermissionDelegate()
    }
    single<PermissionDelegate>(named(Service.LOCATION_SERVICE_ON.name)) {
        LocationServicePermissionDelegate()
    }
    single<PermissionDelegate>(named(Permission.LOCATION_FOREGROUND.name)) {
        LocationForegroundPermissionDelegate()
    }
    single<PermissionDelegate>(named(Permission.LOCATION_BACKGROUND.name)) {
        LocationBackgroundPermissionDelegate(
            locationForegroundPermissionDelegate = get(named(Permission.LOCATION_FOREGROUND.name)),
        )
    }
    single<PermissionDelegate>(named(Permission.AUDIO_MUSIC.name)) {
        MediaAndAppleMusicPermissionDelegate()
    }
    single<PermissionDelegate>(named(Permission.MICROPHONE.name)) {
        MicrophonePermissionDelegate()
    }
    single<PermissionDelegate>(named(Permission.NOTIFICATION.name)) {
        NotificationsPermissionDelegate()
    }
    single<PermissionDelegate>(named(Permission.PHOTO.name)) {
        PhotoPermissionDelegate()
    }
    single<PermissionDelegate>(named(Permission.SMS.name)) {
        SmsPermissionDelegate()
    }
    single<PermissionDelegate>(named(Permission_iOS.SPEECH_RECOGNITION.name)) {
        SpeechRecognitionPermissionDelegate()
    }
    single<PermissionDelegate>(named(Service.WIFI_SERVICE_ON.name)) {
        WifiServicePermissionDelegate()
    }
    single<PermissionDelegate>(named(Service.MOBILE_DATA_SERVICE_ON.name)) {
        MobileDataServicePermissionDelegate()
    }
    single<PermissionDelegate>(named(Service.HOTSPOT_SERVICE_ON.name)) {
        HotspotPermissionDelegate()
    }
}

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName()
}

actual fun getPlatform(): Platform = IOSPlatform()
