<<<<<<< HEAD
# git24j-maven-example
Example that demostrate how to use git24j in a maven project


1. Add maven dependencies

Add the following to pom.xml dependencies section.
```
<dependency>
    <groupId>com.github.git24j</groupId>
    <artifactId>git24j</artifactId>
    <version>1.0.0</version>
</dependency>
```

Then run `mvn install` to install the dependency, to verify installation, you can run the following command and see if git24 is installed:
```
$ mvn dependency:list
...
[INFO] The following files have been resolved:
[INFO]    com.github.git24j:git24j:jar:1.0.0:compile
...
```

2. Use git24j project without linking shared library.

`git24j` is a jni library it relies on two native compiled shared libraries: `libgit2` and `libgit24j` respectively.
Before compiling & installing these two native libraries, we can take palying around with the library that does not
invoke the shared library. One good example is the usage of `Oid`, for example:

```
Oid oid = Oid.of("0123456789012345678901234567890123456789");
System.out.println(oid);
```

