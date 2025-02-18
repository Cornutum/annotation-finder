//////////////////////////////////////////////////////////////////////////////
// 
//                    Copyright 2025, Cornutum Project
//                             www.cornutum.org
//
//////////////////////////////////////////////////////////////////////////////

package org.cornutum.annotation;

/**
 * Defines a class element that references a specified annotation.
 */
public abstract class Annotated
  {
  public enum Type { CLASS, METHOD, FIELD}

  /**
   * Returns true if the annotation is referenced by a class.
   */
  public static boolean isClass( Annotated annotated)
    {
    return annotated.getType() == Type.CLASS;
    }

  /**
   * Returns true if the annotation is referenced by a method.
   */
  public static boolean isMethod( Annotated annotated)
    {
    return annotated.getType() == Type.METHOD;
    }
  
  /**
   * Returns true if the annotation is referenced by a field.
   */
  public static boolean isField( Annotated annotated)
    {
    return annotated.getType() == Type.FIELD;
    }

  /**
   * Creates a new Annotated instance.
   */
  protected Annotated( String annotation, String className, boolean isRuntime)
    {
    annotation_ = annotation;
    className_ = className;
    runtime_ = isRuntime;
    }

  /**
   * Return the class element type.
   */
  public abstract Type getType();

  /**
   * Returns class name of the annotation referenced.
   */
  public String getAnnotation()
    {
    return annotation_;
    }

  /**
   * Returns the annotated class name.
   */
  public String getClassName()
    {
    return className_;
    }

  /**
   * Returns if this annotation is available at runtime.
   */
  public boolean isRuntime()
    {
    return runtime_;
    }
  
  private final String annotation_;
  private final String className_;
  private final boolean runtime_;
  }
