# #!/usr/bin/env python
# # coding: utf-8
#
# # <h1 style="text-align:center" > Plus court chemin dans un magasin </h1>
#
# # <h3> Importations </h3>
#
# # In[86]:
#
#
import numpy as np
import networkx as nx

from scipy.spatial import distance_matrix
#from ortools.constraint_solver import routing_enums_pb2
#from ortools.constraint_solver import pywrapcp
from scipy.sparse import csr_matrix
from scipy.sparse.csgraph import floyd_warshall
from scipy.sparse.csgraph import johnson



# <h3> Création de la matrice pour représenter le graphe</h3>

# In[87]:


def generateAdjencyMatrix(coordonnees, produits):

    pointNumber = []
    for i in range(len(coordonnees)):
        pointNumber.append("Point " + str(i))
    for i in range(len(produits)):
         pointNumber.append("Produit " + str(i))

    df = pd.DataFrame(coordonnees + produits, columns=['xcord', 'ycord'], index=pointNumber)
    matrix =  pd.DataFrame(distance_matrix(df.values, df.values), index=df.index, columns=df.index)
    return matrix.values.tolist()


# In[88]:


def norme(a , b):
    return np.sqrt((b[0]- a[0])**2 + (b[1] - a[1])**2)

def generateAdjencyMatrix2(coordonnees, produits):

    coordonneesTot = coordonnees + produits
    matrix = np.zeros((len(coordonneesTot), len(coordonneesTot)))
    for i in range(len(matrix)):
        for j in range(len(matrix[0])):
            matrix[i][j] = norme(coordonneesTot[i],coordonneesTot[j] )
    return matrix.tolist()


# In[89]:


def transformMatrixToApplyJhonson(M):
    B  = M.copy()
    for i in range(len(B)):
        for j in range(len(B[i])):
            if(B[i][j] >4.25):
                B[i][j] = 0

    return B


# <h3> Création des différents points à placer dans la map</h3>
#

# In[106]:


def generationCoordonnees():
    x = []
    y=  []
    a = 0
    for k in range(0,100,3):
        for i in range(0,150,3):
            x.append( k )
            y.append( i)

    coordonnes = []
    nonCoordonnes = []
    for k in range(len(x)):
        if(abs(x[k] - 20 ) < 15 and abs(y[k] - 60) < 10 or abs(x[k] - 20 ) < 15 and abs(y[k] - 20) < 10 or abs(x[k] - 60 ) < 10 and abs(y[k] - 70) < 20 or abs(x[k] - 70 ) < 10 and abs(y[k] - 30) < 10 or abs(x[k] - 20 ) < 15 and abs(y[k] - 85) < 10 or abs(x[k] - 40 ) < 15 and abs(y[k] - 120) < 10):
            nonCoordonnes.append([x[k],y[k]])
        else:
            coordonnes.append([x[k],y[k]])

    return coordonnes, nonCoordonnes


# <h3> Valeurs des coordonnées des produits </h3>
#

# In[94]:
coordonnes, nonCoordonnes = generationCoordonnees()

produits = [[50,45], [10,10], [20,50],[0,0],[50,20], [80,80],[60,90],[90,20], [21,75], [0,80],[50,105], [20,130], [5,45], [45,60],  [80,60], [90,140] , [80,10]]


# <h3> Matrice d'adjacence  </h3>
#

# In[95]:


adjency_matrix = transformMatrixToApplyJhonson(generateAdjencyMatrix2(coordonnes, produits))


# <h3> Application Johnson </h3>
#

# In[97]:


dist_matrix, predecessors = johnson(csgraph=adjency_matrix, directed=False,indices=[i for i in range(len(coordonnes), len(coordonnes) + len(produits))], return_predecessors=True)


# In[98]:


def getPath(A,i,j):
    L = []
    a = A[i,j]
    while(a != len(coordonnes) + i and a != -9999 ):
        L.append(a)
        a = A[i,a]
    return L


# <h3> Construction de la distance matrix </h3>

# In[99]:


distance_matrix = dist_matrix[:, len(coordonnes):].tolist().copy()
#
# def main():
#     return  getPath(predecessors, 0, len(coordonnes)  + 1)


# In[100]:


#Ajout du point fictif pour appliquer le problème du voyageur de commerce

distance_matrix.append( [10**8] * (len(produits)-2) + [0] * 2)

for i in range(len(distance_matrix)):
    if (i >= len(distance_matrix) - 3):
        distance_matrix[i].append(0)
    else:
        distance_matrix[i].append(10**8)



def main():
    return  distance_matrix


# # <h3> Résolution du problème du voyageur de commerce </h3>
#
# # In[101]:
#
#
# solutionOrder = []
#
#
# def create_data_model():
#     """Stores the data for the problem."""
#     data = {}
#     data['distance_matrix'] = distance_matrix
#
#       # yapf: disable
#     data['num_vehicles'] = 1
#     data['depot'] = len(distance_matrix) - 3
#     return data
#
#
# def print_solution(manager, routing, solution):
#     """Prints solution on console."""
#     print('Objective: {} miles'.format(solution.ObjectiveValue()))
#
#     index = routing.Start(0)
#     plan_output = ''
#     route_distance = 0
#     while not routing.IsEnd(index):
#         plan_output += ' {} ->'.format(manager.IndexToNode(index))
#         print(manager.IndexToNode(index))
#         solutionOrder.append(int(manager.IndexToNode(index)))
#         previous_index = index
#         index = solution.Value(routing.NextVar(index))
#         route_distance += routing.GetArcCostForVehicle(previous_index, index, 0)
#     plan_output += ' {}\n'.format(manager.IndexToNode(index))
#     print(plan_output)
#     plan_output += 'Route distance: {}miles\n'.format(route_distance)
#     return solutionOrder
#
# def main():
#     """Entry point of the program."""
#     # Instantiate the data problem.
#     data = create_data_model()
#
#     # Create the routing index manager.
#     manager = pywrapcp.RoutingIndexManager(len(data['distance_matrix']),
#                                            data['num_vehicles'], data['depot'])
#
#     # Create Routing Model.
#     routing = pywrapcp.RoutingModel(manager)
#
#
#     def distance_callback(from_index, to_index):
#         """Returns the distance between the two nodes."""
#         # Convert from routing variable Index to distance matrix NodeIndex.
#         from_node = manager.IndexToNode(from_index)
#         to_node = manager.IndexToNode(to_index)
#         return data['distance_matrix'][from_node][to_node]
#
#     transit_callback_index = routing.RegisterTransitCallback(distance_callback)
#
#     # Define cost of each arc.
#     routing.SetArcCostEvaluatorOfAllVehicles(transit_callback_index)
#
#     # Setting first solution heuristic.
#     search_parameters = pywrapcp.DefaultRoutingSearchParameters()
#     search_parameters.first_solution_strategy = ( routing_enums_pb2.FirstSolutionStrategy.PATH_CHEAPEST_ARC)
#
#     # Solve the problem.
#     solution = routing.SolveWithParameters(search_parameters)
#
#     # Print solution on console.
#     if solution:
#         return print_solution(manager, routing, solution)
#     return None
#
# if __name__ == '__main__':
#     main()
#
#
# # In[102]:
#
#
# solutionOrder = [solutionOrder[0] ] + list(reversed(solutionOrder[2:]))
#
#
# # <h3> Construction du chemin final </h3>
#
# # In[103]:
#
#
# CheminFinal  = []
#
# for i in range(len(solutionOrder) - 1):
#     #CheminFinal  = CheminFinal + allPaths[solutionOrder[i]][solutionOrder[i+1]]
#     CheminFinal  = CheminFinal + getPath(predecessors, solutionOrder[i+1], len(coordonnes)  + solutionOrder[i])
#
# # In[104]:
#
#
# CoordonneesCheminFinal = []
# for i in range(len(CheminFinal)):
#     if(CheminFinal[i] < len(coordonnes)):
#         CoordonneesCheminFinal.append(coordonnes[CheminFinal[i]])
#
#
# # print(CoordonneesCheminFinal)
#
# # <h3> Affichage du chemin final </h3>
#
# # In[105]:
#
#
#
