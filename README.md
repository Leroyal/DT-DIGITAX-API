# DT-DIGITAX-API

DigiTax is the #1 tax preparation software in the world to file taxes online. Easily file federal and state
income tax returns with 100% accurate Expert Approved Guarantee. 

[![GitHub issues](https://img.shields.io/github/issues/Leroyal/DT-DIGITAX-API)](https://github.com/Leroyal/DT-DIGITAX-API/issues) [![Build Status](https://github.com/Leroyal/DT-DIGITAX-API/workflows/CI/badge.svg)](https://github.com/Leroyal/DT-DIGITAX-API/workflows/CI/badge.svg)

## Table of Contents

* [RESTful Web Services](#rest)
* [Technologies](#technologies)
    * [Java](#java)    
    * [Sprint Boot](#spring-boot)
* <details>
    <summary>DIGITAX API</summary>

    * [Sign Up](#sign-up)
    * [Sign In](#sign-in)
    * [Sign Out](#sign-out)
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


<!-- ************************* -->
<!--    DIGITAX API SECTION    -->
<!-- ************************* -->
## DIGITAX API
The base URL for DIGITAX: http://52.6.241.241/.

### Sign Up
Endpoint: `/api/auth/signup`

API endpoint for user registration.

| Code | Description              | Links    |
|------|--------------------------|----------|
| 200  | CREATE_SUCCESS           | No Links |
| 400  | USER_ALREADY_EXISTS      | No Links |
| 401  | INVALID_EMAIL_FORMAT     | No Links |
| 401  | INVALID_PASSWORD_FORMAT  | No Links |
| 500  | INTERNAL_SERVER_ERROR    | No Links |

<details>
    <summary>POST Request Body: </summary>

```json
{
    "username": "JayantaPA",
    "email": "jayanta.5045ddd@gmail.com",
    "password": "123456",
    "phone": "80189442589"
}
```
</details>
<details>
    <summary>Response:</summary>

```json
{
    "status": {
        "status_code": 200,
        "message": "SUCCESS"
    },
    "data": {
        "user": {
            "id": 4,
            "email": "jayanta.5056@gmail.com",
            "authorities": [
                {
                    "authority": "ROLE_USER"
                }
            ],
            "username": "Jayanta"
        },
        "session": {
            "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJKYXlhbnRhIiwiaWF0IjoxNTk2NjE0OTI5LCJleHAiOjE1OTY3MDEzMjl9.doC6QumEu_c_iF3kgd9Cb7vHyOYw3bFn2gTNVSLJP_M76DOyB7-Ay4KFgIIX4Cd6ZwXtt1ovYOLxjqQsQz72qw",
            "expirationMiliSecond": 86400000,
            "utcExpirationTime": "2020-08-06T08:08:49.119+00:00",
            "ptExpirationTime": "2020-08-06T08:08:49.119+00:00"
        }
    }
}
```
</details>

[table of contents](#table-of-contents)

### Sign In 
Endpoint: `/api/auth/signin`

API endpoint for user authentication.

| Code | Description             | Links    |
|------|-------------------------|----------|
| 200  | SUCCESS                 | No Links |
| 401  | WRONG_EMAIL_OR_PASSWORD | No Links |
| 401  | INVALID_EMAIL_FORMAT    | No Links |
| 401  | INVALID_PASSWORD_FORMAT | No Links |

<details>
    <summary>POST Request Body:</summary>

```json
{
    "username": "Jayanta",
    "password": "123456"
}
```
</details>
<details>
    <summary>Response:</summary>

```json
{
    "status": {
        "status_code": 200,
        "message": "SUCCESS"
    },
    "data": {
        "user": {
            "id": 4,
            "email": "jayanta.5056@gmail.com",
            "authorities": [
                {
                    "authority": "ROLE_USER"
                }
            ],
            "username": "Jayanta"
        },
        "session": {
            "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJKYXlhbnRhIiwiaWF0IjoxNTk2NjE0OTI5LCJleHAiOjE1OTY3MDEzMjl9.doC6QumEu_c_iF3kgd9Cb7vHyOYw3bFn2gTNVSLJP_M76DOyB7-Ay4KFgIIX4Cd6ZwXtt1ovYOLxjqQsQz72qw",
            "expirationMiliSecond": 86400000,
            "utcExpirationTime": "2020-08-06T08:08:49.119+00:00",
            "ptExpirationTime": "2020-08-06T08:08:49.119+00:00"
        }
    }
}
```
</details>

[table of contents](#table-of-contents)

### Sign Out 
Endpoint: `/api/auth/logout`

API endpoint to end user session.

| Code | Description           | Links    |
|------|-----------------------|----------|
| 200  | SUCCESS               | No Links |
| 500  | INTERNAL_SERVER_ERROR | No Links |

<details>
    <summary>POST Request Body:</summary>

```json
{
  "accessToken": "07ywyJ6TdsMLOSh2GTfq328VyYEg6C3L"
}
```
</details>
<details>
    <summary>Response:</summary>

```json
{}
```
</details>

[table of contents](#table-of-contents)

### Commit, Push & Creating Branches
Jira ticket addressed will be associated to it's own branch. For example lets say you are addressing Jira ticket "DIG-27". You will create a branch with the following format: `<type>/<jiraTicketId>-<description>`. 

Practical example is `task/dig-27-filtering-api-update`.

You should commit with a message that includes the jira ticket. You can also have fun, so feel free to include gitmoji too :slightly_smiling_face:. The format for commit messages with gitmoji will be `<gitmoji> <jira ticket> <message>`. 

Practical example is `:rocket: DIG-27 Created filters for APIs`.

More information on this [Strategy](https://nvie.com/posts/a-successful-git-branching-model/#feature-branches).

<a name="changelog"></a>
## CHANGELOG

1. 2020-07-30: Add README to repo




