Pingpong Sample code between 2 servers by using Protobuf
---
### Build project
```bash
$ ./gradlew build
```
### Run Java server
```bash
$ ./gradlew run
```
### Generate Python Protobuf files
Add python { } under task.builtins at [here](https://github.com/Vegvisir-IoT/PingPongSamples/blob/master/java/app/build.gradle). If you don't want python files, just comment it out.
```kotlin
generateProtoTasks {
    all().each { task ->
        task.builtins {
            python { }
            remove java
        }
        task.plugins {
            javalite {}
        }
    }
}
```
### Versions
- java @ 11.0.1
- protobuf/protbuf-lite @ 3.0.0

