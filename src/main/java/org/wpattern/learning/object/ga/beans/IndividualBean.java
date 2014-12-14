package org.wpattern.learning.object.ga.beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.wpattern.learning.object.beans.BaseBean;
import org.wpattern.learning.object.beans.LearningObjectBean;
import org.wpattern.learning.object.ga.utils.Utils;

public class IndividualBean extends BaseBean implements Cloneable {

	private static final long serialVersionUID = 201406081139L;

	private long identifier;

	private Long fitness;

	private List<LearningObjectBean> learningObjects;

	private int hashCode;

	public IndividualBean() {
		this.setLearningObjects(new ArrayList<LearningObjectBean>() {
			private static final long serialVersionUID = -2991112678597935313L;

			@Override
			public void add(int arg0, LearningObjectBean arg1) {
				super.add(arg0, arg1);
				IndividualBean.this.hashCode = IndividualBean.this.calculateHash();
			}

			@Override
			public boolean addAll(Collection<? extends LearningObjectBean> arg0) {
				boolean result = super.addAll(arg0);
				IndividualBean.this.hashCode = IndividualBean.this.calculateHash();
				return result;
			}

			@Override
			public boolean add(LearningObjectBean arg0) {
				boolean result = super.add(arg0);
				IndividualBean.this.hashCode = IndividualBean.this.calculateHash();
				return result;
			}

			@Override
			public boolean addAll(int arg0, Collection<? extends LearningObjectBean> arg1) {
				boolean result = super.addAll(arg0, arg1);
				IndividualBean.this.hashCode = IndividualBean.this.calculateHash();
				return result;
			}

			@Override
			public LearningObjectBean set(int arg0, LearningObjectBean arg1) {
				LearningObjectBean result = super.set(arg0, arg1);
				IndividualBean.this.hashCode = IndividualBean.this.calculateHash();
				return result;
			}

			@Override
			public LearningObjectBean remove(int arg0) {
				LearningObjectBean result = super.remove(arg0);
				IndividualBean.this.hashCode = IndividualBean.this.calculateHash();
				return result;
			}

			@Override
			public boolean remove(Object arg0) {
				boolean result = super.remove(arg0);
				IndividualBean.this.hashCode = IndividualBean.this.calculateHash();
				return result;
			}

			@Override
			public boolean removeAll(Collection<?> arg0) {
				boolean result = super.removeAll(arg0);
				IndividualBean.this.hashCode = IndividualBean.this.calculateHash();
				return result;
			}

			@Override
			protected void removeRange(int arg0, int arg1) {
				super.removeRange(arg0, arg1);
				IndividualBean.this.hashCode = IndividualBean.this.calculateHash();
			}
		});

		this.identifier = Utils.getIndividualIdentifier();
		this.hashCode = this.calculateHash();
	}

	public void addLearningObject(LearningObjectBean learningObject) {
		this.learningObjects.add(learningObject);
		this.hashCode = this.calculateHash();
	}

	public Long getFitness() {
		return this.fitness;
	}

	public void setFitness(Long fitness) {
		this.fitness = fitness;
	}

	public List<LearningObjectBean> getLearningObjects() {
		return this.learningObjects;
	}

	public void setLearningObjects(List<LearningObjectBean> learningObjects) {
		this.learningObjects = learningObjects;
	}

	public long getIdentifier() {
		return this.identifier;
	}

	@Override
	public int hashCode() {
		return this.hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!super.equals(obj)) {
			return false;
		}

		if (this.getClass() != obj.getClass()) {
			return false;
		}

		IndividualBean other = (IndividualBean) obj;

		if (this.learningObjects == null) {
			if (other.learningObjects != null) {
				return false;
			}
		} else if (!this.learningObjects.equals(other.learningObjects)) {
			return false;
		}

		return true;
	}

	private int calculateHash() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.learningObjects == null) ? 0 : this.learningObjects.hashCode());

		return result;
	}

	@Override
	public IndividualBean clone() {
		try {
			IndividualBean newObject = (IndividualBean) super.clone();

			newObject.identifier = Utils.getIndividualIdentifier();

			if (this.fitness != null) {
				newObject.fitness = new Long(this.fitness);
			}

			if (this.learningObjects != null) {
				newObject.learningObjects = new ArrayList<LearningObjectBean>();

				for (LearningObjectBean learningObject : this.learningObjects) {
					newObject.addLearningObject(learningObject.clone());
				}
			}

			return newObject;
		} catch (CloneNotSupportedException e) {
			throw new AssertionError(e.getMessage(), e);
		}
	}

	public Object getState() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getPathFromRoot() {
		// TODO Auto-generated method stub
		return null;
	}

}
