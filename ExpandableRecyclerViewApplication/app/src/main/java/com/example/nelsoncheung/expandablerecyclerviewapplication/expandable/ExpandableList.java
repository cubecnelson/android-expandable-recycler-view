package com.example.nelsoncheung.expandablerecyclerviewapplication.expandable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by Nelson Cheung on 03/03/2017.
 */

public class ExpandableList<I extends ExpandableSublist> extends ArrayList {


    private ExpandableList(int initialCapacity) {
        super(initialCapacity);
    }

    private ExpandableList() {
    }

    public ExpandableList(Collection<? extends I> c) {
        super(c);
    }

    @Override
    public int size() {
        int size = 0;
        for (Object group : this) {
            size += ((I) group).getItemCount();
        }
        return size;
    }

    @Override
    public Object get(int index) {
        int i = 0;
        for (i = 0; i < super.size(); i++) {

            if (index >= ((I) super.get(i)).getItemCount())
                index -= ((I) super.get(i)).getItemCount();
            else
                break;
        }

//        System.out.println("Index: " + index + " | I: " + i + " | itemCount: " + ((I) super.get(i)).getItemCount());
        return ((I) super.get(i)).getChild(index);
    }

    public void setExpandOrCollapse(int groupIndex) {
        ((I) super.get(groupIndex)).setExpanded(!((I) super.get(groupIndex)).isExpanded());
    }

    public void setExpandOrCollapse(ExpandableSublist item) {
        item.setExpanded(!item.isExpanded());
    }


    public int getSublistPosition(ExpandableSublist item) {
        int groupIndex = super.indexOf(item);
        int groupPosition = 0;
        for (int i = 0; i < groupIndex; i++)
            groupPosition += ((I) super.get(i)).getItemCount();
        
        return groupPosition;
    }


    public ExpandableSublist getSublistByIndex(int groupIndex) {
        return (ExpandableSublist) super.get(groupIndex);
    }

    public ExpandableSublist getSublistByPosition(int position) {
        int i = 0;
        for (i = 0; i < super.size(); i++) {

            if (position >= ((I) super.get(i)).getItemCount())
                position -= ((I) super.get(i)).getItemCount();
            else
                break;
        }

        return getSublistByIndex(i);
    }


    public static void main(String[] args) {

        String aString = "A";
        String bString = "B";
        String cString = "C";

        ExpandableSublist<String> Gp1 = new ExpandableSublist(Arrays.asList(aString, bString, cString), "1");
        ExpandableSublist<String> Gp2 = new ExpandableSublist(Arrays.asList(cString, aString, bString), "2");
        ExpandableSublist<String> Gp3 = new ExpandableSublist(Arrays.asList(bString, cString, aString), "3");

        List<ExpandableSublist<String>> groups = Arrays.asList(Gp1, Gp2, Gp3);

        ExpandableList list = new ExpandableList(groups);

        list.setExpandOrCollapse(0);
//        list.setExpandOrCollapse(1);
        list.setExpandOrCollapse(2);



    }
}
