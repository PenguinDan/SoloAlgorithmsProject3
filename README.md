# SoloAlgorithmsProject3
This Project demonstrates my knowledge and mastery working with Graphs and Network Flow Algorithms.

Honors Algorithms Course - Hotlines Problem  
Students are not allowed to share their work WITH EACH OTHER. All work is to be done alone.  
California State University - Long Beach  
This program utilizes a For-Fulkerson like algorithm to maximize the amount of UNIQUE paths 
to the sink by making every edge a value of 1.   

It is the middle of the Cold War and America and its western allies are facing  
down the Union of Soviet Socialist Republics. There is an assassination of a high  
Party official somewhere in Moscow, and the culprit has been named to be a  
rogue CIA agent. The countries are now on the brink of a nuclear confrontation.  
Luckily, the leadership of America and Russia have connected a series of hot  
lines between the White House and the Russian Politburo building. However,  
these hotlines are not direct. They have to pass through numerous intermediate  
routing stations in various countries. The goal is to get the largest number  
of connections from one spot to the other. The catch is that even though the  
switching stations (vertices) can handle a potentially unlimited number of calls,  
a single line (edge) can only be used for one call at a time.  
The goal of your program will be to find the maximum simultaneous number  
of links that can be made between the White House and Russian Polituro.  
Input to your program, the file edges.txt, will be the edge representation of  
a directed graph with vertices numbered 0 (the White House) through N (the  
Russian Poltburo). N will be given in the first line. Each directed edge will be  
two nonnegative integer values, separated by a comma.  
Output, in the file paths.txt, should be a list of paths starting at 0 and  
ending at N, each vertex separated by a comma and each path separated by a  
newline.  
For example, consider the following edges.txt file.  
10  
3,6  
3,1  
9,4  
1,10  
5,10  
0,2  
4,1  
0,6  
2,4  
8,10  
7,4  
0,3  
There is only a single path from the White House (0) to the Politburo (10).  
0,2,4,1,10  
