package com.example.x15745561.accupunctual;

public class Workplace {
    private String workplaceName;
    private String workplaceAddr1;

    public Workplace(String workplaceName, String workplaceAddr1) {
        this.workplaceName = workplaceName;
        this.workplaceAddr1 = workplaceAddr1;
    }

    public String getWorkplaceName() {
        return workplaceName;
    }

    public void setWorkplaceName(String workplaceName) {
        this.workplaceName = workplaceName;
    }

    public String getWorkplaceAddr1() {
        return workplaceAddr1;
    }

    public void setWorkplaceAddr1(String workplaceAddr1) {
        this.workplaceAddr1 = workplaceAddr1;
    }
}
