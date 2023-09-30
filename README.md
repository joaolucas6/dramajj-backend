# DramaJJ back-end

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)


This is a backend project for a website where users can create lists and rate asian television dramas. 


## Installation


1. Clone the repository:

```bash
https://github.com/joaolucas6/dramajj-backend.git
```

2. Install dependencies with Maven

## Usage

Start the application and then the API will be accessible at http://localhost:8080


## API Endpoints
You can see all API Endpoints by acessing the url below:


```markdown
http://localhost:8080/swagger-ui/index.html
```

## Authentication
The API uses Spring Security for authentication control. The following roles are available:

```
- User
- Admin
```
If you want to set the role of a user, you should send it in register request body.

Example:

```json
{
  "firstName": "first name",
  "lastName": "last name",
  "username": "username",
  "password": "password",
  "role": "role"
}
```
