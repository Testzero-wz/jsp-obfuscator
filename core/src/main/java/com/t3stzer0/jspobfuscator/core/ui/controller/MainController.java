package com.t3stzer0.jspobfuscator.core.ui.controller;

import com.t3stzer0.jspobfuscator.api.page.JspPage;
import com.t3stzer0.jspobfuscator.api.page.JspxPage;
import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContextAdapter;
import com.t3stzer0.jspobfuscator.api.plugin.exceptions.ExtensionException;
import com.t3stzer0.jspobfuscator.api.plugin.gadget.GadgetExtension;
import com.t3stzer0.jspobfuscator.api.plugin.obfus.EditPageExtension;
import com.t3stzer0.jspobfuscator.api.plugin.obfus.OutputPageExtension;
import com.t3stzer0.jspobfuscator.api.plugin.obfus.PageFactoryExtension;
import com.t3stzer0.jspobfuscator.core.domain.extension.ExtensionBinding;
import com.t3stzer0.jspobfuscator.core.domain.extension.ExtensionRuntimeContext;
import com.t3stzer0.jspobfuscator.core.domain.obfus.ObfusContext;
import com.t3stzer0.jspobfuscator.core.domain.obfus.ObfusResult;
import com.t3stzer0.jspobfuscator.core.exception.ObfusException;
import com.t3stzer0.jspobfuscator.core.service.ObfusService;
import com.t3stzer0.jspobfuscator.core.service.PluginService;
import com.t3stzer0.jspobfuscator.core.ui.component.ExtensionMenuItem;
import com.t3stzer0.jspobfuscator.core.ui.component.ExtensionTableViewItem;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainController {

  private EditorController editorController;
  private PluginService pluginService;
  private HashMap<TableView<String>, String> tableMap;
  private Path rootPath;

  @FXML private Menu MenuGadgets;

  @FXML private WebView WebViewInputCode;
  @FXML private WebView WebViewInputInclude;
  @FXML private WebView InputDeclaration;
  @FXML private WebView WebViewOutputHex;
  @FXML private WebView WebViewOutputText;

  @FXML private TableView<ExtensionTableViewItem> TableViewPageFactory;
  @FXML private TableView<ExtensionTableViewItem> TableViewEditPage;
  @FXML private TableView<ExtensionTableViewItem> TableViewOutputPage;

  @FXML private TabPane InputTabPane;

  private final Logger log = LoggerFactory.getLogger(MainController.class);

  private ObfusService obfuscator;
  private ObfusResult obfusResult;
  private File lastDirectory = null;

  private void initCodeEditor() {
    editorController = new EditorController(this);
  }

  private void initTableViews() {
    initTableView(TableViewPageFactory, PageFactoryExtension.class, true);
    initTableView(TableViewOutputPage, OutputPageExtension.class, true);
    initTableView(TableViewEditPage, EditPageExtension.class, false);
  }

  private void initTableView(TableView<ExtensionTableViewItem> tableView, Class<?> extensionClazz, boolean exclusive) {
    Menu addExtensionMenu = new Menu("新增");
    // 初始化插件配置以及菜单
    initObfusTableViewContextMenu(tableView, addExtensionMenu, extensionClazz, exclusive);

    // 动态获取第一列
    TableColumn<ExtensionTableViewItem, ExtensionTableViewItem> nameColumn =
        (TableColumn<ExtensionTableViewItem, ExtensionTableViewItem>) tableView.getColumns().get(0);

    nameColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));

    nameColumn.setCellFactory(
        col ->
            new TableCell<ExtensionTableViewItem, ExtensionTableViewItem>() {
              @Override
              protected void updateItem(ExtensionTableViewItem item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                  setText(null);
                } else {
                  setText(item.getDisplayName()); // 仅显示名称
                }
              }
            });
    nameColumn.setSortable(false);

    MenuItem editItem = new MenuItem("编辑");
    MenuItem deleteItem = new MenuItem("删除");

    editItem.setOnAction(
        e -> {
          // ExtensionTableViewItem item = row.getItem();
          ExtensionTableViewItem item = tableView.getSelectionModel().getSelectedItem();
          if (item != null) {
            showExtensionConfigDialog(item.getUuid());
          }
        });

    deleteItem.setOnAction(
        e -> {
          ExtensionTableViewItem item = tableView.getSelectionModel().getSelectedItem();
          if (item != null) {
            pluginService.removeExtensionBinding(item.getUuid());
            tableView.getItems().remove(item);
          }
        });

    ContextMenu contextMenu = new ContextMenu(addExtensionMenu, editItem, deleteItem);
    contextMenu.setOnShowing(
        e -> {
          boolean hasSelection = tableView.getSelectionModel().getSelectedItem() != null;
          editItem.setVisible(hasSelection);
          deleteItem.setVisible(hasSelection);
        });

    // 自动选中
    tableView.setRowFactory(
        tv -> {
          TableRow<ExtensionTableViewItem> row = new TableRow<>();
          row.setOnMousePressed(
              event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.SECONDARY) {
                  tableView.getSelectionModel().select(row.getIndex());
                }
              });
          return row;
        });

    tableView.setContextMenu(contextMenu);
  }

  private String newExtensionConfig(String extKey) {
    String uuid = pluginService.addExtensionBindingByExtensionKey(extKey);
    // generate context popup
    boolean success = showExtensionConfigDialog(uuid);
    if (!success) {
      pluginService.removeExtensionBinding(uuid);
      return null;
    }
    return uuid;
  }

  private String addExtensionTableViewItem(
      TableView<ExtensionTableViewItem> table, String extKey, boolean exclusive) {
    String uuid = newExtensionConfig(extKey);
    if (uuid == null) {
      return null;
    }
    if (exclusive) {
      table.getItems().clear();
    }
    ExtensionTableViewItem item = new ExtensionTableViewItem(uuid, extKey);
    table.getItems().add(item);
    return uuid;
  }

  private void loadPlugins() {
    this.pluginService = new PluginService(rootPath.resolve("plugins").normalize());
  }

  private static final String DefaultPluginName = "default-plugin";
  private static final String DefaultExtensionPrefix = "default";

  public static int pluginSorted(MenuItem i1, MenuItem i2) {

    ExtensionMenuItem item1 = (ExtensionMenuItem) i1;
    ExtensionMenuItem item2 = (ExtensionMenuItem) i2;
    String pluginName1 = item1.getPluginName().toLowerCase();
    String pluginName2 = item2.getPluginName().toLowerCase();
    if (pluginName1.equals(DefaultPluginName) && !pluginName2.equals(DefaultPluginName)) {
      return -1;
    } else if (!pluginName1.equals(DefaultPluginName) && pluginName2.equals(DefaultPluginName)) {
      return 1;
    }

    String extName1 = item1.getLastClassName().toLowerCase();
    String extName2 = item2.getLastClassName().toLowerCase();

    if (extName1.equals(DefaultExtensionPrefix) && !extName2.equals(DefaultExtensionPrefix)) {
      return -1;
    } else if (!extName1.equals(DefaultExtensionPrefix)
        && extName2.equals(DefaultExtensionPrefix)) {
      return 1;
    }

    return item1.getDisplayName().compareTo(item2.getDisplayName());
  }

  private void initGadgetMenu() {
    for (String key : this.pluginService.getExtensionKeysByClass(GadgetExtension.class)) {
      MenuItem item = getGadgetExtensionMenuItem(key);
      MenuGadgets.getItems().add(item);
    }
    // sort items
    MenuGadgets.getItems().sort(MainController::pluginSorted);
  }

  private void initObfusTableViewContextMenu(
      TableView<ExtensionTableViewItem> tableView, Menu menu, Class<?> extensionClazz, boolean exclusive) {
    for (String key : this.pluginService.getExtensionKeysByClass(extensionClazz)) {
      ExtensionMenuItem item = new ExtensionMenuItem(key);
      item.setOnAction(
          event -> {
            String uuid = addExtensionTableViewItem(tableView, item.getFullExtensionKey(), exclusive);
            if (uuid == null) {
              return;
            }
            log.debug("load extension: {}", key);
          });
      menu.getItems().add(item);
    }
    menu.getItems().sort(MainController::pluginSorted);
  }

  private MenuItem getGadgetExtensionMenuItem(String extensionKey) {
    ExtensionMenuItem item = new ExtensionMenuItem(extensionKey);
    item.setOnAction(
        event -> {
          String uuid = newExtensionConfig(extensionKey);
          if (uuid == null) {
            return;
          }
          ExtensionBinding extBinding = pluginService.getExtensionBinding(uuid);
          GadgetExtension gadgetExtension = (GadgetExtension) extBinding.getExtension();
          ExtensionRuntimeContext extContext =
              (ExtensionRuntimeContext) extBinding.getExtensionCtx();
          editorController.setWebViewTextByName(
              EditorController.WebViewName.INCLUDE, gadgetExtension.getImports(extContext));
          editorController.setWebViewTextByName(
              EditorController.WebViewName.DECLARATION, gadgetExtension.getDeclaration(extContext));
          editorController.setWebViewTextByName(
              EditorController.WebViewName.CODE, gadgetExtension.getCode(extContext));
        });
    return item;
  }

  private void initObfusService() {
    obfuscator = new ObfusService(pluginService);
  }

  public void initMainController(Path path) {
    rootPath = path;
    loadPlugins();
    initGadgetMenu();
    initTableViews();
    initCodeEditor();
    initObfusService();
  }

  public WebView getWebViewOutputHex() {
    return WebViewOutputHex;
  }

  public WebView getWebViewOutputText() {
    return WebViewOutputText;
  }

  public TabPane getInputTabPane() {
    return InputTabPane;
  }

  public WebView getWebViewInputCode() {
    return WebViewInputCode;
  }

  public WebView getWebViewInputInclude() {
    return WebViewInputInclude;
  }

  public WebView getInputDeclaration() {
    return InputDeclaration;
  }

  public boolean showDialog(Alert.AlertType t, String title, String msg) {
    Alert alert = new Alert(t);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(msg);
    Optional<ButtonType> result = alert.showAndWait();
    if (t == Alert.AlertType.CONFIRMATION) {
      return result.isPresent() && result.get() == ButtonType.OK;
    }
    return true;
  }

  private boolean showExtensionConfigDialog(String uuid) {
    ExtensionRuntimeContext ctx =
        (ExtensionRuntimeContext) pluginService.getExtensionBinding(uuid).getExtensionCtx();
    if (ctx.getInputMap().isEmpty()) {
      return true;
    }

    Dialog<Boolean> dialog = new Dialog<>();
    dialog.setTitle("编辑配置");
    ButtonType okBtn = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
    ButtonType cancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
    dialog.getDialogPane().getButtonTypes().addAll(okBtn, cancelBtn);
    Map<String, Control> editors = new HashMap<>();
    GridPane grid = new GridPane();
    grid.setVgap(8);
    grid.setHgap(10);
    int row = 0;
    for (Map.Entry<String, ExtensionContextAdapter.ExtensionInputItem> entry :
        ctx.getInputMap().entrySet()) {
      String key = entry.getKey();
      ExtensionContextAdapter.ExtensionInputItem value = entry.getValue();
      ExtensionContextAdapter.InputType type = value.getType();
      String nameField = key;
      if (value.isRequired()) {
        nameField += "*";
      }
      nameField += " : ";

      Label label = new Label(nameField);
      Control node =
          switch (type) {
            case STRING -> new TextField(Objects.toString(value.getStringValue(), ""));
            case NUMBER -> {
              Float number = value.getNumberValue();
              if (number == null) {
                number = 0.0f;
              }
              yield new TextField(number.toString());
            }
            case SELECT -> {
              ComboBox<String> comboBox = new ComboBox<>();
              comboBox.getItems().addAll(value.getOptions());
              if (value.getSelectValue() != null) {
                comboBox.getSelectionModel().select(value.getSelectValue());
              }
              yield comboBox;
            }
          };
      grid.add(label, 0, row);
      grid.add(node, 1, row);
      editors.put(key, node);
      row++;
    }
    dialog.getDialogPane().setContent(grid);

    Button okButton = (Button) dialog.getDialogPane().lookupButton(okBtn);
    okButton.addEventFilter(
        ActionEvent.ACTION,
        event -> {
          boolean valid = true;
          try {
            for (Map.Entry<String, Control> entry : editors.entrySet()) {
              String key = entry.getKey();
              Control ctrl = entry.getValue();
              ExtensionContextAdapter.ExtensionInputItem item = ctx.getInputMap().get(key);
              switch (item.getType()) {
                case STRING:
                  {
                    String text = ((TextField) ctrl).getText().strip();
                    if (text.isEmpty() && item.isRequired()) {
                      throw new IllegalArgumentException(
                          "Required field: " + key + " cannot be empty");
                    }
                    ctx.setStringInput(key, ((TextField) ctrl).getText());
                    break;
                  }
                case NUMBER:
                  {
                    String text = ((TextField) ctrl).getText().strip();
                    if (text.isEmpty() && item.isRequired()) {
                      throw new IllegalArgumentException(
                          "Required field: " + key + " cannot be empty");
                    }
                    if (text.isEmpty()) {
                      text = "0.0";
                    }
                    float v;
                    try {
                      v = Float.parseFloat(text);
                    } catch (NumberFormatException e) {
                      throw new IllegalArgumentException(
                          "Invalid number format in field: " + key + ", Value: " + text);
                    }
                    ctx.setNumberInput(key, v);
                    break;
                  }
                case SELECT:
                  {
                    int selected = ((ComboBox<String>) ctrl).getSelectionModel().getSelectedIndex();
                    if (selected == -1 && item.isRequired()) {
                      throw new IllegalArgumentException(
                          "Required field: " + key + " cannot be empty");
                    }
                    ctx.setSelectInput(key, selected);
                    break;
                  }
              }
            }
          } catch (IllegalArgumentException e) {
            valid = false;
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.setHeaderText("配置存在错误");
            alert.showAndWait();
          }
          if (!valid) {
            event.consume();
          }
        });

    dialog.setResultConverter(btn -> btn == okBtn);
    Optional<Boolean> result = dialog.showAndWait();
    return result.orElse(false);
  }

  @FXML
  void SaveFile(ActionEvent event) {
    if (obfusResult == null || obfusResult.getData().length == 0) {
      Alert alert = new Alert(Alert.AlertType.ERROR, "文件内容为空");
      alert.showAndWait();
      return;
    }
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("保存文件");
    // 如果有上一次的路径，就设置为初始目录
    if (lastDirectory != null && lastDirectory.exists()) {
      fileChooser.setInitialDirectory(lastDirectory);
    }
    FileChooser.ExtensionFilter jspExtensionFilter =
        new FileChooser.ExtensionFilter("JSP文件", "*.jsp");
    FileChooser.ExtensionFilter jspxExtensionFilter =
        new FileChooser.ExtensionFilter("JSP document文件", "*.jspx");
    FileChooser.ExtensionFilter anyExtensionFilter = new FileChooser.ExtensionFilter("任何类型", "*.*");

    fileChooser
        .getExtensionFilters()
        .addAll(jspExtensionFilter, jspxExtensionFilter, anyExtensionFilter);

    // FIXME: not working
    if (JspPage.class.isAssignableFrom(obfusResult.getPageClass())) {
      fileChooser.setSelectedExtensionFilter(jspExtensionFilter);
    } else if (JspxPage.class.isAssignableFrom(obfusResult.getPageClass())) {
      fileChooser.setSelectedExtensionFilter(jspxExtensionFilter);
    } else {
      fileChooser.setSelectedExtensionFilter(anyExtensionFilter);
    }

    File file = fileChooser.showSaveDialog(null);
    if (file != null) {
      try (FileOutputStream out = new FileOutputStream(file)) {
        lastDirectory = file.getParentFile();
        out.write(obfusResult.getData());
      } catch (IOException e) {
        log.error("保存文件失败", e);
        Alert alert = new Alert(Alert.AlertType.ERROR, "保存文件失败: " + e.getMessage());
        alert.showAndWait();
      }
    }
  }

  @FXML
  void About(ActionEvent event) {
    Package pkg = getClass().getPackage();
    if (pkg == null) {
      showDialog(Alert.AlertType.ERROR, "Error", "信息获取失败");
      return;
    }
    String version = pkg.getImplementationVersion();
    String title = pkg.getImplementationTitle();

    String aboutMessage =
        String.format(
"""
项目名: %s
版本号: %s""",
            title, version);
    showDialog(Alert.AlertType.INFORMATION, "About", aboutMessage);
  }

  @FXML
  void Generate(ActionEvent event) throws IOException {
    // 执行混淆

    try {
      ObfusContextBuilder obfusContextBuilder = new ObfusContextBuilder();
      obfusResult = obfuscator.obfus(obfusContextBuilder.build());
    } catch (ObfusException | ExtensionException e) {
      showDialog(Alert.AlertType.ERROR, "Error", e.getMessage());
      return;
    }
    editorController.setOutput(obfusResult.getData());
  }

  public class ObfusContextBuilder {
    public ObfusContext build() {
      ObfusContext ctx = new ObfusContext();
      ctx.code = editorController.getCode();
      ctx.imports = editorController.getImports();
      ctx.declaration = editorController.getDeclaration();

      if (TableViewPageFactory.getItems().isEmpty()) {
        throw new ObfusException("Page factory extension is required!");
      }
      if (TableViewOutputPage.getItems().isEmpty()) {
        throw new ObfusException("Output page extension is required!");
      }
      ctx.pageFactoryExtension = TableViewPageFactory.getItems().get(0).getUuid();
      ctx.outputPageExtension = TableViewOutputPage.getItems().get(0).getUuid();
      for (ExtensionTableViewItem item : TableViewEditPage.getItems()) {
        ctx.editPageExtensions.add(item.getUuid());
      }
      return ctx;
    }
  }
}
