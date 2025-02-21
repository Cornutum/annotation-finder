# annotation-finder

[![Maven](https://img.shields.io/badge/maven-1.0.0-green.svg)](https://search.maven.org/search?q=annotation-finder)
[![Javadoc](https://img.shields.io/badge/javadoc-1.0.0-green.svg)](https://javadoc.io/doc/org.cornutum.annotation/finder/latest/index.html)

## Contents ##

  * [What's New?](#whats-new)
  * [What Is It?](#what-is-it)
  * [How Does It Work?](#how-does-it-work)
  * [Examples](#examples)
    * [Find all annotated elements in classes on the class path](#find-all-annotated-elements-in-classes-on-the-class-path)
    * [Find `Deprecated` methods in the system class path](#find-deprecated-methods-in-the-system-class-path)
    * [Find specific annotations in external JAR files](#find-specific-annotations-in-external-jar-files)
  * [FAQs](#faqs)

## What's New? ##

  * The latest version ([1.0.0](https://github.com/Cornutum/regexp-gen/releases/tag/release-1.0.0))
    is now available at the [Maven Central Repository](https://search.maven.org/search?q=annotation-finder).

## What Is It? ##

This project provides the Java class `org.cornutum.annotation.Finder`. A `Finder` offers an
interface to search Java class files for class elements with specific annotations. A `Finder` can
search for class files in class path elements like directories and JAR files. At runtime, these
class path elements might already be accessible via a `ClassLoader`, but that isn't required. For
example, you can use the `Finder` to locate class path elements to be loaded later.


## How Does It Work? ##

The result of `Finder.find()` is a `Stream` of `Annotated` objects. Each `Annotated` instance
identifies the `Annotation` class found, the class element that was annotated (class, method, or field),
the class containing the annotated element, and the location of the corresponding `*.class` file.

But which `Annotation` references does the `Finder` find? That depends on _where_ you look and
_what_ you look for.

The "where" is defined by the `Finder.inClasses()` method, which defines the class path elements
(directories, JAR files, or `*.class` files) to be searched.

The "what" is determined by the `AnnotationFilter` specified by the `Finder.filter()` method. An
`AnnotationFilter` specifies which `Annotation` classes to look for and which classes to include in
the search. By default, `Finder` uses a filter that returns all `Annotation` references in all
classes searched. But the `PackageFilter` provides more control by allowing you to specify exactly
which `Annotation` classes to look for and which Java packages will be included in the search.
You're free to use your own `AnnotationFilter` implementation to tailor the search more precisely.


## Examples ##

### Find all annotated elements in classes on the class path ###

```java
import org.cornutum.annotation.Annotated;
import org.cornutum.annotation.Finder;
import static org.cornutum.annotation.Files.classPathFor;

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
```

### Find `Deprecated` methods in the system class path ###

```java
import org.cornutum.annotation.Annotated;
import org.cornutum.annotation.Finder;
import org.cornutum.annotation.PackageFilter;

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
```

### Find specific annotations in external JAR files ###

```java
import org.cornutum.annotation.Annotated;
import org.cornutum.annotation.Finder;
import org.cornutum.annotation.PackageFilter;

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
```

## FAQs ##

  * **How do I add this as a dependency to my Maven project?**

    Add this to your project POM:

    ```xml
    <dependency>
      <groupId>org.cornutum.annotation</groupId>
      <artifactId>finder</artifactId>
      <version>...</version>
    </dependency>
    ```

  * **How can I run the examples?**

    Try running the [ExampleTest](https://github.com/Cornutum/annotation-finder/blob/master/src/test/java/org/cornutum/annotation/examples/ExampleTest.java).

  * **How does this compare with the `AnnotationDetector`?**

    The project was inspired by the [`AnnotationDetector`](https://github.com/rmuller/infomas-asl?tab=readme-ov-file#annotation-detector)
    from [Ronald Muller](https://www.linkedin.com/in/ronaldkmuller/) (XIAM Solutions BV) and reuses some of its code.
    Like the `AnnotationDetector`, the `Finder` is fast and light-weight, with no additional dependencies.
    But the `Finder` also offers some important new features.

    * **Stream interface**: Results can be handled using all of the capabilities of a Java `Stream`. In particular, you can use `findFirst()` to
      terminate the search when a specific element is found.

    * **Extensible filtering**: The "what" of the search is decoupled from the "where". You can easily inject your own `AnnotationFilter`
      implementation. You can use a `PackageFilter` to limit any search to specific packages.

    * **Decoupled from the class path**: You can find references to annotation classes that are not currently loaded. You can search
      directories and JAR files that are not currently loaded.

    * **Efficient search**: Large classes are searched without reading the entire class file into memory. During the search, at most one class file input stream
      is open.

