import pandas as pd
import matplotlib.pyplot as plt

# Load the CSV file into a pandas DataFrame
df = pd.read_csv('times.csv')

# Group the data by function, bufferSize, and P+K
grouped = df.groupby(['function', 'bufferSize', 'P+K'])

# Plotting and saving each group's plot as a separate JPG file
for group_name, group_data in grouped:
    function, bufferSize, pk = group_name
    plt.figure(figsize=(8, 6))
    plt.bar(group_data['idx'], group_data['time'])
    plt.title(f'Function: {function}, Buffer Size: {bufferSize}, P{pk}+K{pk}')
    plt.xlabel('idx')
    plt.ylabel('time')
    plt.grid(True)

    # Save the plot as a JPG file with a unique name based on the group
    file_name = f'plots/plot_{function}_{bufferSize}_{pk}.jpg'
    plt.savefig(file_name)

    # Display the plot if you want to view it
    plt.show()