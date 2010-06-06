/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.element;

import java.util.ArrayList;
import java.util.List;

/**
 * Relation map object.
 * @author elek
 */
public class Relation extends Element {

    private List<RelationMember<Way>> ways = new ArrayList();

    private List<RelationMember<Node>> nodes = new ArrayList();

    public Relation() {
    }

    public boolean isIntersect(Box box) {
        for (RelationMember<Node> node : nodes) {
            if (node.getElement() != null && box.isContains(node.getElement())) {
                return true;
            }
        }
        for (RelationMember<Way> way : ways) {
            if (way.getElement() != null && way.getElement().isIntersect(box)) {
                return true;
            }

        }
        return false;
    }

    public void addWay(RelationMember<Way> way) {
        ways.add(way);
    }

    public void addNode(RelationMember<Node> node) {
        nodes.add(node);
    }

    public Way getWay(String role) {
        for (RelationMember<Way> way : ways) {
            if (way.getRole().equals(role)) {
                return way.getElement();
            }
        }
        return null;
    }

    public List<RelationMember<Node>> getNodes() {
        return nodes;
    }

    public void setNodes(List<RelationMember<Node>> nodes) {
        this.nodes = nodes;
    }

    public List<RelationMember<Way>> getWays() {
        return ways;
    }

    public void setWays(List<RelationMember<Way>> ways) {
        this.ways = ways;
    }
}
