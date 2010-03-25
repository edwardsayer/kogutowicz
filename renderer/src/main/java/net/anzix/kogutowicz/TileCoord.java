/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz;

/**
 *
 * @author elek
 */
public class TileCoord {

    private int x;

    private int y;

    public TileCoord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public TileCoord(TileCoord curr) {
        this.x = curr.getX();
        this.y = curr.getY();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TileCoord other = (TileCoord) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + this.x;
        return hash;
    }

    public TileCoord substract(TileCoord tileCoord) {
        return new TileCoord(this.x - tileCoord.getX(), this.y - tileCoord.getY());
    }
}
