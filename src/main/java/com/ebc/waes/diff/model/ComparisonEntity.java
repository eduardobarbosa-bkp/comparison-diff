package com.ebc.waes.diff.model;

/**
 * @version 1.0
 * @author eduardo.costa
 * @since 06/10/2017
 */
public class ComparisonEntity{

    private String id;

    private SourceEntity left;

    private SourceEntity right;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SourceEntity getLeft() {
        return left;
    }

    public void setLeft(SourceEntity left) {
        this.left = left;
    }

    public SourceEntity getRight() {
        return right;
    }

    public void setRight(SourceEntity right) {
        this.right = right;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ComparisonEntity that = (ComparisonEntity) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "ComparisonEntity{" +
                "id='" + id + '\'' +
                ", left=" + left +
                ", right=" + right +
                '}';
    }
}
