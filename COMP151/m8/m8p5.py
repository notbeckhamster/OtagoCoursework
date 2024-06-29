import pandas as pd

df = pd.read_csv("effects-of-covid-19-on-trade.csv")
directions = df["Direction"].unique()
weekdays = df["Weekday"].unique()
for direction in directions:
    dir_df = df[df["Direction"] == direction]
    for day in weekdays:
        day_df = dir_df[dir_df["Weekday"] == day]["Value"]
        print(direction, day, day_df.mean())