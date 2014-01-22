/*
 * Copyright (c) 2000-2005 by JetBrains s.r.o. All Rights Reserved.
 * Use is subject to license terms.
 */
package com.intellij.tapestry.tests;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.codeInsight.completion.impl.CamelHumpMatcher;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.javaee.ExternalResourceManagerEx;
import com.intellij.testFramework.UsefulTestCase;
import com.intellij.xml.util.XmlUtil;
import gnu.trove.THashSet;
import junit.framework.Assert;
import org.jetbrains.annotations.NonNls;

import java.util.Set;

import static com.intellij.util.ArrayUtil.mergeArrays;

/**
 * @author Alexey Chmutov
 */
public class TapestryCompletionTest extends TapestryBaseTestCase {

  public void testTagNameInHtmlParent() throws Throwable {
    initByComponent();
    doTestBasicCompletionVariants(mergeArrays(CORE_5_1_0_5_TAG_NAMES, "head", "body", getElementTagName()));
  }

  public void testTagNameInTmlParent() throws Throwable {
    CamelHumpMatcher.forceStartMatching(getTestRootDisposable());
    initByComponent();
    addComponentToProject("subpackage.Count");
    doTestBasicCompletionVariants(
      mergeArrays(CORE_5_1_0_5_TAG_NAMES, "base", "link", "meta", "noscript", "p:clientId",
                  "p:element", "p:mixins", "script", "style", "title", "t:subpackage.count",
                  getElementTagName()));
  }

  public void testAttrNameInHtmlParent() throws Throwable {
    CamelHumpMatcher.forceStartMatching(getTestRootDisposable());
    final ExternalResourceManagerEx manager = ExternalResourceManagerEx.getInstanceEx();
    final String doctype = manager.getDefaultHtmlDoctype(myFixture.getProject());
    manager.setDefaultHtmlDoctype(XmlUtil.XHTML_URI, myFixture.getProject());
    try {
      initByComponent();
      doTestBasicCompletionVariants("accesskey", "charset", "class", "coords", "dir", "href", "hreflang", "id", "lang", "name", "onblur",
                                    "onclick", "ondblclick", "onfocus", "onkeydown", "onkeypress", "onkeyup", "onmousedown", "onmousemove",
                                    "onmouseout", "onmouseover", "onmouseup", "rel", "rev", "shape", "style", "tabindex", "target", "title",
                                    "type", "t:type", "t:id");
    }
    finally {
      manager.setDefaultHtmlDoctype(doctype, myFixture.getProject());
    }
  }

  public void testAttrNameInHtmlParent1() throws Throwable {
    CamelHumpMatcher.forceStartMatching(getTestRootDisposable());
    initByComponent();
    doTestBasicCompletionVariants("t:id", "t:type", "tabindex", "target", "title", "translate", "type");
  }

  public void testAttrNameInTmlParent() throws Throwable {
    final ExternalResourceManagerEx manager = ExternalResourceManagerEx.getInstanceEx();
    final String doctype = manager.getDefaultHtmlDoctype(myFixture.getProject());
    manager.setDefaultHtmlDoctype(XmlUtil.XHTML_URI, myFixture.getProject());
    try {
      initByComponent();
      addComponentToProject("Count");
      doTestBasicCompletionVariants("class", "dir", "end", "id", "lang", "mixins", "onclick", "ondblclick", "onkeydown", "onkeypress",
                                    "onkeyup",
                                    "onmousedown", "onmousemove", "onmouseout", "onmouseover", "onmouseup", "start", "style", "title",
                                    "value", "t:id");
    }
    finally {
      manager.setDefaultHtmlDoctype(doctype, myFixture.getProject());
    }
  }

  public void testAttrNameInTmlParent1() throws Throwable {
    CamelHumpMatcher.forceStartMatching(getTestRootDisposable());
    initByComponent();
    addComponentToProject("Count");
    doTestBasicCompletionVariants("class", "contenteditable", "contextmenu");
  }

  public void testRootTagName() throws Throwable {
    final ExternalResourceManagerEx manager = ExternalResourceManagerEx.getInstanceEx();
    final String doctype = manager.getDefaultHtmlDoctype(myFixture.getProject());
    manager.setDefaultHtmlDoctype(XmlUtil.XHTML_URI, myFixture.getProject());
    try {
      initByComponent();
      doTestBasicCompletionVariants(mergeArrays(HTML_AND_CORE_5_1_0_5_TAG_NAMES, getElementTagName()));
    }
    finally {
      manager.setDefaultHtmlDoctype(doctype, myFixture.getProject());
    }
  }

  public void testTagNameWithinTmlRootTag() throws Throwable {
    final ExternalResourceManagerEx manager = ExternalResourceManagerEx.getInstanceEx();
    final String doctype = manager.getDefaultHtmlDoctype(myFixture.getProject());
    manager.setDefaultHtmlDoctype(XmlUtil.XHTML_URI, myFixture.getProject());
    try {
      initByComponent();
      doTestBasicCompletionVariants(mergeArrays(HTML_AND_CORE_5_1_0_5_TAG_NAMES, getElementTagName()));
    }
    finally {
      manager.setDefaultHtmlDoctype(doctype, myFixture.getProject());
    }
  }

  public void testTagNameWithoutHtmlContext() throws Throwable {
    final ExternalResourceManagerEx manager = ExternalResourceManagerEx.getInstanceEx();
    final String doctype = manager.getDefaultHtmlDoctype(myFixture.getProject());
    manager.setDefaultHtmlDoctype(XmlUtil.XHTML_URI, myFixture.getProject());
    try {
      initByComponent();
      doTestBasicCompletionVariants(mergeArrays(HTML_AND_CORE_5_1_0_5_TAG_NAMES, getElementTagName()));
    }
    finally {
      manager.setDefaultHtmlDoctype(doctype, myFixture.getProject());
    }
  }

  public void testInvalidTagName() throws Throwable {
    initByComponent();
    UsefulTestCase.assertEmpty(myFixture.complete(CompletionType.BASIC));
    UsefulTestCase.assertEmpty(myFixture.getLookupElementStrings());
  }

  public void testTypeAttrValue() throws Throwable {
    initByComponent();
    addComponentToProject("mycomps.Count");
    doTestBasicCompletionVariants(mergeArrays(CORE_5_1_0_5_ELEMENT_NAMES, getLowerCaseElementName(), "mycomps/count"));
  }

  public void testPageAttrValue() throws Throwable {
    addPageToProject("StartPage");
    initByComponent();
    doTestBasicCompletionVariants(mergeArrays(CORE_5_1_0_5_PAGE_NAMES, "startpage"));
  }

  public void testIdAttrValue() throws Throwable {
    addComponentToProject("Count");
    initByComponent();
    doTestBasicCompletionVariants("index", "link2", "link3");
  }

  public void testAttrValueWithPropPrefix() throws Throwable {
    addComponentToProject("Count");
    initByComponent();
    doTestBasicCompletionVariants("prop:strProp.chars", "prop:strProp.bytes");
  }

  public void testTapestryAttrValue() throws Throwable {
    addComponentToProject("Count");
    initByComponent();
    doTestBasicCompletionVariants("dateProp", "strProp", "intFieldProp");
  }

  public void testTapestryMixinAttr() throws Throwable {
    addComponentToProject("Count");
    addMixinToProject("FooMixin");
    initByComponent();
    doTestBasicCompletionVariants("bar", "end", "mixins", "start", "t:id", "value");
  }

  public void testTapestryMixinTag() throws Throwable {
    addComponentToProject("Count");
    addMixinToProject("FooMixin");
    initByComponent();
    doTestBasicCompletionVariants(true, "p:bar");
  }

  public void testTagNameWithDoctypePresent() throws Throwable {
    initByComponent();
    doTestBasicCompletionVariants(mergeArrays(CORE_5_1_0_5_TAG_NAMES, "body", "head", getElementTagName()));
  }

  public void testTagNameWithDoctypeAndExplicitHtmlNSPresent() throws Throwable {
    initByComponent();
    doTestBasicCompletionVariants(mergeArrays(CORE_5_1_0_5_TAG_NAMES, "body", "head", getElementTagName()));
  }

  public void testTelSecondSegmentAfterProp() throws Throwable {
    initByComponent();
    doTestBasicCompletionVariants("after", "before", "class", "clone", "compareTo", "compareTo", "date", "day", "equals", "getClass",
                                  "getDate", "getDay", "getHours", "getMinutes", "getMonth", "getSeconds", "getTime", "getTimezoneOffset",
                                  "getYear", "hashCode", "hours", "minutes", "month", "seconds", "setDate", "setHours", "setMinutes",
                                  "setMonth", "setSeconds", "setTime", "setYear", "time", "timezoneOffset", "toGMTString", "toLocaleString",
                                  "toString", "year");
  }

  public void testTelFirstSegment() throws Throwable {
    initByComponent();
    doTestBasicCompletionVariants("class", "currentTime", "equals", "getClass", "getCurrentTime", "getSomeProp", "hashCode", "setSomeProp",
                                  "someProp", "toString");
  }

  public void testTelSetterByProperty() throws Throwable {
    initByComponent();
    myFixture.complete(CompletionType.BASIC);
    checkResultByFile();
  }

  public void testTelPropertyByGetter() throws Throwable {
    CamelHumpMatcher.forceStartMatching(getTestRootDisposable());
    initByComponent();
    myFixture.complete(CompletionType.BASIC);
    checkResultByFile();
  }

  void doTestBasicCompletionVariants(@NonNls String... expectedItems) throws Throwable {
    doTestBasicCompletionVariants(false, expectedItems);
  }

  void doTestBasicCompletionVariants(boolean contains, @NonNls String... expectedItems) throws Throwable {
    doTestCompletionVariants(CompletionType.BASIC, contains, expectedItems);
  }

  void doTestCompletionVariants(final CompletionType type, boolean contains, @NonNls String... expectedItems) throws Throwable {
    final LookupElement[] items = myFixture.complete(type);
    Assert.assertNotNull("No lookup was shown, probably there was only one lookup element that was inserted automatically", items);
    if (!contains) {
      UsefulTestCase.assertSameElements(myFixture.getLookupElementStrings(), expectedItems);
      return;
    }
    final Set<String> elements = new THashSet<String>(myFixture.getLookupElementStrings());
    for (String expectedItem : expectedItems) {
      assertTrue(expectedItem + " not found", elements.contains(expectedItem));
    }
  }

  @Override
  protected String getBasePath() {
    return "completion/";
  }

  static final String[] CORE_5_1_0_5_PAGE_NAMES =
    {"exceptionreport", "propertydisplayblocks", "propertyeditblocks", "servicestatus"};
  static final String[] CORE_5_1_0_5_ELEMENT_NAMES =
    {"actionlink", "addrowlink", "ajaxformloop", "any", "beandisplay", "beaneditform", "beaneditor", "block", "body", "checkbox",
      "container", "datefield", "delegate", "errors", "eventlink", "exceptiondisplay", "form", "formfragment", "forminjector", "grid",
      "gridcell", "gridcolumns", "gridpager", "gridrows", "hidden", "if", "label", "linksubmit", "loop", "output", "outputraw", "pagelink",
      "palette", "parameter", "passwordfield", "progressivedisplay", "propertydisplay", "propertyeditor", "radio", "radiogroup",
      "removerowlink", "renderobject", "select", "submit", "submitnotifier", "textarea", "textfield", "textoutput", "unless", "zone"};
  static final String[] CORE_5_1_0_5_TAG_NAMES;

  static {
    CORE_5_1_0_5_TAG_NAMES = new String[CORE_5_1_0_5_ELEMENT_NAMES.length];
    for (int i = 0; i < CORE_5_1_0_5_TAG_NAMES.length; i++) {
      CORE_5_1_0_5_TAG_NAMES[i] = "t:" + CORE_5_1_0_5_ELEMENT_NAMES[i];
    }
  }

  static final String[] HTML_TAG_NAMES =
    {"a", "abbr", "acronym", "address", "applet", "area", "b", "base", "basefont", "bdo", "big", "blockquote", "body", "br", "button",
      "caption", "center", "cite", "code", "col", "colgroup", "dd", "del", "dfn", "dir", "div", "dl", "dt", "em", "embed", "fieldset",
      "font", "form", "h1", "h2", "h3", "h4", "h5", "h6", "head", "hr", "html", "i", "iframe", "img", "input", "ins", "isindex", "kbd",
      "label", "legend", "li", "link", "map", "menu", "meta", "noframes", "noscript", "object", "ol", "optgroup", "option", "p", "param",
      "pre", "q", "s", "samp", "script", "select", "small", "span", "strike", "strong", "style", "sub", "sup", "table", "tbody", "td",
      "textarea", "tfoot", "th", "thead", "title", "tr", "tt", "u", "ul", "var"};

  static final String[] HTML_AND_CORE_5_1_0_5_TAG_NAMES = mergeArrays(CORE_5_1_0_5_TAG_NAMES, HTML_TAG_NAMES);
}
