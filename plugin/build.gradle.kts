plugins {
    `java-library`
    id("org.openjfx.javafxplugin") version "0.0.8"
}

repositories {
    mavenCentral()
    maven {
        name = "WPILib"
        setUrl("https://first.wpi.edu/FRC/roborio/maven/release")
    }
}

dependencies {
    compileOnly(group = "edu.wpi.first.shuffleboard", name = "api", version = "2019.1.1")
    implementation(group = "org.openjfx", name = "javafx-controls", version = "11")
    implementation(group = "org.openjfx", name = "javafx-fxml", version = "11")
}

javafx {
    modules("javafx.controls", "javafx.fxml")
}

sourceSets {
    main {
        java {
            srcDirs("src/main/java")
        }
        resources {
            srcDirs("src/main/resources")
        }
    }
}

tasks.register<Copy>("installPlugin") {
    from(tasks.named("jar"))
    into("${System.getProperty("user.home")}/Shuffleboard/plugins")
    description = "Builds the plugin JAR and installs it in the Shuffleboard plugins directory."
    group = "Shuffleboard Plugin"
}