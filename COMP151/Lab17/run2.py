import numpy as np
import matplotlib.pyplot as plt

t = np.arange(0,4*np.pi,0.01)

x = np.cos(t)
y = np.sin(t)

sx = 0.5
sy = 2
scale = np.array(((sx,0),(0,sy)))
rad = np.pi/4
rotate = np.array(((np.cos(rad),-np.sin(rad)),(np.sin(rad),np.cos(rad))))
pts = np.vstack((x,y))
res = np.dot(scale, pts)
res = np.dot(rotate, res)
plt.plot(res[0],res[1])
plt.gca().set_aspect('equal')
plt.show()
