//////////////////////////////////////////////////////////////////////////////
// 
//                    Copyright 2025, Cornutum Project
//                             www.cornutum.org
//
//////////////////////////////////////////////////////////////////////////////

package org.cornutum.annotation;

import static org.cornutum.annotation.Iterators.toStream;
import static org.cornutum.annotation.TestFiles.*;

import org.junit.Test;
import org.junit.FixMethodOrder;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.runners.MethodSorters.NAME_ASCENDING;

import java.io.File;
import java.util.List;
import static java.util.stream.Collectors.toList;

/**
 * Runs tests for {@link ClassData}
 */
@FixMethodOrder( NAME_ASCENDING)
public class ClassDataTest
  {
  @Test
  public void whenClassFile()
    {
    // Given...
    String classFileName = String.format( "%s.class", getClass().getSimpleName());
    File classFile = getResourceFile( getClass(), classFileName);
    ClassData classData = new ClassFileData( classFile);
    AnnotationFilter filter = new PackageFilter( FixMethodOrder.class, Test.class);

    // When..
    List<Annotated> annotated =
      toStream( classData.getAnnotated( filter))
      .collect( toList());

    // Then...
    assertThat(
      "Annotated",
      annotated,
      contains(
        new AnnotatedMethod( Test.class, getClass().getName(), "whenClassFile", true, classFile),
        new AnnotatedClass( FixMethodOrder.class, getClass().getName(), true, classFile)));
    }

  @Deprecated
  public String stringField;
  }
