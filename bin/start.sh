#!/bin/bash
nohup java -jar -D-Dloader.path="lib" -Dspring.profiles.active="dev" appx-auth-0.0.1-SNAPSHOT.jar
