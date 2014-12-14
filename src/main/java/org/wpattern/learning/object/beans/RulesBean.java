package org.wpattern.learning.object.beans;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("rules")
public class RulesBean extends BaseBean {

	private static final long serialVersionUID = 201406081758L;

	private List<LearningObjectBean> learningObjects;

	public RulesBean() {
		this.setLearningObjects(new ArrayList<LearningObjectBean>());
	}

	public List<LearningObjectBean> getLearningObjects() {
		return this.learningObjects;
	}

	public void setLearningObjects(List<LearningObjectBean> learningObjects) {
		this.learningObjects = learningObjects;
	}

	public void addLearningObject(LearningObjectBean learningObject) {
		this.learningObjects.add(learningObject);
	}

}
