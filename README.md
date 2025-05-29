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



-----------------------------------------------------------------------------------------------------------------


Create a New Jenkins Pipeline
Open Jenkins and go to Dashboard → New Item.

Enter a name for your pipeline.

Select "Pipeline" and click "OK."

Go to the "Pipeline" section and select "Pipeline script from SCM".

Choose "Git" and enter your GitHub repo URL.

Corrected Version:

As per your suggestions, I have updated the text field on the Terraform side, and it is now reflected in the email notifications as well. I have also ensured that each alert has a different text field.

Please find the screenshot below for your reference.

If any modifications are required on the Terraform side or in the text fields, please let me know so I can make the changes. If everything looks good, I will proceed to deploy the code in the UAT and Production environments.