module org.example.inviaggio {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;

    opens org.example.inviaggio to javafx.fxml;
    exports org.example.inviaggio;
}