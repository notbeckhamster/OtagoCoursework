import pandas as pd
import matplotlib.pyplot as plt

df = pd.read_csv("effects-of-covid-19-on-trade.csv")
years = df["Year"].unique()
days = df["Weekday"].unique()
new_df = pd.DataFrame(index=years, columns=days)
for year in years:
    year_df=df[df["Year"] == year]
    for day in days:
        day_df = year_df[year_df["Weekday"] == day]
        new_df.loc[year][day] = day_df["Value"].sum()
        
print(new_df)
new_df.plot(kind="bar")
plt.show()
