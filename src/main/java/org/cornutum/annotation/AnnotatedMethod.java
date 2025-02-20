//////////////////////////////////////////////////////////////////////////////
// 
//                    Copyright 2025, Cornutum Project
//                             www.cornutum.org
//
//////////////////////////////////////////////////////////////////////////////

package org.cornutum.annotation;

import java.io.File;
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
  public AnnotatedMethod( String annotation, String className, String method, boolean isRuntime)
    {
    this( annotation, className, method, isRuntime, null);
    }
  
  /**
   * Creates a new AnnotatedMethod instance.
   */
  public AnnotatedMethod( String annotation, String className, String method, boolean isRuntime, File file)
    {
    super( annotation, className, isRuntime, file);
    method_ = method;
    }
  
  /**
   * Creates a new AnnotatedMethod instance.
   */
  public AnnotatedMethod( Class<? extends Annotation> annotation, String className, String method, boolean isRuntime, File file)
    {
    this( Optional.ofNullable( annotation).map( Class::getName).orElse( null), className, method, isRuntime, file);
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
      super.hashCode()
      ^ Objects.hashCode( getMethod())
      ;
    }

  public boolean equals( Object object)
    {
    AnnotatedMethod other = 
      super.equals( object)
      ? (AnnotatedMethod) object
      : null;

    return
      other != null
      && Objects.equals( other.getMethod(), getMethod());
    }

  public String toString()
    {
    return
      ToString.of( this)
      .append( ToString.simpleClassName( getAnnotation()))
      .append( "class", getClassName())
      .append( "method", getMethod())
      .append( "runtime", isRuntime())
      .append( "file", Optional.ofNullable( getFile()).map( File::getName).orElse( null))
      .toString();
    }

  private String method_;
  }
