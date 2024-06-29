import pandas as pd
import matplotlib.pyplot as plt

df = pd.read_csv("renewable-energy-stock-account-2007-18.csv")
print(df)
gigawatt_hours_df = df[df["variable"] == "Gigawatt hours"]

hydro_df = gigawatt_hours_df[gigawatt_hours_df["resource"] == "Hydro"]
new_df = pd.DataFrame(index=hydro_df["year"], columns=["gwhours"])
new_df["gwhours"] = hydro_df["data_value"].values
print(new_df)
new_df.plot()
plt.show()