pipeline {
    agent any
    parameters {
        booleanParam(name: 'SKIP_CP_CHECK', defaultValue: false, description: 'Skip Change Request Approval Check'),
            }
    environment {
        SERVICENOW_INSTANCE = "https://gemini.service-now.com/servicenow"
        TF_VAR_servicenow_username = credentials('SNOW_USER')
        TF_VAR_servicenow_password = credentials('SNOW_PASS')
    MINION_API_URL=credentials('minion-api-url')
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
                        -X GET '${SERVICENOW_INSTANCE}/api/now/table/change_request/${CR_ID}' \
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
                            to: 'uma.rao@noextrnalmail.hsbc.com'
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
