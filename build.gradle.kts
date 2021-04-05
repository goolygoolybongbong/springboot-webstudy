
val lombokVersion = project.properties["lombokVersion"]

plugins {
    java
    eclipse
    id("io.spring.dependency-management") version "1.0.11.RELEASE" apply true
    id("org.springframework.boot") version "2.4.4" apply true
}

repositories {
    jcenter()
}

dependencies {
    // spring-boot
    implementation("org.springframework.boot:spring-boot-gradle-plugin")
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // jpa
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // mustache
    implementation("org.springframework.boot:spring-boot-starter-mustache")

    // spring security
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    testImplementation("org.springframework.security:spring-security-test")

    // spring session
    implementation("org.springframework.session:spring-session-jdbc")

    // lombok
    implementation("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")

    // H2 database
    implementation("com.h2database:h2")

}

// specify test platform : gradle(run test using)
tasks.withType<Test> {
    useJUnitPlatform()
}