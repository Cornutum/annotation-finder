//////////////////////////////////////////////////////////////////////////////
// 
//                    Copyright 2025, Cornutum Project
//                             www.cornutum.org
//
//        Copyright (c) 2011 - 2013 XIAM Solutions B.V. (http://www.xiam.nl)
//
//////////////////////////////////////////////////////////////////////////////

package org.cornutum.annotation;

import java.io.BufferedInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * An interface to the data for a class definition.
 * <P/>
 * This borrows heavily from the <A href="https://github.com/rmuller/infomas-asl">AnnotationDetector</A>
 * provided by <A href="https://www.linkedin.com/in/ronaldkmuller/">Ronald Muller</A> of XIAM Solutions B.V.
 * (see https://github.com/rmuller/infomas-asl).
 */
public abstract class ClassData
  {
  /**
   * Returns the annotated elements found for this class.
   */
  public Iterator<Annotated> getAnnotated( AnnotationFilter filter)
    {
    setFilter( filter);
    annotated_.clear();
    
    try( DataInputStream data = new DataInputStream( new BufferedInputStream( getInputStream(), 16384)))
      {
      // Is this really a Java class file?
      if( data.readInt() == 0xCAFEBABE)
        {
        // Yes, read contents to find annotated elements.
        readVersion( data);
        setConstants( readConstantPoolEntries( data));
        readAccessFlags( data);

        // Does this class belong to an accepted package?
        Optional.of( readThisClass( data))
          .filter( className -> getFilter().acceptPackage( classPackage( className)))
          .ifPresent( className -> {
            // Yes, find annotated elements.
            findAnnotations( data, className);
            });
        }

      return annotated_.iterator();
      }
    catch( Exception e)
      {
      throw new IllegalStateException( String.format( "Can't read class data for %s", this), e);
      }
    }

  /**
   * Returns the raw type name recorded for the given type in a class file.
   */
  public static String rawTypeName( Class<?> type)
    {
    return String.format( "L%s;", type.getName().replace( '.', '/'));
    }

  /**
   * Returns the name of the package for the given class.
   */
  public static String classPackage( String className)
    {
    return className.substring( 0, className.lastIndexOf( '.'));
    }

  /**
   * Returns the class data input stream.
   */
  protected abstract InputStream getInputStream();

  private void findAnnotations( DataInput data, String className)
    {
    try
      {
      getContext().setClassName( className);
      readSuperClass( data);
      readInterfaces( data);
      readFields( data);
      readMethods( data);
      forType( Annotated.Type.CLASS, null, () -> readAttributes( data));
      }
    catch( Exception e)
      {
      throw new IllegalStateException( String.format( "Can't find annotations for class=%s", className), e);
      }
    }
  
  private void readVersion( DataInput data) throws IOException
    {
    // sequence: minor version, major version (argument_index is 1-based)
    data.readUnsignedShort();
    data.readUnsignedShort();
    }

  private Object[] readConstantPoolEntries( DataInput data) throws IOException
    {
    final int count = data.readUnsignedShort();
    Object[] constantPool = new Object[count];
    for( int i = 1; i < count; i += readConstantPoolEntry( data, constantPool, i));
    return constantPool;
    }

  /**
   * Return the number of slots read.
   */
  private int readConstantPoolEntry( DataInput data, Object[] constantPool, final int index) throws IOException
    {
    final int tag = data.readUnsignedByte();
    int numSlots = 1;
    switch (tag)
      {
      case CP_METHOD_TYPE:
        {
        data.skipBytes(2);  // readUnsignedShort()
        break;
        }
      case CP_METHOD_HANDLE:
        {
        data.skipBytes(3);
        break;
        }
      case CP_INTEGER:
      case CP_FLOAT:
      case CP_REF_FIELD:
      case CP_REF_METHOD:
      case CP_REF_INTERFACE:
      case CP_NAME_AND_TYPE:
      case CP_INVOKE_DYNAMIC:
        {
        data.skipBytes(4); // readInt() / readFloat() / readUnsignedShort() * 2
        break;
        }
      case CP_LONG:
      case CP_DOUBLE:
        {
        data.skipBytes(8); // readLong() / readDouble()
        numSlots = 2;
        break;
        }
      case CP_UTF8:
        {
        constantPool[index] = data.readUTF();
        break;
        }
      case CP_CLASS:
      case CP_STRING:
        {
        // reference to CP_UTF8 entry. The referenced index can have a higher number!
        constantPool[index] = data.readUnsignedShort();
        break;
        }
      default:
        {
        throw new ClassFormatError( "Unkown tag value for constant pool entry: " + tag);
        }
      }

    return numSlots;
    }

  private void readAccessFlags( DataInput data) throws IOException
    {
    data.skipBytes(2); // u2
    }

  private String readThisClass( DataInput data) throws IOException
    {
    return resolveUtf8( data).replace( '/', '.');
    }

  private void readSuperClass( DataInput data) throws IOException
    {
    data.skipBytes(2); // u2
    }

  private void readInterfaces( DataInput data) throws IOException
    {
    final int count = data.readUnsignedShort();
    data.skipBytes(count * 2); // count * u2
    }

  private void readFields( DataInput data) throws IOException
    {
    final int count = data.readUnsignedShort();
    for (int i = 0; i < count; ++i)
      {
      readAccessFlags( data);
      String fieldName = resolveUtf8( data);
      resolveUtf8( data); // descriptor
      forType( Annotated.Type.FIELD, fieldName, () -> readAttributes( data));
      }
    }

  private void readMethods( DataInput data) throws IOException
    {
    final int count = data.readUnsignedShort();
    for (int i = 0; i < count; ++i)
      {
      readAccessFlags( data);
      String methodName = resolveUtf8( data);
      resolveUtf8( data); // descriptor
      forType( Annotated.Type.METHOD, methodName, () -> readAttributes( data));
      }
    }

  private void readAttributes( DataInput data)
    {
    try
      {
      final int count = data.readUnsignedShort();
      for (int i = 0; i < count; ++i)
        {
        final String name = resolveUtf8( data);
        final int length = data.readInt();

        if( "RuntimeVisibleAnnotations".equals( name))
          {
          forRuntime( Boolean.TRUE, () -> readAnnotations( data));
          }
        else if( "RuntimeInvisibleAnnotations".equals( name))
          {
          forRuntime( Boolean.FALSE, () -> readAnnotations( data));
          }
        else
          {
          data.skipBytes(length);
          }
        }
      }
    catch( Exception e)
      {
      throw new IllegalStateException( "Can't read attributes", e);
      }
    }

  private void readAnnotations( DataInput data)
    {
    try
      {
      // the number of Runtime(In)VisibleAnnotations
      final int count = data.readUnsignedShort();
      for (int i = 0; i < count; ++i)
        {
        reportAnnotation( readAnnotation( data));
        }
      }
    catch( Exception e)
      {
      throw new IllegalStateException( "Can't read annotations", e);
      }
    }

  private String readAnnotation( DataInput data) throws IOException
    {
    final String rawTypeName = resolveUtf8( data);
    // num_element_value_pairs
    final int count = data.readUnsignedShort();
    for (int i = 0; i < count; ++i)
      {
      resolveUtf8( data);
      readAnnotationElementValue( data);
      }
    return rawTypeName;
    }

  private void readAnnotationElementValue( DataInput data) throws IOException
    {
    final int tag = data.readUnsignedByte();
    switch (tag)
      {
      case BYTE:
      case CHAR:
      case DOUBLE:
      case FLOAT:
      case INT:
      case LONG:
      case SHORT:
      case BOOLEAN:
      case STRING:
        data.skipBytes(2);
        break;
      case ENUM:
        data.skipBytes(4); // 2 * u2
        break;
      case CLASS:
        data.skipBytes(2);
        break;
      case ANNOTATION:
        readAnnotation( data);
        break;
      case ARRAY:
        final int count = data.readUnsignedShort();
        for (int i = 0; i < count; ++i)
          {
          readAnnotationElementValue( data);
          }
        break;
      default:
        throw new ClassFormatError("Not a valid annotation element type tag: 0x" + Integer.toHexString(tag));
      }
    }

  /**
   * Look up the String value, identified by the u2 index value from constant pool
   * (direct or indirect).
   */
  private String resolveUtf8( DataInput data) throws IOException
    {
    final Object value = getConstant( data.readUnsignedShort());

    return
      value.getClass().equals( String.class)
      ? (String) value
      : (String) getConstant( (Integer) value);
    }

  /**
   * Changes the {@link AnnotationFilter} for this class.
   */
  private void setFilter( AnnotationFilter filter)
    {
    filter_ = filter;
    }

  /**
   * Returns the {@link AnnotationFilter} for this class.
   */
  private AnnotationFilter getFilter()
    {
    return filter_;
    }

  /**
   * Returns the current context for recognizing annotations in this class.
   */
  private AnnotationContext getContext()
    {
    return context_;
    }

  /**
   * Performs the given step in the context of the given class element type.
   */
  private void forType( Annotated.Type type, String elementName, Runnable step)
    {
    AnnotationContext context = getContext();
    try
      {
      context.setType( type);
      context.setElement( elementName);
      step.run();
      }
    catch( Exception e)
      {
      throw new IllegalStateException( String.format( "Can't read attributes for type=%s", type), e);
      }
    finally
      {
      context.setType( null);
      context.setElement( null);
      }    
    }

  /**
   * Performs the given step in the context of the given runtime visibility.
   */
  private void forRuntime( Boolean isRuntime, Runnable step)
    {
    AnnotationContext context = getContext();
    try
      {
      context.setRuntime( isRuntime);
      step.run();
      }
    catch( Exception e)
      {
      throw new IllegalStateException( String.format( "Can't read annotations for runtime=%s", isRuntime), e);
      }
    finally
      {
      context.setRuntime( null);
      }    
    }

  /**
   * Report a reference to an annotation with the given raw type name.
   */
  private void reportAnnotation( String rawTypeName)
    {
    // Is this annotation reference is accepted by the filter?
    getFilter().acceptAnnotation( rawTypeName)
      .ifPresent( annotation -> {

        // Yes, return this reference
        AnnotationContext context = getContext();
        context.setAnnotation( annotation);
        annotated_.add( context.getAnnotated());

        context.setAnnotation( null);
        });
    }

  /**
   * Sets the class file constants pool.
   */
  private void setConstants( Object[] constants)
    {
    constants_ = constants;
    }

  /**
   * Returns the given element of the constants pool.
   */
  private Object getConstant( int i)
    {
    return constants_[i];
    }

  // Constant pool type tags
  private static final int CP_UTF8 = 1;
  private static final int CP_INTEGER = 3;
  private static final int CP_FLOAT = 4;
  private static final int CP_LONG = 5;
  private static final int CP_DOUBLE = 6;
  private static final int CP_CLASS = 7;
  private static final int CP_STRING = 8;
  private static final int CP_REF_FIELD = 9;
  private static final int CP_REF_METHOD = 10;
  private static final int CP_REF_INTERFACE = 11;
  private static final int CP_NAME_AND_TYPE = 12;
  private static final int CP_METHOD_HANDLE = 15;
  private static final int CP_METHOD_TYPE = 16;
  private static final int CP_INVOKE_DYNAMIC = 18;

  // AnnotationElementValue
  private static final int BYTE = 'B';
  private static final int CHAR = 'C';
  private static final int DOUBLE = 'D';
  private static final int FLOAT = 'F';
  private static final int INT = 'I';
  private static final int LONG = 'J';
  private static final int SHORT = 'S';
  private static final int BOOLEAN = 'Z';

  // Used for AnnotationElement only
  private static final int STRING = 's';
  private static final int ENUM = 'e';
  private static final int CLASS = 'c';
  private static final int ANNOTATION = '@';
  private static final int ARRAY = '[';

  private Object[] constants_;
  private AnnotationContext context_ = new AnnotationContext();
  private List<Annotated> annotated_ = new ArrayList<Annotated>();
  private AnnotationFilter filter_;

  /**
   * Represents the current context for recognizing annotations in this class.
   */
  private static class AnnotationContext
    {
    /**
     * Changes the annotation type for this annotation.
     */
    public void setAnnotation( Class<? extends Annotation> annotation)
      {
      annotation_ = annotation;
      }

    /**
     * Returns the annotation type for this annotation.
     */
    public Class<? extends Annotation> getAnnotation()
      {
      return annotation_;
      }
    
    /**
     * Changes the name of the annotated class.
     */
    public void setClassName( String className)
      {
      className_ = className;
      }

    /**
     * Returns the name of the annotated class.
     */
    public String getClassName()
      {
      return className_;
      }
    
    /**
     * Changes the type of the current annotation.
     */
    public void setType( Annotated.Type type)
      {
      type_ = type;
      }

    /**
     * Returns the type of the current annotation.
     */
    public Annotated.Type getType()
      {
      return type_;
      }
    
    /**
     * Changes the name of annotated class element.
     */
    public void setElement( String element)
      {
      elementName_ = element;
      }

    /**
     * Returns the name of annotated class element.
     */
    public String getElement()
      {
      return elementName_;
      }
    
    /**
     * Changes if this annotation is available at runtime.
     */
    public void setRuntime( Boolean runtime)
      {
      runtime_ = runtime;
      }

    /**
     * Returns if this annotation is available at runtime.
     */
    public Boolean isRuntime()
      {
      return runtime_;
      }

    /**
     * Returns the {@link Annotated} object for the current context.
     */
    public Annotated getAnnotated()
      {
      Class<? extends Annotation> annotation =
        Optional.ofNullable( getAnnotation())
        .orElseThrow( () -> new IllegalStateException( "Annotation type undefined for this annotation"));
      
      String className =
        Optional.ofNullable( getClassName())
        .orElseThrow( () -> new IllegalStateException( "Class undefined for this annotation"));

      boolean isRuntime =
        Optional.ofNullable( isRuntime())
        .orElseThrow( () -> new IllegalStateException( "Runtime availability undefined for this annotation"));

      Annotated annotated = null;
      switch( getType())
        {
        case CLASS:
          {
          annotated = new AnnotatedClass( annotation, className, isRuntime);
          break;
          }
        case METHOD:
          {
          annotated =
            new AnnotatedMethod(
              annotation,
              className,
              Optional.ofNullable( getElement()).orElseThrow( () -> new IllegalStateException( "Method undefined for this annotation")),
              isRuntime);
          break;
          }
        case FIELD:
          {
          annotated =
            new AnnotatedMethod(
              annotation,
              className,
              Optional.ofNullable( getElement()).orElseThrow( () -> new IllegalStateException( "Field undefined for this annotation")),
              isRuntime);
          break;
          }
        }
      
      return annotated;
      }

    private Class<? extends Annotation> annotation_;
    private Annotated.Type type_;
    private String className_;
    private String elementName_;
    private Boolean runtime_;
    }
  }
