package library.management.system;

class Student 
{
    private int studentId;
    private String name;
    private String email;

    public Student(int studentId, String name, String email) 
    {
        this.studentId = studentId;
        this.name = name;
        this.email = email;
    }

    public int getStudentId() 
    {
        return studentId;
    }

    public String getName() 
    {
        return name;
    }

    @Override
    public String toString() 
    {
        return "Student ID: " + studentId + ", Name: " + name + ", Email: " + email;
    }
}
