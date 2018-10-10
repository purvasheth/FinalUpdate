package com.example.purva.vaccineupdate1.Model;

import java.util.ArrayList;

public class PointsToRemember {

    private String point;
    private String pointExpanded;

    public void setPoint(String point) {
        this.point = point;
    }

    public String getPoint() {
        return point;
    }

    public String getPointExpanded() {
        return pointExpanded;
    }

    public void setPointExpanded(String pointExpanded) {
        this.pointExpanded = pointExpanded;
    }

    public static ArrayList<PointsToRemember> getdata(){

        ArrayList<PointsToRemember> pointslist = new ArrayList<>();
        String points[] = getPoints();
        String pointsExpanded[] = getExpandedPoints();
        for(int i=0;i<5;i++)
        {
            PointsToRemember table = new PointsToRemember();
            table.setPoint(points[i]);
            table.setPointExpanded(pointsExpanded[i]);
            pointslist.add(table);
        }
        return pointslist;
    }

    public static String[] getPoints()
    {
        String points[] = {
                "Ensure to remember and administer the vaccine t...",
                "A child should not be given the vaccine if he h...",
                "Sometimes pediatricians might offer you the opt...",
                "A vaccination is usually followed by a fever fo...",
                "Taking some family member or spouse to assist y..."
        };
        return points;
    }

    public static String[] getExpandedPoints()
    {
        String pointsExpanded[] = {
                "Ensure to remember and administer the vaccine to your child as per the schedule. Discuss with your pediatrician if you miss giving a vaccine. He will recommend a suitable later date to administer the same.",
                "A child should not be given the vaccine if he has an ongoing fever. Discuss this with your pediatrician and he will help reschedule the administration of the vaccine at a later date.",
                "Sometimes pediatricians might offer you the option to choose from a painless and a painful vaccine for your child. Ensure to discuss them with your pediatrician to make an informed decision as the immunity of the child decreases faster with painless vaccine over a painful vaccine",
                "A vaccination is usually followed by a fever for 1-2 days in your child. You can use sponge bath to reduce your child’s temperature. However, if the fever doesn’t go away in 1-2 days, reach out to your pediatrician for advice.",
                "Taking some family member or spouse to assist you in vaccinating your child can be helpful. They will help in distracting and comforting your child before, during, and after the shot. Carrying your baby’s favorite toy also helps."
        };
        return pointsExpanded;
    }
}
