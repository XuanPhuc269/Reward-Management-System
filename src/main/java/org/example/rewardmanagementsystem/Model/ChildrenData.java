package org.example.rewardmanagementsystem.Model;

import javafx.scene.control.TableView;

public class ChildrenData extends TableView {
    private String childID;
    private String childName;
    private String childDOB;
    private String childSchool;
    private String childClass;
    private String academicPerformance;
    private String householdID;
    private String reward;
    private Integer quantity;
    private Integer value;

    public ChildrenData(String childID, String childName, String childDOB, String childSchool, String childClass, String academicPerformance, String householdID) {
        this.childID = childID;
        this.childName = childName;
        this.childDOB = childDOB;
        this.childSchool = childSchool;
        this.childClass = childClass;
        this.academicPerformance =academicPerformance;
        this.householdID = householdID;
    }

    public ChildrenData(String childID, String childName, String householdID, String reward, Integer quantity, Integer value) {
        this.childID = childID;
        this.childName = childName;
        this.householdID = householdID;
        this.reward = reward;
        this.quantity = quantity;
        this.value = value;
    }

    public ChildrenData(String childName, String childDOB, String childSchool, String childClass, String academicPerformance ) {
        this.childName = childName;
        this.childDOB = childDOB;
        this.childSchool = childSchool;
        this.childClass = childClass;
        this.academicPerformance = academicPerformance;
    }
    public String getChildID() {
        return childID;
    }

    public String getChildName() {
        return childName;
    }

    public String getChildDOB() {
        return childDOB;
    }

    public String getChildSchool() {
        return childSchool;
    }

    public String getChildClass() {
        return childClass;
    }

    public String getAcademicPerformance() {
        return academicPerformance;
    }

    public String getHouseholdID() {
        return householdID;
    }public String getReward(){
        return reward;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public Integer getValue() {
        return value;
    }
}
