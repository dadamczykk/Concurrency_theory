import pandas as pd
import matplotlib.pyplot as plt

# Load the CSV file into a pandas DataFrame
df = pd.read_csv('times2.csv')

# Group the data by function, bufferSize, and P+K
grouped = df.groupby(['function', 'bufferSize', 'P+K'])

# Plotting and saving each group's plot as a separate JPG file
functions_names = {"get2cond": "2 conditions, consume time",
                   "put2cond": "2 conditions, produce time",
                   "get4condbool": "4 conditions boolean, consume time",
                   "put4condbool": "4 conditions boolean, produce time",
                   "get4condwaiters": "4 conditions hasWaiters(), consume time",
                   "put4condwaiters": "4 conditions hasWaiters(), produce time",
                   "get3lock": "3 lock, consume time",
                   "put3lock": "3 lock, produce time"}
for group_name, group_data in grouped:
    function, bufferSize, pk = group_name
    plt.figure(figsize=(8, 6))
    plt.bar(group_data['idx'], group_data['time'])
    avg_time = group_data['time'].mean()
    
    # Plotting the average line
    plt.axhline(y=avg_time, color='r', linestyle='--', label='Average')
    plt.legend()
    plt.title(f'Function: {functions_names[function]}, Buffer Size: {bufferSize}, P{pk}+K{pk}')
    plt.xlabel('Produce / consume chunk size')
    plt.ylabel('Time [ns]')
    plt.grid(True)
    plt.text(len(group_data['idx']) * 0.1, avg_time*1.05, f'Mean: {avg_time:.2f}', color='black', ha='center')
    

    # Save the plot as a JPG file with a unique name based on the group
    file_name = f'plots2/plot_{function}_{bufferSize}_{pk}_time.jpg'
    plt.savefig(file_name)

    # Display the plot if you want to view it
    # plt.show()
    
    
df = pd.read_csv('executions.csv')

# Group the data by function, bufferSize, and P+K
grouped = df.groupby(['class', 'bufferSize', 'P+K'])

class_names = {    "consumer2condbool": "2 conditions, consumer executions",
                   "producer2condbool": "2 conditions, producer executions",
                   "consumer4condbool": "4 conditions boolean, consumer executions",
                   "producer4condbool": "4 conditions boolean, producer executions",
                   "consumer4condwaiters": "4 conditions hasWaiters(), consumer executions",
                   "producer4condwaiters": "4 conditions hasWaiters(), producer executions",
                   "consumer3lock": "3 lock, consumer executions",
                   "producer3lock": "3 lock, producer executions"}
# Plotting and saving each group's plot as a separate JPG file
for group_name, group_data in grouped:
    function, bufferSize, pk = group_name
    plt.figure(figsize=(8, 6))
    plt.bar(group_data['idx'], group_data['executions'])
    plt.title(f'{class_names[function]} Buffer Size: {bufferSize}, P{pk}+K{pk}')
    avg_executions = group_data['executions'].mean()
    
    # Plotting the average line
    plt.axhline(y=avg_executions, color='r', linestyle='--', label='Average')

    plt.xlabel(f'{function} id')
    plt.ylabel('Time [ns]')
    plt.grid(True)
    plt.legend()
    plt.text(len(group_data['idx']) * 0.1, avg_executions*1.05, f'Mean: {avg_executions:.2f}', color='black', ha='center')
    
    # Save the plot as a JPG file with a unique name based on the group
    file_name = f'plots2/plot_{function}_{bufferSize}_{pk}_executions.jpg'
    plt.savefig(file_name)

    # Display the plot if you want to view it
    # plt.show()