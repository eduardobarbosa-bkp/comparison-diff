package com.ebc.waes.diff.domain.dto;

/**
 * @version 1.0
 * @author eduardo.costa
 * @since 06/10/2017
 */
public class ComparisonDiffDTO {

    private String left;

    private String right;

    private String diffs;

    private Integer modifications;

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }

    public String getDiffs() {
        return diffs;
    }

    public void setDiffs(String diffs) {
        this.diffs = diffs;
    }

    public Integer getModifications() {
        return modifications;
    }

    public void setModifications(Integer modifications) {
        this.modifications = modifications;
    }

    @Override
    public String toString() {
        return "ComparisonDiffDTO{" +
                "left='" + left + '\'' +
                ", right='" + right + '\'' +
                ", diffs='" + diffs + '\'' +
                ", modifications=" + modifications +
                '}';
    }
}
