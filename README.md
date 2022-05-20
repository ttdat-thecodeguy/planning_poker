# PLANNING PORKER BACKEND


## Web Socket Message

## API

#### auth
| Method | Url                  | Description     | Sample Body                  | Params / Files                         |
|--------|----------------------|-----------------|------------------------------|----------------------------------------|
| POST   | /api/signup          | Sign up user    | [JSON](json/user_signup.json)     |                                        | 
| POST   | /api/login           | Login user      | [JSON](json/user_login.json)      |                                        |
| POST   | /api/signup-as-guest | Signup as guest | [JSON](json/signup_as_guest.json) | tableId, (isSpectator, required=false) |
| POST   | /api/refresh-token   | refresh token   | [JSON](json/user_signup.json)     |                                        |  |

#### issue
| Method | Url                      | Description           | Sample Body                   | Params / Files                          |
|--------|--------------------------|-----------------------|-------------------------------|-----------------------------------------|
| GET    | /api/issue               | Get Issue By Table Id |                               | TableId                                 | 
| POST   | /api/issue/add           | Add Issue             | [JSON](json/user_signup.json) |                                         |
| POST   | /api/issue/import-as-csv | Import as csv         |                               | MultiPartFile, TableId, isIncludeHeader |
| POST   | /api/issue/import-as-url | Import as url         | [JSON](json/user_signup.json) |                                         |  
| DELETE | /api/delete-all          | delete all issues     | [JSON](json/user_signup.json) | tableId                                 | 

#### table
| Method | Url                     | Description         | Sample Body                          | Params / Files / Path                   |
|--------|-------------------------|---------------------|--------------------------------------|-----------------------------------------|
| GET    | /api/table/add          | Add Table           | [JSON](json/add-table.json)          | TableId                                 | 
| POST   | /api/table/{id}         | Find Table By Id    |                                      |                                         |
| POST   | /api/table/update-issue | Update Issue Active | [JSON](json/table-update-issue.json) | MultiPartFile, TableId, isIncludeHeader |
| POST   | /api/table/update-owner | Update Owner        | [JSON](json/table-update-owner.json) |                                         |  

#### user role