package org.openforis.idm.path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.metamodel.Schema;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.Record;

/**
 * 
 * @author G. Miceli
 *
 */
public class Path implements Axis {
	private Path parentPath;
	private Axis axis;

	public Path(Path parentPath, Axis axis) {
		this.parentPath = parentPath;
		this.axis = axis;
	}

	public Path(Path parentPath, String name) {
		this(parentPath, new PathElement(name));
	}

	public Path(Path parentPath, String name, int idx) {
		this(parentPath, new PathElement(name, idx));
	}

	public Path(Axis axis) {
		this(null, axis);
	}

	public Path getParentPath() {
		return parentPath;
	}
	
	public Axis getAxis() {
		return axis;
	}
	
	@Override
	public List<Node<?>> evaluate(Node<?> context) {
		if ( parentPath == null ) {
			return axis.evaluate(context);
		} else {
			List<Node<?>> contexts = parentPath.evaluate(context);
			List<Node<?>> results = new ArrayList<Node<?>>();
			for (Node<?> ctx : contexts) {
				List<Node<?>> eval = axis.evaluate(ctx);
				results.addAll(eval);
			}
			return Collections.unmodifiableList(results);
		}
	}

	@Override
	public List<Node<?>> evaluate(Record context) {
		if ( parentPath == null ) {
			return axis.evaluate(context);
		} else {
			List<Node<?>> contexts = parentPath.evaluate(context);
			List<Node<?>> results = new ArrayList<Node<?>>();
			for (Node<?> ctx : contexts) {
				List<Node<?>> eval = axis.evaluate(ctx);
				results.addAll(eval);
			}
			return Collections.unmodifiableList(results);
		}
	}
	
	@Override
	public String toString() {
		if ( parentPath == null ) {
			return axis.toString();
		} else {
			return parentPath.toString() + "/" + axis.toString();
		}
	}

	@Override
	public NodeDefinition evaluate(NodeDefinition context) {
		if ( parentPath != null ) {
			context = parentPath.evaluate(context);
		}
		return axis.evaluate(context);
	}

	@Override
	public NodeDefinition evaluate(Schema context) {
		if ( parentPath == null ) {
			return axis.evaluate(context);
		} else {
			NodeDefinition ctx = parentPath.evaluate(context);
			return axis.evaluate(ctx);
		}
	}
	
	public static Path parsePath(String path) throws InvalidPathException {
		int idx = path.lastIndexOf('/');
		if ( idx < 0 ) {
			PathElement axis = PathElement.parseElement(path);
			return new Path(axis);
		} else {
			String head = path.substring(0, idx);
			String tail = path.substring(idx+1);
			Path parentPath = parsePath(head);
			Axis axis = PathElement.parseElement(tail);
			return new Path(parentPath, axis);
		}
	}
}
