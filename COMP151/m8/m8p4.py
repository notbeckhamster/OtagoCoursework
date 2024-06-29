import pandas as pd

df = pd.read_csv("effects-of-covid-19-on-trade.csv")
days = df["Weekday"].unique()
years = df["Year"].unique()
for year in years:
    year_df = df[df["Year"] == year]
    for day in days:
        day_df = year_df[year_df["Weekday"] == day]["Value"]
        print(year, day, day_df.mean())
