# Recipes
Spring project - simple spring framework based program that implements storing in database some info for users. Database build on Hibernate with embedded h2 driver.

## Available endpoints for every user
### POST /api/register
receives a JSON object with two fields: email (string), and password (string). If a user with a specified email does not exist, the program saves (registers) the user in a database and responds with 200 (Ok). If a user is already in the database, respond with the 400 (Bad Request) status code.

## Available endpoints for registered users
Authentication works with spring security module
### POST /api/recipe/new
receives a recipe as a JSON object and returns a JSON object with one id field;
### GET /api/recipe/{id}
returns a recipe with a specified id as a JSON object;
### DELETE /api/recipe/{id}
deletes a recipe with a specified id. The recipe is deleted if only the request is sent by the creator of the recipe (who sent POST /api/recipe/new request for this recipe).
### PUT /api/recipe/{id}  
receives a recipe as a JSON object and updates a recipe with a specified id. If a recipe with a specified id does not exist, the server return 404 (Not found). If recipe is updated, server return the 204 (No Content) status code. The recipe is updated if only the request is sent by the creator of the recipe (who sent POST /api/recipe/new request for this recipe).
### GET /api/recipe/search 
takes one of the two mutually exclusive query parameters:
#### category 
if this parameter is specified, it returns a JSON array of all recipes of the specified category. Search is case-insensitive, sort the recipes by date (newer first);
#### name 
if this parameter is specified, it returns a JSON array of all recipes with the names that contain the specified parameter. Search is case-insensitive, sort the recipes by date (newer first).
