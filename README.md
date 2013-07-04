Maven Enforcer rule for windows executables
===========================================
This simple project adds a custom enforcer rule that can be applied when your maven based build relies on windows executables and you want to make sure that you are using the correct version.

Usage
-----
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-enforcer-plugin</artifactId>
                <version>1.2</version>
                <dependencies>
                    <dependency>
                        <groupId>com.omrispector.mvn.enforce</groupId>
                        <artifactId>exe-version</artifactId>
                        <version>1.0</version>
                    </dependency>
                </dependencies>
                <executions>
                    <execution>
                        <id>enforce</id>
                        <configuration>
                            <rules>
                                <exeVersionRule implementation="com.omrispector.maven.enforce.ExeVersionRule">
                                    <component>Excel</component>
                                    <checkOn>C:\Program Files (x86)\Microsoft Office\Office12\excel.exe</checkOn>
                                    <version>[12.0,12.2)</version>
                                </exeVersionRule>
                            </rules>
                        </configuration>
                        <goals>
                            <goal>enforce</goal>
                        </goals>

                    </execution>
                </executions>
            </plugin>

Version Semantics
-----------------
The version semantics are taken from http://maven.apache.org/enforcer/enforcer-rules/versionRanges.html

Enjoy.