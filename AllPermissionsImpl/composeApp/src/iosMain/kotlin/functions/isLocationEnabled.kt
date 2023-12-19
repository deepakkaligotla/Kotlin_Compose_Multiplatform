package functions

import platform.CoreLocation.CLLocationManager

fun isLocationEnabled(): Boolean {
    return CLLocationManager().locationServicesEnabled
}