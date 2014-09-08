## GM Calendar

[![Build Status](https://travis-ci.org/bckr/GMCal-Android.svg?branch=master)](https://travis-ci.org/stetro/GMCal-Android)

A mobile calendar for the [Cologne University of Applied Sciences](https://www.fh-koeln.de/en/homepage_26.php).

## Preview
![](http://gmcal.s3.amazonaws.com/GMCal_Android_Preview.gif)

## Todo
- Testing
- Proper error handling
- English localization
- Find a proper name for the App

## Planned Features
- Visual indicator for the upcomming class in the day and week view
- Detail view for classes and lecturer
- Possibility to customize your schedule
    - remove modules
    - add modules from past or upcomming semesters
    
## Development

Using gradle wrapper for build and deployment: `./gradlew build` (More information with `./gradlew tasks`). Gradle will automatically download the necessary SDK versions, dependencies and build tools.

#### Coding with Android Studio (or IntelliJ) 
Open build.gradle as project and it will creating a working *.iml file for Android Studio (or IntelliJ). 

#### Coding with Eclipse
If you use Eclipse just add the eclipse plugin to the build.gradle file:

```apply plugin: 'eclipse'```
