# TuxCommand JDA
This is the TuxCommand JDA system. This is partly ready

## Maven
```xml
   <repository>
      <id>kingtux-repo</id>
      <url>http://repo.kingtux.me/repository/maven-public/</url>
    </repository>
    
    <dependency>
      <groupId>me.kingtux</groupId>
      <artifactId>tc-jda</artifactId>
      <!---Make sure you use Latest Version!-->
      <version>1.0-SNAPSHOT</version>
      <scope>compile</scope>
    </dependency>
```
## Gradle
```
repositories {
  maven { url 'http://repo.kingtux.me/repository/maven-public/' }
}

dependencies {
   compile "me.kingtux:tc-jda:1.0-SNAPSHOT"
}
```