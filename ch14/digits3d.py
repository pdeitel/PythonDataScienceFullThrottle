# Code for visualizing Digits dataset in 3D
from sklearn.datasets import load_digits
from sklearn.manifold import TSNE
import matplotlib
import matplotlib.pyplot as plt
import mpl_toolkits.mplot3d.axes3d as axes3d
digits = load_digits()
tsne3 = TSNE(n_components=3, random_state=11)
reduced_data3 = tsne3.fit_transform(digits.data) 
figure = plt.figure(figsize=(7, 5))
axes = figure.add_subplot(projection='3d')
dots = axes.scatter(reduced_data3[:, 0], reduced_data3[:, 1], reduced_data3[:, 2], 
    c=digits.target, cmap=matplotlib.colormaps['nipy_spectral_r'])
colorbar = plt.colorbar(dots)
plt.show()
