# Library Management System (LMS)

The **Library Management System (LMS)** is a console-based application built in Java that provides features to manage users, books, and loan transactions. The system allows librarians to add, remove, update, and search for users and books. It also handles book loans and returns, ensuring efficient management of library resources.

## Features

- **User Management**:
  - Add, remove, update, and search users.
  - User types: Student, Faculty, Public Member.
  - Retrieve detailed information of individual users or display all users.

- **Book Management**:
  - Add, remove, update, and search books.
  - Book types: Textbook, Novel, Reference Book.
  - Retrieve detailed information of individual books or display all books.

- **Loan and Return System**:
  - Loan books to users and track loan status.
  - Return books and update availability.

## Technologies Used

- **Java**: Core programming language used for the entire system.
- **PostgreSQL**: Database used for storing user and book information (optional, depending on the selected storage method).
- **File Handling**: Optionally store data using local files.
- **SQL**: For database operations (when using the database storage option).
- **Object-Oriented Design**: Built using principles of OOP to manage system entities like `User` and `Book`.

## Usage Instructions

1. **Run the Application**: After compiling the program, the user will be presented with a menu that offers different operations such as adding users, removing books, or searching records.

2. **Choose Storage Method**: Upon starting the application, users can choose between using a **Database** or **File-Based** storage:
   - Database: PostgreSQL is used to persist data.
   - File-Based: Local files are used to store user and book information.

3. **User Management**:
   - Select the appropriate option to add, remove, update, or search for users.
   - You can search for users by ID, name, or type.

4. **Book Management**:
   - Manage books by adding, removing, updating, or searching for records.
   - Search books by different attributes like title, author, or genre.

5. **Loan and Return**:
   - Loan books to users by specifying the book and user ID.
   - Return books when done, and the system will update the loan status.

## Setup Instructions

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/Abdulrehman-Baloch/Library-Management-System.git
   ```
2. **Open in Eclipse**:
   - Import the project into Eclipse as an existing Maven/Java project.
   
3. **Configure Database** (if using PostgreSQL):
   - Set up the PostgreSQL database and create the required tables for users and books.
   - Update the database connection settings (URL, username, password) in the project code.

4. **Run the Application**:
   - After setting up the environment, run the `main()` method in the `LibrarySystem` class to start the application.

## Database Schema

### Users Table
```sql
CREATE TABLE USERS (
  user_id VARCHAR(50) PRIMARY KEY,
  name VARCHAR(100),
  email VARCHAR(100),
  phone VARCHAR(20),
  address VARCHAR(200),
  user_type VARCHAR(20)
);
```

### Books Table
```sql
CREATE TABLE BOOKS (
  book_id VARCHAR(50) PRIMARY KEY,
  title VARCHAR(100),
  author VARCHAR(100),
  isbn VARCHAR(20),
  publication_year INT,
  genre VARCHAR(50),
  loan_status BOOLEAN DEFAULT FALSE,
  base_loan_fee DOUBLE PRECISION,
  book_type VARCHAR(20)
);
```

## Future Enhancements

- **Graphical User Interface (GUI)**: Convert the current console-based system into a GUI-based application for improved usability.
- **Report Generation**: Add a feature to generate reports for overdue books, active loans, etc.
- **Email Notifications**: Notify users of due dates or overdue loans via email.

## Contribution

Feel free to contribute to this project by opening issues or submitting pull requests. Contributions are welcome to improve the functionality or optimize the existing code.

## License

This project is licensed under the MIT License.

---

Let me know if you want any changes or additional sections!
