import numpy as np
import matplotlib.pyplot as plt
import math

x = np.arange(0, 10, 0.1)
# y = np.sin(x)
# y = 3*np.sin(x**2)-2*np.cos(x**3)
y = 2**((-x**2)/2)

plt.plot(x,y)
plt.show()