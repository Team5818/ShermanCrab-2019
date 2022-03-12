plugins {
    id("org.rivierarobotics.gradlerioredux") version "0.9.7"
}

gradleRioRedux {
    robotClass = "org.rivierarobotics.robot.Robot"
    teamNumber = 5818
}

repositories {
    mavenCentral()
    maven {
        name = "octyl.net"
        url = uri("https://maven.octyl.net/repository/team5818-releases")
    }
}

dependencies {
    implementation("org.rivierarobotics:5818-lib:0.3.1")
    implementation("org.rivierarobotics.apparjacktus:apparjacktus:0.1.2")
    compileOnly("net.octyl.apt-creator:apt-creator-annotations:0.1.4")
    annotationProcessor("net.octyl.apt-creator:apt-creator-processor:0.1.4")
    implementation("com.google.dagger:dagger:2.41")
    annotationProcessor("com.google.dagger:dagger-compiler:2.41")
}

// Gradle RIO is not applied until this is called!
gradleRioRedux.applyGradleRioConfiguration()