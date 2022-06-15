package com.example.test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.sql.*;
import java.time.LocalDate;

public class HelloApplication extends Application {
    Label insert_label = new Label("Insert Record");
    Label del_label = new Label("Delete Record");
    Label update_label = new Label("Update Record");
    Label display_label = new Label("Display Record");
    Button insert_btn = new Button("Insert");
    Button delete_btn = new Button("Delete");
    Button update_btn = new Button("Update");
    Button display_btn = new Button("Display");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage Stage1) {
        GridPane root = new GridPane();
        root.onScrollProperty();
        root.add(insert_label, 0, 0);
        root.add(del_label, 0, 1);
        root.add(update_label, 0, 2);
        root.add(insert_btn, 1, 0);
        root.add(delete_btn, 1, 1);
        root.add(update_btn, 1, 2);
        root.add(display_label, 0, 3);
        root.add(display_btn, 1, 3);
        Scene sc = new Scene(root);
        Stage1.setScene(sc);
        Stage1.setWidth(370);
        Stage1.setHeight(310);
        Stage1.setTitle("Internship Form");
        Stage1.show();
        insert_btn.setOnAction(event -> {
            Stage InsStage = new Stage();
            registrationForm form1 = new registrationForm();
            form1.showForm1(InsStage);
        });
        update_btn.setOnAction(event -> {
            Stage UpdStage = new Stage();
            registrationForm form3 = new registrationForm();
            form3.showForm2(UpdStage);
        });
        delete_btn.setOnAction(event -> {
            Stage DelStage = new Stage();
            registrationForm form2 = new registrationForm();
            form2.showForm3(DelStage);
        });
        display_btn.setOnAction(event -> {
            Stage disStage = new Stage();
            registrationForm form4 = new registrationForm();
            form4.showForm4(disStage);
        });
    }
}

class registrationForm {
    String post = "";
    Label name_label;
    TextField name_text;
    Label pass_label;

    PasswordField pass_text;
    Label gender_label;
    RadioButton opt1;
    RadioButton opt2;
    Label subject_label;
    CheckBox chk1;
    CheckBox chk2;
    CheckBox chk3;
    Label references_label;
    ComboBox<String> references;
    Button submit;
    Label error_label;
    Label salary;
    DatePicker date;
    TextArea area;
    Label update_name;
    Label update_refer;
    TextField new_name;
    TextField new_refer;
    Button update_btn;
    Label delete_name;
    TextField del_name;
    Button delete_btn;

    public void showForm1(Stage primaryStage) {
        name_label = new Label("Enter your Name");
        name_text = new TextField();
        pass_label = new Label("Enter your Password");
        pass_text = new PasswordField();
        gender_label = new Label("Select your Gender");
        ToggleGroup group = new ToggleGroup();
        opt1 = new RadioButton("Male");
        opt2 = new RadioButton("Female");
        opt1.setToggleGroup(group);
        opt2.setToggleGroup(group);
        subject_label = new Label("Select your post: ");
        chk1 = new CheckBox("Manager ");
        chk2 = new CheckBox("Secretary");
        chk3 = new CheckBox("HR");
        references_label = new Label("Select your references");
        references = new ComboBox<>();

        references.getItems().add("Social Media");
        references.getItems().add("Website");
        references.getItems().add("Friend");
        date = new DatePicker();
        area = new TextArea();
        Label dob_label = new Label("Enter your Date of Birth: ");
        area.setMaxHeight(10);
        Label addr_label = new Label("Enter your Address");
        area.setMaxWidth(150);
        salary = new Label("Your monthly income: ");
        ScrollBar scroll = new ScrollBar();
        scroll.setMin(0);
        scroll.setMax(200000);
        scroll.setValue(25000);
        submit = new Button("Submit");
        error_label = new Label();
        submit.setOnAction(event -> {
            String gender;
            boolean connect_database = validate_form();
            try {
                if (connect_database) {
                    if (opt1.isSelected()) {
                        gender = opt1.getText();
                    } else {
                        gender = opt2.getText();
                    }
                    if (chk1.isSelected()) {
                        System.out.println(chk1.getText());
                        post += chk1.getText() + " ";
                    } else if (chk2.isSelected()) {
                        post += chk2.getText() + " ";
                    } else if (chk3.isSelected()) {
                        post += chk3.getText();
                    }
                    Intern s = new Intern(name_text.getText(), pass_text.getText(), gender, post, date.getValue(),
                            area.getText(), references.getValue(),
                            scroll.getValue());
                    try {
                        s.insertInternRecord();
                        error_label.setTextFill(Color.GREEN);
                        error_label.setText("Record inserted successfully");
                    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException
                            | SQLException e) {
                        error_label.setTextFill(Color.RED);
                        e.printStackTrace();
                        error_label.setText(e.getMessage());
                    }
                } else {
                    error_label.setTextFill(Color.RED);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
        GridPane root = new GridPane();
        root.onScrollProperty();
        root.add(name_label, 0, 1);
        root.add(name_text, 1, 1);
        root.add(pass_label, 0, 2);
        root.add(pass_text, 1, 2);
        root.add(gender_label, 1, 3);
        root.add(opt1, 1, 4);
        root.add(opt2, 1, 5);
        root.add(subject_label, 0, 3);
        root.add(chk1, 0, 4);
        root.add(chk2, 0, 5);
        root.add(chk3, 0, 6);
        root.add(references_label, 0, 7);
        root.add(references, 1, 7);
        root.add(submit, 1, 11);
        root.add(error_label, 0, 17);
        root.add(dob_label, 0, 8);
        root.add(addr_label, 0, 9);
        root.add(date, 1, 8);
        root.add(area, 1, 9);
        root.add(salary, 0, 10);
        root.add(scroll, 1, 10);
        Scene sc = new Scene(root);

        primaryStage.setScene(sc);
        primaryStage.setWidth(370);
        primaryStage.setHeight(310);
        primaryStage.setTitle("Login Page");
        primaryStage.show();
    }

    public boolean validate_form() {
        boolean proceed = true;
        if (name_text.getText().isEmpty()) {
            error_label.setText("Please Enter the Name");
            proceed = false;
        } else if (pass_text.getText().isEmpty()) {
            error_label.setText("Please Enter the Password");
            proceed = false;
        } else if (!opt1.isSelected() && !opt2.isSelected()) {
            error_label.setText("Select your Gender");
            proceed = false;
        } else if (!chk1.isSelected() && !chk2.isSelected() &&
                !chk3.isSelected()) {
            error_label.setText("Select your post");
            proceed = false;
        } else if (references.getValue() == null) {
            error_label.setText("Select your references");
            proceed = false;
        } else if (date.getValue() == null) {
            error_label.setText("Select yor Date of birth: ");
            proceed = false;
        } else if (area.getText().isEmpty()) {
            error_label.setText("Please Enter the Address: ");
            proceed = false;
        }
        return proceed;
    }

    public void showForm2(Stage updStage) {
        update_name = new Label("Enter your Name");
        new_name = new TextField();
        update_refer = new Label("Update your reference");
        new_refer = new TextField();
        update_btn = new Button("Update");

        Label error = new Label();
        update_btn.setOnAction(event -> {
            Intern s = new Intern(new_name.getText(), new_refer.getText());
            try {
                s.updateInternRecord();
                error.setTextFill(Color.GREEN);
                error.setText("Record Updated successfully");
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
                error.setTextFill(Color.RED);
                error.setText("Error");
                e.printStackTrace();
                error_label.setText(e.getMessage());
            }
        });
        GridPane root = new GridPane();
        root.onScrollProperty();
        root.add(update_name, 0, 0);
        root.add(new_name, 1, 0);
        root.add(update_refer, 0, 1);
        root.add(new_refer, 1, 1);
        root.add(update_btn, 0, 2);
        Scene sc = new Scene(root);
        updStage.setScene(sc);
        updStage.setWidth(370);
        updStage.setHeight(310);
        updStage.setTitle("Update Record");
        updStage.show();
    }

    public void showForm3(Stage delStage) {
        delete_name = new Label("Enter your Name");
        del_name = new TextField();
        delete_btn = new Button("Delete");
        Label error = new Label();
        delete_btn.setOnAction(event -> {
            Intern s = new Intern(del_name.getText());
            try {
                s.deleteInternRecord();
                error.setTextFill(Color.GREEN);

                error.setText("Record Deleted successfully");
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
                error.setTextFill(Color.RED);
                error.setText("Error");
                e.printStackTrace();
                error_label.setText(e.getMessage());
            }
        });
        GridPane root = new GridPane();
        root.onScrollProperty();
        root.add(delete_name, 0, 1);
        root.add(del_name, 1, 1);
        root.add(delete_btn, 0, 2);
        Scene sc = new Scene(root);
        delStage.setScene(sc);
        delStage.setWidth(370);
        delStage.setHeight(310);
        delStage.setTitle("Delete Record");
        delStage.show();
    }

    public void showForm4(Stage disStage) {
        Label error = new Label();
        Label message = new Label("Records Displayed on the console");
        Intern s = new Intern();
        try {
            s.displayInternRecord();
            error.setTextFill(Color.GREEN);
            error.setText(" successfully");
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
            error.setTextFill(Color.RED);
            error.setText(" Unsuccessfully");
            e.printStackTrace();
            error_label.setText(e.getMessage());
        }
        GridPane root = new GridPane();
        root.onScrollProperty();
        root.add(error, 1, 0);

        root.add(message, 0, 0);
        Scene sc = new Scene(root);
        disStage.setScene(sc);
        disStage.setWidth(370);
        disStage.setHeight(310);
        disStage.setTitle("Display Records");
        disStage.show();
    }
}

class Intern {
    String post;
    String name;
    String pass;
    String gender;
    String references;
    String date;
    String area;
    int salary;
    String del, update1, update2;

    public Intern(String name, String pass, String gender, String post, LocalDate date, String area, String refer,
                  double salary) {
        this.name = name;
        this.pass = pass;
        this.gender = gender;
        this.post = post;
        this.references = refer;
        this.date = String.valueOf(date);
        this.area = area;
        this.salary = (int) salary;
    }

    public Intern(String del) {
        this.del = del;
    }

    public Intern() {
    }

    public Intern(String update1, String update2) {
        this.update2 = update2;
        this.update1 = update1;
    }

    public void insertInternRecord()
            throws InstantiationException, IllegalAccessException,
            ClassNotFoundException, SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/internship", "root",
                    "Password");
            String sql = "insert into interns values (?,?,?,?,?,?,?,?);";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, pass);
            stmt.setString(3, gender);
            stmt.setString(5, post);
            stmt.setString(4, references);
            stmt.setString(6, date);
            stmt.setString(7, area);
            stmt.setInt(8, salary);
            int i = stmt.executeUpdate();
            System.out.println("The value of i is " + i);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateInternRecord()
            throws InstantiationException, IllegalAccessException,
            ClassNotFoundException, SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/internship", "root",
                    "Password");
            String sql = "update interns set reference=? where name=?";
            PreparedStatement stmt1 = con.prepareStatement(sql);
            stmt1.setString(1, update2);
            stmt1.setString(2, update1);
            int a = stmt1.executeUpdate();
            if (a == 1) {
                System.out.println("Updation successful");
            } else {
                System.out.println(" Updation Failure");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteInternRecord()
            throws InstantiationException, IllegalAccessException,
            ClassNotFoundException, SQLException {
        System.out.println("In delete");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/internship", "root",
                    "Password");
            String sql = "delete from interns where name=?;";
            PreparedStatement stmt1 = con.prepareStatement(sql);
            stmt1.setString(1, del);
            int a = stmt1.executeUpdate();
            if (a == 1) {
                System.out.println("Deletion successful");
            } else {
                System.out.println("Deletion Failure");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void displayInternRecord()
            throws InstantiationException, IllegalAccessException,
            ClassNotFoundException, SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/internship", "root",
                    "Password");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from interns");
            while (rs.next()) {
                System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " "
                        + rs.getString(4) + " " + rs.getString(5) + " " + rs.getString(6) + rs.getString(7) + " "
                        + "  " + rs.getInt(8));
            }
            if (rs != null) {
                System.out.println(" successful");
            } else {
                System.out.println(" Failure");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}