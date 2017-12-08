package soot.javaToJimple.extendj.ast;

import java.util.HashSet;
import java.util.*;
import java.util.ArrayList;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.jastadd.util.*;
import java.util.Collections;
import java.util.zip.*;
import java.io.*;
import java.util.Collection;
import org.jastadd.util.PrettyPrintable;
import org.jastadd.util.PrettyPrinter;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import soot.*;
import soot.util.*;
import soot.jimple.*;
import soot.coffi.ClassFile;
import soot.coffi.method_info;
import soot.coffi.CONSTANT_Utf8_info;
import soot.tagkit.SourceFileTag;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import beaver.*;
import soot.coffi.CoffiMethodSource;
/**
 * @ast class
 * @declaredat ASTNode$State:2
 */
public class ASTNode$State extends java.lang.Object {
  
  /** @apilevel internal */
  protected static class CircularValue {
    Object value;
    Cycle cycle;
  }

  

  /**
   * Instances of this class are used to uniquely identify circular evaluation cycles.
   * @apilevel internal
   */
  protected static class Cycle {
  }

  

  /** The cycle ID used outside of circular evaluation. */
  public static final Cycle NON_CYCLE = new Cycle();

  

  /**
   * Tracks the state of the current circular evaluation. This class defines a
   * stack structure where the next element on the stack is pointed to by the
   * {@code next} field.
   * @apilevel internal
   */
  protected static class CircleState {
    final CircleState next;
    boolean inCircle = false;
    boolean change = false;

    /** Evaluation depth of lazy attributes. */
    int lazyAttribute = 0;

    /** Cycle ID of the latest cycle in this circular evaluation. */
    Cycle cycle = NON_CYCLE;

    protected CircleState(CircleState next) {
      this.next = next;
    }
  }

  

  /** Sentinel circle state representing non-circular evaluation. */
  private static final CircleState CIRCLE_BOTTOM = new CircleState(null);

  

  /**
   * Current circular state.
   * @apilevel internal
   */
  private CircleState circle = CIRCLE_BOTTOM;

  

  /** @apilevel internal */
  protected boolean inCircle() {
    return circle.inCircle;
  }

  

  /** @apilevel internal */
  protected boolean calledByLazyAttribute() {
    return circle.lazyAttribute > 0;
  }

  

  /** @apilevel internal */
  protected void enterLazyAttribute() {
    circle.lazyAttribute += 1;
  }

  

  /** @apilevel internal */
  protected void leaveLazyAttribute() {
    circle.lazyAttribute -= 1;
  }

  

  /** @apilevel internal */
  protected void enterCircle() {
    CircleState next = new CircleState(circle);
    next.inCircle = true;
    circle = next;
  }

  

  /** @apilevel internal */
  protected void leaveCircle() {
    circle = circle.next;
  }

  

  /** @apilevel internal */
  protected Cycle nextCycle() {
    Cycle cycle = new Cycle();
    circle.cycle = cycle;
    return cycle;
  }

  

  /** @apilevel internal */
  protected Cycle cycle() {
    return circle.cycle;
  }

  

  /** @apilevel internal */
  protected CircleState currentCircle() {
    return circle;
  }

  


  /** @apilevel internal */
  protected void setChangeInCycle() {
    circle.change = true;
  }

  

  /** @apilevel internal */
  protected boolean testAndClearChangeInCycle() {
    boolean change = circle.change;
    circle.change = false;
    return change;
  }

  

  /** @apilevel internal */
  protected boolean changeInCycle() {
    return circle.change;
  }

  


  protected ASTNode$State() {
  }

  public void reset() {
    // Reset circular evaluation state.
    circle = CIRCLE_BOTTOM;
  }


}
