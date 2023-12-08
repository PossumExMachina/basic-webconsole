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
    <!-- Action confirmation card -->
    <div class="card">
        <p></p>
        <a href="/" class="button">Go back</a>

    </div>


</div>
</body>
</html>
