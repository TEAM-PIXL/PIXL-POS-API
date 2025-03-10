
# PIXL POS API Service

## Endpoints

| **Endpoint**                   | **Parameters**                            | **Returns**      | **Purpose**                         |
|--------------------------------|-------------------------------------------|------------------|-------------------------------------|
| GET /api/v1                    | None                                      | Map              | Stat whether the database is alive. |
| POST /api/v1/user              | @RequestBody UserDTO                      | Map              | To create a user in database.       |
| GET /api/v1/user               | None                                      | Collection<User> | To get all users in database.       |
| GET /api/v1/user/{username}    | @PathVariable String                      | User             | Gets specific user from username.   |
| PUT /api/v1/user/{username}    | @PathVariable String @RequestBody UserDTO | Map              | Allows for an update to a user.     |
| PATCH /api/v1/user/{username}  | @PathVariable String @RequestBody UserDTO | Map              | Allows for a patch of a user.       |
| DELETE /api/v1/user/{username} | @PathVariable String                      | Map              | Delete a specific user.             |
| DELETE /api/v1/user            | None                                      | Map              | Deletes all users in database.      |
