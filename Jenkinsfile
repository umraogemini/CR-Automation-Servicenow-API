// Jenkins Pipeline for CR Automation - Automates CR creation & approval in Jenkins

pipeline {
    agent any

    parameters {
        booleanParam(name: 'SKIP_CP_CHECK', defaultValue: false, description: 'Skip Change Request Approval Check')
    }

    environment {
        SERVICENOW_INSTANCE = 'https://gemini.service-now.com/servicenow'
        CR_ID = 'CHANGE0012345'
        TF_VAR_servicenow_username = credentials('servicenow-username')
        TF_VAR_servicenow_password = credentials('servicenow-password')
        MINION_API_URL = credentials('minion-api-url')  // If secured
    }

    stages {
        stage('Checkout Code') {
            steps {
                git 'https://github.com/your-repo/terraform-servicenow-cr.git'
            }
        }

        stage('Initialize Terraform') {
            steps {
                sh 'terraform init'
            }
        }

        stage('Plan Terraform') {
            steps {
                sh 'terraform plan -out=tfplan'
            }
        }

        stage('Apply Terraform') {
            steps {
                sh 'terraform apply -auto-approve tfplan'
            }
        }

        stage('Check CR Approval & Notify') {
            when {
                expression { return !params.SKIP_CR_CHECK }
            }
            steps {
                script {
                    def crStatus = sh(
                        script: """curl -s -u "${env.TF_VAR_servicenow_username}:${env.TF_VAR_servicenow_password}" \
                            -X GET "${env.SERVICENOW_INSTANCE}/api/now/table/change_request/${env.CR_ID}" \
                            | jq -r '.result.state'""",
                        returnStdout: true
                    ).trim()

                    if (crStatus == "approved") {
                        emailext(
                            subject: "Change Request ${env.CR_ID} Approved",
                            body: """
                                Hello Team,<br><br>
                                The Change Request <b>${env.CR_ID}</b> has been <b>approved</b>.<br>
                                Please proceed with the deployment.<br><br>
                                Regards,<br>
                                DevOps Team
                            """,
                            mimeType: 'text/html',
                            to: 'uma.rao@noexternalmail.hsbc.com'
                        )
                    } else {
                        echo "CR not approved yet!"
                    }
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}
