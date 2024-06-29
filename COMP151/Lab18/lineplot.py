# we need matplotlib to move the legend
import matplotlib.pyplot as plt
import pandas as pd

ghg = pd.read_csv("greenhouse-gas-emissions.csv")
regions = ghg['region'].unique()
years = ghg['year'].unique()
regions_df = pd.DataFrame(index=years, columns=regions)

for region in regions:
    regions_df[region] = \
    ghg[(ghg['region']==region) &
    (ghg['anzsic_descriptor']=='Agriculture')]['data_val'].values

regions_df.plot(figsize=(10,6), ylim=(0,12000))

plt.legend(ncol=4)
plt.show()
