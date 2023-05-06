# README #

This README would normally document whatever steps are necessary to get your application up and running.

### What is this repository for? ###

* Quick summary
* Version
* [Learn Markdown](https://bitbucket.org/tutorials/markdowndemo)

### How do I get set up? ###

* Summary of set up
* Configuration
* Dependencies
* Database configuration
* How to run tests
* Deployment instructions

### Contribution guidelines ###

* Writing tests
* Code review
* Other guidelines

### Who do I talk to? ###

* Repo owner or admin
* Other community or team contact

## clean a build
* mvn clean package -DskipTests

##  docker image
* docker build -t demo:v0.0.1 . 
* docker run --rm -p 4000:9090 --name demo-spring demo:v0.0.1

## Login - simple auth
* user	=	demo
* pass 	=	demo#2023	