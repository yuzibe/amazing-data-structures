package com.keybarrel.core;

import java.util.ArrayList;
import java.util.List;

public class Trie {

    private TrieNode root = new TrieNode('/');

    static StringBuilder key = new StringBuilder();
    static ArrayList res = null;
    static int INDEX = 0;

    public void insert(String text) {
        TrieNode p = root;
        for (int i = 0; i < text.length(); ++i) {
            int index = text.charAt(i) - 'a';
            if(index < 0){
                continue;
            }
            if (p.children[index] == null ) {
                TrieNode newNode = new TrieNode(text.charAt(i));
                p.children[index] = newNode;
            }
            p = p.children[index];
        }
        p.isEndingChar = true;
    }


    public List<String> find(String pattern) {
        res = new ArrayList();
        TrieNode p = root;
        int i;
        for (i = 0; i < pattern.length(); i++) {
            int index = pattern.charAt(i) - 'a';
            if (p.children[index] == null) {
                return null;
            }
            p = p.children[index];
            key.replace(i, i + 1, p.data + "");
        }
        if (p.isEndingChar == false) {
            dfs(p, i - 1);
            return res;
        } else {
            res.add(new String(key).substring(0, i));
            return res;
        }
    }

    void dfs(TrieNode root, int index) {
        key.replace(index, index + 1, root.data + "");
        if (root.isEndingChar == false) {
            for (int i = 0; i < root.children.length; i++) {
                if (root.children[i] != null) {
                    ++index;
                    dfs(root.children[i], index);
                    --index;
                }
            }
        } else {
            res.add(new String(key).substring(0, index + 1));
        }
    }

    // Trie Tree
    public class TrieNode {

        public char data;
        public TrieNode[] children = new TrieNode[26];
        public boolean isEndingChar = false;

        public TrieNode(char data) {
            this.data = data;
        }

    }

}

