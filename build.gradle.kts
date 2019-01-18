import com.techshroom.inciseblue.commonLib

plugins {
    id("org.rivierarobotics.gradlerioredux") version "0.2.1"
    id("net.ltgt.apt-idea") version "0.20"
    id("net.ltgt.apt-eclipse") version "0.20"
}
gradleRioRedux {
    robotClass = "org.rivierarobotics.robot.Robot"
    teamNumber = 5818
    addCtre = true
}

dependencies {
	commonLib("net.octyl.apt-creator", "apt-creator", "0.1.3") {
		compileOnly(lib("annotations"))
		annotationProcessor(lib("processor"))
	}
	commonLib("com.google.dagger", "dagger", "2.16") {
		implementation(lib())
		annotationProcessor(lib("compiler"))
	}
}
