import pandas as pd
df = pd.read_csv("greenhouse-gas-emissions.csv")
df.to_excel("greenhouse-gas-emissions.xlsx")
print(df)
print(df["anzsic_descriptor"].unique())

hh_df = df[df["anzsic_descriptor"] == "Households"]
print(hh_df)
print(hh_df[(hh_df["year"] >= 2007) & (hh_df["year"] <= 2018)]["data_val"].sum())
print(hh_df[(hh_df["year"] >= 2007) & (hh_df["year"] <= 2018)].groupby("year").sum("data_val"))


