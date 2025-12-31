import numpy as np                      # Libreria para las operaciones matemáticas 
import matplotlib.pyplot as plt         # Creación de  graficos
from mpl_toolkits.mplot3d import axes3d # Módulo para el dibijo 3D

# Rango para la curva y el ángulo
u = np.linspace(-1, 2, 60)      # Creamos 60 puntos entre -1 y 2 
v = np.linspace(0, 2*np.pi, 60) # Creamos 60 puntos entre 0 y 2 pi (Angulo de giro)  

# Se crea la "Malla" del solido, tomando cada valor de u y v
U, V = np.meshgrid(u, v)

# Definir la función
# f(x) = x^2 + 1
def f(x):
    return x**2 + 1

# Parametrización
X = U                
Y = f(U) * np.cos(V) 
Z = f(U) * np.sin(V) 

# Crear la figura y el eje 3D
fig = plt.figure(figsize=(10, 8))
ax = fig.add_subplot(1, 1, 1, projection='3d')

# Graficar el sólido de revolución
surf = ax.plot_surface(X, Y, Z, cmap='coolwarm')

# Configurar la gráfica
ax.set_xlabel('X')
ax.set_ylabel('Y')
ax.set_zlabel('Z')
ax.set_title('Solido de Revolución ($R = X^2 + 1$)')

# Añadir una barra de color para el mapeo de color
fig.colorbar(surf, shrink=0.5, aspect=5, label='Valor de Mapeo de Color')

# Ajustar ángulo de vista para mejor perspectiva
ax.view_init(elev=20, azim=-60)

plt.show()