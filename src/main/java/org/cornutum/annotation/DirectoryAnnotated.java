//////////////////////////////////////////////////////////////////////////////
// 
//                    Copyright 2025, Cornutum Project
//                             www.cornutum.org
//
//////////////////////////////////////////////////////////////////////////////

package org.cornutum.annotation;

import java.io.File;
import java.util.Iterator;

/**
 * Returns selected {@link Annotated} instances from class files in the given directory.
 */
public class DirectoryAnnotated extends FlatMapIterator<Annotated,File>
  {
  /**
   * Creates a new DirAnnotated instance.
   */
  public DirectoryAnnotated( File dir, AnnotationFilter filter)
    {
    super( Files.classFiles( dir));
    dir_ = dir;
    filter_ = filter;
    }

  /**
   * Returns the directory for this iterator.
   */
  public File getDir()
    {
    return dir_;
    }

  /**
   * Maps the given file to a sequence of {@link Annotated} instances.
   */
  protected Iterator<Annotated> map( File file)
    {
    return new ClassFileData( file).getAnnotated( filter_);
    }

  public String toString()
    {
    return
      ToString.of( this)
      .append( getDir())
      .toString();
    }

  private final File dir_;
  private final AnnotationFilter filter_;
  }
