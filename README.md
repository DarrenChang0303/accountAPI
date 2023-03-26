# AccountAPI
## Overview
AccountAPI is a simple RESTful API built using Spring Boot for creating and verifying user accounts.
## Main Features

1. Create Account
2. Verify Account

## How to Use

1. Reserve port 8080 for Spring Boot and port 3306 for the database.
2. Use the following docker-compose command to create a container:
```bash
docker-compose up
```
3. Use Postman to access this application.
4. Use the following code to access this application:

### Create an account
```bash
curl -X POST -H "Content-Type: application/json" -d '{"username":"username","password": "password"}' "http://localhost:8080/user/register"
```
### log in
```bash
curl -X POST -H "Content-Type: application/json" -d '{"username":"username","password": "password"}' "http://localhost:8080/user/login"
```

Thank you for using AccountAPI!
