import numpy as np
from scipy.spatial import KDTree
N   = 200
pts = 2500*np.random.random((N,2))

tree = KDTree(pts)
print(tree)
print(tree.sparse_distance_matrix(tree, 200))
