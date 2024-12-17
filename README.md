# Project Summary
The **"Web Quiz API"** project is a web service that handles multiple quizzes, allowing their creation, retrieval, solving, and permanent storage in a persistent database. It includes authentication and authorization to ensure only registered users can interact with quizzes. Pagination mechanisms have been implemented to efficiently manage large volumes of data. The application is developed in Kotlin with Spring Boot, using Spring Data JPA and an H2 database for persistent data storage.


---

## Technologies Used

- **Spring Boot**: A framework for developing Java and Kotlin applications that provides a comprehensive environment for web development, security, data access, and dependency management.
- **Spring Data JPA**: Facilitates interaction with the database using Java Persistence API (JPA) and provides simplified access to database entities.
- **H2 Database**: A SQL database engine that store data on disk, used for permanently storing quiz data.
- **Spring Security**: For user authentication and authorization in the system.
- **BCrypt**: A password hashing algorithm used for encrypting user passwords.
- **Paging and Sorting**: Implemented to handle large volumes of data and to allow paginated navigation through quizzes.

---

## Available Endpoints:

### Quiz Creation and Get
- **POST /api/quizzes**
  - **Description**: Creates a new quiz. The request should contain a JSON with the following fields:
    ```json
    {
      "title": "<string, not empty>",
      "text": "<string, not empty>",
      "options": ["<string 1>", "<string 2>", ...],
      "answer": [<integer>, <integer>, ...] (optional)
    }
    ```
  - **Response**: 200 (OK) JSON with the generated ID and quiz details or 400 (BAD REQUEST) if the body is wrong
    ```json
    {
      "id": <integer>,
      "title": "<string>",
      "text": "<string>",
      "options": ["<string 1>", "<string 2>", ...]
    }
    ```

- **Quiz Retrieval**
  - **GET /api/quizzes/{id}**
    - **Description**: Retrieves a specific quiz by its ID.
    - **Response**: 200 (OK) JSON with the quiz details, excluding the correct answer to prevent leaking of answers. or 404 (NOT FOUND) if quiz id was not found
      ```json
      {
        "id": <integer>,
        "title": "<string>",
        "text": "<string>",
        "options": ["<string 1>", "<string 2>", ...]
      }
      ```
- **GET /api/quizzes?page={number}**
  - **Description**: Retrieves a specific page of quizzes with pagination.
  - **Response**: JSON with a list of quizzes, pagination information, and statistics.
    ```json
    {
      "totalPages": 1,
      "totalElements": 3,
      "last": true,
      "first": true,
      "content": [
        {"id": <quiz id>, "title": "<string>", "text": "<string>", "options": ["<string>", "<string>", ...]},
        ...
      ]
    }
    ```

### Quiz Solving
- **POST /api/quizzes/{id}/solve**
  - **Description**: Allows users to solve a quiz by specifying the correct answers. The request should contain a JSON with the following fields:
  - ```json
    {
      "answer": [<integer>, <integer>, ...]
    }
    ```
  - **Response**: JSON indicating the success of the answer and a feedback message.
    ```json
    {
      "success": true,
      "feedback": "Congratulations, you got it right!"
    }
    ```
    or JSON indicating the failure of the answer and a feedback message.
     ```json
    {
      "success":false,
      "feedback":"Wrong answer! Please, try again."
    }
    ```
     or 404 (NOT FOUND) if quiz id was not found
    
    
### Delete a quiz   
- **DELETE /api/quizzes/{id}**
  - **Description**: Allows a user to delete a quiz they created.
  - **Response**: 204 (NO CONTENT) if the quiz was deleted successfully; 403 (FORBIDDEN) if the user is not the creator of the quiz. or 404 (NOT FOUND) if quiz id was not found

### Completed Quiz by User
- **GET /api/quizzes/completed?page={number}**
  - **Description**: Retrieves a paginated list of quiz completions for the authenticated user. Each completion is represented by a JSON object.
  - **Response**: JSON with quiz completions (inside content) and additional metadata as in the previous operation.
    ```json
    {
      "totalPages": 1,
      "totalElements": 5,
      "last": true,
      "first": true,
      "content": [
        {"id": <quiz id>, "completedAt": "<date_time>" },
        ...
      ]
    }
    ```
    
### User Authentication and Authorization
- **POST /api/register**
  - **Description**: Allows users to register by providing an email and password.
    ```json
    {
      "email": "<username>@<domain>.<extension>",
      "password": "<string, at least 5 characters>"
    }
    ```
  - **Response**: 200 (OK) if registration was successful, 400 (BAD REQUEST) if the email is already taken.
  - All endpoints of this API except this registration endpoint are protected and require a valid user authentication to access. If a valid user is not provided, a 401 Unauthorised error will be returned.
    
---
This project was created as part of the JetBrains Academy Kotlin Backend Developer (Spring Boot) course.

