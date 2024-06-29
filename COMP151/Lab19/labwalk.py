import pandas as pd
import matplotlib.pyplot as plt

ghg = pd.read_csv("greenhouse-gas-emissions.csv")
ghg.plot()
plt.show()

ghg[ghg['region']=="Auckland"].plot()
plt.show()

ghg[(ghg['region']=='Auckland') &
(ghg['anzsic_descriptor']=='Agriculture')].plot(x='year',y='data_val')
plt.show()
print(ghg[(ghg['region']=='Auckland') &
(ghg['anzsic_descriptor']=='Agriculture')])