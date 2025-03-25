# CR-Automation-Servicenow-API

✅ Modular Terraform code using reusable modules
✅ Jenkins & GitHub Actions trigger CR automation
✅ Secure Terraform state storage with S3 backend
✅ ServiceNow → Jira auto-updates when CR is approved
✅ Rollback mechanism if CR is rejected

Would you like to add Slack/MS Teams notifications for CR approvals?

Jenkins Email Notification for CR Approvals

pipeline {
    agent any
    environment {
        CR_ID = sh(script: "terraform output -raw cr_id", returnStdout: true).trim()
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
                    def crStatus = sh(script: "curl -s 'https://your-instance.service-now.com/api/now/table/change_request/${CR_ID}' | jq -r '.result.state'", returnStdout: true).trim()
                    
                    if (crStatus == "approved") {
                        emailext (
                            subject: "CR ${CR_ID} Approved!",
                            body: "Your Change Request ${CR_ID} has been approved.",
                            recipientProviders: [[$class: 'RequesterRecipientProvider']]
                        )
                    }
                }
            }
        }
    }
}


