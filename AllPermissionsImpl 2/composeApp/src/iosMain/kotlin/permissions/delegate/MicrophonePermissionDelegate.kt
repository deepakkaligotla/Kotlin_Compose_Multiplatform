package permissions.delegate

import permissions.model.PermissionState
import permissions.util.openAppSettingsPage
import platform.AVFAudio.AVAudioSession
import platform.AVFAudio.AVAudioSessionRecordPermissionDenied
import platform.AVFAudio.AVAudioSessionRecordPermissionGranted
import platform.AVFAudio.AVAudioSessionRecordPermissionUndetermined

internal class MicrophonePermissionDelegate : PermissionDelegate {

    override fun getPermissionState(): PermissionState {
        return when (AVAudioSession.sharedInstance().recordPermission()) {
            AVAudioSessionRecordPermissionUndetermined -> PermissionState.NOT_DETERMINED
            AVAudioSessionRecordPermissionGranted -> PermissionState.GRANTED
            AVAudioSessionRecordPermissionDenied -> PermissionState.DENIED
            else -> PermissionState.NOT_DETERMINED
        }
    }

    override suspend fun providePermission() {
        val audioSession = AVAudioSession.sharedInstance()
        audioSession.requestRecordPermission { granted ->
            if (granted) {
                println("granted Microphone permission")
            } else {
                println("denied Microphone permission")
            }
        }
    }

    override fun openSettingPage() {
        openAppSettingsPage()
    }
}