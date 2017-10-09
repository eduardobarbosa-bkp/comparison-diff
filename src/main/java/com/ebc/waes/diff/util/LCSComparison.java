package com.ebc.waes.diff.util;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

/**
 * This class is responsible to comparison between to text
 * using Longest common subsequence problem
 * https://en.wikipedia.org/wiki/Longest_common_subsequence_problem
 * @version 1.0
 * @author eduardo.costa
 * @since 09/10/2017
 */
public class LCSComparison {

    private String left;
    private String right;

    public LCSComparison(String left, String right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Perform the comparison between the left and right Strings
     * @return a String with the changes to the left to turn in the right
     */
    public String performDiff(){
        if(StringUtils.isEmpty(left)
                || StringUtils.isEmpty(right)){
            return null;
        }
        left = normalizeText(left);
        right = normalizeText(right);
        ArrayList<String> lcsList = lcs(left, right);
        return markTextDiff(left, right, lcsList);
    }

    /**
     * Finds a list of longest common subsequence (lcs) of given two texts.
     * @param left
     * @param right
     * @return - longest common subsequence list
     */
    private ArrayList<String> lcs(String left, String right) {
        String[] leftWords = left.split("");
        String[] rightWords = right.split("");
        int leftWordCount = left.length();
        int rightWordCount = right.length();

        int[][] solutionMatrix = new int[leftWordCount + 1][rightWordCount + 1];

        for (int i = leftWordCount - 1; i >= 0; i--) {
            for (int j = rightWordCount - 1; j >= 0; j--) {
                if (leftWords[i].equals(rightWords[j])) {
                    solutionMatrix[i][j] = solutionMatrix[i + 1][j + 1] + 1;
                }
                else {
                    solutionMatrix[i][j] = Math.max(solutionMatrix[i + 1][j],
                            solutionMatrix[i][j + 1]);
                }
            }
        }

        int i = 0;
        int j = 0;
        ArrayList<String> lcsList = new ArrayList<>();
        while (i < leftWordCount && j < rightWordCount) {
            if (leftWords[i].equals(rightWords[j])) {
                lcsList.add(rightWords[j]);
                i++;
                j++;
            }
            else if (solutionMatrix[i + 1][j] >= solutionMatrix[i][j + 1]) {
                i++;
            }
            else {
                j++;
            }
        }
        return lcsList;
    }

    /**
     * Mark text with the diffs with sign [+] before an addition and [-] before a remove
     * @param left the text from the left side
     * @param right the text from the right side
     * @param lcsList Longest common subsequence computed
     * @return a String with the changes to the left to turn in the right
     */
    private String markTextDiff(String left, String right,
                                ArrayList<String> lcsList) {
        StringBuilder builder = new StringBuilder();
        if (left != null && lcsList != null) {
            String[] leftWords = left.split("");
            String[] rightWords = right.split("");
            int i;
            int j;
            int leftLastIndex = 0;
            int rightLastIndex = 0;
            for (int k = 0; k < lcsList.size(); k++) {
                for (i = leftLastIndex, j = rightLastIndex;
                     i < leftWords.length && j < rightWords.length;) {
                    if (leftWords[i].equals(lcsList.get(k)) &&
                            rightWords[j].equals(lcsList.get(k))) {
                        builder.append(lcsList.get(k));
                        leftLastIndex = i + 1;
                        rightLastIndex = j + 1;
                        i = leftWords.length;
                        j = rightWords.length;
                    }
                    else if (!rightWords[j].equals(lcsList.get(k))) {
                        for (; j < rightWords.length &&
                                !rightWords[j].equals(lcsList.get(k)); j++) {
                            builder.append("[+]" + rightWords[j]);
                        }
                    }else if (!leftWords[i].equals(lcsList.get(k))) {
                        for (; i < leftWords.length &&
                                !leftWords[i].equals(lcsList.get(k)); i++) {
                            builder.append("[-]" + leftWords[i]);
                        }
                    }
                }
            }
            for (; leftLastIndex < leftWords.length; leftLastIndex++) {
                builder.append("[-]" + leftWords[leftLastIndex]);
            }
            for (; rightLastIndex < rightWords.length; rightLastIndex++) {
                builder.append("[+]" + rightWords[rightLastIndex]);
            }
        }
        return builder.toString();
    }

    /**
     * Normalizes given string by deleting \n, \t and extra spaces.
     * @param text - initial string
     * @return - normalized string
     */
    private String normalizeText(String text) {
        String replaced = text.trim();
        replaced = replaced.replace("\n", " ");
        replaced = replaced.replace("\t", " ");
        while (replaced.contains("  ")) {
            replaced = replaced.replace("  ", " ");
        }
        return replaced;
    }
}