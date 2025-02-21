//////////////////////////////////////////////////////////////////////////////
// 
//                    Copyright 2025, Cornutum Project
//                             www.cornutum.org
//
//////////////////////////////////////////////////////////////////////////////

package org.cornutum.annotation.examples;

import org.cornutum.annotation.Annotated;
import org.cornutum.annotation.Finder;
import org.cornutum.annotation.PackageFilter;
import static org.cornutum.annotation.Files.classPathFor;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import static org.cornutum.annotation.TestFiles.getResourceFile;

import java.util.stream.Stream;


/**
 * Runs {@link Finder} examples.
 */
public class ExampleTest
  {

  @Test
  public void findAllInClasspath()
    {
    // When...
    Stream<Annotated> stream =

      new Finder()

      // ...searching in: currently loaded resources in the "org.cornutum.annotation" package
      .inClasses( classPathFor( "org.cornutum.annotation"))

      // ...looking for: all annotations in all classes (the default)
      .find();

    // Then...
    printAnnotated( stream);
    }

  @Test
  public void findDeprecatedMethodsInSystemClassPath()
    {
    // When...
    Stream<Annotated> stream =

      new Finder()

      // ...searching in: all members of the class path used to start the JVM
      .inSystemClassPath()

      // ...looking for: Deprecated elements
      .filter( new PackageFilter( Deprecated.class))
      
      // ...returning: only annotated methods
      .find()
      .filter( Annotated::isMethod);

    // Then...
    printAnnotated( stream);
    }

  @Test
  public void findSpecificInJar()
    {
    // When...
    Stream<Annotated> stream =

      new Finder()

      // ...searching in: external JAR files not on the class path
      .inClasses(
        getResourceFile( getClass(), "tcases-openapi.jar"),
        getResourceFile( getClass(), "tcases-rest-assured.jar"))

      // ...looking for: specific annotations in certain packages
      .filter(
        new PackageFilter()
        .annotation(
          "org.cornutum.tcases.openapi.testwriter.ApiTestCaseWriter",
          "org.cornutum.tcases.openapi.testwriter.ApiTestWriter")
        .inPackage( "org.cornutum.tcases.openapi.restassured"))

      .find();

    // Then...
    printAnnotated( stream);
    }


  private void printAnnotated( Stream<Annotated> stream)
    {
    System.out.println();
    System.out.println( String.format( "%s:", testName.getMethodName()));
    stream.forEach( annotated -> System.out.println( String.format( "  %s", annotated)));
    }

  @Rule public TestName testName = new TestName();
  }
