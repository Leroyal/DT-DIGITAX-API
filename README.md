DigiTax is the #1 tax preparation software in the world to file taxes online. Easily file federal and state
income tax returns with 100% accurate Expert Approved Guarantee. 

[![GitHub issues](https://img.shields.io/github/issues/Leroyal/DT-DIGITAX-API)](https://github.com/Leroyal/DT-DIGITAX-API/issues) [![Build Status](https://github.com/Leroyal/DT-DIGITAX-API/workflows/CI/badge.svg)](https://github.com/Leroyal/DT-DIGITAX-API/workflows/CI/badge.svg)

## Table of Contents

* [RESTful Web Services](#rest)
* [Technologies](#technologies)
    * [Java](#java)    
    * [Sprint Boot](#spring-boot)
    * [SQL](#SQL)
* <details>
    <summary>DIGITAX API</summary>
</details>    

* [CHANGELOG](#changelog)

<a name="rest"></a>
## RESTful Web Services 
### Client-Server
By separating the user interface concerns from the data storage concerns, we improve the portability of the user interface across multiple platforms and improve scalability by simplifying the server components.

### Stateless
Each request from the client to server must contain all of the information necessary to understand the request, and cannot take advantage of any stored context on the server. The session state is therefore kept entirely on the client.

### Cacheable
Cache constraints require that the data within a response to a request be implicitly or explicitly labeled as cacheable or non-cacheable. If a response is cacheable, then a client cache is given the right to reuse that response data for later, equivalent requests.

### Uniform Interface 
By applying the software engineering principle of generality to the component interface, the overall system architecture is simplified and the visibility of interactions is improved. To obtain a uniform interface, multiple architectural constraints are needed to guide the behavior of components. REST is defined by four interface constraints: identification of resources; manipulation of resources through representations; self-descriptive messages; and, hypermedia as the engine of application state.

### Layered System
The layered system style allows an architecture to be composed of hierarchical layers by constraining component behavior such that each component cannot “see” beyond the immediate layer with which they are interacting.

### Code on Demand (optional)
REST allows client functionality to be extended by downloading and executing code in the form of applets or scripts. This simplifies clients by reducing the number of features required to be pre-implemented.

<a name="technologies"></a>
## Technologies

Below is a short list of technologies currently being used for DigiTax APIs:

<a name="java"></a>
### Java

1. Java pros outweight it's cons. It is a very familiar and universally used language which generally means that Java 
reduces costs shortens development timeframes, drives innovation, and improves application services. Some other
advantages are:

    * It is a fully object-oriented programming language. 
    * The language enables automatic garbage collection and simple memory management, besides, features like generics. 
    All of these make it a robust language. 
    * Static type-checking at compile-time and runtime checking make it a highly secure language. 
    * Compilation into bytecodes enable Java Virtual Machine (JVM) to execute the code fast, hence Java offers 
    high performance. 
    * Java allows multi-threading.
    * You can run it in any system with a JVM. This portability is a major advantage.

Source: [https://docs.oracle.com/javase/8/docs/technotes/guides/language/index.html](https://docs.oracle.com/javase/8/docs/technotes/guides/language/index.html)

<a name="spring-boot"></a>
### Spring Boot

1. Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications that you can "just run".
Some features and advantages are:

    * Create stand-alone Spring applications.
    * Embed Tomcat, Jetty or Undertow directly (no need to deploy WAR files)
    * Provide opinionated 'starter' dependencies to simplify your build configuration
    * Automatically configure Spring and 3rd party libraries whenever possible
    * Provide production-ready features such as metrics, health checks, and externalized configuration
    * Absolutely no code generation and no requirement for XML configuration

Source: [https://spring.io/projects/spring-boot](https://spring.io/projects/spring-boot)

<a name="SQL"></a>
### SQL

1. SQL is a standard language for accessing and manipulating databases.
Some features and advantages are:

    * SQL can execute queries against a database
    * SQL can retrieve data from a database
    * SQL can insert records in a database
    * SQL can update records in a database
   
Source: [https://www.mysql.com](https://www.mysql.com)


<!-- ************************* -->
<!--    DIGITAX API SECTION    -->
<!-- ************************* -->
## DIGITAX API
The base URL for DIGITAX: http://52.6.241.241/.

The base URL for DIGITAX Swagger:http://digitaxapi-env.eba-nrr834zb.us-east-1.elasticbeanstalk.com:8080/swagger-ui.html#/

## Eclipse Instructions
Prerequisites:

Install Eclipse, the Maven plugin, and optionally the GitHub plugin.
Set up Eclipse Preferences

Window > Preferences... (or on Mac, Eclipse > Preferences...)

Select Maven

check on "Download Artifact Sources"
check on "Download Artifact JavaDoc"
Create a new project using storage/xml-api/cmdline-sample

Create a new Java Project.
Choose the Location of the project to be the location of cmdline-sample
Select the project and Convert to Maven Project to add Maven Dependencies.
Click on Run > Run configurations
Navigate to your Java Application's configuration section

Run

Right-click on project
Run As > Spring boot App

## Run in Live
Create a buid by Run as > Maven build
before it please check all theconfugration file set to live credentials

We have used Beanstalk and RDS of AWS for Digitax

Login to bean stalk and uplaod the build 

After successfull uplaod of build you can see the changes in port number 8080(default port of beanstalk)



