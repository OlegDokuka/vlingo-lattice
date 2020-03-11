// Copyright © 2012-2020 VLINGO LABS. All rights reserved.
//
// This Source Code Form is subject to the terms of the
// Mozilla Public License, v. 2.0. If a copy of the MPL
// was not distributed with this file, You can obtain
// one at https://mozilla.org/MPL/2.0/.

package io.vlingo.lattice.model.process;

import io.vlingo.symbio.store.object.StateObject;

import java.util.concurrent.atomic.AtomicLong;

public class StepCountObjectState extends StateObject implements Comparable<StepCountObjectState> {
  private static final long serialVersionUID = 1L;

  private int stepCount;

  public StepCountObjectState(final long id) {
    super(id);
    this.stepCount = 0;
  }

  public void countStep() {
    ++this.stepCount;
  }

  public int stepCount() {
    return this.stepCount;
  }

  @Override
  public int compareTo(final StepCountObjectState other) {
    return Long.compare(this.persistenceId(), other.persistenceId());
  }
}
