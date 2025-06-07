<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Spring Bean Scopes Demo</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            line-height: 1.6;
        }
        h1, h2, h3 {
            color: #333;
        }
        .scope-section {
            margin-bottom: 30px;
            padding: 15px;
            border: 1px solid #ddd;
            border-radius: 5px;
        }
        .bean-info {
            background-color: #f8f9fa;
            padding: 10px;
            margin-bottom: 10px;
            border-left: 4px solid #007bff;
        }
        .refresh-link {
            display: inline-block;
            margin: 20px 0;
            padding: 10px 15px;
            background-color: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 4px;
        }
        .scope-explanation {
            font-style: italic;
            color: #666;
        }
    </style>
</head>
<body>
    <h1>Spring Bean Scopes Demonstration</h1>
    <p>This page demonstrates different bean scopes available in Spring. Refresh the page to see which beans change.</p>

    <a href="<c:url value='/' />" class="refresh-link">Refresh Page</a>

    <div class="scope-section">
        <h2>XML-Based Configuration</h2>

        <div class="bean-info">
            <h3>Singleton Scope</h3>
            <p class="scope-explanation">A single instance is created and shared across the entire application.</p>
            <p><strong>Bean ID:</strong> ${singletonBean.id}</p>
            <p><strong>Created:</strong> ${singletonBean.creationTime}</p>
            <p><strong>Scope:</strong> ${singletonBean.scopeName}</p>
        </div>

        <div class="bean-info">
            <h3>Prototype Scope (Controller Autowired)</h3>
            <p class="scope-explanation">A new instance is created each time the bean is requested.</p>
            <p><strong>Bean ID:</strong> ${prototypeBean.id}</p>
            <p><strong>Created:</strong> ${prototypeBean.creationTime}</p>
            <p><strong>Scope:</strong> ${prototypeBean.scopeName}</p>
        </div>

        <div class="bean-info">
            <h3>Prototype Scope (New Instance)</h3>
            <p class="scope-explanation">Compare with autowired instance to see difference.</p>
            <p><strong>Bean ID:</strong> ${newPrototypeInstance.id}</p>
            <p><strong>Created:</strong> ${newPrototypeInstance.creationTime}</p>
            <p><strong>Scope:</strong> ${newPrototypeInstance.scopeName}</p>
        </div>

        <div class="bean-info">
            <h3>Request Scope</h3>
            <p class="scope-explanation">A new instance is created for each HTTP request.</p>
            <p><strong>Bean ID:</strong> ${requestBean.id}</p>
            <p><strong>Created:</strong> ${requestBean.creationTime}</p>
            <p><strong>Scope:</strong> ${requestBean.scopeName}</p>
        </div>

        <div class="bean-info">
            <h3>Session Scope</h3>
            <p class="scope-explanation">A single instance is created and maintained for the user's session.</p>
            <p><strong>Bean ID:</strong> ${sessionBean.id}</p>
            <p><strong>Created:</strong> ${sessionBean.creationTime}</p>
            <p><strong>Scope:</strong> ${sessionBean.scopeName}</p>
        </div>

        <div class="bean-info">
            <h3>Global Session Scope</h3>
            <p class="scope-explanation">A single instance across all sessions in a portlet application.</p>
            <p><strong>Bean ID:</strong> ${globalSessionBean.id}</p>
            <p><strong>Created:</strong> ${globalSessionBean.creationTime}</p>
            <p><strong>Scope:</strong> ${globalSessionBean.scopeName}</p>
        </div>
    </div>

    <div class="scope-section">
        <h2>Annotation-Based Configuration</h2>

        <div class="bean-info">
            <h3>Singleton Scope (Annotated)</h3>
            <p><strong>Bean ID:</strong> ${singletonAnnotatedBean.id}</p>
            <p><strong>Created:</strong> ${singletonAnnotatedBean.creationTime}</p>
            <p><strong>Type:</strong> ${singletonAnnotatedBean.type}</p>
        </div>

        <div class="bean-info">
            <h3>Prototype Scope (Annotated - Controller Autowired)</h3>
            <p><strong>Bean ID:</strong> ${prototypeAnnotatedBean.id}</p>
            <p><strong>Created:</strong> ${prototypeAnnotatedBean.creationTime}</p>
            <p><strong>Type:</strong> ${prototypeAnnotatedBean.type}</p>
        </div>

        <div class="bean-info">
            <h3>Prototype Scope (Annotated - New Instance)</h3>
            <p><strong>Bean ID:</strong> ${newAnnotatedPrototypeInstance.id}</p>
            <p><strong>Created:</strong> ${newAnnotatedPrototypeInstance.creationTime}</p>
            <p><strong>Type:</strong> ${newAnnotatedPrototypeInstance.type}</p>
        </div>

        <div class="bean-info">
            <h3>Request Scope (Annotated)</h3>
            <p><strong>Bean ID:</strong> ${requestAnnotatedBean.id}</p>
            <p><strong>Created:</strong> ${requestAnnotatedBean.creationTime}</p>
            <p><strong>Type:</strong> ${requestAnnotatedBean.type}</p>
        </div>

        <div class="bean-info">
            <h3>Session Scope (Annotated)</h3>
            <p><strong>Bean ID:</strong> ${sessionAnnotatedBean.id}</p>
            <p><strong>Created:</strong> ${sessionAnnotatedBean.creationTime}</p>
            <p><strong>Type:</strong> ${sessionAnnotatedBean.type}</p>
        </div>

        <div class="bean-info">
            <h3>Global Session Scope (Annotated)</h3>
            <p><strong>Bean ID:</strong> ${globalSessionAnnotatedBean.id}</p>
            <p><strong>Created:</strong> ${globalSessionAnnotatedBean.creationTime}</p>
            <p><strong>Type:</strong> ${globalSessionAnnotatedBean.type}</p>
        </div>
    </div>

    <div class="scope-section">
        <h2>How to Test Different Scopes</h2>
        <ol>
            <li><strong>Singleton:</strong> Refresh the page - ID and creation time will remain the same.</li>
            <li><strong>Prototype:</strong> Refresh the page - both autowired and new instances will have new IDs and creation times.</li>
            <li><strong>Request:</strong> Refresh the page - new ID and creation time for each request.</li>
            <li><strong>Session:</strong> Refresh the page - ID and creation time will remain the same within your session. Close browser and reopen to get a new instance.</li>
            <li><strong>Global Session:</strong> Similar to session scope but shared across portlets in a portlet application.</li>
        </ol>
    </div>

    <a href="<c:url value='/' />" class="refresh-link">Refresh Page</a>
</body>
</html>
