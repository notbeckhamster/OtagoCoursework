import pandas as pd
import matplotlib.pyplot as plt
bf_inliers_count_df = pd.read_csv("./build/bf_inliers_count_list.csv", header=None).T
bf_total_match_df = pd.read_csv("./build/bf_total_match_list.csv", header=None).T
bf_inlier_ratio_df = pd.read_csv("./build/bf_inlier_ratio.csv", header=None).T
print(bf_inliers_count_df)
print(bf_total_match_df)
print(bf_inlier_ratio_df)
bf_proportion_df = pd.concat([bf_inliers_count_df, bf_total_match_df, bf_inlier_ratio_df], axis=1)
bf_proportion_df.columns = ["inlier_count", "total", "inlier_ratio"]
bf_proportion_df["outlier_count"] = bf_proportion_df["total"] - bf_proportion_df["inlier_count"]
print(bf_proportion_df)


flann_inliers_count_df = pd.read_csv("./build/flann_inliers_count_list.csv", header=None).T
flann_total_match_df = pd.read_csv("./build/flann_total_match_list.csv", header=None).T
flann_inlier_ratio_df = pd.read_csv("./build/flann_inlier_ratio.csv", header=None).T
print(flann_inliers_count_df)
print(flann_total_match_df)
print(flann_inlier_ratio_df)
flann_proportion_df = pd.concat([flann_inliers_count_df, flann_total_match_df, flann_inlier_ratio_df], axis=1)
flann_proportion_df.columns = ["inlier_count", "total", "inlier_ratio"]
flann_proportion_df["outlier_count"] = flann_proportion_df["total"] - flann_proportion_df["inlier_count"]
print(flann_proportion_df)

plt.figure(figsize=(10, 6))  # Adjust figure size if needed
plt.boxplot([bf_proportion_df["inlier_ratio"], flann_proportion_df["inlier_ratio"]], labels=['Brute-Force', 'FLANN'], showfliers=False)
plt.ylabel('Inlier Ratio')
plt.title('Comparison of Inlier Ratio(Brute-Force vs FLANN)')
plt.show()

print(bf_proportion_df["inlier_ratio"].describe())
print(flann_proportion_df["inlier_ratio"].describe())
print(bf_proportion_df["inlier_count"].describe())
print(flann_proportion_df["inlier_count"].describe())
print(bf_proportion_df["outlier_count"].describe())
print(flann_proportion_df["outlier_count"].describe())
print(bf_proportion_df["total"].describe())
print(flann_proportion_df["total"].describe())

plt.figure(figsize=(10, 6))  # Adjust figure size if needed
plt.boxplot([bf_proportion_df["outlier_count"], flann_proportion_df["outlier_count"]], labels=['Brute-Force', 'FLANN'], showfliers=False)
plt.ylabel('Outlier Count')
plt.title('Comparison of Outlier Count (Brute-Force vs FLANN)')
plt.show()
