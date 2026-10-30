module br.senac.sp.pizzaria {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;

    requires java.desktop;


    opens br.senac.sp.pizzaria.controller to javafx.fxml;
    opens br.senac.sp.pizzaria.model to javafx.base, org.hibernate.orm.core;
    exports br.senac.sp.pizzaria;
}