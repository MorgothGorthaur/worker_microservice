dependencies {
    implementation("org.seleniumhq.selenium:selenium-java:4.13.0")
    implementation(project(":model"))
    implementation(project(":logger"))
}

tasks.bootJar {
    enabled = false
}