package com.t3stzer0.jspobfuscator.core.ui;

import com.t3stzer0.jspobfuscator.core.ui.controller.MainController;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainApplication extends Application {
  private final Logger log = LoggerFactory.getLogger(this.getClass());
  public static Path getRuntimeRootPath() {
    try {
      String jarPath =
          new File(MainApplication.class.getProtectionDomain().getCodeSource().getLocation().toURI())
              .getPath();
      return Paths.get(jarPath).getParent();
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

  @Override
  public void start(Stage primaryStage) {
    java.net.URL uri = Objects.requireNonNull(getClass().getResource("/ui/fxml/Main.fxml"));
    FXMLLoader loader = new FXMLLoader(uri);
    Parent root;
    try {
      root = loader.load();
    } catch (IOException e) {
      log.error("FXML load error", e);
      throw new RuntimeException("Failed to load FXML: " + e.getMessage(), e);
    }
    MainController controller = loader.getController();
    Scene mainScene = new Scene(root);
    String cssFilePath =
        Objects.requireNonNull(getClass().getResource("/ui/css/Main.css")).toExternalForm();
    mainScene.getStylesheets().add(cssFilePath);

    controller.initMainController(getRuntimeRootPath());
    primaryStage.setTitle("jsp-obfuscator");
    primaryStage.setScene(mainScene);
    primaryStage.show();
  }

}
