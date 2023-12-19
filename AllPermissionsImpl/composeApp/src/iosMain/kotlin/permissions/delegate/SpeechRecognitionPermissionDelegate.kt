package permissions.delegate

import permissions.model.PermissionState
import permissions.util.openAppSettingsPage
import platform.Speech.SFSpeechRecognizer
import platform.Speech.SFSpeechRecognizerAuthorizationStatus

internal class SpeechRecognitionPermissionDelegate : PermissionDelegate {

    override fun getPermissionState(): PermissionState {
        val recognizer = SFSpeechRecognizer()

        return when (SFSpeechRecognizer.authorizationStatus()) {
            SFSpeechRecognizerAuthorizationStatus.SFSpeechRecognizerAuthorizationStatusNotDetermined -> PermissionState.NOT_DETERMINED
            SFSpeechRecognizerAuthorizationStatus.SFSpeechRecognizerAuthorizationStatusAuthorized -> PermissionState.GRANTED
            SFSpeechRecognizerAuthorizationStatus.SFSpeechRecognizerAuthorizationStatusDenied -> PermissionState.DENIED
            else -> PermissionState.NOT_DETERMINED
        }
    }

    override suspend fun providePermission() {
        SFSpeechRecognizer.requestAuthorization { status ->
            if (status == SFSpeechRecognizerAuthorizationStatus.SFSpeechRecognizerAuthorizationStatusAuthorized) {
                println("granted Speech Recognition permission")
            } else {
                println("denied Speech Recognition permission")
            }
        }
    }

    override fun openSettingPage() {
        openAppSettingsPage()
    }
}