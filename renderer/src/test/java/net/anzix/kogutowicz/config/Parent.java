/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.config;

/**
 *
 * @author elek
 */
public class Parent {

    private ChildInterface child;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ChildInterface getChild() {
        return child;
    }

    public void setChild(ChildInterface child) {
        this.child = child;
    }

        
}
