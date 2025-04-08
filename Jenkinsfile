// File: Jenkinsfile
pipeline {
    agent any

    parameters {
        booleanParam(name: 'SKIP_CP_CHECK', defaultValue: false, description: 'Skip Change Request Approval Check')
    }

    environment {
        SERVICENOW_INSTANCE = "https://gemini.service-now.com/servicenow"
        TF_VAR_servicenow_username = credentials('SNOW_USER')
        TF_VAR_servicenow_password = credentials('SNOW_PASS')
    }

    stages {
        stage('Terraform Apply') {
            steps {
                script {
                    sh 'terraform apply -auto-approve'
                    env.CR_ID = sh(script: "terraform output -raw cr_id", returnStdout: true).trim()
                    echo "Captured CR ID: ${env.CR_ID}"
                }
            }
        }

        stage('Check CR Approval & Notify') {
            when {
                expression { return !params.SKIP_CP_CHECK }
            }
            steps {
                script {
                    def crStatus = sh(
                        script: "curl -s -u '${env.TF_VAR_servicenow_username}:${env.TF_VAR_servicenow_password}' " +
                                "-X GET '${SERVICENOW_INSTANCE}/api/now/table/change_request/${env.CR_ID}' " +
                                "| jq -r '.result.state'",
                        returnStdout: true
                    ).trim()

                    echo "CR Status: ${crStatus}"

                    if (crStatus == "approved") {
                        emailext (
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
