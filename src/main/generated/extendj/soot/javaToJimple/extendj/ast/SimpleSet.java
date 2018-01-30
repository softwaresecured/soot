package soot.javaToJimple.extendj.ast;

import java.util.HashSet;
import java.util.LinkedHashSet;
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
import soot.validation.ValidationException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import beaver.*;
import soot.coffi.CoffiMethodSource;
/**
 * @ast interface
 * @aspect DataStructures
 * @declaredat /home/olivier/projects/extendj/java4/frontend/DataStructures.jrag:40
 */
public interface SimpleSet<T> extends Iterable<T> {

     
    int size();


     

    boolean isEmpty();


     

    SimpleSet<T> add(T o);


     

    Iterator<T> iterator();


    /** @return {@code true} if this set contains the given object. */
     

    /** @return {@code true} if this set contains the given object. */
    boolean contains(Object o);


    /** @return {@code true} if this set is a singleton set (size == 1). */
     

    /** @return {@code true} if this set is a singleton set (size == 1). */
    boolean isSingleton();


    /** @return {@code true} if this set is a singleton set containing the given object. */
     

    /** @return {@code true} if this set is a singleton set containing the given object. */
    boolean isSingleton(T o);


    /** @return the single value in the set. Throws an error if the set is not a singleton. */
     

    /** @return the single value in the set. Throws an error if the set is not a singleton. */
    T singletonValue();


     

    SimpleSet<Object> EMPTY_SET = new SimpleSet<Object>() {
      @Override
      public int size() {
        return 0;
      }

      @Override
      public boolean isEmpty() {
        return true;
      }

      @Override
      public SimpleSet<Object> add(Object o) {
        if (o instanceof SimpleSet) {
          return (SimpleSet<Object>) o;
        }
        return new SimpleSetImpl<Object>(o);
      }

      @Override
      public boolean contains(Object o) {
        return false;
      }

      @Override
      public Iterator<Object> iterator() {
        return (Iterator<Object>) Collections.EMPTY_LIST.iterator();
      }

      @Override
      public boolean isSingleton() {
        return false;
      }

      @Override
      public boolean isSingleton(Object o) {
        return false;
      }

      @Override
      public Object singletonValue() {
        throw new Error("The empty set has no singleton value.");
      }

      @Override
      public boolean equals(Object o) {
        if (o == this) {
          return true;
        }
        if (o instanceof SimpleSet) {
          return ((SimpleSet) o).isEmpty();
        }
        return false;
      }
    };


     

    SimpleSet<Object> fullSet = new SimpleSet<Object>() {
      @Override
      public int size() {
        throw new UnsupportedOperationException("The full set does not have a size.");
      }

      @Override
      public boolean isEmpty() {
        return false;
      }

      @Override
      public SimpleSet<Object> add(Object o) {
        return this;
      }

      @Override
      public boolean contains(Object o) {
        return true;
      }

      @Override
      public Iterator<Object> iterator() {
        throw new UnsupportedOperationException("The full set can not be iterated.");
      }

      @Override
      public boolean isSingleton() {
        return false;
      }

      @Override
      public boolean isSingleton(Object o) {
        return false;
      }

      @Override
      public Object singletonValue() {
        throw new Error("The full set has no singleton value.");
      }

      @Override
      public boolean equals(Object o) {
        return o == fullSet;
      }
    };


     

    class SimpleSetImpl<T> implements SimpleSet<T> {
      private java.util.Set<T> internalSet;

      public SimpleSetImpl() {
        internalSet = new LinkedHashSet<T>(4);
      }

      public SimpleSetImpl(T a) {
        internalSet = Collections.singleton(a);
      }

      public SimpleSetImpl(T a, T b) {
        internalSet = new LinkedHashSet<T>(2);
        internalSet.add(a);
        internalSet.add(b);
      }

      public SimpleSetImpl(T... set) {
        internalSet = new LinkedHashSet<T>(set.length);
        for (T item : set) {
          internalSet.add(item);
        }
      }

      public SimpleSetImpl(java.util.Collection<? extends T> c) {
        internalSet = new LinkedHashSet<T>(c);
      }

      private SimpleSetImpl(SimpleSetImpl<? extends T> set) {
        this.internalSet = new LinkedHashSet<T>(set.internalSet);
      }

      @Override
      public int size() {
        return internalSet.size();
      }

      @Override
      public boolean isEmpty() {
        return internalSet.isEmpty();
      }

      @Override
      public SimpleSet<T> add(T o) {
        if (internalSet.contains(o)) {
          return this;
        }
        SimpleSetImpl<T> set = new SimpleSetImpl<T>(this);
        set.internalSet.add(o);
        return set;
      }

      @Override
      public Iterator<T> iterator() {
        return internalSet.iterator();
      }

      @Override
      public boolean contains(Object o) {
        return internalSet.contains(o);
      }

      @Override
      public boolean isSingleton() {
        return internalSet.size() == 1;
      }

      @Override
      public boolean isSingleton(T o) {
        return isSingleton() && contains(o);
      }

      @Override
      public T singletonValue() {
        if (!isSingleton()) {
          throw new Error("This set has no singleton value.");
        }
        return internalSet.iterator().next();
      }

      @Override
      public boolean equals(Object o) {
        if (o instanceof SimpleSet) {
          SimpleSet<?> other = (SimpleSet<?>) o;
          if (size() != other.size()) {
            return false;
          }
          return isEqualCollection(this, other);
        } else {
          return false;
        }
      }

      // See http://llbit.se/?p=2009 for algorithm description.
      private static boolean isEqualCollection(Iterable<?> a, Iterable<?> b) {
        Map<Object, Integer> map = new java.util.IdentityHashMap<Object, Integer>();
        for (Object o : a) {
          Integer val = map.get(o);
          int count = (val == null) ? 0 : val;
          map.put(o, count + 1);
        }
        for (Object o : b) {
          Integer val = map.get(o);
          int count;
          if (val != null) {
            count = val;
            if (count == 0) {
              return false;
            }
          } else {
            return false;
          }
          map.put(o, count - 1);
        }
        for (Integer count : map.values()) {
          if (count != 0) {
            return false;
          }
        }
        return true;
      }
    }
}
