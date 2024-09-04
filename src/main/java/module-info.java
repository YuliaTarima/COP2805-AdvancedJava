module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    requires javafx.graphics;
    requires java.desktop;
    requires javafx.media;
    requires java.sql;
    requires java.sql.rowset;

    opens com.example.demo to javafx.fxml;
    exports com.example.demo;

    opens com.book_code.chapter14 to javafx.graphics;
    opens com.assignments.week02 to javafx.graphics;
    exports com.assignments.week03 to javafx.graphics;
    exports com.assignments.week04 to javafx.graphics;
}