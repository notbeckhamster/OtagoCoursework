import numpy as np
import matplotlib.pyplot as plt


x = np.arange(0,10,0.1)
print(x.shape)
y = np.sin(x)

plt.plot(x,y)
plt.show()

b = 3*np.sin(x*x) - 2*np.cos(x*x)
plt.plot(x,b)
plt.show()