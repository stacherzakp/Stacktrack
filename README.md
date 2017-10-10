# Stacktrack
Pastebin alternative for local usage.

Web application as an alternative for pastebin, allows to serve a text content via unique URL. 
Can be deployed in internal network as an internal tool. 

### Functionalities
- creating content from text input or file (up to 5mb)
- serving content via unique urls
- searching by title/author
- last 10 pastes
- securing paste by password (optional)

## Preview

![Create paste](https://s1.postimg.org/61nzd1v7lr/main.png)

![Search and activity](https://s1.postimg.org/693ymnvssv/activity.png)

![Preview](https://s1.postimg.org/6qu0b8q3r3/preview.png)

## Deployment (in Tomcat)

1. Build war file using `mvn clean package -Dmaven.test.skip=true`
2. Create postgresql database
3. Set JVM argument for tomcat application in setenv.sh 

````
-Dstacktrack-database=path_to_properties_file
 
example: export CATALINA_OPTS="$CATALINA_OPTS -Dstacktrack-database=/usr/share/tomcat8/conf/stacktrack-database.properties
````

4. Create properties file with your database credentials:

````
db.url=jdbc:postgresql://localhost:5432/stacktrack
db.username=postgres
db.password=admin
````

5. Deploy war on tomcat
6. Enjoy and paste your stacks
