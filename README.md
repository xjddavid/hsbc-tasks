# About this project
This project is a demo of a web service to manage task list.
It has the ability to create, update, delete, retrieve tasks. Each task has a title and a due date.

Backend service is using Java Spring and provide RESTful interface.

All operations on tasks are logged to a file in the repo root folder called operation.log.

A python script is provided to interact with API from the command line.
Examples:

·         tasks add "write some code" 21/08/2019

·         tasks list

·         tasks list --expiring-today

·         tasks done 3

# Architecture
Folder app contains all the codes of backend springboot application. It provides the function
of create, update, delete, retrieve tasks in a restful style.
Folder data records the mysql database data in data/mysql path. setup.sql file create the hsbc 
database and use it when mysql starts.
Folder python contains the front end python script and corresponding unit test script.


# Prepare Environmet
You need have docker and python installed in your system.

# Start Application
1. clone the repo and cd repo folder

2. Using docker-compose to start the backend spring application. Type the following command in the terminal. It might take some time to download the docker images and java dependencies. You can have a coffee break. 
`docker-compose up`

3. Open another terminal window. Use python(python3) to run front end. All the input is from terminal
`python3 python/terminal_input.py`

4. Type inputs in the same terminal. 

5. All the operation and console output will be logged in the operation.log file.

6. You can stop the front end script by type "stop" in the terminal. 
Use the following command to stop the backend Spring application. 
`docker-compose down`

# How to run tests
Backend spring boot application has integration test file in the test folder. You can run the test
by using IDE or use maven by typing `mvn test`
The test for the frontend python script can run by the following command.
`python3 python/tests.py`
