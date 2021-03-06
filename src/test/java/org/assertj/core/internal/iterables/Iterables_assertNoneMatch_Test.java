/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2016 the original author or authors.
 */
package org.assertj.core.internal.iterables;

import java.util.List;
import java.util.function.Predicate;

import org.assertj.core.internal.IterablesBaseTest;
import org.junit.Test;

import static org.assertj.core.error.NoElementsShouldMatch.noElementsShouldMatch;
import static org.assertj.core.test.TestData.someInfo;
import static org.assertj.core.test.TestFailures.failBecauseExpectedAssertionErrorWasNotThrown;
import static org.assertj.core.util.Lists.newArrayList;
import static org.mockito.Mockito.verify;

/**
 * @author Filip Hrisafov
 */
public class Iterables_assertNoneMatch_Test extends IterablesBaseTest {

  @Test
  public void should_pass_if_each_element_does_not_satisfy_the_predicate() {
    List<String> actual = newArrayList("123", "1234", "12345");
    iterables.assertNoneMatch(someInfo(), actual, s -> s.length() < 3);
  }

  @Test
  public void should_throw_error_if_predicate_is_null() {
    thrown.expectNullPointerException("The predicate to evaluate should not be null");
    iterables.assertNoneMatch(someInfo(), actual, null);
  }

  @Test
  public void should_fail_if_predicate_is_met() {
    List<String> actual = newArrayList("Luke", "Leia", "Yoda");
    Predicate<? super String> predicate = s -> s.startsWith("L");
    try {
      iterables.assertNoneMatch(info, actual, predicate);
    } catch (AssertionError e) {
      verify(failures).failure(info, noElementsShouldMatch(actual, "Luke"));
      return;
    }
    failBecauseExpectedAssertionErrorWasNotThrown();
  }

}
