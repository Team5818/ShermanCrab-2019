import com.techshroom.inciseblue.commonLib

plugins {
    id("org.rivierarobotics.gradlerioredux") version "0.6.0"
    id("net.ltgt.apt-idea") version "0.21"
    id("net.ltgt.apt-eclipse") version "0.21"
}

gradleRioRedux {
    robotClass = "org.rivierarobotics.robot.Robot"
    teamNumber = 5818
}

dependencies {
	commonLib("net.octyl.apt-creator", "apt-creator", "0.1.4") {
		compileOnly(lib("annotations"))
		annotationProcessor(lib("processor"))
	}
	commonLib("com.google.dagger", "dagger", "2.21") {
		implementation(lib())
		annotationProcessor(lib("compiler"))
	}
	implementation("org.slf4j:slf4j-api:1.7.25")
	commonLib("ch.qos.logback", "logback", "1.2.3") {
		implementation(lib("classic"))
		implementation(lib("core"))
	}
}

repositories {
	jcenter()
}
