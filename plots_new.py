import pandas as pd
import matplotlib.pyplot as plt
import os

# Read the CSV file into a pandas DataFrame
data = pd.read_csv('AverageTimes2.csv')

# Grouping the data by function, bufferSize, PKNumber, and genName
grouped = data.groupby(['function', 'bufferSize', 'PKNumber', 'genName'])

# Create a folder to save the plots if it doesn't exist
os.makedirs('plots', exist_ok=True)

# Plotting bar plots for each group and saving to 'plots' folder
for group_keys, group_df in grouped:
    group_title = '_'.join(str(key) for key in group_keys)
    
    function, bufferSize, PKNumber, genName = group_keys

    # Create a title with annotations for parameter values
    title = f"Average single operation execution time per chunk size\nBuffer type {function}, size: {bufferSize}, PK: {PKNumber}, Generator: {genName}"
    
    
    # Create a single bar plot for each group
    plt.figure()
    plt.title(title)
    
    aggregated_mean = group_df['time'].mean()

    # Plot bars for each chunkSize in the group with a consistent color
    for chunk_size, chunk_df in group_df.groupby('chunkSize'):
        plt.bar(chunk_df['chunkSize'], chunk_df['time'], color='skyblue', alpha=0.7)

    plt.xlabel('Chunk Size')
    plt.ylabel('Time [ns]')
    
    plt.axhline(aggregated_mean, color='red', linestyle='--', label=f'Mean: {aggregated_mean:.2f}')
    plt.legend()
    
    plt.ticklabel_format(axis='y', style='sci', scilimits=(0, 0))


    # Save the plot as an image in the 'plots' folder
    plt.savefig(f'plots/AverageTimes_{group_title}_plot.png')
    plt.close()  # Close the plot to release memory

print("Plots saved successfully in 'plots' folder.")



data = pd.read_csv('CPUTimes2.csv')
grouped = data.groupby(['function', 'bufferSize', 'PKNumber', 'genName'])

# Create a folder to save the plots if it doesn't exist
os.makedirs('plots', exist_ok=True)

# Plotting line plots for each group and saving to 'plots' folder
for group_keys, group_df in grouped:
    group_title = '_'.join(str(key) for key in group_keys)
    
    # Extracting parameter values for the title
    function, bufferSize, PKNumber, genName = group_keys

    # Create a title with annotations for parameter values
    title = f"CPU time of each thread\nBuffer type {function}, size: {bufferSize}, PK: {PKNumber}, Generator: {genName}"
    aggregated_mean = group_df['CPUTime'].mean()

    # Create a single line plot for each group
    plt.figure()
    plt.title(title)

    # Plot CPUTime against id
    
    plt.bar(group_df['id'], group_df['CPUTime'], color='skyblue', alpha=0.7)

    plt.xlabel('Thread ID')
    plt.ylabel('CPUTime [ns]')
    
    plt.axhline(aggregated_mean, color='red', linestyle='--', label=f'Mean: {aggregated_mean:.2f}')
    plt.legend()
    
    plt.ticklabel_format(axis='y', style='sci', scilimits=(0, 0))

    # Save the plot as an image in the 'plots' folder
    plt.savefig(f'plots/CPUTime_{group_title}_plot.png')
    plt.close()  # Close the plot to release memory

print("Plots saved successfully in 'plots' folder.")



data = pd.read_csv('ExecutionsNumber2.csv')
grouped = data.groupby(['function', 'bufferSize', 'PKNumber', 'genName'])

# Create a folder to save the plots if it doesn't exist
os.makedirs('plots', exist_ok=True)

# Plotting line plots for each group and saving to 'plots' folder
for group_keys, group_df in grouped:
    group_title = '_'.join(str(key) for key in group_keys)
    
    # Extracting parameter values for the title
    function, bufferSize, PKNumber, genName = group_keys

    # Create a title with annotations for parameter values
    title = f"Produce / consume executions for each thread during runtime\nBuffer type {function}, size: {bufferSize}, PK: {PKNumber}, Generator: {genName}"
    aggregated_mean = group_df['ExecutionsNumber'].mean()

    # Create a single line plot for each group
    plt.figure()
    plt.title(title)

    # Plot CPUTime against id
    
    plt.bar(group_df['id'], group_df['ExecutionsNumber'], color='skyblue', alpha=0.7)

    plt.xlabel('Thread ID')
    plt.ylabel('Executions')
    
    plt.axhline(aggregated_mean, color='red', linestyle='--', label=f'Mean: {aggregated_mean:.2f}')
    plt.legend()
    
    plt.ticklabel_format(axis='y', style='sci', scilimits=(0, 0))

    # Save the plot as an image in the 'plots' folder
    plt.savefig(f'plots/ExecutionsNumber_{group_title}_plot.png')
    plt.close()  # Close the plot to release memory

print("Plots saved successfully in 'plots' folder.")