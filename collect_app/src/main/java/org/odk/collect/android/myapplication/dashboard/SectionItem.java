package org.odk.collect.android.myapplication.dashboard;

public class SectionItem implements Section {

    private int section;

    public SectionItem(String section) {
        this.section = section;
    }

    @Override
    public int type() {
        return ITEM;
    }

    @Override
    public int sectionPosition() {
        return section;
    }
}