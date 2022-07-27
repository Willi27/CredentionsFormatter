pipeline {
    agent any;

    stages {
        stage("Build") {
            steps {
                echo 'Building...'
                sh """
                    mvn clean install -DskipTests=true
                """
            }
        }
        stage("Test") {
            steps {
                echo 'Testing...'
                sh """
                    mvn test -Dsurefire.useFile=false
                """
            }
        }
        stage("SonarQube") {
            steps {
                echo 'SonarQubeScanning...'
                sh """
                    mvn clean verify sonar:sonar \
                      -Dsonar.projectKey=CredentionsFormatter \
                      -Dsonar.host.url=http://localhost:9000 \
                      -Dsonar.login=sqp_3e7dd22f548886f53aa1813e35709fd7eb2fdee4
                """
           }
       }
    }
}
