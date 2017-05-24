/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotlinescecs328;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daniel
 */
public class HotLinesCECS328 {

    static final boolean CAN_FLOW = true, CANNOT_FLOW = false;
    static String connectedString = "";
    static boolean successfulDFS = true;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Vertex[] vertexList = null;
        HashMap<String, Boolean> residualGraph = new HashMap<String, Boolean>();
        //-----------------------------------------------------------------------------------------------------------------------
        //Connect every vertex to their respective vertex
        //Add the list of connected vertices to the edgeOrientationList to set them all to their
        //defualt values of NORMAL orientation to later create a residual graph if needed.

        try {
            boolean[] vertexCreated = null;
            Scanner input = new Scanner(new File("edgesTest2.txt"));
            int highestVertex = Integer.parseInt(input.nextLine());
            vertexCreated = new boolean[highestVertex + 1];
            vertexList = new Vertex[highestVertex + 1];
            Arrays.fill(vertexCreated, false);

            String toSplit = "";
            String[] twoNumbers = null;
            short number1 = 0, number2 = 0;
            while (input.hasNextLine()) {
                toSplit = input.nextLine();
                twoNumbers = toSplit.split(",");
                number1 = Short.parseShort(twoNumbers[0]);
                number2 = Short.parseShort(twoNumbers[1]);
                if (!vertexCreated[number1]) {
                    vertexCreated[number1] = true;
                    vertexList[number1] = new Vertex(number1);
                }
                if (!vertexCreated[number2]) {
                    vertexCreated[number2] = true;
                    vertexList[number2] = new Vertex(number2);
                }
                vertexList[number1].addConnection(vertexList[number2]);
                vertexList[number2].addConnectedFrom(vertexList[number1]);
                residualGraph.put(number1 + "," + number2, CAN_FLOW);
                residualGraph.put(number2 + "," + number1, CANNOT_FLOW);
            }
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(HotLinesCECS328.class.getName()).log(Level.SEVERE, null, ex);
        }

        //------------------------------------------------------------------------------------------------------------------------
        Vertex source = vertexList[0];
        Vertex sink = vertexList[vertexList.length - 1];
        LinkedList<Vertex> queue = new LinkedList<Vertex>();
        boolean foundSink = false;
        Vertex currVertex = null;
        int currVertexID = 0;
        Vertex connectedToVertex = null;
        Vertex connectedFromVertex = null;
        int connectedFromVertexID = 0;
        int connectedToVertexID = 0;
        boolean canFlow = false;

        while (true) {
            queue.add(source);
            boolean[] visited = new boolean[vertexList.length];
            Vertex[] path = new Vertex[vertexList.length];
            foundSink = false;

            while (!queue.isEmpty()) {
                currVertex = queue.poll();
                currVertexID = currVertex.getID();
                canFlow = false;

                for (int i = 0; i < currVertex.getNumberOfConnectedTo(); i++) {
                    connectedToVertex = currVertex.getConnectedTo(i);
                    connectedToVertexID = connectedToVertex.getID();
                    canFlow = residualGraph.get(currVertexID + "," + connectedToVertexID);
                    if (!canFlow) {
                        continue;
                    }
                    if (!visited[connectedToVertexID]) {
                        visited[connectedToVertexID] = true;
                        path[connectedToVertexID] = currVertex;
                        queue.add(connectedToVertex);
                    }
                    if (connectedToVertexID == sink.getID()) {
                        foundSink = true;
                        break;
                    }
                }

                if (!foundSink) {
                    for (int i = 0; i < currVertex.getConnectedFromSize(); i++) {
                        connectedFromVertex = currVertex.getConnectedFrom(i);
                        connectedFromVertexID = connectedFromVertex.getID();
                        canFlow = residualGraph.get(currVertexID + "," + connectedFromVertexID);
                        if(!canFlow) {
                            continue;
                        }
                        if(!visited[connectedFromVertexID]) {
                            visited[connectedFromVertexID] = true;
                            path[connectedFromVertexID] = currVertex;
                            queue.add(connectedFromVertex);
                        }
                    }
                }
                if (foundSink) {
                    currVertex = sink;
                    while (!currVertex.equals(source)) {
                        connectedFromVertex = path[currVertex.getID()];
                        switchEdgeDirection(connectedFromVertex, currVertex, residualGraph);
                        currVertex = connectedFromVertex;
                    }
                    queue.clear();
                    break;
                }
            }
            if (!foundSink) {
                break;
            }
        }
        //--------------------------------------------------------------------------------------------------------------------------
        //Go through all the paths from the sink to the source through the residual graph
        try {
            int count = 0;
            PrintWriter output = new PrintWriter(new File("paths.txt"));
            connectedFromVertex = null;
            while (successfulDFS) {
                boolean[] visited = new boolean[vertexList.length];
                successfulDFS = false;
                connectedString = sink.getID() + "";
                DFS(residualGraph, visited, sink);
                if (successfulDFS) {
                    count++;
                    output.println(connectedString);
                    String[] splitString = connectedString.split(",");
                    for (int i = splitString.length - 1; i > 0; i--) {
                        currVertexID = Integer.parseInt(splitString[i]);
                        currVertex = vertexList[currVertexID];
                        connectedFromVertexID = Integer.parseInt(splitString[i - 1]);
                        connectedFromVertex = vertexList[connectedFromVertexID];
                        currVertex.removeConnectedFrom(connectedFromVertex);
                    }
                }
            }
            System.out.println(count);
            output.close();
        }
        catch (Exception ex) {
            System.out.println("Error");
        }
        //End
    }

    public static void switchEdgeDirection(Vertex connectedFrom, Vertex currVertex, HashMap<String, Boolean> residualGraph) {
        try {
            residualGraph.replace(currVertex.getID() + "," + connectedFrom.getID(), CAN_FLOW);
            residualGraph.replace(connectedFrom.getID() + "," + currVertex.getID(), CANNOT_FLOW);
        }
        catch (NullPointerException ex) {
            System.out.println(currVertex);
        }
    }

    public static boolean DFS(HashMap<String, Boolean> residualGraph, boolean[] visited, Vertex reachedVertex) {
        Vertex connectedVertex = null;
        int reachedVertexID = reachedVertex.getID();
        int connectedFromID = 0;
        visited[reachedVertexID] = true;
        if (reachedVertex.getID() != visited.length - 1) {
            connectedString = reachedVertexID + "," + connectedString;
        }
        if (reachedVertex.getID() == 0) {
            return true;
        }

        for (int i = 0; i < reachedVertex.getConnectedFromSize(); i++) {
            connectedVertex = reachedVertex.getConnectedFrom(i);
            connectedFromID = connectedVertex.getID();
            boolean canFlow = residualGraph.get(reachedVertexID + "," + connectedFromID);

            if (!visited[connectedFromID] && canFlow) {
                successfulDFS = DFS(residualGraph, visited, connectedVertex);
                if (successfulDFS) {
                    return true;
                }
            }
        }
        return false;
    }
}
