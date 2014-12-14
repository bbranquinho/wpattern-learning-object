package org.wpattern.learning.object.beans;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("learningObject")
public class LearningObjectBean extends BaseBean implements Cloneable {

	private static final long serialVersionUID = 201406081757L;

	private final String identifier;

	private int duration;

	private List<String> constraints;

	private List<String> competencies;

	public LearningObjectBean(String identifier) {
		this.identifier = identifier;
		this.constraints = new ArrayList<String>();
		this.competencies = new ArrayList<String>();
	}

	public LearningObjectBean(int duration, String identifier) {
		this(identifier);
		this.duration = duration;
	}

	@Override
	public LearningObjectBean clone() {
		try {
			LearningObjectBean newObject = (LearningObjectBean) super.clone();

			if (this.competencies != null) {
				newObject.competencies = new ArrayList<String>();

				for (int i = 0; i < this.competencies.size(); i++) {
					newObject.competencies.add(this.competencies.get(i));
				}
			}

			if (this.constraints != null) {
				newObject.constraints = new ArrayList<String>();

				for (int i = 0; i < this.constraints.size(); i++) {
					newObject.constraints.add(this.constraints.get(i));
				}
			}

			return newObject;
		} catch (CloneNotSupportedException e) {
			throw new AssertionError(e.getMessage(), e);
		}
	}

	public String getIdentifier() {
		return this.identifier;
	}

	public int getDuration() {
		return this.duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public List<String> getConstraints() {
		return this.constraints;
	}

	public void setConstraints(List<String> constraints) {
		this.constraints = constraints;
	}

	public List<String> getCompetencies() {
		return this.competencies;
	}

	public void setCompetencies(List<String> competencies) {
		this.competencies = competencies;
	}

	public void addCompetency(String competency) {
		this.competencies.add(competency);
	}

	public void addConstraint(String constraint) {
		this.constraints.add(constraint);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((this.identifier == null) ? 0 : this.identifier.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (this.getClass() != obj.getClass()) {
			return false;
		}

		LearningObjectBean other = (LearningObjectBean) obj;

		if (this.competencies == null) {
			if (other.competencies != null) {
				return false;
			}
		} else if (!this.competencies.equals(other.competencies)) {
			return false;
		}

		if (this.constraints == null) {
			if (other.constraints != null) {
				return false;
			}
		} else if (!this.constraints.equals(other.constraints)) {
			return false;
		}

		if (this.duration != other.duration) {
			return false;
		}

		if (this.identifier == null) {
			if (other.identifier != null) {
				return false;
			}
		} else if (!this.identifier.equals(other.identifier)) {
			return false;
		}

		return true;
	}

}
