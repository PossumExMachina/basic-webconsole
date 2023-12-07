[#ftl ]
<!DOCTYPE html>
<html>
<head>
    <title>Server Resources Monitoring</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
        }
        .container {
            width: 80%;
            margin: auto;
            overflow: hidden;
        }
        header {
            background: white;
            color: white;
            padding-top: 30px;
            min-height: 70px;
            border-bottom: #e8491d 3px solid;
        }
        header a {
            color: #ffffff;
            text-decoration: none;
            text-transform: uppercase;
            font-size: 16px;
        }
        header ul {
            padding: 0;
            margin: 0;
            list-style: none;
            overflow: hidden;
        }
        header li {
            float: left;
            display: inline;
            padding: 0 20px 0 20px;
        }
        header #branding {
            float: left;
        }
        header #branding h1 {
            margin: 0;
        }
        header nav {
            float: right;
            margin-top: 10px;
        }
        header .highlight, header .current a {
            color: #e8491d;
            font-weight: bold;
        }
        header a:hover {
            color: #ffffff;
            font-weight: bold;
        }
        .card {
            background: #ffffff;
            padding: 20px;
            margin-top: 15px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            border: 1px solid #ddd;
            margin-bottom: 20px; /* Added bottom margin for separation */
        }
        .card h2 {
            margin-top: 0;
        }
        .button {
            display: inline-block;
            padding: 10px 15px;
            background-color: #e8491d;
            color: white;
            text-decoration: none;
            font-weight: bold;
            text-transform: uppercase;
            border-radius: 5px;
            transition: background-color 0.3s;
        }
    </style>
    link rel="stylesheet" href="css/styles.css">

    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css" rel="stylesheet">
</head>
<body>
<header>
    <div class="container">
        <div id="branding">
            <h1 style="color: black">Server Resources Monitoring</h1>
        </div>
        <nav>
            <ul>
                <li class="current"><a href="/resources">Resources</a></li>
            </ul>
        </nav>
    </div>
</header>

<div class="container">
    <!-- Server Information Card -->
    <div class="card">
        <i class="fas fa-cat"></i><h2>Regarding your Tomcat server, I have access to the following data: </h2>
            [#if resourceData.tomcatRunning]
                <p>Tomcat is running, no worries. </p>
            [#else]
                <p style="color: darkred">Tomcat is not running, I sure hope it's supposed to be that way. </p>
            [/#if]
            [#if resourceData.runningApplications?size gt 0]
                <h2>Running Applications</h2>
                <ul>
                    [#list resourceData.runningApplications as app]
                        <li style="color: darkgreen">${app}</li>
                    [/#list]
                </ul>
            [#else]
                <p>No applications are currently running.</p>
            [/#if]

            [#if resourceData.notRunningApplications?size gt 0]
                <h2>These apps are dead: </h2>
                <ul>
                    [#list resourceData.notRunningApplications as app]
                        <li style="color: darkred">${app}</li>
                    [/#list]
                </ul>
            [#else]
                <p style="color: darkgreen">Good job, all your apps are running.</p>
            [/#if]

        <a href="/resources/tomcat" class="button">Go to detail</a>



    </div>

    <!-- Network Activity Card -->
    <div class="card">
        <h2>Network Activity</h2>
        <p>Incoming Traffic: ...</p>
        <p>Outgoing Traffic: ...</p>
        <!-- More network details -->
    </div>

    <!-- Additional cards for other resources -->
</div>
</body>
</html>
