package org.neo4j.cypherdsl.codegen.sdn6.models.self_referential;

import java.util.List;
import javax.annotation.Generated;
import org.neo4j.cypherdsl.core.MapExpression;
import org.neo4j.cypherdsl.core.NodeBase;
import org.neo4j.cypherdsl.core.NodeLabel;
import org.neo4j.cypherdsl.core.Properties;
import org.neo4j.cypherdsl.core.Property;
import org.neo4j.cypherdsl.core.SymbolicName;

@Generated(
	value = "org.neo4j.cypherdsl.codegen.core.NodeImplBuilder",
	date = "2019-09-21T21:21:00+01:00",
	comments = "This class is generated by the Neo4j Cypher-DSL. All changes to it will be lost after regeneration."
)
public final class Example_ extends NodeBase<Example_> {
	public static final Example_ EXAMPLE = new Example_();

	public final Property ID = this.property("id");

	public Example_() {
		super("Example");
	}

	private Example_(SymbolicName symbolicName, List<NodeLabel> labels, Properties properties) {
		super(symbolicName, labels, properties);
	}

	@Override
	public Example_ named(SymbolicName newSymbolicName) {
		return new Example_(newSymbolicName, getLabels(), getProperties());
	}

	@Override
	public Example_ withProperties(MapExpression newProperties) {
		return new Example_(getSymbolicName().orElse(null), getLabels(), Properties.create(newProperties));
	}

	public BelongsTo_ withParent(Example_ parent) {
		return new BelongsTo_(this, parent);
	}
}
