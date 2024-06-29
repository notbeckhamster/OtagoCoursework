import pandas as pd
import matplotlib.pyplot as plt

df = pd.read_csv("renewable-energy-stock-account-2007-18.csv")
print(df)
gigawatt_hours_df = df[df["variable"] == "Gigawatt hours"]
resource_list = gigawatt_hours_df["resource"].unique()
year_list = gigawatt_hours_df["year"].unique()
gw_year_df = pd.DataFrame(index = year_list, columns=resource_list)
print(gigawatt_hours_df[gigawatt_hours_df["resource"] == "Hydro"])
for resource in resource_list:
    resource_df = gigawatt_hours_df[gigawatt_hours_df["resource"] == resource]
    gw_year_df[resource] = resource_df["data_value"].values
gw_year_df.T.plot(kind="bar")
gw_year_df.plot(kind="box")
plt.show()
