import numpy as np
import matplotlib.pyplot as plt

t = np.arange(0, 2*2*np.pi, 0.01)

x = t*np.cos(t)
y = t*np.sin(t)
# 
# x = np.exp(0.3*t)*np.cos(t)
# y = np.exp(0.3*t)*np.sin(t)

# x = t - np.sin(t)
# y = 1 - np.cos(t)


plt.plot(x,y)

plt.gca().set_aspect('equal')
plt.show()