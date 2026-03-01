package com.t3stzer0.jspobfuscator.core.service;

import com.t3stzer0.jspobfuscator.api.plugin.BaseExtension;
import com.t3stzer0.jspobfuscator.api.plugin.gadget.GadgetExtension;
import com.t3stzer0.jspobfuscator.api.plugin.obfus.EditPageExtension;
import com.t3stzer0.jspobfuscator.api.plugin.obfus.OutputPageExtension;
import com.t3stzer0.jspobfuscator.api.plugin.obfus.PageFactoryExtension;
import java.nio.file.Path;
import java.util.*;

import com.t3stzer0.jspobfuscator.core.domain.extension.ExtensionBinding;
import org.pf4j.DefaultPluginManager;
import org.pf4j.PluginManager;
import org.pf4j.PluginWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PluginService {

  private final Logger log = LoggerFactory.getLogger(this.getClass());
  private final Map<String, BaseExtension> allExtensions = new HashMap<>();
  private final Map<String, ExtensionBinding> allExtensionBindings = new HashMap<>();

  // return the uuid of the extension binding
  public String addExtensionBindingByExtensionKey(String extKey) {
    String uuid = UUID.randomUUID().toString();
    ExtensionBinding binding = new ExtensionBinding(extKey, this);
    allExtensionBindings.put(uuid, binding);
    return uuid;
  }

  public void removeExtensionBinding(String uuid) {
    allExtensionBindings.remove(uuid);
  }

  public ExtensionBinding getExtensionBinding(String uuid) {
    return allExtensionBindings.get(uuid);
  }

  String getExtensionKey(String pluginId, BaseExtension ext) {
    return pluginId + ":" + ext.getClass().getName();
  }

  public BaseExtension getExtension(String extKey) {
    return allExtensions.get(extKey);
  }

  public PluginService(Path pluginDir) {
    PluginManager pluginManager = new DefaultPluginManager(pluginDir);
    pluginManager.loadPlugins();
    pluginManager.startPlugins();
    for (PluginWrapper pluginWrapper : pluginManager.getStartedPlugins()) {
      String pluginId = pluginWrapper.getPluginId();
      for (Object ext : pluginManager.getExtensions(pluginId)) {
        if (ext instanceof BaseExtension baseExt) {
          allExtensions.put(getExtensionKey(pluginId, baseExt), baseExt);
        }
      }
    }
    log.debug("Gadget Extensions: {}", getExtensionKeysByClass(GadgetExtension.class).size());
    log.debug("PageFactory Extensions: {}", getExtensionKeysByClass(PageFactoryExtension.class).size());
    log.debug("EditPage Extensions: {}", getExtensionKeysByClass(EditPageExtension.class).size());
    log.debug("OutputPage Extensions: {}", getExtensionKeysByClass(OutputPageExtension.class).size());
    log.debug("All Extensions: {}", allExtensions.size());
  }

  public List<String> getExtensionKeysByClass(Class<?> clazz) {
    List<String> keys = new ArrayList<>();
    for (Map.Entry<String, BaseExtension> entry : allExtensions.entrySet()) {
      if (clazz.isInstance(entry.getValue())) {
        keys.add(entry.getKey());
      }
    }
    return keys;
  }
}
