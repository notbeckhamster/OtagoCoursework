import pandas as pd

df = pd.read_csv("effects-of-covid-19-on-trade.csv")
trade_types = df["Direction"].unique()
years = df["Year"].unique()
for direction in trade_types:
    direction_df = df[df["Direction"] == direction]
    for year in years:
        year_df = direction_df[direction_df["Year"] == year]["Value"]
        print(direction, year, str(year_df.mean()))
