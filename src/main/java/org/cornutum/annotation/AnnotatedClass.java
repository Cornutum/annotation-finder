//////////////////////////////////////////////////////////////////////////////
// 
//                    Copyright 2025, Cornutum Project
//                             www.cornutum.org
//
//////////////////////////////////////////////////////////////////////////////

package org.cornutum.annotation;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.Optional;

/**
 * Defines a class that references a specified annotation.
 */
public class AnnotatedClass extends Annotated
  {
  /**
   * Creates a new AnnotatedClass instance.
   */
  public AnnotatedClass( String annotation, String className, boolean isRuntime)
    {
    this( annotation, className, isRuntime, null);
    }
  
  /**
   * Creates a new AnnotatedClass instance.
   */
  public AnnotatedClass( String annotation, String className, boolean isRuntime, File file)
    {
    super( annotation, className, isRuntime, file);
    }
  
  /**
   * Creates a new AnnotatedClass instance.
   */
  public AnnotatedClass( Class<? extends Annotation> annotation, String className, boolean isRuntime, File file)
    {
    this( Optional.ofNullable( annotation).map( Class::getName).orElse( null), className, isRuntime, file);
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
    return super.hashCode();
    }

  public boolean equals( Object object)
    {
    return super.equals( object);
    }

  public String toString()
    {
    return
      ToString.of( this)
      .append( ToString.simpleClassName( getAnnotation()))
      .append( "class", getClassName())
      .append( "runtime", isRuntime())
      .append( "file", Optional.ofNullable( getFile()).map( File::getName).orElse( null))
      .toString();
    }
  }
