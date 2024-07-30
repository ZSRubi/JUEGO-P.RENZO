import numpy as np
import networkx as nx
import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation
from networkx.algorithms.approximation import traveling_salesman_problem
import pandas as pd

# Leer las coordenadas de los nodos desde el archivo Excel
coords_df = pd.read_excel('nodos_coordenadas.xlsx')
pos = {row['Nodo']: (row['X'], row['Y']) for _, row in coords_df.iterrows()}

# Definir la matriz de distancias
distance_matrix = np.array([
    [0, 29, 20, 21, 17, 28, 25, 17, 21, 29, 25, 27, 24, 25, 27, 24, 23, 26, 25, 22],
    [29, 0, 15, 29, 20, 25, 17, 21, 29, 25, 27, 20, 23, 30, 21, 29, 23, 25, 26, 27],
    [20, 15, 0, 15, 14, 21, 14, 22, 30, 24, 21, 17, 20, 24, 18, 23, 23, 20, 22, 21],
    [21, 29, 15, 0, 16, 24, 18, 20, 30, 22, 28, 22, 24, 30, 28, 22, 28, 21, 25, 22],
    [17, 20, 14, 16, 0, 26, 22, 25, 22, 28, 27, 21, 20, 24, 20, 24, 25, 25, 27, 23],
    [28, 25, 21, 24, 26, 0, 19, 21, 28, 24, 20, 22, 26, 28, 22, 27, 26, 30, 28, 30],
    [25, 17, 14, 18, 22, 19, 0, 23, 29, 21, 30, 17, 21, 26, 18, 24, 22, 22, 28, 24],
    [17, 21, 22, 20, 25, 21, 23, 0, 27, 23, 20, 23, 24, 22, 21, 27, 22, 23, 22, 21],
    [21, 29, 30, 30, 22, 28, 29, 27, 0, 20, 24, 24, 29, 30, 26, 22, 27, 25, 26, 28],
    [29, 25, 24, 22, 28, 24, 21, 23, 20, 0, 23, 28, 25, 27, 20, 26, 22, 20, 26, 22],
    [25, 27, 21, 28, 27, 20, 30, 20, 24, 23, 0, 18, 27, 29, 21, 19, 20, 24, 28, 26],
    [27, 20, 17, 22, 21, 22, 17, 23, 24, 28, 18, 0, 24, 25, 27, 20, 20, 25, 27, 22],
    [24, 23, 20, 24, 20, 26, 21, 24, 29, 25, 27, 24, 0, 30, 23, 22, 27, 28, 22, 21],
    [25, 30, 24, 30, 24, 28, 26, 22, 30, 27, 29, 25, 30, 0, 27, 24, 23, 30, 28, 26],
    [27, 21, 18, 28, 20, 22, 18, 21, 26, 20, 21, 27, 23, 27, 0, 25, 21, 24, 28, 27],
    [24, 29, 23, 22, 24, 27, 24, 27, 22, 26, 19, 20, 22, 24, 25, 0, 22, 24, 27, 30],
    [23, 23, 23, 28, 25, 26, 22, 22, 27, 22, 20, 20, 27, 23, 21, 22, 0, 27, 26, 25],
    [26, 25, 20, 21, 25, 30, 22, 23, 25, 20, 24, 25, 28, 30, 24, 24, 27, 0, 22, 23],
    [25, 26, 22, 25, 27, 28, 28, 22, 26, 26, 28, 27, 22, 28, 28, 27, 26, 22, 0, 24],
    [22, 27, 21, 22, 23, 30, 24, 21, 28, 22, 26, 22, 21, 26, 27, 30, 25, 23, 24, 0]
])

# Crear un grafo para la visualización
G = nx.Graph()

# Agregar nodos
for i in range(len(distance_matrix)):
    G.add_node(i)

# Agregar aristas con distancias como pesos
for i in range(len(distance_matrix)):
    for j in range(i + 1, len(distance_matrix)):
        G.add_edge(i, j, weight=distance_matrix[i][j])

# Encontrar la ruta más corta usando el algoritmo de aproximación
def tsp_approximation(distance_matrix):
    G = nx.from_numpy_array(distance_matrix, create_using=nx.Graph)
    tour = traveling_salesman_problem(G, cycle=True)
    return tour

# Obtener la ruta más corta
tour = tsp_approximation(distance_matrix)
print(f"Ruta más corta: {tour}")

# Calcular la distancia total de la ruta
total_distance = sum(distance_matrix[tour[i], tour[i + 1]] for i in range(len(tour) - 1))
total_distance += distance_matrix[tour[-1], tour[0]]
print(f"Distancia total: {total_distance}")

# Obtener los pesos de las aristas
weights = nx.get_edge_attributes(G, 'weight')

# Dibujar el grafo con la ruta
fig, ax = plt.subplots(figsize=(10, 7))

def update(num, tour, pos, ax):
    ax.clear()
    nx.draw(G, pos, with_labels=True, node_color='lightblue', node_size=500, font_size=16, font_weight='bold', ax=ax)
    nx.draw_networkx_edge_labels(G, pos, edge_labels=weights, font_color='green', ax=ax)
    if num > 0:
        edges = [(tour[i], tour[i + 1]) for i in range(num)] + [(tour[num], tour[0])] if num == len(tour) - 1 else [(tour[i], tour[i + 1]) for i in range(num)]
        nx.draw_networkx_edges(G, pos, edgelist=edges, edge_color='red', width=2, ax=ax)

ani = FuncAnimation(fig, update, frames=len(tour), fargs=(tour, pos, ax), interval=500, repeat=False)

plt.title(f"Ruta más corta (Distancia total: {total_distance})")
plt.show()

