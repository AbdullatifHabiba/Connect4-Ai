module main.connect4 {
    requires javafx.controls;
    requires javafx.fxml;


    opens src to javafx.fxml;
    exports src;
}