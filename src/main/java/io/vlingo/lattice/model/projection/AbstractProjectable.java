// Copyright © 2012-2018 Vaughn Vernon. All rights reserved.
//
// This Source Code Form is subject to the terms of the
// Mozilla Public License, v. 2.0. If a copy of the MPL
// was not distributed with this file, You can obtain
// one at https://mozilla.org/MPL/2.0/.

package io.vlingo.lattice.model.projection;

import java.util.Collection;
import java.util.Optional;

import io.vlingo.symbio.Entry;
import io.vlingo.symbio.State;

public abstract class AbstractProjectable implements Projectable {
  private final Collection<Entry<?>> entries;
  private final String projectionId;
  private final State<?> state;
  private int index;

  public AbstractProjectable(final State<?> state, final Collection<Entry<?>> entries, final String projectionId) {
    this.state = state;
    this.entries = entries;
    this.projectionId = projectionId;
  }

  @Override
  public String[] becauseOf() {
    int count = (state != null ? 1:0) + entries().size();
    final String[] becauseOf = new String[count];
    index = 0;
    if (state != null) {
      becauseOf[0] = state.metadata.operation;
      ++index;
    }
    entries().stream().map(entry -> entry.typeName()).forEach(type -> becauseOf[index++] = type);

    return becauseOf;
  }

  @Override
  public byte[] dataAsBytes() {
    throw new UnsupportedOperationException("Projectable data is not binary compatible.");
  }

  @Override
  public String dataAsText() {
    throw new UnsupportedOperationException("Projectable data is not text compatible.");
  }

  @Override
  public int dataVersion() {
    if (state == null) {
      return -1;
    }
    return state.dataVersion;
  }

  @Override
  public String dataId() {
    if (state == null) {
      return "";
    }
    return state.id;
  }

  @Override
  public Collection<Entry<?>> entries() {
    return entries;
  }

  @Override
  public String metadata() {
    if (state == null) {
      return "";
    }
    return state.metadata.value;
  }

  @Override
  public boolean hasObject() {
    if (state == null) {
      return false;
    }
    return state.metadata.hasObject();
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T object() {
    if (state == null) {
      return null;
    }
    return (T) state.metadata.object;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> Optional<T> optionalObject() {
    if (state == null) {
      return Optional.empty();
    }
    return (Optional<T>) state.metadata.optionalObject();
  }

  @Override
  public String projectionId() {
    return projectionId;
  }

  @Override
  public boolean hasState() {
    return state != null;
  }

  @Override
  public String type() {
    if (state == null) {
      return "null";
    }
    return state.type;
  }

  @Override
  public int typeVersion() {
    if (state == null) {
      return -1;
    }
    return state.typeVersion;
  }

  @SuppressWarnings("unchecked")
  protected State<byte[]> binaryState() {
    return (State<byte[]>) this.state;
  }

  @SuppressWarnings("unchecked")
  protected State<String> textState() {
    return (State<String>) this.state;
  }
}
