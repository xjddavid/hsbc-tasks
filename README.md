#Prepare Environmet
You need have docker, java and python installed in your system.

#Start Application
1. clone the repo and cd repo folder

2. Using docker to start mysql
docker run -p 3306:3306 --name mysql -v /route/to/repo/data/mysql:/var/lib/mysql -v /route/to/repo/data:/docker-entrypoint-initdb.d -e MYSQL_ROOT_PASSWORD=123456  -d mysql

3. use maven to generate jar, use default routes. You can do it by using IDE or 'mvn package'.

4. run backend spring service 
java -jar target/tasks-1.0-SNAPSHOT.jar

5. use python(python3) to run front end. All the input is from terminal
python3 python/terminal_input.py

6. then type inputs in the terminal. Examples are:
tasks add "write some code" 21/08/2019

tasks list

tasks list --expiring-today

tasks list --expiring-12/12/2019

tasks done 3

7. all the operation and console output will be logged in the operation.log file.