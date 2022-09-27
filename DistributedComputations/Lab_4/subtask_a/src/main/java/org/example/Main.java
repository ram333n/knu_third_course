package org.example;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Database database = new Database(PathBuilder.getPath(".", "src", "main", "java", "org", "example", "database.txt"));
        database.delete((user -> user.getFirstName().equals("Roman")));
        database.write(new User("Foman", "Aboba", "Abobich", "3312"));
    }
}