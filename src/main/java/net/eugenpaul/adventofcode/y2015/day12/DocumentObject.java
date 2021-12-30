package net.eugenpaul.adventofcode.y2015.day12;

import java.util.List;

public class DocumentObject {
    private String name = null;
    private Integer valueInt = null;
    private String valueString = null;
    private List<DocumentObject> arrays = null;
    private List<DocumentObject> objects = null;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValueInt() {
        return this.valueInt;
    }

    public void setValueInt(Integer valueInt) {
        this.valueInt = valueInt;
    }

    public String getValueString() {
        return this.valueString;
    }

    public void setValueString(String valueString) {
        this.valueString = valueString;
    }

    public List<DocumentObject> getArrays() {
        return this.arrays;
    }

    public void setArrays(List<DocumentObject> arrays) {
        this.arrays = arrays;
    }

    public List<DocumentObject> getObjects() {
        return this.objects;
    }

    public void setObjects(List<DocumentObject> objects) {
        this.objects = objects;
    }

}
