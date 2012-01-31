package com.intellij.flex.uiDesigner {
import com.intellij.flex.uiDesigner.flex.MainFocusManagerSB;

import flash.display.DisplayObject;
import flash.display.Sprite;

[Abstract]
public class AbstractDocumentDisplayManager extends Sprite {
  protected var _document:DisplayObject;
  public function get document():Object {
    return _document;
  }

  // requires only for error reporting
  protected var _documentFactory:Object;
  public function get documentFactory():Object {
    return _documentFactory;
  }

  public function init(moduleFactory:Object, uiErrorHandler:UiErrorHandler,
                         mainFocusManager:MainFocusManagerSB, documentFactory:Object):void {
    _documentFactory = documentFactory;
  }

  protected var _explicitDocumentWidth:int = -1;
  protected var _explicitDocumentHeight:int = -1;

  public function get explicitDocumentWidth():int {
    return _explicitDocumentWidth;
  }

  public function get explicitDocumentHeight():int {
    return _explicitDocumentHeight;
  }

  protected static function initialExplicitDimension(dimension:Number):int {
    return dimension == 0 || dimension != dimension ? -1 : dimension;
  }

  public function setDocumentBounds(w:int, h:int):void {
    // originally set by setLayoutBoundsSize, but the Application without explicit size hangs on Stage and listen to resize - we can not change this behavior without the injection of the byte-code
    _document.width = w;
    _document.height = h;
  }

  public function addRealEventListener(type:String, listener:Function, useCapture:Boolean = false):void {
    super.addEventListener(type, listener, useCapture);
  }

  public function removeRealEventListener(type:String, listener:Function):void {
    super.removeEventListener(type, listener);
  }
}
}
