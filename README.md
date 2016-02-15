# ProtectEmailNotifier
Sends email notifications when Nest Protect devices status is changed

## Install
Before build and run this application perform following steps:
1. Install Java 8
2. Install Maven
3. Edit src/main/resources/config.properties It should contain valid email and Nest connectivity properties.
    "recipient.mail" property should contain email of person who supposed to receive notifications

## Build
Run "mvn clean install" in command line

## Run
Run "mvn exec:java" in command line

## Stop
Press any Enter