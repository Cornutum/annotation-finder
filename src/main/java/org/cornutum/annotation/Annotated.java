//////////////////////////////////////////////////////////////////////////////
// 
//                    Copyright 2025, Cornutum Project
//                             www.cornutum.org
//
//////////////////////////////////////////////////////////////////////////////

package org.cornutum.annotation;

import java.lang.annotation.Annotation;

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
  protected Annotated( Class<? extends Annotation> annotation, String className)
    {
    annotation_ = annotation;
    className_ = className;
    }

  /**
   * Return the class element type.
   */
  public abstract Type getType();

  /**
   * Returns the annotation refer
   */
  public Class<? extends Annotation> getAnnotation()
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

  private Class<? extends Annotation> annotation_;
  private String className_;
  }
