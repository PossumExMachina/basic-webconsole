function fetchData() {
    console.log("Fetching data");
    fetch('/resources')
        .then(response => response.json())
        .then(data => {
            console.log("Received data:", data); // Add this line
            updateUI(data);
        })
        .catch(error => console.error('Error fetching resource data:', error));


    fetch('/resources/availability')
        .then(response => response.json())
        .then(availability => {
            displayResourcePanels(availability);
        })
        .catch(error => console.error('Error fetching resource availability:', error));
}

function updateUI(data) {
    // Update Tomcat status
    const tomcatStatusText = document.getElementById('tomcatStatusText');
    if (data.tomcatState.toString() === "RUNNING") {
        tomcatStatusText.innerHTML = '<p style="color: darkgreen">Tomcat is running.</p>';
    } else {
        tomcatStatusText.innerHTML = '<p style="color: darkred">Tomcat is not running.</p>';
    }


    // Update running applications list
    const applicationsList = document.getElementById('runningAppsList');
    console.log("Applications:", data.applications);
    if (data.applications && data.applications.length > 0) {
        applicationsList.innerHTML = data.applications
            .map(app => {
                const statusStyle = app.state === 'RUNNING' ? 'style="color: darkgreen;"' : 'style="color: darkred;"';
                return `<li>${app.name}, <span ${statusStyle}>Status: ${app.state}</span></span></li>`;
            }).join('');
    } else {
        applicationsList.innerHTML = '<li style="color: darkred">No running applications</li>';
    }


    // Update docker container info
    // Update docker container info
    const dockerContainerList = document.getElementById('dockerContainerList');
    console.log("dockerContainers:", data.dockerContainers);

    if (data.dockerContainers && data.dockerContainers.length > 0) {
        console.log("Processing docker containers");

        let tableHTML = `<table>
                    <tr>
                        <th>Container ID</th>
                        <th>Image</th>
                        <th>Created</th>
                        <th>Status</th>
                        <th>Name</th>
                        <th>Action</th>
                    </tr>`;

        tableHTML += data.dockerContainers
            .map(container => {
                const statusStyle = container.state === 'EXITED' ? 'style="color: darkred;"' : 'style="color: green;"';
                return `<tr>
                <td>${container.containerID}</td>
                <td>${container.image}</td>
                <td>${container.created}</td>
                <td ${statusStyle}>${container.state}</td>
                <td>${container.name}</td>
                <td>
                <button onclick="startContainer('${container.containerID}')">Start</button>
                <button onclick="stopContainer('${container.containerID}')">Stop</button>
                <button onclick="removeContainer('${container.containerID}')">Remove</button>
                </td>
            </tr>`;
            })
            .join('');

        tableHTML += '</table>';
        dockerContainerList.innerHTML = tableHTML;
    } else {
        console.log("No docker containers available");
        dockerContainerList.innerHTML = '<p>No docker data available</p>';
    }







    // Update disk usage
    const diskUsageList = document.getElementById('diskUsageList');
    console.log("diskUsage:", data.diskUsage);

    if (data.diskUsage && data.diskUsage.length > 0) {
        console.log("Processing disk usage");
        let tableHTML = `<table>
                        <tr>
                            <th>File System</th>
                            <th>Size</th>
                            <th>Used</th>
                            <th>Available</th>
                            <th>Capacity</th>
                            <th>Mounted On</th>
                        </tr>`;

        tableHTML += data.diskUsage.map(usage => {
            const capacityStyle = usage.capacity > 95 ? 'style="color: darkred;"' : '';
            return `<tr>
                    <td>${usage.fileSystem}</td>
                    <td>${usage.fileSystemSize}</td>
                    <td>${usage.used}</td>
                    <td>${usage.available}</td>
                    <td ${capacityStyle}>${usage.capacity}%</td>
                    <td>${usage.mountedOn}</td>
                </tr>`;
        }).join('');

        tableHTML += '</table>';
        diskUsageList.innerHTML = tableHTML;
    } else {
        console.log("disk usage not available");
        diskUsageList.innerHTML = '<p>No disk usage data available</p>';
    }


    // Update memory usage
    const freeMemoryList = document.getElementById('freeMemoryList');
    if (data.freeMemory && data.freeMemory.length > 0) {
        console.log("Processing freeMemory");
        let tableHTML = `<table>
                        <tr>
                            <th>Total</th>
                            <th>Used</th>
                            <th>Free</th>
                        </tr>`;

        tableHTML += data.freeMemory.map(memory => {
            return `<tr>
                    <td>${memory.total} MB</td>
                    <td>${memory.used} MB</td>
                    <td>${memory.free} MB</td>
                </tr>`;
        }).join('');

        tableHTML += '</table>';
        freeMemoryList.innerHTML = tableHTML;
    } else {
        console.log("memory usage not available");
        freeMemoryList.innerHTML = '<p>No memory usage data available</p>';
    }

}

document.addEventListener('DOMContentLoaded', function() {
    document.querySelectorAll('.control-button').forEach(function(button) {
        button.addEventListener('click', function() {
            const resourceType = this.getAttribute('data-type');
            const action = this.getAttribute('data-action');

            fetch(`/${resourceType}/${action}/`, { method: 'POST' })
                .then(response => {
                    if (response.ok) {
                        return response.text();
                    } else {
                        throw new Error(`Failed to ${action} ${resourceType}`);
                    }
                })
                .then(data => {
                    alert(data); // Display success message
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert(error.message); // Display error message
                });
        });
    });


    function startContainer(containerID) {
        sendContainerCommand(containerID, 'start');
    }

    function stopContainer(containerID) {
        sendContainerCommand(containerID, 'stop');
    }

    function removeContainer(containerID) {
        sendContainerCommand(containerID, 'remove');
    }

    function sendContainerCommand(containerID, action) {
        fetch(`/${containerID}/${action}/`, { method: 'POST' })
            .then(response => {
                if (response.ok) {
                    return response.text();
                } else {
                    throw new Error(`Failed to ${action} container ${containerID}`);
                }
            })
            .then(data => {
                alert(data); // Display success message
                fetchData(); // Refresh data after action
            })
            .catch(error => {
                console.error('Error:', error);
                alert(error.message); // Display error message
            });
    }

});

function startContainer(containerID) {
    console.log("Starting")
    sendContainerCommand(containerID, 'start');
}

function stopContainer(containerID) {
    console.log("Stopping")
    sendContainerCommand(containerID, 'stop');
}

function removeContainer(containerID) {
    sendContainerCommand(containerID, 'remove');
}

function sendContainerCommand(containerID, action) {
    console.log("Sending container command")
    fetch(`/${containerID}/${action}`, { method: 'POST' })
        .then(response => {
            if (response.ok) {
                console.log("response is ok i guess")
                return response.text();
            } else {
                throw new Error(`Failed to ${action} container ${containerID}`);
            }
        })
        .then(data => {
            alert(data); // Display success message
            fetchData(); // Refresh data after action
        })
        .catch(error => {
            console.error('Error:', error);
            alert(error.message); // Display error message
        });
}

function displayResourcePanels(availability) {
    const dockerPanel = document.getElementById('dockerContainers');
   // const tomcatPanel = document.getElementById('clickable'); // Assuming this is your Tomcat panel

    if (availability.dockerAvailable) {
        dockerPanel.style.display = 'block';
    } else {
        dockerPanel.style.display = 'none';
    }

    // if (availability.tomcatAvailable) {
    //     tomcatPanel.style.display = 'block';
    // } else {
    //     tomcatPanel.style.display = 'none';
    // }
}


// Call fetchData when the page loads
document.addEventListener('DOMContentLoaded', fetchData);
