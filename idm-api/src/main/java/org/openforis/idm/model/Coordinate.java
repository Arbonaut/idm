package org.openforis.idm.model;

import org.openforis.idm.metamodel.SpatialReferenceSystem;

public interface Coordinate extends Value {

	/** 
	 * @return  Returns the x.
	 * @uml.property  name="x" readOnly="true"
	 */
	public Long getX();

	/**
	 * @return  Returns the y.
	 * @uml.property  name="y" readOnly="true"
	 */
	public Long getY();

	/**
	 * @return  Returns the z.
	 * @uml.property  name="z" readOnly="true"
	 */
	public Long getZ();

	/**
	 * @return  Returns the spatialReferenceSystem.
	 * @uml.property  name="spatialReferenceSystem" readOnly="true"
	 */
	public SpatialReferenceSystem getSpatialReferenceSystem();

}
