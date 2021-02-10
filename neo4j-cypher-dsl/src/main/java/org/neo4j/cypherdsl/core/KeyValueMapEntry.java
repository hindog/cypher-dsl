/*
 * Copyright (c) 2019-2021 "Neo4j,"
 * Neo4j Sweden AB [https://neo4j.com]
 *
 * This file is part of Neo4j.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.neo4j.cypherdsl.core;

import static org.apiguardian.api.API.Status.EXPERIMENTAL;
import static org.apiguardian.api.API.Status.INTERNAL;

import org.apiguardian.api.API;
import org.neo4j.cypherdsl.core.support.Visitor;

/**
 * Helper class, only for internal use.
 *
 * @author Michael J. Simons
 * @soundtrack Rammstein - RAMMSTEIN
 * @since 1.0
 */
@API(status = EXPERIMENTAL, since = "1.0")
public final class KeyValueMapEntry implements Expression {

	private final String key;

	private final Expression value;

	KeyValueMapEntry(String key, Expression value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * @return The key of this entry.
	 */
	@API(status = INTERNAL)
	public String getKey() {
		return key;
	}

	@Override
	public void accept(Visitor visitor) {

		visitor.enter(this);
		value.accept(visitor);
		visitor.leave(this);
	}
}