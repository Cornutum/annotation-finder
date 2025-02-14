//////////////////////////////////////////////////////////////////////////////
// 
//                    Copyright 2025, Cornutum Project
//                             www.cornutum.org
//
//////////////////////////////////////////////////////////////////////////////

package org.cornutum.annotation;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;

/**
 * Returns selected {@link Annotated} instances from a class path element.
 */
public class ClassPathAnnotated extends FlatMapIterator<Annotated,File>
  {
  /**
   * Creates a new ClassPathAnnotated instance.
   */
  public ClassPathAnnotated( Collection<File> classPath, AnnotationFilter filter)
    {
    super( classPath);
    filter_ = filter;
    }

  /**
   * Maps the given file to a sequence of {@link Annotated} instances.
   */
  protected Iterator<Annotated> map( File file)
    {
    return
      file.isDirectory()?
      new DirectoryAnnotated( file, filter_) :
      
      file.getName().endsWith( ".jar")?
      new JarAnnotated( file, filter_) :

      new ClassFileData( file).getAnnotated( filter_); 
    }

  /**
   * Returns true if this file will be included in the mapping.
   */
  @Override
  protected boolean accept( File file)
    {
    return
      file.isDirectory()
      || file.getName().endsWith( ".jar")
      || file.getName().endsWith( ".class");
    }

  private AnnotationFilter filter_;
  }
