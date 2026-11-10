# Learning Management System (LMS) 📚

A **desktop learning management system** built with **JavaFX**, with a MySQL backend and built-in real-time chat.

## ✨ Features
- JavaFX UI designed with **FXML and Scene Builder**
- **MySQL** database access through JDBC
- **Real-time chat** between users over a custom **multi-threaded TCP client-server** written in core Java
- **OTP email verification** for signup and password recovery using JavaMail (SMTP)

## 🛠️ Tech stack
Java · JavaFX · FXML · Scene Builder · MySQL (JDBC) · JavaMail · Maven

## 🚀 Running
```bash
git clone https://github.com/Arnob-32116/LMS.git
cd LMS
./mvnw clean javafx:run      # Windows: mvnw.cmd clean javafx:run
```
Create the MySQL database and update the connection settings in the source before running. Start the chat server before opening multiple clients.
