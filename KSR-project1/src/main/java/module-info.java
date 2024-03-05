module ksr1.ksrproject1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens ksr1.ksrproject1 to javafx.fxml;
    exports ksr1.ksrproject1;
}