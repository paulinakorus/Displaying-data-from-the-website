module Client {
    requires org.json;
    requires java.net.http;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;

    opens org.example.model to com.fasterxml.jackson.databind;
    exports org.example.model;
}