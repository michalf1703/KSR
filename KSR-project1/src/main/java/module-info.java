module ksr1.ksrproject1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;
    requires org.apache.commons.io;
    requires java.desktop;
    requires org.jfree.jfreechart;
    requires javafx.graphics;


    opens ksr1.ksrproject1 to javafx.fxml;
    exports ksr1.ksrproject1;
    exports ksr1.ksrproject1.charts to javafx.graphics;
}