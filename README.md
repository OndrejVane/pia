# Run application
Tools: Docker, Maven
## With script
1) go to application root folder(where is pom.xml file)

2) run this commands in terminal
```
chmod +x run.sh
./run.sh
```

## With single commands
1) go to application root folder(where is pom.xml file)

2) build application by maven
```
mvn clean install
```
3) create docker image
```
docker build -t <imagename> .
```

4) run docker image on port 8080
```
docker run -p8080:8080 <imagename>
```
# Default users in application

1) username: Admin001 pw: 1234 role: ADMIN 
2) username: User0001 pw: 1234 role: --
3) username: Accou001 pw: 1234 role: ACCOUNTANT


# New user creating
 
Only admin can create new users. When a user is created, an alphanumeric code is generated, which is used as a username,
and a four-digit pin, which is used as a password. This data is sent to the user's email (if the "Send email" option is checked).
The password can be changed by the administrator in user editing page.

