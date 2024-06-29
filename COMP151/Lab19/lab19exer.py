import pandas as pd
import matplotlib.pyplot as plt

df = pd.read_csv("renewable-energy-stock-account-2007-18.csv")
df = df[df["variable"] == "Gigawatt hours"]
years = df["year"].unique()
resources = df["resource"].unique()
new_df = pd.DataFrame(index = years, columns = resources)

for year in years:
    year_df = df[df["year"] == year]
    for resource in resources:
        res_df = year_df[year_df["resource"] == resource]
        ym = res_df["data_value"].mean()
        new_df.loc[year][resource] = ym
new_df.plot()
plt.show()