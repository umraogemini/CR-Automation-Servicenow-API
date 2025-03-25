## Jenkins Pipeline for CR Automation -Automates CR creation & approval in Jenkins
pipeline {
    agent any
    environment {
        TF_VAR_servicenow_username = credentials('servicenow-user')
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
