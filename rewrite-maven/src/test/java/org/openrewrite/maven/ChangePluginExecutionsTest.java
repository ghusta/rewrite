/*
 * Copyright 2021 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openrewrite.maven;

import org.junit.jupiter.api.Test;
import org.openrewrite.DocumentExample;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.maven.Assertions.pomXml;

class ChangePluginExecutionsTest implements RewriteTest {

    @DocumentExample
    @Test
    void removeExecutions() {
        rewriteRun(
          spec -> spec.recipe(new ChangePluginExecutions("org.openrewrite.maven", "rewrite-maven-plugin", null)),
          pomXml(
            """
              <project>
                  <groupId>org.example</groupId>
                  <artifactId>foo</artifactId>
                  <version>1.0</version>

                  <build>
                      <plugins>
                          <plugin>
                              <groupId>org.openrewrite.maven</groupId>
                              <artifactId>rewrite-maven-plugin</artifactId>
                              <version>4.1.5</version>
                              <executions>
                                  <execution>
                                      <phase>validate</phase>
                                      <goals><goal>dryRun</goal></goals>
                                  </execution>
                              </executions>
                          </plugin>
                      </plugins>
                  </build>
              </project>
              """,
            """
              <project>
                  <groupId>org.example</groupId>
                  <artifactId>foo</artifactId>
                  <version>1.0</version>

                  <build>
                      <plugins>
                          <plugin>
                              <groupId>org.openrewrite.maven</groupId>
                              <artifactId>rewrite-maven-plugin</artifactId>
                              <version>4.1.5</version>
                          </plugin>
                      </plugins>
                  </build>
              </project>
              """
          )
        );
    }

    @Test
    void addExecutions() {
        rewriteRun(
          spec -> spec.recipe(new ChangePluginExecutions(
            "org.openrewrite.maven",
            "rewrite-maven-plugin",
            "<execution>\n<phase>validate</phase>\n<goals><goal>dryRun</goal></goals>\n</execution>")),
          pomXml(
            """
              <project>
                  <groupId>org.example</groupId>
                  <artifactId>foo</artifactId>
                  <version>1.0</version>

                  <build>
                      <plugins>
                          <plugin>
                              <groupId>org.openrewrite.maven</groupId>
                              <artifactId>rewrite-maven-plugin</artifactId>
                              <version>4.1.5</version>
                          </plugin>
                      </plugins>
                  </build>
              </project>
              """,
            """
              <project>
                  <groupId>org.example</groupId>
                  <artifactId>foo</artifactId>
                  <version>1.0</version>

                  <build>
                      <plugins>
                          <plugin>
                              <groupId>org.openrewrite.maven</groupId>
                              <artifactId>rewrite-maven-plugin</artifactId>
                              <version>4.1.5</version>
                              <executions>
                                  <execution>
                                      <phase>validate</phase>
                                      <goals>
                                          <goal>dryRun</goal>
                                      </goals>
                                  </execution>
                              </executions>
                          </plugin>
                      </plugins>
                  </build>
              </project>
              """
          )
        );
    }

    @Test
    void replaceExecutions() {
        rewriteRun(
          spec -> spec.recipe(new ChangePluginExecutions(
            "org.openrewrite.maven",
            "rewrite-maven-plugin",
            "<execution>\n<phase>validate</phase>\n<goals><goal>dryRun</goal></goals>\n</execution>")),
          pomXml(
            """
              <project>
                  <groupId>org.example</groupId>
                  <artifactId>foo</artifactId>
                  <version>1.0</version>

                  <build>
                      <plugins>
                          <plugin>
                              <groupId>org.openrewrite.maven</groupId>
                              <artifactId>rewrite-maven-plugin</artifactId>
                              <version>4.1.5</version>
                              <executions />
                          </plugin>
                      </plugins>
                  </build>
              </project>
              """,
            """
              <project>
                  <groupId>org.example</groupId>
                  <artifactId>foo</artifactId>
                  <version>1.0</version>

                  <build>
                      <plugins>
                          <plugin>
                              <groupId>org.openrewrite.maven</groupId>
                              <artifactId>rewrite-maven-plugin</artifactId>
                              <version>4.1.5</version>
                              <executions>
                                  <execution>
                                      <phase>validate</phase>
                                      <goals>
                                          <goal>dryRun</goal>
                                      </goals>
                                  </execution>
                              </executions>
                          </plugin>
                      </plugins>
                  </build>
              </project>
              """
          )
        );
    }
}
