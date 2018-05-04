# Steps

- This API is versioned, all routes should be prefixed with /api/v1
- When the app first starts it will attempt to fetch all Todos in the system. Handle the request and return all the Todos.
- Look at the browser tool to see what is being requested and how and create the appropriate route
  - When a Todo is created and the save link is clicked, it will make a request to the server. Handle the request by creating a Todo and setting the proper status code.
- Look at the browser tool to see what is being requested and how and create the appropriate route
  - When a previously saved Todo is updated and the save link is clicked, it will make a request to the server. Handle the request by updating the existing Todo.
- Look at the browser tool to see what is being requested and how and create the appropriate route.
  - When a previously saved Todo is deleted and the save link is clicked, it will make a request to the server. Handle the deletion and return a blank response and the proper status code.

- Add unit tests to test your model and dao implementation
- Add functional testing to prove the API is working as expected
