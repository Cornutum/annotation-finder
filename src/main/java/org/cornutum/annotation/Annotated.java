//////////////////////////////////////////////////////////////////////////////
// 
//                    Copyright 2025, Cornutum Project
//                             www.cornutum.org
//
//////////////////////////////////////////////////////////////////////////////

package org.cornutum.annotation;

import java.io.File;
import java.util.Objects;
import java.util.Optional;

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
  protected Annotated( String annotation, String className, boolean isRuntime, File file)
    {
    annotation_ = annotation;
    className_ = className;
    runtime_ = isRuntime;
    file_ = file;
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

  /**
   * Changes the file containing the annotated class.
   */
  void setFile( File file)
    {
    file_ = file;
    }

  /**
   * Returns the file containing the annotated class.
   */
  public File getFile()
    {
    return file_;
    }

  public int hashCode()
    {
    return
      Objects.hashCode( getClass())
      ^ Objects.hashCode( getAnnotation())
      ^ Objects.hashCode( getClassName())
      ^ Objects.hashCode( isRuntime())
      ^ Objects.hashCode( getFile())
      ;
    }

  public boolean equals( Object object)
    {
    return
      Optional.ofNullable( object)
      .filter( other -> getClass().equals( other.getClass()))
      .map( other -> (Annotated) other)
      .map( other ->
            Objects.equals( other.getAnnotation(), getAnnotation())
            && Objects.equals( other.getClassName(), getClassName())
            && Objects.equals( other.isRuntime(), isRuntime())
            && Objects.equals( other.getFile(), getFile()))
      .orElse( false);
    }

  private final String annotation_;
  private final String className_;
  private final boolean runtime_;
  private File file_;
  }
