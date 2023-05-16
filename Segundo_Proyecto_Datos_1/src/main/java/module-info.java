module sample.segundoproyectodatos1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.xml;
    requires com.fasterxml.jackson.databind;
    requires java.scripting;
    requires com.google.gson;
    //requires org.json;

    opens Admin_y_Usuario to javafx.fxml;
    exports Servidor;
    opens Servidor to javafx.fxml;
    exports Admin_y_Usuario;
    exports Clases_auxiliares;
    opens Clases_auxiliares to javafx.fxml;
}