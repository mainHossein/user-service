# user service
Service related to product management

base url: http://localhost:8080/api/v1/user


1-check user exists :
    
    end point : /users/check-national-id/{nationalId}

    path variables :
        national_id ->
            type : Long

    query params : null

    data : null
    
    headers : null

    example:
        request :
            curl -X GET http://localhost:8080/api/v1/user/users/check-national-id/1000000000
        response : 
            {
                "user":null,
                "metaData":{
                    "requestId":"f86ec573-1d1b-4f4f-8fb2-2afa3826d1b1",
                    "status":{
                        "statusCode":200,
                        "message":"User exists"
                    }
                }
            }

2-get user :

    end point : /users/{national_id}

    path variables :
        national_id ->
            type : Long

    query params : null

    data : null
    
    headers : null

    example:
        request :
            curl -X GET http://localhost:8080/api/v1/user/users/1000000000
        response : 
            {
                "user":{
                    "nationalId":1000000000,
                    "firstName":"John",
                    "lastName":"Smith",
                    "email":"john.smith0@example.com",
                    "phoneNumber":"+12000000000",
                    "birthDate":"1946-02-14"
                },
                "metaData":{
                    "requestId":"7b97084c-0842-4faf-96b0-0fd11ee5e296",
                    "status":{
                        "statusCode":200,
                        "message":"User found"
                    }
                }
            }

3-post user :

    end point : /users

    path variables : null

    query params : null

    data :
        {
            nationalId ->
                type : Long
            firstName ->
                type : String
            lastName ->
                type : String
            email ->
                type : String
            phoneNumber ->
                type ; String
            birthDate ->
                type ; Date(Pattern = yyyy-MM-dd)

        }
    
    headers :
        Content-Type : application/json

    example:
        request :
            curl -X POST http://localhost:8080/api/v1/user/users
            -d 
                '{
                    "nationalId" : 1,
                    "firstName" : "Ali",
                    "lastName" : "Mohammadi",
                    "email" : "aliemail@gmail.com",
                    "phoneNumber" : "+989148846217",
                    "birthDate" : "2000-05-09"
                '}
            -H 
                "Content-Type: application/json"
        response : 
            {
                "user":{
                    "nationalId":1,
                    "firstName":"Ali",
                    "lastName":"Mohammadi",
                    "email":"aliemail@gmail.com",
                    "phoneNumber":"+989148846217",
                    "birthDate":"2000-05-09"
                },
                "metaData":{
                    "requestId":"26f50740-c462-4900-8fa2-e963e5cde8f9",
                    "status":{
                        "statusCode":201,
                        "message":"User created!"
                    }
                }
            }

4-put user :

    end point : /users/{national_id}

    path variables : 
        national_id ->
            type : Long

    query params : null

    data :
        {
            firstName ->
                type : String
            lastName ->
                type : String
            email ->
                type : String
            phoneNumber ->
                type ; String
            birthDate ->
                type ; Date(Pattern = yyyy-MM-dd)
        }
    
    headers :
        Content-Type : application/json

    example:
        request :
            curl -X PUT http://localhost:8080/api/v1/user/users/1
            -d 
                '{
                    "firstName" : "Sohrab",
                    "lastName" : "Hosseini",
                    "email" : "sohrabemail@gmail.com",
                    "phoneNumber" : "+989221524693",
                    "birthDate" : "1990-12-25"
                }'
            -H 
                "Content-Type: application/json"
        response : 
            {
                "user":{
                    "nationalId":1,
                    "firstName":"Sohrab",
                    "lastName":"Hosseini",
                    "email":"sohrabemail@gmail.com",
                    "phoneNumber":"+989221524693",
                    "birthDate":"1990-12-25"
                },
                "metaData":{
                    "requestId":"841b8cc3-08a1-4278-8b92-79d64eb658a9",
                    "status":{
                        "statusCode":202,
                        "message":"Accepted!"
                    }
                }
            }


5-patch user :

    end point : /users/{national_id}

    path variables : 
        national_id ->
            type : Long

    query params : null

    data :
        {
            firstName(OPTIONAL) ->
                type : String
            lastName(OPTIONAL) ->
                type : String
            email(OPTIONAL) ->
                type : String
            phoneNumber(OPTIONAL) ->
                type ; String
            birthDate(OPTIONAL) ->
                type ; Date(Pattern = yyyy-MM-dd)
        }
    
    headers :
        Content-Type : application/json

    example:
        request :
            curl -X PATCH http://localhost:8080/api/v1/user/users/1
            -d 
                '{
                    "phoneNumber" : "+989396665842",
                    "birthDate" : "1998-11-01"
                }' 
            -H 
                "Content-Type: application/json"
        response : 
            {
                "user":{
                    "nationalId":1,
                    "firstName":"Sohrab",
                    "lastName":"Hosseini",
                    "email":"sohrabemail@gmail.com",
                    "phoneNumber":"+989396665842",
                    "birthDate":"1998-11-01"
                },
                "metaData":{
                    "requestId":"29ab35eb-d568-4a52-9a8f-bb6ff2c23b6a",
                    "status":{
                        "statusCode":202,
                        "message":"Accepted!"
                    }
                }
            }

6-delete user :

    end point : /users/{national_id}

    path variables : 
        national_id ->
            type : Long

    query params : null

    data : null
    
    headers : null

    example:
        request :
            curl -X DELETE http://localhost:8080/api/v1/user/users/1

        response : 
            {
                "user":null,
                "metaData":{
                    "requestId":"6f65a9a2-2805-47df-8492-0ced2bf5bda3",
                    "status":{
                        "statusCode":200,
                        "message":"Ok!"
                    }
                }
            } 