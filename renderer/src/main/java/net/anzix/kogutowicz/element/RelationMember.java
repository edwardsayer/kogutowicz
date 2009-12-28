/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.element;

/**
 *
 * @author elek
 */
public class RelationMember<T extends Element> {

    private String role;

    private T element;

    public RelationMember(String role, T element) {
        this.role = role;
        this.element = element;
    }

    public T getElement() {
        return element;
    }

    public void setElement(T element) {
        this.element = element;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

   
}
