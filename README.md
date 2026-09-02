# ⛽ Petrol Pump Management System

<p align="center">
  <img src="https://img.shields.io/badge/Java-8-orange?style=for-the-badge&logo=java" alt="Java 8">
  <img src="https://img.shields.io/badge/Spring%20Boot-2.7.18-brightgreen?style=for-the-badge&logo=springboot" alt="Spring Boot">
  <img src="https://img.shields.io/badge/Thymeleaf-3.0-green?style=for-the-badge&logo=thymeleaf" alt="Thymeleaf">
  <img src="https://img.shields.io/badge/MySQL-8.0-blue?style=for-the-badge&logo=mysql" alt="MySQL">
  <img src="https://img.shields.io/badge/Bootstrap-5-purple?style=for-the-badge&logo=bootstrap" alt="Bootstrap">
</p>

<p align="center">
  <img src="https://readme-typing-svg.demolab.com?font=Fira+Code&size=25&duration=3000&pause=1000&center=true&vCenter=true&width=700&lines=Petrol+Pump+Management+System;Fuel+Inventory+Management;Sales+%26+Billing;Employee+%26+Customer+Management;Reports+%26+Analytics" alt="Typing Animation">
</p>

<p align="center">
  <b>🚀 A complete web-based solution for modern petrol pump operations</b>
</p>

---

## ✨ Features

<table>
<tr>
<td width="50%">

### 🔐 Authentication
- Secure Login/Logout
- Admin & Staff roles
- BCrypt password encryption
- Role-based authorization

</td>
<td width="50%">

### ⛽ Fuel Management
- Petrol & Diesel management
- Stock tracking
- Price management
- Low-stock alerts

</td>
</tr>

<tr>
<td width="50%">

### 💰 Sales & Billing
- Automatic bill calculation
- Cash / Card / UPI
- Automatic stock deduction
- Customer information

</td>
<td width="50%">

### 📊 Reports
- Sales reports
- Expense reports
- Fuel stock reports
- Profit & loss analysis

</td>
</tr>
</table>

---

## 🏗️ System Workflow

```text
                 👤 USER
                    │
                    ▼
             🔐 LOGIN SYSTEM
                    │
                    ▼
              📊 DASHBOARD
                    │
        ┌───────────┼───────────┐
        ▼           ▼           ▼
     ⛽ FUEL      💰 SALES     👥 USERS
   MANAGEMENT    & BILLING   MANAGEMENT
        │           │           │
        └───────────┼───────────┘
                    ▼
              🗄️ MySQL
                    │
                    ▼
             📈 REPORTS
