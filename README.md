# Percolation
![Percolation matrix showing a situation where the simulation percolates](/doc/percolation_image "Percolation Matrix") <br><br>
The main objective of this project is to calculate the <i>percolation treshold</i> via <i>Monte Carlo</i> simulation
 > <strong>Percolation</strong> is the process of modeling a situation where we have, for example, a composite <br>
 > system comprised of randomly distributed insulating and metallic materials and we wanted to <br>
 > know what fraction of the materials need to be metallic so that the composite system is an <br>
 > electrical conductor. <br>
 > You can find more information about the concept of Percolation in this [scientific article](https://www.sciencedirect.com/science/article/abs/pii/S0378437114002969).

 > <strong>Monte Carlo Simulation</strong> is a computerized mathematical technique to generate random sample <br> 
 > data based on some known distribution for numerical experiments. [Reference](https://edisciplinas.usp.br/pluginfile.php/5190162/mod_resource/content/1/Monte%20Carlo%20Simulation%20lecture.pdf).

## Main Challenges
As described earlier, the main objective of this project is to calculate the percolation threshold and for this <br>
I made use of the Monte Carlo method. But the main challenge is not directly using this method, instead, it was how <br>
to model the percolation system and execute the simulations from a computational efficient perspective.<br> 
Achieving this efficiency was possible thanks to the <strong>Union-Find Data Structure</strong> as well as the mathematical <br>
relation that I came up to to translate a matrix element's position to an array position. 

## Solving the main challenges
The mathematical relation mentioned in the previous section is described as follow:<br><br>

$$ArrayPosition = ((R(M_{i,j}) - 1) * n ) + C(M_{i,j}) - 1$$ 

Where $R(M_{i,j})$ is a matrix row element; $n$ is the grid size; and $C(M_{i,j})$ is a matrix column element <br>
> Obs.: I did not formally proved this relation, but it worked for all test cases that it was submitted to

#### Example
The following image shows a matrix with a $n = 4$, so the matrix size is 4x4. For the element $M_{3,4}$ ,for example, <br>
we have a mapping to an array position of 11 <br><br>
![Four by four matrix enumerating each element to an integer following the mathematical relation previously established](/doc/matrizpercolation.png)

### Union-find Data structure 
The Union-find data structure is used to solve the problem of dynamic connectivity. This data structure is capable of, given <br>
a set of integers, and given that we can connect pairs in this set, union find is capable of "remembering" these connections, <br>
as well as connecting pairs and efficiently verifying if a element p is connected to a element q in a set. Applied to the <br>
percolation problem, we use this data structure to model the connections among the sites. One of the main challenges were <br>
to know when do a simulation percolates, and to address this issue, it was used the concept of "virtual top" and "virtual bottom" <br><br>

![Virtual top and virtual bottom](/doc/percolation.jpg)

As we can see in the image above, the virtual top is connected to the first row of the matrix. Similarly, the virtual bottom is <br>
connected to the last row of the matrix. We say that a system percolates if any element in the virtual top is connected to any <br>
element in the virtual bottom.

### Joining concepts 
Using the matrix as the basis for the percolation system; a method for translating elements in the matrix to a an array position; <br>
and the union find that, at its core, uses an array to do the operations needed, we have the complete set of concepts and solutions <br>
that were used to solve the percolation problem.

## How to run this project
 To run these programs you need to have Java installed and configured (version >= 5.0) as well as add <strong><i>algs4.jar</i></strong> <br>
 to the classpath. <br>
 The main class to run is the Percolation Stats.java, where it receives two arguments:<br>
 <strong>n</strong> - The size of the grid <br>
 <strong>T</strong> - The number of trials. By repeating this computation experiment T times and averaging the results, we obtain a more accurate estimate of the percolation threshold

## Observation
> 1 -The use of this code (or any modification of it) to solve the same assignment results in a direct infringement of [cousera's Honor Code](https://www.coursera.support/s/article/209818863-Coursera-Honor-Code?language=en_US").

> 2 - The assessment score was 91/100. The main issues remaining to solve for this algorithm are some corner cases and the <i>backwash</i> problem.