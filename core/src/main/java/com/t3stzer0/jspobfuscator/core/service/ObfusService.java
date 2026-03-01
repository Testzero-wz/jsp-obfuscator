package com.t3stzer0.jspobfuscator.core.service;

import com.t3stzer0.jspobfuscator.api.page.Page;
import com.t3stzer0.jspobfuscator.api.plugin.exceptions.ExtensionException;
import com.t3stzer0.jspobfuscator.api.plugin.obfus.EditPageExtension;
import com.t3stzer0.jspobfuscator.api.plugin.obfus.OutputPageExtension;
import com.t3stzer0.jspobfuscator.api.plugin.obfus.PageFactoryExtension;
import com.t3stzer0.jspobfuscator.core.domain.extension.ExtensionBinding;
import com.t3stzer0.jspobfuscator.core.domain.extension.ExtensionExceptionWrapper;
import com.t3stzer0.jspobfuscator.core.domain.obfus.ObfusContext;
import com.t3stzer0.jspobfuscator.core.domain.obfus.ObfusResult;

public class ObfusService {

  public PluginService pluginService;

  public ObfusService(PluginService pluginService) {
    this.pluginService = pluginService;
  }

  public ObfusResult obfus(ObfusContext obfusContext) {

    ExtensionBinding extensionBinding = null;
    try {
      // init page
      extensionBinding = pluginService.getExtensionBinding(obfusContext.pageFactoryExtension);
      PageFactoryExtension pageFactoryExtension =
          (PageFactoryExtension) extensionBinding.getExtension();
      Page page =
          pageFactoryExtension.createPage(
              extensionBinding.getExtensionCtx(),
              obfusContext.getImports(),
              obfusContext.getDeclaration(),
              obfusContext.getCode());

      // edit page
      for (String uuid : obfusContext.editPageExtensions) {
        extensionBinding = pluginService.getExtensionBinding(uuid);
        EditPageExtension editPageExt = (EditPageExtension) extensionBinding.getExtension();
        editPageExt.editPage(extensionBinding.getExtensionCtx(), page);
      }

      // output page
      extensionBinding = pluginService.getExtensionBinding(obfusContext.outputPageExtension);
      OutputPageExtension outputExtension = (OutputPageExtension) extensionBinding.getExtension();
      byte[] out = outputExtension.outputPage(extensionBinding.getExtensionCtx(), page);

      return new ObfusResult(out, page.getClass());

    } catch (ExtensionException e) {
      assert extensionBinding != null;
      throw ExtensionExceptionWrapper.wrapFromExtensionException(extensionBinding, e);
    }
  }
}
