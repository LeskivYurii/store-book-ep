# Book Store. Spring Project

The purpose of this task is to check your knowledge and understanding in Java and Spring.

Duration: **15** hours

## Description

Your objective is to develop a "Book Store Service" following the MVC pattern.
The project structure is already set up, with essential classes waiting for implementation in their respective folders.

Your project is organized into several packages. Here's a brief overview of each:

### Packages Overview

#### `conf`

- Houses all configuration classes.

#### `controller`

- **`dto`**: Contains DTO (Data Transfer Object) files.
- **`error`**: Holds the ErrorHandler file.
- **`impl`**: Includes implementations of declared controllers.
- **`base`**: Consists of interfaces with declared methods for all controllers.

#### `model`

- Contains all model classes.

#### `service`

- `exception`: Contains custom user exception files.
- `repo`: Contains repository files.
- `impl`: Encompasses implementations of declared services.
- `base`: Includes interfaces with declared methods for all services.

The class diagram of the Domain model is shown in the figure below:

<img src="img/Diagram.png" alt="DTO" width="1000"/>

### Permissions

> For All Registered Users

- Access a list of available books.
- View detailed information about any book.
- Edit personal information and view user profile.

> For Employees

- Add, edit, or delete books from the list.
- Confirm orders placed by customers.
- Block or unblock customer accounts as necessary.
- Access a list of registered customers.

> For Customers

- Add books to the basket for purchase.
- Delete their account if desired.
- Submit orders for purchase.

### Services

> OrderService

* `getAllOrders()`
  Retrieves a list of all orders placed in the system.
* `getOrderById(id)`
  Fetches a specific order identified by its unique identifier.
* `deleteOrderById(id)`
  Deletes an order from the system based on its ID.
* `addOrder(order)`
  Adds a new order to the system, incorporating the provided order details.

> EmployeeService

* `getAllEmployees()`
  Retrieves a list of all employees registered in the system.
* `getEmployeeById(id)`
  Fetches details of a specific employee based on their unique identifier.
* `updateEmployeeById(id, employee)`
  Updates the information of an existing employee identified by their ID with the provided details.
* `deleteEmployeeById(id)`
  Removes an employee from the system based on their ID.
* `addEmployee(employee)`
  Registers a new employee in the system with the provided details.

> ClientService

* `getAllClients()`
  Retrieves a list of all clients (customers) registered in the system.
* `getClientById(id)`
  Fetches details of a specific client based on their unique identifier.
* `updateClientById(id, client)`
  Updates the information of an existing client identified by their ID with the provided details.
* `deleteClientById(id)`
  Removes a client from the system based on their ID.
* `addClient(client)`
  Registers a new client in the system with the provided details.

> BookService

* `getAllBooks()`
  Retrieves a list of all books available in the store.
* `getBookById(id)`
  Fetches details of a specific book based on its unique identifier.
* `updateBookById(id, book)`
  Updates the information of an existing book identified by its ID with the provided details.
* `deleteBookById(id)`
  Removes a book from the system based on its ID.
* `addBook(book)`
  Adds a new book to the system with the provided details.

> BookOrderService

* `getActualCart(id)`
  Retrieves the current shopping cart (order) of a specific client (customer).
* `isBooksAdded()`
  Checks if any book is already added to the client's cart.
* `addBookItemToOrder(book)`
  Adds a book to the client's shopping cart.
* `calculateOrderPrice(bookItems, operation)`
  Calculates the total price of the items in the client's shopping cart.

## Requirements

Ensure implementation of the following:

- `Spring Data JPA` for efficient data management.
-  Incorporate `Spring Security` for robust authentication and authorization.
-  Enable `Internationalization and Localization` to support English and your native languages.
-  Implement `Validation` for data integrity.
-  Establish `Error handling` for graceful error management.
-  Utilize `DTOs` - data transfer objects structured as illustrated below:

<img src="img/DTO.png" alt="DTO" width="600"/>

## Would be nice

Consider the following additional features:

- Incorporate `Logging` for comprehensive system monitoring.
- Implement `Pagination and Sorting` for enhanced data presentation.
- Utilize Other Spring technologies such as `Spring HATEOAS`, `Spring REST` or else.
- Integrate `Swagger API` for streamlined API documentation and testing.

## Recommendations

> Use wrapper classes (like Long, Integer, etc.) instead of primitive types whenever possible.

- Utilize `Lombok` for streamlined Java code.
- Employ tools like `Postman` or `Insomnia` for API testing.
- Implement `ModelMapper` for easy mapping between objects.
- Utilize `Thymeleaf` for dynamic HTML rendering.
- Explore the `test` folder to execute provided test cases for your solution.
- Refer to the `main\resources\sql` folder for SQL scripts to initialize data.

## Special message

1. Not forget to improvise and try to use different approaches while implementing your solution.
   Time is limited to 15 hours. Don't waste your time.

2. Make the most of the time available.
   While we understand you may not cover all points,
   aim to accomplish as much as possible within the given duration.

3. Enjoy the process!
