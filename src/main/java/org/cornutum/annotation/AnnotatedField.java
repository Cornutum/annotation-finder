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
 * Defines a field that references a specified annotation.
 */
public class AnnotatedField extends Annotated
  {
  /**
   * Creates a new AnnotatedField instance.
   */
  public AnnotatedField( String annotation, String className, String field, boolean isRuntime)
    {
    this( annotation, className, field, isRuntime, null);
    }

  
  /**
   * Creates a new AnnotatedField instance.
   */
  public AnnotatedField( String annotation, String className, String field, boolean isRuntime, File file)
    {
    super( annotation, className, isRuntime, file);
    field_ = field;
    }
  
  /**
   * Creates a new AnnotatedField instance.
   */
  public AnnotatedField( Class<? extends Annotation> annotation, String className, String field, boolean isRuntime, File file)
    {
    this( Optional.ofNullable( annotation).map( Class::getName).orElse( null), className, field, isRuntime, file);
    }

  /**
   * Return the class element type.
   */
  public Type getType()
    {
    return Type.FIELD;
    }

  /**
   * Returns the field that references the annotation.
   */
  public String getField()
    {
    return field_;
    }

  public int hashCode()
    {
    return
      super.hashCode()
      ^ Objects.hashCode( getField())
      ;
    }

  public boolean equals( Object object)
    {
    AnnotatedField other = 
      super.equals( object)
      ? (AnnotatedField) object
      : null;

    return
      other != null
      && Objects.equals( other.getField(), getField());
    }

  public String toString()
    {
    return
      ToString.of( this)
      .append( ToString.simpleClassName( getAnnotation()))
      .append( "class", getClassName())
      .append( "field", getField())
      .append( "runtime", isRuntime())
      .append( "file", Optional.ofNullable( getFile()).map( File::getName).orElse( null))
      .toString();
    }

  private String field_;
  }
