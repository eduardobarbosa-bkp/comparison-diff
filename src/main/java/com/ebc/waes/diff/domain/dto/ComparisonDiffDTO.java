package com.ebc.waes.diff.domain.dto;

/**
 * @version 1.0
 * @author eduardo.costa
 * @since 06/10/2017
 */
public class ComparisonDiffDTO {

    private String diffs;

    public String getDiffs() {
        return diffs;
    }

    public void setDiffs(String diffs) {
        this.diffs = diffs;
    }

    @Override
    public String toString() {
        return "ComparisonDiffDTO{" +
                ", diffs='" + diffs + '\'' +
                '}';
    }
}
