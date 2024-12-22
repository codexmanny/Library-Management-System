package library.management.system;

import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class Library 
{
    private Connection connection;

    public Library(String url, String user, String password) throws SQLException 
    {
        this.connection = DriverManager.getConnection(url, user, password);
    }

    public void addBook(String title, String author, String isbn) throws SQLException 
    {
        String sql = "INSERT INTO Books (Title, Author, ISBN) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) 
        {
            statement.setString(1, title);
            statement.setString(2, author);
            statement.setString(3, isbn);
            statement.executeUpdate();
            System.out.println("Book '" + title + "' added to the library.");
        }
    }

    public void addStudent(String name, String email) throws SQLException 
    {
        String sql = "INSERT INTO Students (Name, Email) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) 
        {
            statement.setString(1, name);
            statement.setString(2, email);
            statement.executeUpdate();
            System.out.println("Student '" + name + "' added to the library.");
        }
    }

    public void displayStudents() throws SQLException {
    String sql = "SELECT * FROM Students";
    try (Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(sql)) {

        // Create Frame
        Frame frame = new Frame("Students in Library");
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        Panel panel = new Panel();
        panel.setLayout(new GridLayout(0, 3)); // 3 columns: StudentID, Name, Email

        // Add column headers
        panel.add(new Label("StudentID"));
        panel.add(new Label("Name"));
        panel.add(new Label("Email"));

        // Add data rows
        if (!resultSet.next()) {
            Label noDataLabel = new Label("No students registered in the library.");
            frame.add(noDataLabel, BorderLayout.CENTER);
        } else {
            do {
                panel.add(new Label(String.valueOf(resultSet.getInt("StudentID"))));
                panel.add(new Label(resultSet.getString("Name")));
                panel.add(new Label(resultSet.getString("Email")));
            } while (resultSet.next());
        }

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.add(panel);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Add Close button
        Button closeButton = new Button("Close");
        closeButton.addActionListener(e -> frame.dispose());
        frame.add(closeButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}



    public void displayBooks() throws SQLException {
    String sql = "SELECT * FROM Books";
    try (Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(sql)) {

        // Create Frame
        Frame frame = new Frame("Books in Library");
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        Panel panel = new Panel();
        panel.setLayout(new GridLayout(0, 5)); // 5 columns: BookID, Title, Author, ISBN, Availability

        // Add column headers
        panel.add(new Label("BookID"));
        panel.add(new Label("Title"));
        panel.add(new Label("Author"));
        panel.add(new Label("ISBN"));
        panel.add(new Label("Available"));

        // Add data rows
        if (!resultSet.next()) {
            Label noDataLabel = new Label("No books available in the library.");
            frame.add(noDataLabel, BorderLayout.CENTER);
        } else {
            do {
                panel.add(new Label(String.valueOf(resultSet.getInt("BookID"))));
                panel.add(new Label(resultSet.getString("Title")));
                panel.add(new Label(resultSet.getString("Author")));
                panel.add(new Label(resultSet.getString("ISBN")));
                panel.add(new Label(resultSet.getBoolean("Available") ? "Yes" : "No"));
            } while (resultSet.next());
        }

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.add(panel);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Add Close button
        Button closeButton = new Button("Close");
        closeButton.addActionListener(e -> frame.dispose());
        frame.add(closeButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}



    public void borrowBook(String isbn, int studentId) throws SQLException 
    {
        String sql = "SELECT * FROM Books WHERE ISBN = ? AND Available = TRUE";
        try (PreparedStatement statement = connection.prepareStatement(sql)) 
        {
            statement.setString(1, isbn);
            try (ResultSet resultSet = statement.executeQuery()) 
            {
                if (resultSet.next()) 
                {
                    int bookId = resultSet.getInt("BookID");
                    String borrowSql = "INSERT INTO BorrowedBooks (BookID, StudentID, BorrowDate) VALUES (?, ?, CURDATE())";
                    try (PreparedStatement borrowStatement = connection.prepareStatement(borrowSql)) 
                    {
                        borrowStatement.setInt(1, bookId);
                        borrowStatement.setInt(2, studentId);
                        borrowStatement.executeUpdate();

                        String updateSql = "UPDATE Books SET Available = FALSE WHERE BookID = ?";
                        try (PreparedStatement updateStatement = connection.prepareStatement(updateSql)) 
                        {
                            updateStatement.setInt(1, bookId);
                            updateStatement.executeUpdate();
                            System.out.println("Book '" + resultSet.getString("Title") + "' borrowed successfully.");
                        }
                    }
                } 
                else 
                {
                    System.out.println("Book not available for borrowing.");
                }
            }
        }
    }

    public void returnBook(String isbn, int studentId) throws SQLException 
    {
        String sql = "SELECT * FROM BorrowedBooks WHERE BookID = (SELECT BookID FROM Books WHERE ISBN = ?) AND StudentID = ? AND ReturnDate IS NULL";
        try (PreparedStatement statement = connection.prepareStatement(sql)) 
        {
            statement.setString(1, isbn);
            statement.setInt(2, studentId);
            try (ResultSet resultSet = statement.executeQuery()) 
            {
                if (resultSet.next()) 
                {
                    int borrowId = resultSet.getInt("BorrowID");
                    String returnSql = "UPDATE BorrowedBooks SET ReturnDate = CURDATE() WHERE BorrowID = ?";
                    try (PreparedStatement returnStatement = connection.prepareStatement(returnSql)) 
                    {
                        returnStatement.setInt(1, borrowId);
                        returnStatement.executeUpdate();

                        String updateSql = "UPDATE Books SET Available = TRUE WHERE ISBN = ?";
                        try (PreparedStatement updateStatement = connection.prepareStatement(updateSql)) 
                        {
                            updateStatement.setString(1, isbn);
                            updateStatement.executeUpdate();
                            System.out.println("Book returned successfully.");
                        }
                    }
                } 
                else 
                {
                    System.out.println("Book not found or already returned.");
                }
            }
        }
    }

    public void close() throws SQLException 
    {
        if (connection != null) 
        {
            connection.close();
        }
    }
}
