//////////////////////////////////////////////////////////////////////////////
// 
//                    Copyright 2025, Cornutum Project
//                             www.cornutum.org
//
//////////////////////////////////////////////////////////////////////////////

package org.cornutum.annotation;

import java.util.Optional;

/**
 * A standard <CODE>toString()</CODE> builder.
 */
public class ToString
  {

  /**
   * Returns a {@link ToString} builder for the given object.
   */
  public static ToString of( Object object)
    {
    return new ToString( object);
    }

  /**
   * Creates a new ToString instance.
   */
  public ToString( Object object)
    {
    if( object == null)
      {
      throw new IllegalArgumentException( "Object must be non-null");
      }
    object_ = object;
    }

  /**
   * Appends <CODE>String.valueOf( element)</CODE>.
   */
  public ToString append( Object element)
    {
    appendDelimiter();
    builder_.append( element);
    return this;
    }

  /**
   * Appends the given name/value pair.</CODE>.
   */
  public ToString append( String name, Object value)
    {
    appendDelimiter();
    builder_.append( name).append( '=').append( value);
    return this;
    }

  /**
   * Appends a field delimiter, if necessary.
   */
  private void appendDelimiter()
    {
    if( builder_.length() > 0)
      {
      builder_.append( ',');
      }
    }

  public String toString()
    {
    return String.format( "%s[%s]", object_.getClass().getSimpleName(), builder_.toString());
    }

  /**
   * Returns the simple name of the given class.
   */
  public static String simpleClassName( String className)
    {
    return
      Optional.ofNullable( className)
      .map( name -> name.substring( name.lastIndexOf( '.') + 1))
      .orElse( null);
    }

  final private Object object_;
  final private StringBuilder builder_ = new StringBuilder();
  }
