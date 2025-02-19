/*
 * Copyright (c) 2019-2023 "Neo4j,"
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
package org.neo4j.cypherdsl.core.renderer;

import static org.apiguardian.api.API.Status.STABLE;

import java.util.Objects;

import org.apiguardian.api.API;

/**
 * This class provides some configuration settings for the Cypher-DSL, mainly around rendering of a statement. Instances
 * of {@link Configuration} are threadsafe and can be reused. Please use the {@link Configuration.Builder associated builder}
 * via {@link Configuration#newConfig()} to create new variants.
 *
 * @author Michael J. Simons
 * @soundtrack Slayer - Undisputed Attitude
 * @since 2021.0.1
 */
@API(status = STABLE, since = "2021.0.1")
public final class Configuration {

	private static final Configuration DEFAULT_CONFIG = newConfig().build();
	private static final Configuration PRETTY_PRINTING = newConfig()
		.withPrettyPrint(true)
		.alwaysEscapeNames(false)
		.build();

	/**
	 * Enum for the available indent styles.
	 */
	public enum IndentStyle {
		/** Use tabs for indentation. */
		TAB,
		/** Use a configurable number of spaces for indentation. */
		SPACE
	}

	/**
	 * Set to {@literal true} to enable pretty printing.
	 */
	private final boolean prettyPrint;

	/**
	 * Configure your favorite indentation style.
	 */
	private final IndentStyle indentStyle;

	/**
	 * The indentation sizes. Only applicable when using {@link IndentStyle#SPACE}. Defaults to {@literal 2}.
	 */
	private final int indentSize;

	/**
	 * A flag if all names (node labels and relationship types) should be escaped all the time. Defaults to {@literal true}.
	 * When pretty printing, this defaults to {@literal false}.
	 */
	private final boolean alwaysEscapeNames;

	/**
	 * Turning this flag on will replace all symbolic names and all parameter names with a generated name. This name will
	 * stay constant for the lifetime of a statement. It defaults to {@literal false}. This flag can be useful if you want
	 * to normalize your statements: Imagine two statements coming from two different sources who may have used different
	 * variable or parameter names. Turning {@code useGeneratedNames} on will make those statements produce the same rendering,
	 * given they have the same semantics.
	 */
	private final boolean useGeneratedNames;

	/**
	 * The dialect to use when rendering a statement. The default dialect works well with Neo4j 4.4 and prior.
	 */
	private final Dialect dialect;

	/**
	 * Cypher is not pretty printed by default. No indentation settings apply.
	 *
	 * @return the default config
	 */
	public static Configuration defaultConfig() {
		return DEFAULT_CONFIG;
	}

	/**
	 * Pretty printing with default indentation settings.
	 *
	 * @return a configuration enabling pretty printing.
	 */
	public static Configuration prettyPrinting() {
		return PRETTY_PRINTING;
	}

	/**
	 * {@return a new builder} for creating a new configuration from scratch
	 */
	public static Builder newConfig() {
		return Builder.newConfig();
	}

	/**
	 * Use this builder to create new {@link Configuration} instances.
	 */
	@SuppressWarnings("HiddenField")
	public static final class Builder {

		private boolean prettyPrint = false;
		private IndentStyle indentStyle = IndentStyle.SPACE;
		private int indentSize = 2;
		private boolean alwaysEscapeNames = true;
		private Dialect dialect = Dialect.DEFAULT;
		private boolean useGeneratedNames = false;

		private Builder() {
		}

		static Builder newConfig() {
			return new Builder();
		}

		/**
		 * Enables or disables pretty printing. Enabling pretty printing will disable unnecessary escaping of labels and types.
		 *
		 * @param prettyPrint use {@literal true} for enabling pretty printing
		 * @return this builder
		 */
		public Builder withPrettyPrint(boolean prettyPrint) {
			this.prettyPrint = prettyPrint;
			if (this.prettyPrint) {
				return this.alwaysEscapeNames(false);
			}
			return this;
		}

		/**
		 * @param indentStyle The new indentation style
		 * @return this builder
		 */
		public Builder withIndentStyle(IndentStyle indentStyle) {

			if (indentStyle == null) {
				throw new IllegalArgumentException("Indent style is required.");
			}
			this.indentStyle = indentStyle;
			return this;
		}

		/**
		 * @param indentSize The new indentation size
		 * @return this builder
		 */
		public Builder withIndentSize(int indentSize) {
			this.indentSize = indentSize;
			return this;
		}

		/**
		 * Configure whether names should be always escaped.
		 *
		 * @param alwaysEscapeNames use {@literal true} to always escape names
		 * @return this builder
		 */
		public Builder alwaysEscapeNames(boolean alwaysEscapeNames) {
			this.alwaysEscapeNames = alwaysEscapeNames;
			return this;
		}

		/**
		 * Configure whether variable names should be always generated.
		 * @param useGeneratedNames Set to {@literal true} to use generated symbolic and parameter names.
		 * @return this builder
		 */
		public Builder useGeneratedNames(boolean useGeneratedNames) {
			this.useGeneratedNames = useGeneratedNames;
			return this;
		}

		/**
		 * Use a configuration with a dialect fitting your target database if the {@link Dialect#DEFAULT default dialect}
		 * leads to incompatible results with your version of Neo4j.
		 *
		 * @param dialect The new dialect
		 * @return This builder. You can both use the original or this instance.
		 * @since 2022.3.0
		 */
		public Builder withDialect(Dialect dialect) {
			this.dialect = dialect;
			return this;
		}

		/**
		 * @return a new immutable configuration
		 */
		public Configuration build() {
			return new Configuration(this);
		}
	}

	private Configuration(Builder builder) {
		this.prettyPrint = builder.prettyPrint;
		this.alwaysEscapeNames = builder.alwaysEscapeNames;
		this.indentStyle = builder.indentStyle;
		this.indentSize = builder.indentSize;
		this.dialect = builder.dialect == null ? Dialect.DEFAULT : builder.dialect;
		this.useGeneratedNames = builder.useGeneratedNames;
	}

	/**
	 * @return {@literal true} if this configuration uses pretty printing
	 */
	public boolean isPrettyPrint() {
		return prettyPrint;
	}

	/**
	 * {@return the indentation style} whether to use tabs or spaces to indent things
	 */
	public IndentStyle getIndentStyle() {
		return indentStyle;
	}

	/**
	 * @return width of one indentation
	 */
	public int getIndentSize() {
		return indentSize;
	}

	/**
	 * @return {@literal true} when names should be always escaped
	 */
	public boolean isAlwaysEscapeNames() {
		return alwaysEscapeNames;
	}

	/**
	 * @return {@literal true} when symbolic and parameter names should be replaced with generated names
	 * @since 2023.2.0
	 */
	public boolean isUseGeneratedNames() {
		return useGeneratedNames;
	}

	/**
	 * @return the target dialect
	 */
	public Dialect getDialect() {
		return dialect;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Configuration that = (Configuration) o;
		return prettyPrint == that.prettyPrint && indentSize == that.indentSize && indentStyle == that.indentStyle &&
			alwaysEscapeNames == that.alwaysEscapeNames && dialect == that.dialect &&
			useGeneratedNames == that.useGeneratedNames;
	}

	@Override
	public int hashCode() {
		return Objects.hash(prettyPrint, indentStyle, indentSize, alwaysEscapeNames, dialect, useGeneratedNames);
	}

	@Override
	public String toString() {
		return "Configuration{" +
			"prettyPrint=" + prettyPrint +
			", indentStyle=" + indentStyle +
			", indentSize=" + indentSize +
			'}';
	}
}
