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
 * Defines a class that references a specified annotation.
 */
public class AnnotatedClass extends Annotated
  {
  /**
   * Creates a new AnnotatedClass instance.
   */
  protected AnnotatedClass( String annotation, String className, boolean isRuntime)
    {
    super( annotation, className, isRuntime);
    }
  
  /**
   * Creates a new AnnotatedClass instance.
   */
  protected AnnotatedClass( Class<? extends Annotation> annotation, String className, boolean isRuntime)
    {
    this( Optional.ofNullable( annotation).map( Class::getName).orElse( null), className, isRuntime);
    }

  /**
   * Return the class element type.
   */
  public Type getType()
    {
    return Type.CLASS;
    }

  public int hashCode()
    {
    return
      Objects.hashCode( getClass())
      ^ Objects.hashCode( getAnnotation())
      ^ Objects.hashCode( getClassName())
      ^ Objects.hashCode( isRuntime())
      ;
    }

  public boolean equals( Object object)
    {
    return
      Optional.ofNullable( object)
      .filter( other -> getClass().equals( other.getClass()))
      .map( other -> (AnnotatedClass) other)
      .map( other ->
            Objects.equals( other.getAnnotation(), getAnnotation())
            && Objects.equals( other.getClassName(), getClassName())
            && Objects.equals( other.isRuntime(), isRuntime()))
      .orElse( false);
    }

  public String toString()
    {
    return
      ToString.of( this)
      .append( ToString.simpleClassName( getAnnotation()))
      .append( "class", getClassName())
      .append( "runtime", isRuntime())
      .toString();
    }
  }
