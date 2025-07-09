// Task 6: Chain and Handle Multiple Promises with Error Recovery
// Execute a sequence of asynchronous tasks and collect results, skipping failed ones

/**
 * Run tasks sequentially and collect results, skipping failed tasks
 * @param {Array} tasks - Array of functions that return promises
 * @return {Promise<Array>} - Promise that resolves to array of successful results
 */
function runTasksSequentially(tasks) {
    // Array to store successful results
    const results = [];
    
    // Start with a resolved promise
    return tasks.reduce((promiseChain, task) => {
        // Chain each task to the promise chain
        return promiseChain.then(() => {
            // Execute the current task
            return task()
                .then(result => {
                    // If successful, add to results
                    results.push(result);
                })
                .catch(error => {
                    // If failed, log the error but continue chain
                    console.error(error);
                    logToOutput(`Error: ${error}`);
                });
        });
    }, Promise.resolve()) // Start with a resolved promise
    .then(() => {
        // When all tasks are done, return the successful results
        return results;
    });
}

// Alternative implementation using async/await
async function runTasksSequentiallyAsync(tasks) {
    const results = [];
    
    for (const task of tasks) {
        try {
            const result = await task();
            results.push(result);
        } catch (error) {
            console.error(error);
            logToOutput(`Error: ${error}`);
        }
    }
    
    return results;
}

// Helper function to log to the output div
function logToOutput(message) {
    const outputDiv = document.getElementById('output');
    const paragraph = document.createElement('p');
    paragraph.textContent = message;
    outputDiv.appendChild(paragraph);
}

// Example tasks for demonstration
const tasks = [
    () => new Promise(resolve => {
        setTimeout(() => {
            logToOutput('Task 1 running...');
            resolve('Task 1 done');
        }, 1000);
    }),
    
    () => new Promise((_, reject) => {
        setTimeout(() => {
            logToOutput('Task 2 running...');
            reject('Task 2 failed');
        }, 1000);
    }),
    
    () => new Promise(resolve => {
        setTimeout(() => {
            logToOutput('Task 3 running...');
            resolve('Task 3 done');
        }, 1000);
    }),
    
    () => new Promise(resolve => {
        setTimeout(() => {
            logToOutput('Task 4 running...');
            resolve('Task 4 done');
        }, 1000);
    })
];

// Event listener for the button
document.getElementById('runTasksBtn').addEventListener('click', function() {
    // Clear the output
    document.getElementById('output').innerHTML = '';
    logToOutput('Starting tasks...');
    
    // Run the tasks sequentially
    runTasksSequentially(tasks)
        .then(results => {
            logToOutput('All tasks completed!');
            logToOutput('Results: ' + JSON.stringify(results));
        });
});
