/*
 * Copyright (c) 2019-2022 "Neo4j,"
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
package org.neo4j.cypherdsl.examples.sdn6.movies;

// tag::additional-fragments[]
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.support.CypherdslConditionExecutor;
import org.springframework.data.neo4j.repository.support.CypherdslStatementExecutor;

// end::additional-fragments[]

/**
 * @author Michael J. Simons
 */
// tag::additional-fragments[]
public interface PeopleRepository extends Neo4jRepository<Person, Long>,
	CypherdslConditionExecutor<Person>, // <.>
	CypherdslStatementExecutor<Person> { // <.>
}
// end::additional-fragments[]
