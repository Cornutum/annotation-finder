//////////////////////////////////////////////////////////////////////////////
// 
//                    Copyright 2025, Cornutum Project
//                             www.cornutum.org
//
//////////////////////////////////////////////////////////////////////////////

package org.cornutum.annotation;

import java.lang.annotation.Annotation;
import java.util.Objects;
import java.util.Optional;

/**
 * Defines a method that references a specified annotation.
 */
public class AnnotatedMethod extends Annotated
  {
  /**
   * Creates a new AnnotatedMethod instance.
   */
  protected AnnotatedMethod( String annotation, String className, String method, boolean isRuntime)
    {
    super( annotation, className, isRuntime);
    method_ = method;
    }
  
  /**
   * Creates a new AnnotatedMethod instance.
   */
  protected AnnotatedMethod( Class<? extends Annotation> annotation, String className, String method, boolean isRuntime)
    {
    this( Optional.ofNullable( annotation).map( Class::getName).orElse( null), className, method, isRuntime);
    }

  /**
   * Return the class element type.
   */
  public Type getType()
    {
    return Type.METHOD;
    }

  /**
   * Returns the method that references the annotation.
   */
  public String getMethod()
    {
    return method_;
    }

  public int hashCode()
    {
    return
      Objects.hashCode( getClass())
      ^ Objects.hashCode( getAnnotation())
      ^ Objects.hashCode( getClassName())
      ^ Objects.hashCode( getMethod())
      ^ Objects.hashCode( isRuntime())
      ;
    }

  public boolean equals( Object object)
    {
    return
      Optional.ofNullable( object)
      .filter( other -> getClass().equals( other.getClass()))
      .map( other -> (AnnotatedMethod) other)
      .map( other ->
            Objects.equals( other.getAnnotation(), getAnnotation())
            && Objects.equals( other.getClassName(), getClassName())
            && Objects.equals( other.getMethod(), getMethod())
            && Objects.equals( other.isRuntime(), isRuntime()))
      .orElse( false);
    }

  public String toString()
    {
    return
      ToString.of( this)
      .append( ToString.simpleClassName( getAnnotation()))
      .append( "class", getClassName())
      .append( "method", getMethod())
      .append( "runtime", isRuntime())
      .toString();
    }

  private String method_;
  }
