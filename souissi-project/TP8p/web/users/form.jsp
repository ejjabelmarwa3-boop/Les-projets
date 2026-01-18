<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.model.User" %>
<%
    String action = (String) request.getAttribute("action");
    User user = (User) request.getAttribute("user");
    if (action == null) action = "insert";
    
    String nom = "";
    String email = "";
    int id = 0;
    
    if (user != null) { 
        nom = user.getNom(); 
        email = user.getEmail();
        id = user.getId();
    } else {
        nom = request.getParameter("nom");
        email = request.getParameter("email");
        if (nom == null) nom = "";
        if (email == null) email = "";
    }
    
    String error = (String) request.getAttribute("error");
    String title = "update".equals(action) ? "✏️ Modifier l'utilisateur" : "➕ Ajouter un utilisateur";
    String buttonText = "update".equals(action) ? "Mettre à jour" : "Ajouter";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title><%= title %></title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px;
        }
        .form-container {
            width: 100%;
            max-width: 500px;
            background: white;
            border-radius: 15px;
            padding: 40px;
            box-shadow: 0 15px 35px rgba(0,0,0,0.2);
        }
        h2 {
            color: #333;
            margin-bottom: 30px;
            display: flex;
            align-items: center;
            gap: 10px;
        }
        .error-message {
            background: #ffebee;
            color: #c62828;
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 25px;
            border-left: 4px solid #f44336;
            display: flex;
            align-items: center;
            gap: 10px;
        }
        .form-group {
            margin-bottom: 25px;
        }
        label {
            display: block;
            margin-bottom: 8px;
            font-weight: 600;
            color: #555;
            display: flex;
            align-items: center;
            gap: 8px;
        }
        input[type="text"],
        input[type="email"] {
            width: 100%;
            padding: 14px 18px;
            border: 2px solid #ddd;
            border-radius: 8px;
            font-size: 16px;
            transition: all 0.3s;
        }
        input[type="text"]:focus,
        input[type="email"]:focus {
            outline: none;
            border-color: #667eea;
            box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
        }
        .button-group {
            display: flex;
            gap: 15px;
            margin-top: 35px;
        }
        button[type="submit"] {
            flex: 1;
            padding: 16px;
            background: #4CAF50;
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
            transition: all 0.3s;
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 8px;
        }
        button[type="submit"]:hover {
            background: #45a049;
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
        }
        .btn-cancel {
            flex: 1;
            padding: 16px;
            background: #757575;
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
            transition: all 0.3s;
            text-decoration: none;
            text-align: center;
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 8px;
        }
        .btn-cancel:hover {
            background: #616161;
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
        }
        .required::after {
            content: " *";
            color: #f44336;
        }
        .form-icon {
            color: #667eea;
            font-size: 20px;
        }
    </style>
</head>
<body>
    <div class="form-container">
        <h2><%= title %></h2>
        
        <% if (error != null) { %>
            <div class="error-message">
                <span style="font-size: 20px;">⚠️</span>
                <span><%= error %></span>
            </div>
        <% } %>

        <form action="${pageContext.request.contextPath}/users" method="post">
            <input type="hidden" name="action" value="<%= action %>"/>
            <% if ("update".equals(action)) { %>
                <input type="hidden" name="id" value="<%= id %>"/>
            <% } %>
            
            <div class="form-group">
                <label class="required">
                    <span class="form-icon">👤</span>
                    Nom
                </label>
                <input type="text" name="nom" value="<%= nom %>" required 
                       placeholder="Entrez le nom complet" />
            </div>

            <div class="form-group">
                <label class="required">
                    <span class="form-icon">📧</span>
                    Email
                </label>
                <input type="email" name="email" value="<%= email %>" required 
                       placeholder="exemple@email.com" />
            </div>

            <div class="button-group">
                <button type="submit">
                    <% if ("update".equals(action)) { %>
                        <span>💾</span> <%= buttonText %>
                    <% } else { %>
                        <span>➕</span> <%= buttonText %>
                    <% } %>
                </button>
                <a href="${pageContext.request.contextPath}/users" class="btn-cancel">
                    <span>❌</span> Annuler
                </a>
            </div>
        </form>
    </div>
</body>
</html>
