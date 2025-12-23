<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.model.User" %>
<%@ page import="java.util.List" %>
<%
    List<User> userList = (List<User>) request.getAttribute("userList");
    String q = (String) request.getAttribute("q");
    if (q == null) q = request.getParameter("q");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>📋 Liste des utilisateurs</title>
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
            padding: 20px;
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
            background: white;
            border-radius: 15px;
            padding: 30px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.2);
        }
        header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
            padding-bottom: 20px;
            border-bottom: 2px solid #eee;
        }
        h1 {
            color: #333;
            display: flex;
            align-items: center;
            gap: 10px;
        }
        .search-box {
            display: flex;
            gap: 10px;
            margin-bottom: 20px;
        }
        input[type="text"] {
            flex: 1;
            padding: 12px 20px;
            border: 2px solid #ddd;
            border-radius: 8px;
            font-size: 16px;
            transition: border 0.3s;
        }
        input[type="text"]:focus {
            outline: none;
            border-color: #667eea;
        }
        button, .btn {
            padding: 12px 24px;
            background: #4CAF50;
            color: white;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            font-size: 16px;
            font-weight: bold;
            text-decoration: none;
            display: inline-block;
            transition: all 0.3s;
        }
        button:hover, .btn:hover {
            background: #45a049;
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
        }
        .btn-add {
            background: #2196F3;
        }
        .btn-add:hover {
            background: #0b7dda;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            box-shadow: 0 5px 15px rgba(0,0,0,0.05);
        }
        th {
            background: #667eea;
            color: white;
            padding: 15px;
            text-align: left;
            font-weight: 600;
        }
        td {
            padding: 15px;
            border-bottom: 1px solid #eee;
            vertical-align: middle;
        }
        tr:hover {
            background: #f8f9fa;
        }
        .actions {
            display: flex;
            gap: 10px;
        }
        .btn-edit {
            background: #FF9800;
            padding: 8px 16px;
            font-size: 14px;
        }
        .btn-delete {
            background: #f44336;
            padding: 8px 16px;
            font-size: 14px;
        }
        .btn-edit:hover {
            background: #e68a00;
        }
        .btn-delete:hover {
            background: #d32f2f;
        }
        .empty-state {
            text-align: center;
            padding: 50px;
            color: #666;
        }
        .empty-state i {
            font-size: 48px;
            margin-bottom: 20px;
            color: #ddd;
        }
        .user-count {
            background: #e3f2fd;
            padding: 10px 20px;
            border-radius: 20px;
            font-weight: bold;
            color: #1976d2;
            margin-left: 10px;
        }
    </style>
</head>
<body>
    <div class="container">
        <header>
            <h1>👥 Liste des Utilisateurs <span class="user-count"><%= userList != null ? userList.size() : 0 %> utilisateur(s)</span></h1>
            <a href="${pageContext.request.contextPath}/users?action=new" class="btn btn-add">➕ Ajouter un utilisateur</a>
        </header>

        <form action="${pageContext.request.contextPath}/users" method="get" class="search-box">
            <input type="hidden" name="action" value="search"/>
            <input type="text" name="q" placeholder="🔍 Rechercher par nom..." value="<%= q != null ? q : "" %>"/>
            <button type="submit">Rechercher</button>
            <% if (q != null && !q.isEmpty()) { %>
                <a href="${pageContext.request.contextPath}/users" class="btn" style="background: #666;">❌ Effacer</a>
            <% } %>
        </form>

        <% if (userList != null && !userList.isEmpty()) { %>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nom</th>
                        <th>Email</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                <%
                    for (User u : userList) {
                %>
                    <tr>
                        <td><strong>#<%= u.getId() %></strong></td>
                        <td><%= u.getNom() %></td>
                        <td><%= u.getEmail() %></td>
                        <td>
                            <div class="actions">
                                <a href="${pageContext.request.contextPath}/users?action=edit&id=<%= u.getId() %>" class="btn btn-edit">✏️ Modifier</a>
                                <a href="${pageContext.request.contextPath}/users?action=delete&id=<%= u.getId() %>" 
                                   class="btn btn-delete" 
                                   onclick="return confirm('⚠️ Êtes-vous sûr de vouloir supprimer <%= u.getNom().replace("'", "\\'") %> ?');">🗑️ Supprimer</a>
                            </div>
                        </td>
                    </tr>
                <%
                    }
                %>
                </tbody>
            </table>
        <% } else { %>
            <div class="empty-state">
                <div style="font-size: 48px; margin-bottom: 20px;">📭</div>
                <h3>Aucun utilisateur trouvé</h3>
                <p><%= (q != null && !q.isEmpty()) ? 
                    "Aucun résultat pour \"" + q + "\"" : 
                    "La liste des utilisateurs est vide" %></p>
                <a href="${pageContext.request.contextPath}/users?action=new" class="btn btn-add" style="margin-top: 20px;">➕ Ajouter le premier utilisateur</a>
            </div>
        <% } %>
        
        <div style="margin-top: 30px; text-align: center;">
            <a href="${pageContext.request.contextPath}/" class="btn" style="background: #666;">🏠 Retour à l'accueil</a>
        </div>
    </div>
</body>
</html>