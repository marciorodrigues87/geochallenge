# geochallenge

## environment

You will need the following dependencies:

	npm
	node
	bower
	java8
	maven3
	docker
	docker-compose
	a docker-machine named default
	docker env configured (docker-machine start default && eval $(docker-machine env default))

## building and running

Just run the command:
 
	cd finup-backend && mvn clean install && cd ../finup-frontend/public && bower install --allow-root && cd ../.. && docker-compose up --build

on project root dir.

NOTE: If the application receives error when connecting to the database, wait until the database container is created and then run the command again :(

## local testing

To signup:
	
	http://$(docker-machine ip default):8081

## automated tests

After building and running, just run the following command:

	cd finup-backend/integration-tests && mvn test -P integration-tests -Dvrcc.tests.integration.host=http://$(docker-machine ip default)

on project root dir.

## logs

To see the application logs access this link:

	http://$(docker-machine ip default):9292/index.html#/dashboard/file/logstash.json

## demo

The running demo of this code can be seen in this link:

	http://ec2-54-187-136-187.us-west-2.compute.amazonaws.com:8081/