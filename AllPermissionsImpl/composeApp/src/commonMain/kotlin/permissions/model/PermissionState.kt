package permissions.model

enum class PermissionState {
    NOT_DETERMINED,
    GRANTED,
    DENIED;
    fun notGranted(): Boolean {
        return this != GRANTED
    }
}

