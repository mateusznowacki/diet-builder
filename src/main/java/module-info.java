module pl.dietbuilder {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens pl.dietbuilder.app to javafx.fxml;
    exports pl.dietbuilder.app;

    opens pl.dietbuilder.controllers to javafx.fxml;
    exports pl.dietbuilder.controllers;

    opens pl.dietbuilder.model to javafx.fxml;
    exports pl.dietbuilder.model;

    opens pl.dietbuilder.dbmanagement to javafx.fxml;
    exports pl.dietbuilder.dbmanagement;

}