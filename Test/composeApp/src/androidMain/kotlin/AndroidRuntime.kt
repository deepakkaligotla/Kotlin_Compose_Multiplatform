actual fun determineCurrentRuntime(): CurrentRuntime {
    val name = System.getProperty("java.vm.name") ?: "Android"

    val version = System.getProperty("java.version")

    return CurrentRuntime(name, version)
}