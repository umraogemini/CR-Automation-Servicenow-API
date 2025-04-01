1️⃣ Change ServiceNow Instance, Credentials, and Assignment Group
📌 File: terraform.tfvars

hcl
Copy
Edit
servicenow_instance = "your-instance.service-now.com"
servicenow_username = "your-username"
servicenow_password = "your-password"
assignment_group    = "ITSM Team"
➡️ Modify these values if you have a different ServiceNow instance or assignment group.
❗ Use AWS Secrets Manager or Vault instead of hardcoding credentials.

2️⃣ Modify Change Request Fields
📌 File: modules/servicenow_cr/main.tf

hcl
Copy
Edit
resource "servicenow_record" "change_request" {
  table  = "change_request"
  fields = {
    short_description = var.short_description
    description       = var.description
    category         = var.category   # Change this value (e.g., "Infrastructure")
    risk            = var.risk        # Options: "low", "medium", "high"
    impact          = var.impact      # Options: "low", "medium", "high"
    assignment_group = var.assignment_group
    requested_by    = var.requested_by
    state           = "new"
  }
}
➡️ Modify default values for category, risk, and impact if needed.

3️⃣ Change Module Inputs (Modify Values Passed to the CR Module)
📌 File: main.tf

hcl
Copy
Edit
module "create_cr" {
  source            = "./modules/servicenow_cr"
  short_description = "Automated CR via Terraform"
  description       = "Created for deployment automation"
  category         = "Software"  # Change to "Infrastructure", "Security", etc.
  risk            = "low"        # Change to "medium" or "high"
  impact          = "low"        # Change to "medium" or "high"
  assignment_group = var.assignment_group
  requested_by    = "umrao@example.com"  # Change to requester's email
}
➡️ Modify values for category, risk, impact, and requested_by.

4️⃣ Modify Remote State Storage (Backend Configuration)
📌 File: backend.tf

hcl
Copy
Edit
terraform {
  backend "s3" {
    bucket = "terraform-state-bucket"   # Change bucket name
    key    = "servicenow/terraform.tfstate"  # Change path if needed
    region = "us-east-1"   # Change AWS region
  }
}
➡️ Modify values if using a different AWS region, bucket, or path.

5️⃣ Modify Jenkins Pipeline (Approval Logic & Email Notification)
📌 File: Jenkinsfile

groovy
Copy
Edit
pipeline {
    agent any
    environment {
        TF_VAR_servicenow_username = credentials('servicenow-user')  # Change credential ID in Jenkins
        TF_VAR_servicenow_password = credentials('servicenow-pass')
    }
    stages {
        stage('Terraform Init') {
            steps {
                sh 'terraform init'
            }
        }
        stage('Terraform Apply') {
            steps {
                sh 'terraform apply -auto-approve'
            }
        }
        stage('Check CR Approval') {
            steps {
                script {
                    def crState = sh(script: 'terraform output -raw cr_id', returnStdout: true).trim()
                    if (crState == 'approved') {
                        echo "CR Approved! Proceeding with Deployment..."
                    } else {
                        echo "CR Not Approved! Stopping Deployment..."
                        error("Change Request not approved!")
                    }
                }
            }
        }
    }
}
➡️ Modify credential IDs, email recipients, or notification settings if needed.

6️⃣ Modify GitHub Actions Workflow (Trigger CR Creation on PRs)
📌 File: .github/workflows/cr.yml

yaml
Copy
Edit
name: Create CR on PR
on:
  pull_request:
    types: [opened]

jobs:
  create-cr:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout Repo
        uses: actions/checkout@v3

      - name: Setup Terraform
        uses: hashicorp/setup-terraform@v2

      - name: Terraform Init & Apply
        env:
          TF_VAR_servicenow_username: ${{ secrets.SNOW_USER }}  # Change secret name
          TF_VAR_servicenow_password: ${{ secrets.SNOW_PASS }}
        run: |
          terraform init
          terraform apply -auto-approve
➡️ Modify GitHub Secrets (SNOW_USER, SNOW_PASS) or adjust trigger conditions.

🚀 Summary: Where to Change Values
What You Want to Change	File	Field to Modify
ServiceNow instance, username, password	terraform.tfvars	servicenow_instance, servicenow_username, servicenow_password
Assignment group for CR	terraform.tfvars, modules/servicenow_cr/main.tf	assignment_group
CR category, risk, impact	main.tf, modules/servicenow_cr/main.tf	category, risk, impact
Requested by (email)	main.tf	requested_by
Terraform state backend	backend.tf	bucket, key, region
Jenkins credentials	Jenkinsfile	credentials('servicenow-user'), credentials('servicenow-pass')
GitHub Secrets for ServiceNow API	.github/workflows/cr.yml	SNOW_USER, SNOW_PASS
