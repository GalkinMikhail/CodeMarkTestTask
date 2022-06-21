# Test task for CODEMARK

Develop a SOAP service and methods of working with data. Make methods that will:
- Get a list of users from the database (without roles)
- Get a specific user (with his roles) from the database
- Delete a user in the database
- Add a new user with roles in the database.
- Edit an existing user in the database. If an array of roles is passed in the editing request, the system must update the list of user roles in the database - add new bindings, remove outdated bindings.
On the backend, for the methods of adding and editing, a formatological control of the values received should be performed. The fields name, login, password are mandatory, password contains a letter in uppercase and a digit.  

# Instructions for deploying the application  
Run the attached Jar file using the command:  
```bash  
  java -DDB_NAME=db_name -DDB_USER=db_user -DDB_PASSWORD=db_pass -jar codeMarkTestTask-0.0.1-SNAPSHOT.jar
```  
Example:  
```bash  
  java -DDB_NAME=SoapService -DDB_USER=postgres -DDB_PASSWORD=postgres -jar codeMarkTestTask-0.0.1-SNAPSHOT.jar
```  
# API documentation  
App will be available at  
`localhost:8080/api`  
## Add roles to the database
insert into roles(id,name) values (1,'Админ');  
insert into roles(id,name) values (2,'Оператор');  
insert into roles(id,name) values (3,'Аналитик');  
  
## Example of user creation  
### Request with correct data  
Request:  
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:us="http://soap.rca/classc/courses">
    <soapenv:Header/>
    <soapenv:Body>
        <us:CreateUserRequest>
            <us:users>
                <us:login>galkin</us:login>
                <us:name>Mikhail</us:name>
                <us:password>Root1</us:password>
                <us:roleID>1</us:roleID>
                <us:roleID>2</us:roleID>
                <us:roleID>3</us:roleID>
            </us:users>
        </us:CreateUserRequest>
    </soapenv:Body>
</soapenv:Envelope>
```   
Response:  
```xml  
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
<SOAP-ENV:Header/>
<SOAP-ENV:Body>
    <ns2:CreateUserResponse xmlns:ns2="http://soap.rca/classc/courses">
        <ns2:message>User created successfully</ns2:message>
    </ns2:CreateUserResponse>
</SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```  

### Requests with incorrect data  
Request:  
```xml  
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:us="http://soap.rca/classc/courses">
<soapenv:Header/>
<soapenv:Body>
    <us:CreateUserRequest>
        <us:users>
            <us:login>testWithEmptyValues</us:login>
            <us:name></us:name>
            <us:password></us:password>
            <us:roleID>1</us:roleID>
        </us:users>
    </us:CreateUserRequest>
</soapenv:Body>
</soapenv:Envelope>
```  
Response:  
```xml  
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
<SOAP-ENV:Header/>
<SOAP-ENV:Body>
    <SOAP-ENV:Fault>
        <faultcode>SOAP-ENV:Server</faultcode>
        <faultstring xml:lang="en">Fields name, login, password cannot be empty</faultstring>
    </SOAP-ENV:Fault>
</SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```  

Request:  
```xml  
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:us="http://soap.rca/classc/courses">
<soapenv:Header/>
<soapenv:Body>
    <us:CreateUserRequest>
        <us:users>
            <us:login>testWithInvalidPassword</us:login>
            <us:name>Mikhail</us:name>
            <us:password>root</us:password>
            <us:roleID>1</us:roleID>
        </us:users>
    </us:CreateUserRequest>
</soapenv:Body>
</soapenv:Envelope>
```  
Response:  
```xml  
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
<SOAP-ENV:Header/>
<SOAP-ENV:Body>
    <SOAP-ENV:Fault>
        <faultcode>SOAP-ENV:Server</faultcode>
        <faultstring xml:lang="en">Password must contain an upper case letter and a number</faultstring>
    </SOAP-ENV:Fault>
</SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```  
## Example of getting a specific user  
Request:
```xml  
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:us="http://soap.rca/classc/courses">
<soapenv:Header/>
<soapenv:Body>
    <us:GetUserRequest>
        <us:login>galkin</us:login>
    </us:GetUserRequest>
</soapenv:Body>
</soapenv:Envelope>
```  
Response:  
```xml  
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
<SOAP-ENV:Header/>
<SOAP-ENV:Body>
    <ns2:GetUserResponse xmlns:ns2="http://soap.rca/classc/courses">
        <ns2:login>galkin</ns2:login>
        <ns2:name>Mikhail</ns2:name>
        <ns2:password>Root1</ns2:password>
        <ns2:roles>Оператор</ns2:roles>
        <ns2:roles>Админ</ns2:roles>
        <ns2:roles>Аналитик</ns2:roles>
    </ns2:GetUserResponse>
</SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```  
## Getting a list of all users
Request:  
```xml  
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:us="http://soap.rca/classc/courses">
<soapenv:Header/>
<soapenv:Body>
    <us:GetAllUsersRequest>
    </us:GetAllUsersRequest>
</soapenv:Body>
</soapenv:Envelope>
```  
Response:  
```xml  
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
<SOAP-ENV:Header/>
<SOAP-ENV:Body>
    <ns2:GetAllUsersResponse xmlns:ns2="http://soap.rca/classc/courses">
        <ns2:users>Mikhail</ns2:users>
    </ns2:GetAllUsersResponse>
</SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```  
## Example of updating user information  
### With correct data  
Request:  
```xml  
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:us="http://soap.rca/classc/courses">
<soapenv:Header/>
<soapenv:Body>
    <us:UpdateUserRequest>
        <us:users>
            <us:login>galkin</us:login>
            <us:name>Mikhail</us:name>
            <us:password>Root1</us:password>
            <us:roleID>1</us:roleID>
        </us:users>
    </us:UpdateUserRequest>
</soapenv:Body>
</soapenv:Envelope>
```  
Response:  
```xml  
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
<SOAP-ENV:Header/>
<SOAP-ENV:Body>
    <ns2:UpdateUserResponse xmlns:ns2="http://soap.rca/classc/courses">
        <ns2:message>User galkin updated successfully</ns2:message>
    </ns2:UpdateUserResponse>
</SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```  
### With incorrect data  
Request:  
```xml  
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:us="http://soap.rca/classc/courses">
<soapenv:Header/>
<soapenv:Body>
    <us:UpdateUserRequest>
        <us:users>
            <us:login>galkin</us:login>
            <us:name>Mikhail</us:name>
            <us:password>root</us:password>
            <us:roleID>1</us:roleID>
        </us:users>
    </us:UpdateUserRequest>
</soapenv:Body>
</soapenv:Envelope>
```  
Response:  
```xml  
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
<SOAP-ENV:Header/>
<SOAP-ENV:Body>
    <SOAP-ENV:Fault>
        <faultcode>SOAP-ENV:Server</faultcode>
        <faultstring xml:lang="en">Password must contain an upper case letter and a number</faultstring>
    </SOAP-ENV:Fault>
</SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```  

### Deleting a user from the database  
Request:  
```xml  
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:us="http://soap.rca/classc/courses">
<soapenv:Header/>
<soapenv:Body>
    <us:DeleteUserRequest>
        <us:login>galkin</us:login>
    </us:DeleteUserRequest>
</soapenv:Body>
</soapenv:Envelope>
```  
Response:  
```xml  
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
<SOAP-ENV:Header/>
<SOAP-ENV:Body>
    <ns2:DeleteUserResponse xmlns:ns2="http://soap.rca/classc/courses">
        <ns2:message>User with login: galkin was deleted successfully</ns2:message>
    </ns2:DeleteUserResponse>
</SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```  









