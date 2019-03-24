package org.odk.collect.android.myapplication.dashboard;

public class SectionHeader implements Section {

    private int section;
    private String name;

    public SectionHeader(int section,String name) {
        this.section = section;
        this.name = name;
    }

    @Override
    public int type() {
        return HEADER;
    }

    @Override
    public int sectionPosition() {
        return section;
    }
}