import numpy as np
import matplotlib.pyplot as plt

t = np.arange(0,2*np.pi, 0.01)

# x = np.cos(t)
# y = np.sin(t)

x = t * np.cos(t)
y = t * np.sin(t)




plt.plot(scaled_xy[0],scaled_xy[1])
plt.gca().set_aspect('equal')
plt.show()