import pandas as pd
import matplotlib.pyplot as plt
# bf_error_projections_1_df = pd.read_csv("./build/bf_error_projections_1_list.csv", header=None).T
# bf_error_projections_3_df = pd.read_csv("./build/bf_error_projections_3_list.csv", header=None).T
# bf_error_projections_5_df = pd.read_csv("./build/bf_error_projections_5_list.csv", header=None).T
# bf_error_projections_7_df = pd.read_csv("./build/bf_error_projections_7_list.csv", header=None).T

# print(bf_error_projections_1_df)
# print(bf_error_projections_3_df)
# print(bf_error_projections_5_df)
# print(bf_error_projections_7_df)
# plt.figure(figsize=(10, 6))  # Adjust figure size if needed
# plt.boxplot([bf_error_projections_1_df[0], bf_error_projections_3_df[0], bf_error_projections_5_df[0],bf_error_projections_7_df[0]], labels=['1', '3','5','7'], showfliers=False)
# plt.xlabel('RANSAC Threshold (in pixels)')
# plt.ylabel('Reprojection Error (in pixels)')
# plt.title('Comparison of Reprojection Errors for inliers points for varying RANSAC thresholds')
# plt.show()

# print(bf_error_projections_1_df[0].describe())
# print(bf_error_projections_3_df[0].describe())
# print(bf_error_projections_5_df[0].describe())
# print(bf_error_projections_7_df[0].describe())

def filter(data) -> pd.DataFrame:
    q1 = data[0].quantile(0.25)
    q3 = data[0].quantile(0.75)
    iqr = q3 - q1
    threshold_multiplier = 1.5
    lower_bound = q1 - threshold_multiplier * iqr
    upper_bound = q3 + threshold_multiplier * iqr

    # Filter out outliers
    filtered_data = data[(data[0] >= lower_bound) & (data[0] <= upper_bound)]
    return filtered_data


bf_error_projections_1_df = pd.read_csv("./build/bf_error_projections_1_list_with_outliers.csv", header=None).T
bf_error_projections_3_df = pd.read_csv("./build/bf_error_projections_3_list_with_outliers.csv", header=None).T
bf_error_projections_5_df = pd.read_csv("./build/bf_error_projections_5_list_with_outliers.csv", header=None).T
bf_error_projections_7_df = pd.read_csv("./build/bf_error_projections_7_list_with_outliers.csv", header=None).T
bf_error_projections_9_df = pd.read_csv("./build/bf_error_projections_9_list_with_outliers.csv", header=None).T
bf_error_projections_1_df = filter(bf_error_projections_1_df)
bf_error_projections_3_df = filter(bf_error_projections_3_df)
bf_error_projections_5_df = filter(bf_error_projections_5_df)
bf_error_projections_7_df = filter(bf_error_projections_7_df)
bf_error_projections_9_df = filter(bf_error_projections_9_df)
print(bf_error_projections_1_df)
print(bf_error_projections_3_df)
print(bf_error_projections_5_df)
print(bf_error_projections_7_df)
print(bf_error_projections_9_df)
plt.figure(figsize=(10, 6))  # Adjust figure size if needed
plt.boxplot([bf_error_projections_1_df[0], bf_error_projections_3_df[0], bf_error_projections_5_df[0],bf_error_projections_7_df[0],bf_error_projections_9_df[0]], labels=['1', '3','5','7', '9'], showfliers=False)
plt.xlabel('RANSAC Threshold (in pixels)')
plt.ylabel('Reprojection Error (in pixels)')
plt.title('Comparison of Reprojection Errors for both inlier and outlier points for varying RANSAC thresholds')
plt.show()

print(bf_error_projections_1_df[0].describe())
print(bf_error_projections_3_df[0].describe())
print(bf_error_projections_5_df[0].describe())
print(bf_error_projections_7_df[0].describe())
print(bf_error_projections_9_df[0].describe())





