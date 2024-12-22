package library.management.system;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

class LMS extends Frame implements ActionListener {
    private Library library;

    // GUI components
    private Button addBookButton, addStudentButton, displayBooksButton, displayStudentsButton,
            borrowBookButton, returnBookButton, exitButton;
    private TextField inputField1, inputField2, inputField3;
    private Label infoLabel;

    public LMS(String url, String user, String password) throws SQLException {
        this.library = new Library(url, user, password);

        // Setting up the Frame
        setTitle("Library Management System");
        setSize(400, 400);
        setLayout(new GridLayout(8, 1));

        // Creating Buttons
        addBookButton = new Button("Add Book");
        addStudentButton = new Button("Add Student");
        displayBooksButton = new Button("Display Books");
        displayStudentsButton = new Button("Display Students");
        borrowBookButton = new Button("Borrow Book");
        returnBookButton = new Button("Return Book");
        exitButton = new Button("Exit");

        // Adding Buttons to Frame
        add(addBookButton);
        add(addStudentButton);
        add(displayBooksButton);
        add(displayStudentsButton);
        add(borrowBookButton);
        add(returnBookButton);
        add(exitButton);

        // Adding Action Listeners
        addBookButton.addActionListener(this);
        addStudentButton.addActionListener(this);
        displayBooksButton.addActionListener(this);
        displayStudentsButton.addActionListener(this);
        borrowBookButton.addActionListener(this);
        returnBookButton.addActionListener(this);
        exitButton.addActionListener(this);

        // Close window on exit
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Button source = (Button) e.getSource();

        if (source == addBookButton) {
            addBookGUI();
        } else if (source == addStudentButton) {
            addStudentGUI();
        } else if (source == displayBooksButton) {
            try {
                library.displayBooks();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else if (source == displayStudentsButton) {
            try {
                library.displayStudents();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else if (source == borrowBookButton) {
            borrowBookGUI();
        } else if (source == returnBookButton) {
            returnBookGUI();
        } else if (source == exitButton) {
            System.exit(0);
        }
    }

    private void addBookGUI() {
        Frame addBookFrame = new Frame("Add Book");
        addBookFrame.setSize(300, 200);
        addBookFrame.setLayout(new GridLayout(4, 2));

        Label titleLabel = new Label("Title:");
        Label authorLabel = new Label("Author:");
        Label isbnLabel = new Label("ISBN:");

        TextField titleField = new TextField();
        TextField authorField = new TextField();
        TextField isbnField = new TextField();

        Button submitButton = new Button("Submit");

        addBookFrame.add(titleLabel);
        addBookFrame.add(titleField);
        addBookFrame.add(authorLabel);
        addBookFrame.add(authorField);
        addBookFrame.add(isbnLabel);
        addBookFrame.add(isbnField);
        addBookFrame.add(submitButton);

        submitButton.addActionListener(event -> {
            try {
                library.addBook(titleField.getText(), authorField.getText(), isbnField.getText());
                addBookFrame.dispose();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        addBookFrame.setVisible(true);

        addBookFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                addBookFrame.dispose();
            }
        });
    }

    private void addStudentGUI() {
        Frame addStudentFrame = new Frame("Add Student");
    addStudentFrame.setSize(300, 200);
    addStudentFrame.setLayout(new GridLayout(3, 2));

    Label nameLabel = new Label("Name:");
    Label emailLabel = new Label("Email:");

    TextField nameField = new TextField();
    TextField emailField = new TextField();

    Button submitButton = new Button("Submit");

    addStudentFrame.add(nameLabel);
    addStudentFrame.add(nameField);
    addStudentFrame.add(emailLabel);
    addStudentFrame.add(emailField);
    addStudentFrame.add(submitButton);

    submitButton.addActionListener(event -> {
        try {
            library.addStudent(nameField.getText(), emailField.getText());
            addStudentFrame.dispose();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    });

    addStudentFrame.setVisible(true);

    addStudentFrame.addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
            addStudentFrame.dispose();
        }
    });
    }

    private void borrowBookGUI() {
        Frame borrowBookFrame = new Frame("Borrow Book");
    borrowBookFrame.setSize(300, 200);
    borrowBookFrame.setLayout(new GridLayout(3, 2));

    Label isbnLabel = new Label("ISBN:");
    Label studentIdLabel = new Label("Student ID:");

    TextField isbnField = new TextField();
    TextField studentIdField = new TextField();

    Button submitButton = new Button("Borrow");

    borrowBookFrame.add(isbnLabel);
    borrowBookFrame.add(isbnField);
    borrowBookFrame.add(studentIdLabel);
    borrowBookFrame.add(studentIdField);
    borrowBookFrame.add(submitButton);

    submitButton.addActionListener(event -> {
        try {
            int studentId = Integer.parseInt(studentIdField.getText());
            library.borrowBook(isbnField.getText(), studentId);
            borrowBookFrame.dispose();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
    });

    borrowBookFrame.setVisible(true);

    borrowBookFrame.addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
            borrowBookFrame.dispose();
        }
    });
    }

    private void returnBookGUI() {
        Frame returnBookFrame = new Frame("Return Book");
    returnBookFrame.setSize(300, 200);
    returnBookFrame.setLayout(new GridLayout(3, 2));

    Label isbnLabel = new Label("ISBN:");
    Label studentIdLabel = new Label("Student ID:");

    TextField isbnField = new TextField();
    TextField studentIdField = new TextField();

    Button submitButton = new Button("Return");

    returnBookFrame.add(isbnLabel);
    returnBookFrame.add(isbnField);
    returnBookFrame.add(studentIdLabel);
    returnBookFrame.add(studentIdField);
    returnBookFrame.add(submitButton);

    submitButton.addActionListener(event -> {
        try {
            int studentId = Integer.parseInt(studentIdField.getText());
            library.returnBook(isbnField.getText(), studentId);
            returnBookFrame.dispose();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
    });

    returnBookFrame.setVisible(true);

    returnBookFrame.addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
            returnBookFrame.dispose();
        }
    });
    }

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/LMS?user=root";
        String user = "root";
        String password = "493060599";

        try {
            new LMS(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
