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
    String classFile = String.format( "%s.class", getClass().getSimpleName());
    ClassData classData = new ClassFileData( getResourceFile( getClass(), classFile));
    AnnotationFilter filter = new SimpleAnnotationFilter( FixMethodOrder.class, Test.class);

    // When..
    List<Annotated> annotated =
      toStream( classData.getAnnotated( filter))
      .collect( toList());

    // Then...
    assertThat(
      "Annotated",
      annotated,
      contains(
        new AnnotatedMethod( Test.class, getClass().getName(), "whenClassFile", true),
        new AnnotatedClass( FixMethodOrder.class, getClass().getName(), true)));
    }

  @Deprecated
  public String stringField;
  }
