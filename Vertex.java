/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotlinescecs328;

import java.util.ArrayList;

/**
 *
 * @author Daniel
 */
public class Vertex {

    private short idNumber;
    private ArrayList<Vertex> connectedVertices;
    private ArrayList<Vertex> connectedFromVertices;

    public Vertex(short number) {
        idNumber = number;
        connectedVertices = new ArrayList<Vertex>();
        connectedFromVertices = new ArrayList<Vertex>();
    }

    public void addConnection(Vertex vertex) {
        connectedVertices.add(vertex);
    }

    public void addConnectedFrom(Vertex vertex) {
        connectedFromVertices.add(vertex);
    }

    public Vertex removeConnection() {
        return connectedVertices.remove(0);
    }

    public Vertex getConnectedTo(int index) {
        return connectedVertices.get(index);
    }
    
    public Vertex getConnectedFrom(int index) {
        return connectedFromVertices.get(index);
    }

    public boolean contains(Vertex vertex) {
        return connectedVertices.contains(vertex);
    }

    public Vertex removeConnection(int index) {
        return connectedVertices.remove(index);
    }

    public void removeConnection(Vertex vertex) {
        connectedVertices.remove(vertex);
    }
    
    public void removeConnectedFrom(Vertex vertex) {
        connectedFromVertices.remove(vertex);
    }

    public int getNumberOfConnectedTo() {
        return connectedVertices.size();
    }
    
    public int getConnectedFromSize() {
        return connectedFromVertices.size();
    }

    public short getID() {
        return idNumber;
    }
    @Override
    public String toString() {
        return idNumber + "";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vertex) {
            Vertex compareTo = (Vertex) obj;
            return compareTo.idNumber == this.idNumber;
        }
        return false;
    }
}
