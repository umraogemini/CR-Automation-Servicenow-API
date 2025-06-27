---

🔧 General DevOps Questions

1. What is DevOps? How is it different from Agile?


2. What are the key benefits of DevOps?
A.  Faster Delivery and Deployment,Increased Deployment Frequency,Better Stability and Reliability,
Continuous Testing & Integration,Cost Efficiency,Scalability and Flexibility and Better Customer Experience

3. What are the most important DevOps tools you have worked with?
A. Version control :- Github
    CI/CD :- Jenkins
    Containerization :- Dockers, Kubernets
    Infrastruture as code :- Terraform
    Monitoring & Logging :- Prometheus and Grafana, GCP Monitoring

4. What is CI/CD? How have you implemented it?
A.  CI/CD stands for:

CI (Continuous Integration):
The practice of automatically building and testing code whenever developers commit changes to a shared repository. It ensures early detection of bugs and keeps the codebase stable.

CD (Continuous Delivery / Continuous Deployment):

Continuous Delivery: Automatically prepares code changes for release to staging or production (manual approval for production).

Continuous Deployment: Fully automates the entire release process to production, with no manual steps.

CI/CD Pipeline – Real-World Implementation Steps
🟦 1. Continuous Integration (CI):
Developers push code to GitHub.

Jenkins is triggered via webhook or polling.

Pipeline stages:

Checkout Code

Build (e.g., Maven/Gradle/npm)

Unit Tests (e.g., JUnit, pytest)

Static Code Analysis (e.g., SonarQube)

Package Artifact (JAR, Docker image)

Output: Docker image pushed to Docker Hub or Nexus.

5. What is the difference between continuous delivery and continuous deployment?




---

🛠️ Tools-Specific Questions

Jenkins

1. How does Jenkins work?


2. How do you set up a Jenkins pipeline?


3. What are Jenkins agents and nodes?


4. How do you manage credentials in Jenkins?



Git & GitHub/GitLab

1. What is the difference between git fetch and git pull?


2. How do you resolve a merge conflict?


3. What is a Git rebase?



Docker

1. What is Docker? What are its advantages?


2. What is the difference between Docker image and container?


3. How do you manage persistent data in Docker?


4. How do you write a Dockerfile?



Kubernetes

1. What is Kubernetes? How does it work?


2. What is the difference between a Pod, ReplicaSet, and Deployment?


3. How do you perform rolling updates in Kubernetes?


4. How do you debug a failed Pod?



Terraform

1. What is Terraform? Why is it used in DevOps?


2. What is the difference between Terraform plan, apply, and destroy?


3. How do you manage state in Terraform?


4. How do you handle sensitive variables?



Ansible

1. What is Ansible? How does it work?


2. What is an Ansible playbook?


3. How do you manage inventory in Ansible?


4. How do you handle secrets in Ansible?




---

☁️ Cloud (AWS/GCP/Azure)

1. What services have you used in AWS/GCP/Azure?


2. How do you provision infrastructure using IaC on the cloud?


3. How do you manage security groups and IAM roles?




---

🔐 Security and Monitoring

1. How do you secure CI/CD pipelines?


2. What tools do you use for logging and monitoring (e.g., Prometheus, Grafana, ELK)?


3. How do you implement alerting for failures?




---

🧪 Practical / Scenario-Based

1. A deployment fails. How do you troubleshoot?


2. How would you roll back a release?


3. Your Jenkins job is running slow. How would you optimize it?
