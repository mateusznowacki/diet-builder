module pl.dietbuilder {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires org.mariadb.jdbc;
    //requires org.apache.pdfbox;

    opens pl.dietbuilder.app to javafx.fxml;
    exports pl.dietbuilder.app;

    opens pl.dietbuilder.controllers.main to javafx.fxml;
    exports pl.dietbuilder.controllers.main;

    opens pl.dietbuilder.model to javafx.fxml;
    exports pl.dietbuilder.model;

    opens pl.dietbuilder.dbmanagement to javafx.fxml;
    exports pl.dietbuilder.dbmanagement;
    exports pl.dietbuilder.controllers.meal;
    opens pl.dietbuilder.controllers.meal to javafx.fxml;
    exports pl.dietbuilder.controllers.product;
    opens pl.dietbuilder.controllers.product to javafx.fxml;
    exports pl.dietbuilder.controllers.shoppinglist;
    opens pl.dietbuilder.controllers.shoppinglist to javafx.fxml;


}