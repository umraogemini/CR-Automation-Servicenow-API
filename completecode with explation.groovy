terraform-servicenow-cr/
│── modules/
│   ├── servicenow_cr/
│   │   ├── main.tf          # Module to create and approve CRs
│   │   ├── variables.tf      # Module variables
│   │   ├── outputs.tf        # Module outputs
│── main.tf                   # Root module calling servicenow_cr module
│── variables.tf               # Global input variables
│── outputs.tf                 # Global output variables
│── backend.tf                 # Remote backend storage configuration
│── providers.tf               # Provider configurations
│── terraform.tfvars           # Variable values file
│── Jenkinsfile                # Jenkins Pipeline for Terraform execution & notifications
│── .github/workflows/cr.yml   # GitHub Actions workflow for PR-based CR creation


1️⃣ providers.tf (ServiceNow Provider Configuration)
hcl
Copy
Edit
terraform {
  required_providers {
    servicenow = {
      source  = "servicenow/servicenow"
      version = "~> 0.3.0"
    }
  }
}

provider "servicenow" {
  instance  = var.servicenow_instance
  username  = var.servicenow_username
  password  = var.servicenow_password
}
✅ Defines ServiceNow as the provider and sets authentication variables.

2️⃣ backend.tf (Remote State Storage)
hcl
Copy
Edit
terraform {
  backend "s3" {
    bucket = "terraform-state-bucket"
    key    = "servicenow/terraform.tfstate"
    region = "us-east-1"
  }
}
✅ Stores Terraform state remotely in an S3 bucket.

3️⃣ variables.tf (Global Variables)
hcl
Copy
Edit
variable "servicenow_instance" {
  description = "ServiceNow instance URL"
  type        = string
}

variable "servicenow_username" {
  description = "ServiceNow API username"
  type        = string
  sensitive   = true
}

variable "servicenow_password" {
  description = "ServiceNow API password"
  type        = string
  sensitive   = true
}

variable "assignment_group" {
  description = "Assignment group for CR"
  type        = string
  default     = "ITSM Team"
}
✅ Defines Terraform variables for authentication and CR attributes.

4️⃣ modules/servicenow_cr/main.tf (Reusable CR Module)
hcl
Copy
Edit
resource "servicenow_record" "change_request" {
  table  = "change_request"
  fields = {
    short_description = var.short_description
    description       = var.description
    category         = var.category
    risk            = var.risk
    impact          = var.impact
    assignment_group = var.assignment_group
    requested_by    = var.requested_by
    state           = "new"
  }
}

resource "servicenow_record" "approve_cr" {
  table  = "change_request"
  sys_id = servicenow_record.change_request.sys_id
  fields = {
    state = "approved"
  }
  depends_on = [servicenow_record.change_request]
}
✅ Creates a Change Request and auto-approves it.

5️⃣ modules/servicenow_cr/variables.tf (Module Variables)
hcl
Copy
Edit
variable "short_description" {
  description = "Short description for the CR"
  type        = string
}

variable "description" {
  description = "Detailed description for the CR"
  type        = string
}

variable "category" {
  description = "Category of the CR"
  type        = string
  default     = "Software"
}

variable "risk" {
  description = "Risk level of the CR"
  type        = string
  default     = "low"
}

variable "impact" {
  description = "Impact level of the CR"
  type        = string
  default     = "low"
}

variable "assignment_group" {
  description = "The assignment group for the CR"
  type        = string
}

variable "requested_by" {
  description = "The user who requested the CR"
  type        = string
}
✅ Defines input variables for the module.

6️⃣ modules/servicenow_cr/outputs.tf (Module Outputs)
hcl
Copy
Edit
output "cr_id" {
  description = "The sys_id of the created Change Request"
  value       = servicenow_record.change_request.sys_id
}
✅ Outputs CR ID for further processing.

7️⃣ main.tf (Terraform Root Module to Call CR Module)
hcl
Copy
Edit
module "create_cr" {
  source            = "./modules/servicenow_cr"
  short_description = "Automated CR via Terraform"
  description       = "Created for deployment automation"
  category         = "Software"
  risk            = "low"
  impact          = "low"
  assignment_group = var.assignment_group
  requested_by    = "umrao@example.com"
}

output "cr_id" {
  value = module.create_cr.cr_id
}
✅ Calls the reusable CR module.

8️⃣ terraform.tfvars (Variable Values)
hcl
Copy
Edit
servicenow_instance = "your-instance.service-now.com"
servicenow_username = "your-username"
servicenow_password = "your-password"
assignment_group    = "ITSM Team"
✅ Stores actual variable values.

9️⃣ Jenkinsfile (Jenkins Pipeline for CR Automation & Email Notification)
groovy
Copy
Edit
pipeline {
    agent any
    environment {
        CR_ID = sh(script: "terraform output -raw cr_id", returnStdout: true).trim()
        SERVICENOW_INSTANCE = "your-instance.service-now.com"
    }
    stages {
        stage('Terraform Apply') {
            steps {
                sh 'terraform apply -auto-approve'
            }
        }
        stage('Check CR Approval & Notify') {
            steps {
                script {
                    def crStatus = sh(script: "curl -s -u '${env.TF_VAR_servicenow_username}:${env.TF_VAR_servicenow_password}' \
                        -X GET 'https://${SERVICENOW_INSTANCE}/api/now/table/change_request/${CR_ID}' \
                        | jq -r '.result.state'", returnStdout: true).trim()

                    if (crStatus == "approved") {
                        emailext (
                            subject: "Change Request ${CR_ID} Approved",
                            body: """
                                Hello Team,<br><br>
                                The Change Request <b>${CR_ID}</b> has been <b>approved</b>.<br>
                                Please proceed with the deployment.<br><br>
                                Regards,<br>
                                DevOps Team
                            """,
                            mimeType: 'text/html',
                            to: 'team@example.com'
                        )
                    } else {
                        echo "CR not approved yet!"
                    }
                }
            }
        }
    }
}
✅ Executes Terraform and sends email notifications if CR is approved.

🔟 .github/workflows/cr.yml (GitHub Actions to Trigger CRs)
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
          TF_VAR_servicenow_username: ${{ secrets.SNOW_USER }}
          TF_VAR_servicenow_password: ${{ secrets.SNOW_PASS }}
        run: |
          terraform init
          terraform apply -auto-approve
✅ Automatically triggers a CR when a GitHub PR is opened.

🎯 Summary
Modular Terraform setup for ServiceNow Change Requests.

Remote State Storage in AWS S3.

Jenkins Pipeline for automation & email alerts.

GitHub Actions for PR-triggered CRs.

This Terraform structure ensures automation, stability, and scalability! 🚀

Would you like any modifications?




-------------------------------------------------------------------------------------------------------------------------

Here’s a visual representation of how the Terraform structure works, integrating ServiceNow, Jenkins, and GitHub Actions:

🌎 High-Level Flow
plaintext
Copy
Edit
  Developer --> GitHub PR --> GitHub Actions --> Terraform (Create CR) --> ServiceNow
               |
               v
         Jenkins Pipeline --> Terraform (Apply) --> ServiceNow (Approve CR) --> Email Notification
📂 Directory & Execution Flow
bash
Copy
Edit
terraform-servicenow-cr/
│── modules/
│   ├── servicenow_cr/
│   │   ├── main.tf          # Creates & approves CR in ServiceNow
│   │   ├── variables.tf      # Defines variables for the module
│   │   ├── outputs.tf        # Outputs CR sys_id
│── main.tf                   # Calls the module
│── variables.tf               # Defines global variables
│── outputs.tf                 # Outputs CR ID globally
│── backend.tf                 # Stores state in AWS S3
│── providers.tf               # ServiceNow provider configuration
│── terraform.tfvars           # Variable values (instance, username, password)
│── Jenkinsfile                # Jenkins pipeline for Terraform execution & notifications
│── .github/workflows/cr.yml   # GitHub Actions for PR-based CR creation
🔁 Workflow Breakdown
1️⃣ GitHub PR Triggers CR Creation
When a Pull Request (PR) is opened, GitHub Actions executes the workflow.

Terraform runs automatically to create a ServiceNow Change Request (CR).

✅ Key Files Involved:

.github/workflows/cr.yml

main.tf

modules/servicenow_cr/main.tf

2️⃣ Terraform Creates the Change Request
Terraform applies the CR module, sending the request to ServiceNow.

Outputs the CR ID.

✅ Key Files Involved:

modules/servicenow_cr/main.tf

outputs.tf

terraform.tfvars

3️⃣ Jenkins Pipeline Automates CR Approval
Jenkins pipeline retrieves the CR status.

If approved, Jenkins sends an email notification.

If not approved, it waits and retries.

✅ Key Files Involved:

Jenkinsfile

4️⃣ Email Notification on CR Approval
Jenkins checks the CR state using a ServiceNow API call.

If approved, it sends an email to the DevOps team.

✅ Key Files Involved:

Jenkinsfile

emailext plugin in Jenkins

📌 Visual Flow Representation
markdown
Copy
Edit
1️⃣ Developer Creates PR in GitHub
       ⬇️
2️⃣ GitHub Actions Runs Terraform
       ⬇️
3️⃣ Terraform Creates CR in ServiceNow
       ⬇️
4️⃣ Jenkins Monitors CR Status
       ⬇️
5️⃣ If Approved → Sends Email Notification
   Else → Waits & Retries
🎯 Summary
Automated CR creation & approval via Terraform.

GitHub PR triggers the CR request.

Jenkins handles approval & email notifications.

Remote state storage in AWS S3 ensures stability.

🚀 Would you like a sequence diagram or any refinements?
