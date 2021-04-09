#!/usr/bin/env python
# coding: utf-8

# In[1]:


import numpy as np
import matplotlib.pyplot as plt
import pandas as pd
from scipy.spatial import distance_matrix
import sys
import math
from ortools.constraint_solver import routing_enums_pb2
from ortools.constraint_solver import pywrapcp


# In[2]:


def generateDistanceMatrix(coordonnees, produits):

    pointNumber = []
    for i in range(len(coordonnees)):
        pointNumber.append("Point " + str(i))
    for i in range(len(produits)):
         pointNumber.append("Produit " + str(i))

    df = pd.DataFrame(coordonnees + produits, columns=['xcord', 'ycord'], index=pointNumber)
    matrix =  pd.DataFrame(distance_matrix(df.values, df.values), index=df.index, columns=df.index)
    return matrix


# In[3]:


def norme(a , b):
    return np.sqrt((b[0]- a[0])**2 + (b[1] - a[1])**2)

def generateDistanceMatrix2(coordonnees, produits):

    coordonneesTot = coordonnees + produits
    matrix = np.zeros((len(coordonneesTot), len(coordonneesTot)))
    for i in range(len(matrix)):
        for j in range(len(matrix[0])):
            matrix[i][j] = norme(coordonneesTot[i],coordonneesTot[j] )
    return matrix.tolist()


# In[4]:


generateDistanceMatrix2([[5, 7], [7, 3], [8, 1]],[[10, 7], [8, 3]] )


# In[6]:


def getCoordonnees(numeroDuPoint, coordonnees, produits):

    if(numeroDuPoint >= len(coordonnees)):
        return produits[i - len(coordonnees)]
    else:
        return coordonnees[i]



def transformMatrixToApplyDijkstra(M):
    B  = M.copy()
    for i in range(len(B)):
        for j in range(len(B[i])):
            if(B[i][j] >1/11 + 0.01 ):
                B[i][j] = 0

    return B



# In[10]:


def generationCoordonnees():
    x = []
    y=  []
    a = 0
    for k in range(0,100,2):
        for i in range(0,150,2):
            x.append( k )
            y.append( i)

    coordonnes = []
    for k in range(len(x)):
        if(abs(x[k] - 25 ) < 10 and abs(y[k] - 40) < 10 or abs(x[k] - 20 ) < 15 and abs(y[k] - 20) < 10 or abs(x[k] - 60 ) < 10 and abs(y[k] - 70) < 20 or abs(x[k] - 70 ) < 10 and abs(y[k] - 30) < 10 or abs(x[k] - 20 ) < 15 and abs(y[k] - 85) < 10 ):
             continue
        else:
            coordonnes.append([x[k],y[k]])

    return coordonnes

def generationCoordonnees2():
    coor = []

    for i in range(1,18):
        coor.append([1/11,i/18])

    for i in range(1,18):
        coor.append([4/11,i/18])
    for i in range(1,18):
        coor.append([7/11,i/18])

    for i in range(1,18):
        coor.append([10/11,i/18])


    for i in range(2,10):
        coor.append([i/11,1/18])
    for i in range(2,10):
        coor.append([i/11,17/18])
    for i in range(2,10):
        coor.append([i/11,1/2])

    return coor

coordonnes2 = generationCoordonnees2()

coordonnes = generationCoordonnees()
# In[9]:


from collections import defaultdict

#Class to represent a graph
class Graph:
    ListeDesCoordonnes = [[]]
    ListeDesDistances = []

    # A utility function to find the
    # vertex with minimum dist value, from
    # the set of vertices still in queue
    def minDistance(self,dist,queue):
        # Initialize min value and min_index as -1
        minimum = float("Inf")
        min_index = -1

        # from the dist array,pick one which
        # has min value and is till in queue
        for i in range(len(dist)):
            if dist[i] < minimum and i in queue:
                minimum = dist[i]
                min_index = i
        return min_index


    # Function to print shortest path
    # from source to j
    # using parent array
    def printPath(self, parent, j,compteur):

        #Base Case : If j is source
        if parent[j] == -1 :
            # print(j)
            self.ListeDesCoordonnes[compteur].append(j)
            return
        self.printPath(parent , parent[j], compteur)
        # print(j)
        self.ListeDesCoordonnes[compteur].append(j)


    # A utility function to print
    # the constructed distance
    # array
    def printSolution(self, src, dist, parent, tailleCoordonnees):
        print("Vertex \t\tDistance from Source\tPath")
        compteur = 0
        for i in range(tailleCoordonnees, len(dist)):
            print("\n%d --> %d \t\t%d \t\t\t\t\t" % (src, i, dist[i])),
            self.ListeDesDistances.append(dist[i])
            self.printPath(parent,i, compteur)
            compteur = compteur + 1
            self.ListeDesCoordonnes.append([])


    '''Function that implements Dijkstra's single source shortest path
    algorithm for a graph represented using adjacency matrix
    representation'''
    def dijkstra(self, graph, src, tailleCoordonnees):
        self.ListeDesCoordonnes= [[]]
        self.ListeDesDistances= []

        compteur =  0

        row = len(graph)
        col = len(graph[0])

        dist = [float("Inf")] * row

        parent = [-1] * row


        dist[src] = 0.0

        # Add all vertices in queue
        queue = []
        for i in range(row):
            queue.append(i)

        while queue:

            u = self.minDistance(dist,queue)

            if( u >= len(coordonnes2)):
                compteur += 1

            queue.remove(u)


            for i in range(col):
                '''Update dist[i] only if it is in queue, there is
                an edge from u to i, and total weight of path from
                src to i through u is smaller than current value of
                dist[i]'''
                if graph[u][i] and i in queue:
                    if dist[u] + graph[u][i] < dist[i]:
                        dist[i] = dist[u] + graph[u][i]
                        parent[i] = u
            if(compteur == len(coordonnes2)):
                self.printSolution(src, dist,parent, tailleCoordonnees)
                return self.ListeDesCoordonnes[:-1], self.ListeDesDistances



        # print the constructed distance array
        self.printSolution(src, dist,parent, tailleCoordonnees)
        return self.ListeDesCoordonnes[:-1], self.ListeDesDistances





# In[11]:


def affichageCoordonnes(coordonnes):
    xRouge = []
    yRouge = []

    for i in range(len(coordonnes)):
        xRouge.append(coordonnes[i][0])
        yRouge.append(coordonnes[i][1])
    plt.scatter(xRouge, yRouge,  color='red')
    plt.show()



# In[12]:


affichageCoordonnes(coordonnes2)


# In[13]:


def affichageProduits(produits):

    xBleu = []
    yBleu = []

    for i in range(len(produits)):
        xBleu.append(produits[i][0])
        yBleu.append(produits[i][1])
    plt.scatter(xBleu, yBleu,  color='blue')


# In[14]:



produits = [[2/11,3/18], [5/11,15/18], [8/11,11/18],[5/11,15/18]    ,[9/11,3/18], [1/11,0], [10/11,0]]




# In[15]:


g = Graph()
graph = transformMatrixToApplyDijkstra(generateDistanceMatrix2(coordonnes2, produits))
print(len(coordonnes))
A,distance  = g.dijkstra(graph,len(coordonnes2) + 5, len(coordonnes2))



# In[16]:


def displayPathFromCoordonnes(coordonnes):
    xfinal = []
    yfinal = []

    for i in range(len(CoordonneesCheminFinal)):
        xfinal.append(CoordonneesCheminFinal[i][0])
        yfinal.append(CoordonneesCheminFinal[i][1])

    plt.scatter(xfinal, yfinal, color='green')



# In[17]:


affichageCoordonnes(coordonnes2)

affichageProduits(coordonnes2)

a = []
for i in range(0,len(A)):
    for k in range(1, len(A[i]) -1 ):
        if i != 0:
            if ( A[i][k] < len(coordonnes2)):
                a.append(coordonnes2[A[i][k]])

b = []
c = []
for i in range(len(a)):
    b.append(a[i][0])
    c.append(a[i][1])

plt.scatter(b, c, color='green')
plt .show()


# In[18]:


allPaths= []
allDistances= []
for i in range(len(produits)):
    paths, distances = g.dijkstra(graph,len(coordonnes2) + i, len(coordonnes2))
    allPaths.append(paths)
    allDistances.append(distances)


# In[19]:


distance_matrix = allDistances.copy()


# In[20]:


distance_matrix.append( [10**8] * (len(produits)-2) + [0] * 2)

for i in range(len(distance_matrix)):
    if (i >= len(distance_matrix) - 3):
        distance_matrix[i].append(0)
    else:
        distance_matrix[i].append(10**8)

# In[21]:


solutionOrder = []


def create_data_model():
    """Stores the data for the problem."""
    data = {}
    data['distance_matrix'] = distance_matrix

      # yapf: disable
    data['num_vehicles'] = 1
    data['depot'] = len(distance_matrix) - 3
    return data


def print_solution(manager, routing, solution):
    """Prints solution on console."""
    print('Objective: {} miles'.format(solution.ObjectiveValue()))

    index = routing.Start(0)
    plan_output = 'Route for vehicle 0:\n'
    route_distance = 0
    while not routing.IsEnd(index):
        plan_output += ' {} ->'.format(manager.IndexToNode(index))
        print(manager.IndexToNode(index))
        solutionOrder.append(int(manager.IndexToNode(index)))
        previous_index = index
        index = solution.Value(routing.NextVar(index))
        route_distance += routing.GetArcCostForVehicle(previous_index, index, 0)
    plan_output += ' {}\n'.format(manager.IndexToNode(index))
    print(plan_output)
    plan_output += 'Route distance: {}miles\n'.format(route_distance)

def main():
    """Entry point of the program."""
    # Instantiate the data problem.
    data = create_data_model()

    # Create the routing index manager.
    manager = pywrapcp.RoutingIndexManager(len(data['distance_matrix']),
                                           data['num_vehicles'], data['depot'])

    # Create Routing Model.
    routing = pywrapcp.RoutingModel(manager)


    def distance_callback(from_index, to_index):
        """Returns the distance between the two nodes."""
        # Convert from routing variable Index to distance matrix NodeIndex.
        from_node = manager.IndexToNode(from_index)
        to_node = manager.IndexToNode(to_index)
        return data['distance_matrix'][from_node][to_node]

    transit_callback_index = routing.RegisterTransitCallback(distance_callback)

    # Define cost of each arc.
    routing.SetArcCostEvaluatorOfAllVehicles(transit_callback_index)

    # Setting first solution heuristic.
    search_parameters = pywrapcp.DefaultRoutingSearchParameters()
    search_parameters.first_solution_strategy = ( routing_enums_pb2.FirstSolutionStrategy.PATH_CHEAPEST_ARC)

    # Solve the problem.
    solution = routing.SolveWithParameters(search_parameters)

    # Print solution on console.
    if solution:
        print_solution(manager, routing, solution)

main()


# In[22]:
print(solutionOrder)


solutionOrder = [solutionOrder[0] ] + list(reversed(solutionOrder[2:]))


# In[23]:





# In[24]:

def test():
    for i in range(1,len(coordonnes2)-1):
        print(coordonnes2[i])

CheminFinal  = []

for i in range(len(solutionOrder) - 1):
    CheminFinal  = CheminFinal + allPaths[solutionOrder[i]][solutionOrder[i+1]]


# In[25]:


CoordonneesCheminFinal = []
for i in range(len(CheminFinal)):

    if(CheminFinal[i] < len(coordonnes2)):

        CoordonneesCheminFinal.append(coordonnes2[CheminFinal[i]])




# In[26]:


def displayPathFromCoordonnes(coordonnes):
    xfinal = []
    yfinal = []

    for i in range(len(coordonnes)):
        xfinal.append(coordonnes[i][0])
        yfinal.append(coordonnes[i][1])

    plt.scatter(xfinal, yfinal, color='green')






# In[27]:


affichageCoordonnes(coordonnes2)

plt.axis([-0.1, 1, -0.1, 1])


affichageProduits(produits)

displayPathFromCoordonnes(CoordonneesCheminFinal)
plt .show()

print()
print()
print()


def result():
    return CoordonneesCheminFinal

if __name__ == '__main__':
    result()



# In[ ]:





# In[ ]:




