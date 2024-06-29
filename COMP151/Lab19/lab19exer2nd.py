import pandas as pd
import matplotlib.pyplot as plt

df = pd.read_csv("renewable-energy-stock-account-2007-18.csv")
gh_df = df[df["variable"] == "Gigawatt hours"]
print(gh_df.groupby("resource")["data_value"].sum())
# resources = df["resource"].unique()
# for resource in resources:
#     print(resource, round(gh_df[gh_df["resource"] == resource]["data_value"].sum(),2))
print(gh_df.groupby("year")["data_value"].mean())
print(gh_df.groupby("resource")["data_value"].mean())
    
years = gh_df["year"].unique()
resources = gh_df["resource"].unique()

hydro_gw_df = pd.DataFrame(index=years, columns=resources)

for resource in resources:
    hydro_gw_df[resource] = gh_df[gh_df["resource"] == resource]["data_value"].values
#hydro_gw_df.T.plot(kind="bar")
hydro_gw_df.plot(kind="box")
plt.show()
