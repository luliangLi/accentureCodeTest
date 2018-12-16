# Accenture Code Test
Before everything, the following stuffs are only tested on a 32-bit 2GB memory window7 laptop, in case you encounter any issues on a linux or mac, please contact me by:
* babyinair@hotmail.com

### Overall 
Structure:
![image](https://github.com/luliangLi/accentureCodeTest/blob/master/Design1.png)

The APIs contains the following endpoints and parameters
* /login
* /web/admin/create?isAdmin={true/false} -d "{User Profile instance}"
* /web/admin/update?id={id} -d "{User Profile instance}"
* /web/admin/delete/{id}
* /web/admin/query/{id}
* /web/admin/grant?id={id}&isAdmin={true/false}
* /web/user/query
* /web/user/delete
* /app/admin/v1/create?isAdmin={true/false} -d "{User Profile instance}"
* /app/admin/v1/update?id={id} -d "{User Profile instance}"
* /app/admin/v1/delete/{id}
* /app/admin/v1/query/{id}
* /app/admin/v1/grant?id={id}&isAdmin={true/false}
* /app/user/v1/query
* /app/user/v1/grant

The response of all the above APIs is a ResultVO with the structure:

```
{
    isSuccess: True/False // Tell if the service works 
    messages : [] // if services works, the messages list will be blank, otherwise the exceptions will be added into the list.
    obj:{} // User Instance
}
```

### Installation

#### Preparation
* Jdk 1.8
* Maven 3

#### Install and Run
* Clone the project to your local
* Go to the root dir and run `mvn clean install`
* Run `java -jar target/user-access-0.0.1-SNAPSHOT.jar`
* If it goes well, you should see the following log:

```
2018-12-15 12:37:17.937  INFO 7992 --- [           main] yConfig$$EnhancerBySpringCGLIB$$15ceb10c : The admin is User [id=673, permission=0, up=null]
2018-12-15 12:37:17.937  INFO 7992 --- [           main] yConfig$$EnhancerBySpringCGLIB$$15ceb10c : The user is User [id=674, permission=1, up=null]
2018-12-15 12:37:17.989  INFO 7992 --- [           main] o.s.s.web.DefaultSecurityFilterChain     : Creating filter chain: any request, [org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter@83443b, org.springframework.security.web.context.SecurityContextPersistenceFilter@16fa167, org.springframework.security.web.header.HeaderWriterFilter@1a0bbc7, org.springframework.web.filter.CorsFilter@c18217, org.springframework.security.web.authentication.logout.LogoutFilter@15e94f2, com.accenture.test.security.JwtLoginFilter@13c5b46, com.accenture.test.security.JwtAuthenticationFilter@129a757, org.springframework.security.web.savedrequest.RequestCacheAwareFilter@14f9638, org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter@1e93130, org.springframework.security.web.authentication.AnonymousAuthenticationFilter@19b8115, org.springframework.security.web.session.SessionManagementFilter@61da52, org.springframework.security.web.access.ExceptionTranslationFilter@11c288a, org.springframework.security.web.access.intercept.FilterSecurityInterceptor@305270]
2018-12-15 12:37:18.150  INFO 7992 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 80 (http) with context path ''
2018-12-15 12:37:18.154  INFO 7992 --- [           main] c.accenture.test.UserAccessApplication   : Started UserAccessApplication in 8.537 seconds (JVM running for 13.546)
```
By default it will insert 2 users into H2 db, one is admin role and the other is user role which is easy for testing.

```
The admin is User [id=673, permission=0, up=null]
The user is User [id=674, permission=1, up=null]
```
Remember the id of admin, then we can use it in the following sections.

### Usage

First of all, use the following to login admin user, the default password of all the accounts is 123456.

```
>curl -i -H "Content-Type: application/json" -X POST -d "{\"id\": \"0\", \"password\": \"123456\"}" http://localhost:80/login
```
Then we should get the response with jwt bearer like below, each time you login a user, a new jwt bearer will be generated. In the application the expiration of the bearer is 24h.

```
HTTP/1.1 200
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIzODUiLCJleHAiOjE1NDQ5MTMyNzh9.iSB8xNQoaUVrnhZVQnGcMXymMjzc13jw1mgWddT6uOLr-OXLMEhD228UqUQMGNfNulyMn_k3TfT4Qit9Lj4h2w
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 0
Date: Fri, 14 Dec 2018 22:34:39 GMT
```
To test if the bearer is working or not.

```
>curl -H "Content-Type: application/json" -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIwIiwiZXhwIjoxNTQ0ODgxMzM1fQ.IpfcnqE9PXoL_OEUZTslK-0jNu7pjQg-rXvxznt7LpnBzfTDj-Y8eQ31AR6_T66Jy-XOdw1jCx37IxC0SWBiQ" http://localhost:80/hello
```
#### Mobile app APIs
##### Admin Actions

Create, which is a post with json format data to transfer user profile information, sample as below

```
>curl -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0NDkiLCJleHAiOjE1NDQ5MTY2MzJ9.C8abFrbRojg73s2RbKJb2vNz3ElZESuuCiWon-Ka6Bcg4f9QFmJXNQWSaBj4JuP234NPhpHe9wMcv_eGcqzR4g" -H "Content-Type: application/json" -X POST -d "{\"firstName\":\"Luliang\", \"lastName\":\"Li\", \"birthDate\":\"04-08-1983\"}"  "http://localhost:80/app/admin/v1/create?isAdmin=false"
```
Response as below, the id of ojb is the userid which can be used to login or query later.

```
{"message":[],"ojb":{"id":450,"username":null,"password":"123456","permission":1
,"up":{"id":451,"user":null,"firstName":"Luliang","lastName":"Li","birthDate":"0
4-08-1983","address":null}},"success":true}
```

Query, receive useid as the pathVariable, in the example userid=450

``` 
>curl -H "Authorization: Bearer  eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0MTc
iLCJleHAiOjE1NDQ5MTQ3MzZ9.IIEGxxkTZLuevLsgXfbREViOYKg4Xnvs0CazDM_Orv9G8TOfik_Evb
cRN32RTaAOXiTTQXiHRsjgroa61tJ1pA" -H "Content-Type: application/json" "http://lo
calhost:80/app/admin/v1/query/450" 
```

Update 

```
>curl -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0NDki
LCJleHAiOjE1NDQ5MTY2MzJ9.C8abFrbRojg73s2RbKJb2vNz3ElZESuuCiWon-Ka6Bcg4f9QFmJXNQW
SaBj4JuP234NPhpHe9wMcv_eGcqzR4g" -H "Content-Type: application/json" -X POST -d
"{\"firstName\":\"Lu\", \"lastName\":\"Li\", \"birthDate\":\"04-08-1983\"}" "htt
p://localhost:80/app/admin/v1/update?id=450"
```
Response:

```
{"message":[],"ojb":{"id":450,"username":null,"password":"123456","permission":1
,"up":{"id":452,"user":null,"firstName":"Lu","lastName":"Li","birthDate":"04-08-
1983","address":null}},"success":true}
```
Change the role

```
>curl -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0NDki
LCJleHAiOjE1NDQ5MTY2MzJ9.C8abFrbRojg73s2RbKJb2vNz3ElZESuuCiWon-Ka6Bcg4f9QFmJXNQW
SaBj4JuP234NPhpHe9wMcv_eGcqzR4g" -H "Content-Type: application/json" "http://loc
alhost:80/app/admin/v1/grant?id=453&isAdmin=true"
```
Response: note the permission field has become 0 which represent admin role

```
{"message":[],"ojb":{"id":453,"username":null,"password":"123456","permission":0
,"up":{"id":454,"user":null,"firstName":"Some","lastName":"one","birthDate":"04-
08-1983","address":null}},"success":true}
```

Delete

```
>curl -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0NDkiLCJleHAiOjE1NDQ5MTY2MzJ9.C8abFrbRojg73s2RbKJb2vNz3ElZESuuCiWon-Ka6Bcg4f9QFmJXNQWSaBj4JuP234NPhpHe9wMcv_eGcqzR4g" -H "Content-Type: application/json" "http://localhost:80/app/admin/v1/delete/453"
```
Response

```
{"message":[],"ojb":null,"success":true}
```

##### User Actions
-- First login with User role, for the following example, ther userid is 482
-- Query with admin APIs to query info of user 481, will get 403

```
>curl -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0ODIiLCJleHAiOjE1NDQ5MjAyMjJ9.F-ldqrbvaC5jR50lJ0leQGH6R6d4wv8PqpmD1ohwp1jokkAGDgVO89RDxODDD9XssCrkXWJ4U76Oi4EDvYovgg" -H "Content-Type: application/json" "http://localhost:80/app/admin/v1/query/481"

```
Response:

```
{"timestamp":"2018-12-15T00:31:03.918+0000","status":403,"error":"Forbidden","message":"Access Denied","path":"/app/admin/v1/query/481"}
```

-- Query with User APIs,  will get the information of the login user, and the API do NOT receive a userid as the parameter

```
>curl -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2NDIiLCJleHAiOjE1NDQ5MjM5MDZ9.Jtnpxz_FebpnvQCJDGisYcYcFseMQ4KeOgiBnxIEPPvjqCpR5chE74jXGppRz-IzLIJBCXuk6tCgPZooUw0ZiQ" -H "Content-Type: application/json" "http://localhost:80/app/user/v1/query"
```
Response

```
{"message":[],"ojb":{"id":642,"username":"user","password":"$2a$10$ebbVLxr5Fy4pgqSHXQ0CzO7972xSHbNdKn15Drtuyd1FeVHNNOaQu","permission":1,"up":null},"success":true}
```
-- Delete with User APIs, same as the query, do NOT receive a userid as parameter since user can only delete themselves.

```
>curl -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2NzQiLCJleHAiOjE1NDQ5MjQyNTN9.4dbNHdX2YQL_JLgrvtagnMxo2uBnfw7f9RwKeQ2KvK04vhwySpRziG9WusYOF8u-C-PffJn8e-50ZJGDWpYCnQ" -H "Content-Type: application/json" "http://localhost:80/app/user/v1/delete"
```
Response:

```
{"message":[],"ojb":null,"success":true}
```

#### Web APIs
- The parameters and APIs are similar to the APIs of Mobile ones, please refer to the examples in the previous section.

### Time consuming
- setup dev environment : 1.5h
- functions implementation : 2h
- functional testing : 3h
- JWT involved: 6h
- SWAGGER : 1h
- documentations : 1h

### challenges
- not familiar with JWT, took 6 hours to learn, implement and debug
- curl post --data with '' not work on windows, need to use \", took 1 hours
