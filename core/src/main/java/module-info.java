module jspobfuscator.core {
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.web;
  requires javafx.base;
  requires jspobfuscator.api;
  requires org.pf4j;
  requires org.slf4j;

  exports com.t3stzer0.jspobfuscator.core.ui;

  opens com.t3stzer0.jspobfuscator.core.ui.controller to
      javafx.fxml;
    opens com.t3stzer0.jspobfuscator.core.ui.component to javafx.fxml;
}
