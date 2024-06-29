import pandas as pd
import matplotlib.pyplot as plt

df = pd.read_csv("effects-of-covid-19-on-trade.csv")
years = df["Year"].unique()
year_df = pd.DataFrame(index=years, columns=["sum"])
for year in years:
    year_df.loc[year]["sum"]=df[df["Year"] == year]["Value"].sum()


year_df.plot(kind="bar")
plt.show()