# Server Monitoring console

## Description
Web console written in Java, that allows you to monitor your disk usage, memory usage, status of your Tomcat application server and Docker Containers. 

**Important Note:**
- **Operating Systems Supported**: As of now, the software is compatible with macOS and Ubuntu.
- **Development Status**: This project is still in the early stages of development. It is not yet fully functional and is expected to undergo significant enhancements and bug fixes. 

## Features
- Stop / Start of Docker containers
- Stop / Start of Apache Tomcat application server
- Monitoring of applications deployed on Tomcat
- Monitoring of your disk and memory usage

## Technologies Used
- Java Spring Boot for backend
- Basic Javascript and HTML for frontend
- Maven

# Project Installation Guide

This README provides detailed instructions for installing and running the project on an Ubuntu system running on macOS.

## Prerequisites

- Java must be installed on your system. You can check this by running `java -version` in your terminal.
- If you are using Apache Tomcat, ensure it is correctly installed and operational. Verify this by running `catalina version` in your terminal.

## Installation Steps

### 1. Download the Project

Navigate to the project's download page and download the project files to your Ubuntu on macOS machine.

### 2. Set Environment Variables (If Using Tomcat)

If Apache Tomcat is installed:
#### Add CATALINA_HOME to your environment variables
`echo 'export CATALINA_HOME="/path/to/tomcat"' >> ~/.bashrc`

#### Apply the changes
`source ~/.bashrc`

#### Verify the environment variable
`echo $CATALINA_HOME`

### 3. Run the jar file
Go to <project_folder>/target and run `java -jar monitoring-console.jar`

### Open the console in your browser
Type localhost:8085/overview.html into your browser. The console should be running. 


