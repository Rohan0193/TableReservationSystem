package main.model;

public class restrictionObj {
    String restrictionType;
    String restrictionDate;

    public restrictionObj(String restrictionType, String restrictionDate){
        this.restrictionType = restrictionType;
        this.restrictionDate = restrictionDate;
    }

    public String getRestrictionType() {
        return restrictionType;
    }

    public void setRestrictionType(String restrictionType) {
        this.restrictionType = restrictionType;
    }

    public String getRestrictionDate() {
        return restrictionDate;
    }

    public void setRestrictionDate(String restrictionDate) {
        this.restrictionDate = restrictionDate;
    }

}
