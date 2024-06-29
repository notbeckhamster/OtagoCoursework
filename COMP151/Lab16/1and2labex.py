import numpy as np
import matplotlib.pyplot as plt


x = np.arange(0,10,10/100)
print(x)
a = x*x
plt.plot(x,a)
plt.show()

b = (-3)*(x*x) + 2*(x*x) -5
plt.plot(x,b)
plt.show()


c = np.sin(x)/x
plt.plot(x,c)
plt.show()

d = np.cos(x)
plt.plot(x,d)
plt.show()

e = np.log(x)
plt.plot(x,e)
plt.show()

f = np.exp(x)
plt.plot(x,f)
plt.show()