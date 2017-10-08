package com.ebc.waes.diff.domain;

import java.io.Serializable;

/**
 * @version 1.0
 * @author eduardo.costa
 * @since 06/10/2017
 */
public class Comparison implements Serializable {

    private static final long serialVersionUID = -2867576428436227967L;

    private String id;

    private String left;

    private String right;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comparison that = (Comparison) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Comparison{" +
                "id='" + id + '\'' +
                ", left=" + left +
                ", right=" + right +
                '}';
    }

    public Comparison decode() {


        return null;
    }
}
