# git24j-maven-example
Example that demostrate how to use git24j in a maven project


### 1. Add maven dependencies

Add the following to pom.xml dependencies section (find recent version from [this link](https://search.maven.org/artifact/com.github.git24j/git24j)):
```
<dependency>
    <groupId>com.github.git24j</groupId>
    <artifactId>git24j</artifactId>
    <version>1.0.0.20200805</version>
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

### 2. Use git24j project without linking shared library.

`git24j` is a jni library it relies on two native compiled shared libraries: `libgit2` and `libgit24j` respectively.
Before compiling & installing these two native libraries, we can take palying around with the library that does not
invoke the shared library. One good example is the usage of `Oid`, for example:

```
Oid oid = Oid.of("0123456789012345678901234567890123456789");
System.out.println(oid);
```

### 3. Install and initialize the shared libraries
In this step, we will try to make the following code work:
```java
try (Repository repo = Repository.open(repoDir)){
    System.out.println(repo.workdir());
}
```

without the shared libraries `libgit2` and `libgit24j`, the above code will fail with errors like the following:
```
Exception in thread "main" java.lang.UnsatisfiedLinkError: com.github.git24j.core.Repository.jniOpen(Ljava/util/concurrent/atomic/AtomicLong;Ljava/lang/String;)I
	at com.github.git24j.core.Repository.jniOpen(Native Method)
	at com.github.git24j.core.Repository.open(Repository.java:121)
	at com.github.git24j.example.Main.main(Main.java:15)
```

To build the native libraries:

```
# at the root of this example repo
$ git clone https://github.com/git24j/git24j.git dist/git24j
$ git -C dist/git24j/ submodule sync --recursive
$ git -C dist/git24j/ submodule update --init --recursive
$ make -C dist/git24j/ 
```

Now you should find the following built share-libraries:

> dist/git24j/target/git24j/libgit24j.so (linux) 
> dist/git24j/target/git2/libgit2.so (linux)
or 
> dist/git24j/target/git24j/libgit24j.dylib (MacOs) 
> dist/git24j/target/git2/libgit2.dylib (MacOs)

And we can load them and init Libgit2
```
Path distTarget = Paths.get("dist/git24j/target");
Init.loadLibraries(distTarget.resolve("git2"), distTarget.resolve("git24j"));
Libgit2.init();
```

