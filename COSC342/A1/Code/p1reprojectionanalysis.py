import pandas as pd
import matplotlib.pyplot as plt
# bf_error_projections_df = pd.read_csv("./build/bf_error_projections.csv", header=None).T
# flann_error_projections_df = pd.read_csv("./build/flann_error_projections.csv", header=None).T

# bf_error_projections_df = filter(bf_error_projections_df)
# flann_error_projections_df = filter(flann_error_projections_df)
# print(bf_error_projections_df)
# print(flann_error_projections_df)
# plt.figure(figsize=(10, 6))  # Adjust figure size if needed
# plt.boxplot([bf_error_projections_df[0], flann_error_projections_df[0]], labels=['Brute-Force', 'FLANN'])
# plt.ylabel('Reprojection Error (in pixels)')
# plt.title('Comparison of Reprojection Errors for inliers (Brute-Force vs FLANN) ')
# plt.show()

# print(bf_error_projections_df[0].describe())
# print(flann_error_projections_df[0].describe())
def filterdf(data) -> pd.DataFrame:
    q1 = data[0].quantile(0.25)
    q3 = data[0].quantile(0.75)
    iqr = q3 - q1
    threshold_multiplier = 1.5
    lower_bound = q1 - threshold_multiplier * iqr
    upper_bound = q3 + threshold_multiplier * iqr

    # Filter out outliers
    filtered_data = data[(data[0] >= lower_bound) & (data[0] <= upper_bound)]
    return filtered_data

bf_error_projections_with_outliers_df = pd.read_csv("./build/bf_error_projections_with_outliers.csv", header=None).T
flann_error_projections_with_outliers_df = pd.read_csv("./build/flann_error_projections_with_outliers.csv", header=None).T
bf_error_projections_with_outliers_df = filterdf(bf_error_projections_with_outliers_df)
flann_error_projections_with_outliers_df = filterdf(flann_error_projections_with_outliers_df)
print(bf_error_projections_with_outliers_df)
print(flann_error_projections_with_outliers_df)
plt.figure(figsize=(10, 6))  # Adjust figure size if needed
plt.boxplot([bf_error_projections_with_outliers_df[0], flann_error_projections_with_outliers_df[0]], labels=['Brute-Force', 'FLANN'], showfliers=False)
plt.ylabel('Reprojection Error (in pixels)')
plt.title('Comparison of Reprojection Errors for both inliers and outliers (Brute-Force vs FLANN)')
plt.show()

print(bf_error_projections_with_outliers_df[0].describe())
print(flann_error_projections_with_outliers_df[0].describe())


