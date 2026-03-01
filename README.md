# jsp-obfuscator

一个可扩展的 JSP/JSPX Webshell 混淆工具，旨在提升 Webshell 的隐蔽性与免杀能力。

---

## 🚀 功能特性

* **多维混淆策略**：内置多种混淆引擎，支持字符级与页面级混淆。
* **内置 Gadget 生成器**：集成常用代码片段生成器，支持自定义文本输入，快速构建高隐蔽性 Payload。
* **实时交互预览**：支持输入与输出的实时双栏对比，混淆效果即刻呈现。
* **高度模块化架构**：混淆逻辑采用插件化设计，可针对特定环境快速扩展、组合混淆插件。

## 🛠️ 混淆插件

本项目支持第三方插件开发，通过解耦混淆逻辑，让安全研究员能够专注于绕过策略的实现。

### 插件开发
* **完整插件示例**：[🔗 访问插件开发 Demo](https://github.com/Testzero-wz/jsp-obfuscator-plugin-example)
* **官方插件库**：[📦 浏览更多官方混淆插件](https://github.com/Testzero-wz/jsp-obfuscator/tree/master/plugins/src/main/java/com/t3stzer0/jspobfuscator/plugins/extensions)

### 已实现插件
| 类别 | 插件名 |
| --- | --- |
| EditPage | Cdata |
| | ChangeNamespace |
| | CodeSplit |
| | HtmlEntityEncode |
| | PiTag |
| | QuotedStringReplaceAes |
| | QuotedStringReplaceB64 |
| | StringConcat |
| | UnicodeEncode |
| Gadget | Behinder |
| | GadgetBcel |
| | MemShellListener |
| | MethodReference |
| | ProcessBuilderStart |
| | RuntimeExec |
| | ScriptEngine |
| | UnclosedBrace |
| | UrlClassLoader |
| OutputPage | DefaultOutputPage |
| | DoubleEncodeOutputPage |
| | SetPropertyOutputPage |
| PageFactory | DefaultPageFactory |


## 环境依赖

* **Java 17+**


## ⚖️ 免责声明

本项目仅供合法的安全研究与授权测试使用。使用者需自行承担因不当使用或非法用途引发的所有法律责任，作者及贡献者概不负责。

**一旦使用本程序，即视为你已同意本条款。**