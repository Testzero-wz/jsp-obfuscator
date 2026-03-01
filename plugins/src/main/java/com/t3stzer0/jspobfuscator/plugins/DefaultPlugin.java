package com.t3stzer0.jspobfuscator.plugins;

import org.pf4j.Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultPlugin extends Plugin {
  public DefaultPlugin() {
    super();
  }

  private final Logger log = LoggerFactory.getLogger(DefaultPlugin.class);

  @Override
  public void start() {
    log.info("Default Plugin started");
  }

  @Override
  public void stop() {
    log.info("Default Plugin stopped");
  }
}
