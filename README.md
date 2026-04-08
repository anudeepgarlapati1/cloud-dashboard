

1. LIVE PROJECT LINK
   http://localhost:8080 (Local Development)
   


2. PROJECT TITLE
   Multi-Cloud Management Dashboard



                          PROJECT FEATURES


✓ Add, Edit, Delete Cloud Providers (AWS, Azure, GCP)
✓ Start/Stop Virtual Machines
✓ Add Storage to Clouds
✓ Reset Cloud Configuration
✓ Real-time Database Updates (MySQL)
✓ Cost & Storage Analytics Dashboard
✓ Bar Chart - Cost Comparison
✓ Pie Chart - Storage Distribution
✓ Search & Filter Clouds
✓ Sort by Cost/Storage/Name
✓ Dark/Light Mode Toggle
✓ Export Data as CSV
✓ Export Dashboard as PDF
✓ Cost Efficiency Rating (Storage per Dollar)
✓ VM Status Summary (Running/Stopped)
✓ Last Updated Timestamp
✓ Confirmation Dialogs for Safety
✓ Loading Spinners
✓ Responsive Design


                          TECHNOLOGY STACK


Backend:   Spring Boot (Java)
Database:  MySQL
Frontend:  HTML, CSS, JavaScript
Charts:    Chart.js
PDF Export: html2pdf.js
Build Tool: Maven
Version Control: Git & GitHub


                          API ENDPOINTS


GET    /api/clouds            - Get all clouds
GET    /api/clouds/{id}       - Get cloud by ID
POST   /api/clouds            - Add new cloud
PUT    /api/clouds/{id}       - Update cloud
PUT    /api/clouds/{id}/start - Start VM
PUT    /api/clouds/{id}/stop  - Stop VM
PUT    /api/clouds/{id}/storage/{gb} - Add storage
PUT    /api/clouds/{id}/reset - Reset cloud
DELETE /api/clouds/{id}       - Delete cloud
DELETE /api/clouds/all        - Delete all clouds
GET    /api/clouds/analytics  - Get analytics
GET    /api/clouds/export     - Export CSV


