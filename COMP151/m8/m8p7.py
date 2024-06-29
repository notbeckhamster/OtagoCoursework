import pandas as pd
import matplotlib.pyplot as plt

df = pd.read_csv("effects-of-covid-19-on-trade.csv")
years = df["Year"].unique()
directions = df["Direction"].unique()

new_df = pd.DataFrame(index=years, columns=directions)
for year in years:
    year_df = df[df["Year"] == year]
    for direction in directions:
        new_df.loc[year][direction] = year_df[year_df["Direction"] == direction]["Value"].sum()

new_df.plot(kind="bar")
plt.show()