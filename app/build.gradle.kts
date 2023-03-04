plugins {
    application
    id("org.jetbrains.kotlin.jvm") version "1.8.10"
}
java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(19))
}
kotlin {
    jvmToolchain(19)
}
tasks.test {
    useJUnitPlatform()
}
tasks.withType<JavaCompile>().configureEach {
    javaCompiler.set(javaToolchains.compilerFor {
        languageVersion.set(JavaLanguageVersion.of(19))
    })
}
tasks.withType<JavaExec>().configureEach {
    javaLauncher.set(javaToolchains.launcherFor {
        languageVersion.set(JavaLanguageVersion.of(19))
    })
    jvmArgs = listOf("--enable-preview")
}
val mainSourceSet = sourceSets.main.get()
val run = tasks.getByName<JavaExec>("run") {
    group = "application"
    mainClass.set("com.example.myapp.MainAppKt")
}
val runServer by tasks.registering(JavaExec::class) {
    group = "application"
    classpath = mainSourceSet.runtimeClasspath
    mainClass.set("com.example.myapp.ServerKt")
}
val runClient by tasks.registering(JavaExec::class) {
    group = "application"
    classpath = mainSourceSet.runtimeClasspath
    mainClass.set("com.example.myapp.ClientKt")
    standardInput = System.`in`
}
