API Specification Document

**1. Authentication**

**1.1. Login Endpoint**

URL: /api/auth/login
Method: POST
Description: Authenticates the user and returns a JWT token.
Request

Headers:
Content-Type: application/json
Body:
json
{
"username": "string",
"password": "string"
}
Response

Success:
Status Code: 200 OK
Body:
json
{
"token": "string"
}
Error:
Status Code: 401 Unauthorized
Body:
json
{
"error": "Invalid credentials"
}


**2. Customer Management**

2.1. Create Customer
URL: /api/customers
Method: POST
Description: Creates a new customer. If the customer already exists and SyncDb is true, updates the existing customer.
Request

Headers:
Content-Type: application/json
Authorization: Bearer <token>
Body:
json
{
"firstName": "string",
"lastName": "string",
"email": "string",
"phone": "string",
"street": "string",
"address": "string",
"city": "string",
"state": "string",
"SyncDb": "boolean"
}
Response

Success:
Status Code: 201 Created
Body:
json
{
"uid": "string",
"firstName": "string",
"lastName": "string",
"email": "string",
"phone": "string",
"street": "string",
"address": "string",
"city": "string",
"state": "string",
"message": "Customer account created/updated successfully"
}
Error:
Status Code: 400 Bad Request (if customer already exists and SyncDb is false)
Body:
json
{
"error": "Found an existing account with the same email"
}
Status Code: 404 Not Found (if any required field is missing)
Body:
json
{
"error": "Required fields are missing"
}


**2.2. Update Customer**
URL: /api/customers/{email}
Method: PUT
Description: Updates the details of an existing customer.
Request

Headers:
Content-Type: application/json
Authorization: Bearer <token>
Path Parameters:
email: The email of the customer to be updated.
Body:
json
{
"firstName": "string",
"lastName": "string",
"phone": "string",
"street": "string",
"address": "string",
"city": "string",
"state": "string"
}
Response

Success:
Status Code: 200 OK
Body:
json
{
"uid": "string",
"firstName": "string",
"lastName": "string",
"email": "string",
"phone": "string",
"street": "string",
"address": "string",
"city": "string",
"state": "string",
"message": "Customer info updated successfully"
}
Error:
Status Code: 404 Not Found (if customer with the provided email does not exist)
Body:
json
{
"error": "Account not found with the provided email"
}


**2.3. Get Customer by ID**
URL: /api/customers/{email}
Method: GET
Description: Retrieves the details of a customer by their email.
Request

Headers:
Authorization: Bearer <token>
Path Parameters:
email: The email of the customer to retrieve.
Response

Success:
Status Code: 200 OK
Body:
json
{
"uid": "string",
"firstName": "string",
"lastName": "string",
"email": "string",
"phone": "string",
"street": "string",
"address": "string",
"city": "string",
"state": "string",
"message": "Account found with the provided email"
}
Error:
Status Code: 404 Not Found (if customer with the provided email does not exist)
Body:
json
{
"error": "No account found with the provided email"
}


**2.4. Delete Customer**
URL: /api/customers/{email}
Method: DELETE
Description: Deletes a customer by their email.
Request

Headers:
Authorization: Bearer <token>
Path Parameters:
email: The email of the customer to delete.
Response

Success:
Status Code: 200 OK
Body:
json
{
"message": "Account deleted"
}
Error:
Status Code: 404 Not Found (if customer with the provided email does not exist)
Body:
json
{
"error": "No account found with the provided email"
}


**2.5. Get All Customers**
URL: /api/customers
Method: GET
Description: Retrieves a paginated list of customers with optional sorting and searching.
Request

Headers:
Authorization: Bearer <token>
Query Parameters:
pageNo: Page number (starts at 1).
rowsCount: Number of rows per page.
sortBy: Attribute to sort by (optional).
searchBy: Attribute to search by (optional).
searchQuery: Search term (optional).
Response

Success:
Status Code: 200 OK
Body:
json
{
"content": [
{
"uid": "string",
"firstName": "string",
"lastName": "string",
"email": "string",
"phone": "string",
"street": "string",
"address": "string",
"city": "string",
"state": "string"
}
],
"pageNumber": 1,
"totalPages": 1,
"totalElements": 10
}


**2.6. Search Customers**
URL: /api/customers/search
Method: GET
Description: Searches for customers based on a specific column and search query.
Request

Headers:
Authorization: Bearer <token>
Query Parameters:
searchBy: Column to search by (e.g., firstName, email).
searchQuery: Search term.
Response

Success:
Status Code: 200 OK
Body:
json
[
{
"uid": "string",
"firstName": "string",
"lastName": "string",
"email": "string",
"phone": "string",
"street": "string",
"address": "string",
"city": "string",
"state": "string"
}
]
Error:
Status Code: 400 Bad Request (if searchBy parameter is invalid)
Body:
json
{
"error": "Invalid search column"
}


**6. Security Configuration**

6.1. JWT Authentication
Class: JwtAuthenticationEntryPoint
Purpose: Handles unauthorized access attempts and responds with a 401 status code.
Class: JwtAuthenticatonFilter
Purpose: Intercepts requests to extract and validate JWT tokens, and sets the security context if the token is valid.
Class: JwtHelperClass
Purpose: Provides methods to generate, parse, and validate JWT tokens.
Configuration

**7. Token Generation:**

Use the JwtHelperClass to generate a token when a user logs in.
Token Validation:
Use the JwtAuthenticatonFilter to validate tokens on protected endpoints.
Exception Handling:
Use the JwtAuthenticationEntryPoint to handle and respond to unauthorized access attempts.
Setup Instructions

**8. Add Dependencies:**
Ensure that you have dependencies for Spring Boot, Spring Security, JWT, and any other required libraries in your pom.xml or build.gradle.
Configure Application Properties:
Set up application properties in application.properties or application.yml, including JWT secret and other relevant configurations.
Database Setup:
Ensure that the database is configured and the Customer entity is properly mapped.
Run the Application:
Start the Spring Boot application using your preferred IDE or the command line (mvn spring-boot:run or ./gradlew bootRun).
Test Endpoints:
Use tools like Postman or curl to test the API endpoints as described in the specifications.


**9. Configuration**

**1.Database Configuration**
spring.datasource.url=jdbc:mysql://localhost:3306/Customer_Management?createTableIfNotExists=true
spring.datasource.username=root
spring.datasource.password=Arjun@123
spring.jpa.hibernate.ddl-auto=update

**2.Server Configuration**
server.port=1000

**3.Security Configuration**
security.user.name=MUKTESHWAR
security.user.password=1234
security.user.role=ADMIN

**4.JWT Configuration**
jwt.secret=12345678910bdjgfhsdcvdsbcFSGADCVXGDSAHGFDVXZBXvxzbvsgfteyrefdgdvcbxncvdfghfgdhvfdBVNCMVBHJFGFJHHHT
