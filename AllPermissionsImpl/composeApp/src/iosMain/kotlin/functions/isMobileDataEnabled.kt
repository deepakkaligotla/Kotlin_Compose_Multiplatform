package functions

import platform.CoreTelephony.CTCellularData
import platform.CoreTelephony.CTCellularDataRestrictedState

fun isMobileDataEnabled(): Boolean {
    return when(CTCellularData().restrictedState) {
        CTCellularDataRestrictedState.kCTCellularDataNotRestricted -> true
        CTCellularDataRestrictedState.kCTCellularDataRestricted, CTCellularDataRestrictedState.kCTCellularDataRestrictedStateUnknown -> false
        else -> false
    }
}