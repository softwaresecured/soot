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
 * @aspect ErrorCheck
 * @declaredat /home/olivier/projects/extendj/java4/frontend/ErrorCheck.jrag:110
 */
public class Problem extends java.lang.Object implements Comparable {
  
    public static class Severity {
      public static final Severity ERROR = new Severity("error");
      public static final Severity WARNING = new Severity("warning");

      private final String name;

      private Severity(String name) {
        this.name = name;
      }

      @Override
      public String toString() {
        return name;
      }
    }

  

    public static class Kind {
      public static final Kind OTHER = new Kind();
      public static final Kind LEXICAL = new Kind();
      public static final Kind SYNTACTIC = new Kind();
      public static final Kind SEMANTIC = new Kind();
      private Kind() { }
    }

  

    protected int line = -1;

  

    protected int column = -1;

  

    protected int endLine = -1;

  

    protected int endColumn = -1;

  

    protected String fileName;

  

    protected final String message;

  

    protected Severity severity = Severity.ERROR;

  

    protected Kind kind = Kind.OTHER;

  

    public Problem(String fileName, String message, int line, Severity severity, Kind kind) {
      this.fileName = fileName;
      this.message = message;
      this.line = line;
      this.kind = kind;
      this.severity = severity;
    }

  

    public Problem(String fileName, String message, int line, int column,
        Severity severity, Kind kind) {
      this.fileName = fileName;
      this.message = message;
      this.line = line;
      this.column = column;
      this.kind = kind;
      this.severity = severity;
    }

  

    public Problem(String fileName, String message, int line, int column, int endLine,
        int endColumn, Severity severity, Kind kind) {
      this.fileName = fileName;
      this.message = message;
      this.line = line;
      this.column = column;
      this.endLine = endLine;
      this.endColumn = endColumn;
      this.kind = kind;
      this.severity = severity;
    }

  

    public int line() {
      return line;
    }

  

    public int column() {
      return column;
    }

  

    public int endLine() {
      return endLine;
    }

  

    public int endColumn() {
      return endColumn;
    }

  

    public String fileName() {
      return fileName;
    }

  

    public void setFileName(String fileName) {
      this.fileName = fileName;
    }

  

    public String message() {
      return message;
    }

  

    public Severity severity() {
      return severity;
    }

  

    public Kind kind() {
      return kind;
    }

  

    @Override
    public String toString() {
      String location = "";
      if (line != -1 && column != -1) {
        location = line + "," + column + ":";
      } else if (line != -1) {
        location = line + ":";
      }
      return String.format("%s:%s %s: %s", fileName, location, severity, message);
    }

  

    @Override
    public int compareTo(Object o) {
      if (o instanceof Problem) {
        Problem other = (Problem) o;
        if (!fileName.equals(other.fileName)) {
          return fileName.compareTo(other.fileName);
        } else if (line != other.line) {
          // Using Integer.compare(int, int) breaks Java 6 builds.
          return Integer.valueOf(line).compareTo(other.line);
        } else {
          return message.compareTo(other.message);
        }
      }
      return 0;
    }


}
