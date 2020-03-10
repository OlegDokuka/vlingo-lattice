// Copyright © 2012-2020 VLINGO LABS. All rights reserved.
//
// This Source Code Form is subject to the terms of the
// Mozilla Public License, v. 2.0. If a copy of the MPL
// was not distributed with this file, You can obtain
// one at https://mozilla.org/MPL/2.0/.

package io.vlingo.lattice.model.object;

import io.vlingo.common.Completes;
import io.vlingo.symbio.store.object.StateObject;

public class PersonEntity extends ObjectEntity<PersonState> implements Person {
  private PersonState person;

  public PersonEntity() {
    super(String.valueOf(StateObject.unidentified()));
    this.person = new PersonState(); // unidentified
  }

  public PersonEntity(final long id) {
    super(String.valueOf(id));
    this.person = new PersonState(id, "", 0); // recover
  }

  @Override
  public Completes<PersonState> current() {
    return completes().with(person);
  }

  @Override
  public Completes<PersonState> identify(final String name, final int age) {
    return apply(new PersonState(name, age), () -> person);
  }

  @Override
  public Completes<PersonState> change(String name) {
    return apply(person.with(name), () -> person);
  }

  @Override
  public Completes<PersonState> increaseAge() {
    return apply(person.with(person.age + 1), () -> person);
  }

  @Override
  protected PersonState stateObject() {
    return person;
  }

  @Override
  protected void stateObject(final PersonState stateObject) {
    this.person = stateObject;
  }

  @Override
  protected Class<PersonState> stateObjectType() {
    return PersonState.class;
  }
}
