##### To pipeline cloneing and install & into nexus & into sonar
pipeline {
    agent any

    environment {
        NEXUS_VERSION = "nexus3"
        NEXUS_REPOSITORY = "REPO NAME"
        NEXUS-URL = "IP WITH PORT"
        NEXUS_PROTOCOL = "HTTP"
        NEXUS_CREDENTIAL_ID = "LOGINID"
        ARTVERSION = "${env.BUILD.ID}"
        scannerHome = tool 'sonar4.4.02170'
        NEXUS_REPOGRP_ID = "REPO GROUP ID"
    } 
    stages {
        stage ('clone') {
            steps {
                branch: 'master', url: 'git url'
            }
        }
        stage ('build') {
            steps {
                sh 'mvn install'
            }
        }
        stage ('test') {
            steps {
                sh 'mvn test'
            }
            post {
                success {
                    echo "now archiving artifacts"
                    archiveAritifacts artifacts: '**/target/*.war'
                }
            }
            }
        }
        stage ('integration test') {
            steps {
                sh 'mvn verify -DskipUnitTests'
            }
        }
        stage ('code analysis with checkstyle') {
            stpes {
                sh 'mvn checkstyle:checkstyle'
            }
            post{
                success {
                    echo "generated code analysis results"
                }
            }
        }
        stage ('code analysis with sonar') {
            environment {
                scannerHome = tool 'myscanner4'
                        }          
            steps {
                withSonarQubeEnv('sonar-pro') {
                    sh ''' ${scannerHome}/bin/sonar-scanner -Dsonar.projectKey= <projectname>
                   -Dsonar.projectname=       \
                   -Dsonar.projectVersion=    \
                   -Dsonar.sources=src/ \
                   -Dsonar.java.binaries= \
                   -Dsonar.junit.reportspath= \
                   -Dsonar.jacoco.reportspath= \
                   -Dsonar.java.chekcstyle.reportpaths=    '''
                }
            }
            timeout(time :10, units: 'MINUTES') {
                waitForQualityGate abortPipeline: true
            }
        }
   }
}

############# For Multibranch pipeline will trigger only one specific branch
pipeline {
    agent any

    stages {
        stage ('clone stage') {
            step {
                git url clone
            }
        }
        stage ('build stage') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage ('upload to nexus') {
            when {
               branch "release"
            }
            steps {
                sh nexus pipeline syntax have to provide here
            }
        }
    }
}    Then save ----->> Multibranch pipeline ----->>> scan Multibranch pipeline

####### ALL STAGES ARE TRIGGER PARELLAY
pipeline {
    agent any
    stages {
        stage ('parellel trigger') {
            failFast true
            parellel {
                stage ('stage1') {
                    steps {
                        clone git URL
                    }
                }
                stage ('stage2') {
                    steps {
                        sh 'mvn pacakge'
                    }
                }
                stage ('buld') {
                    steps {
                        nexus 
                    }
                }
            }
        }
    }
}



